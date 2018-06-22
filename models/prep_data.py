import glob
import pandas as pd
from datetime import datetime
import requests
import json
from collections import Counter

import constants
from api_key import places_API_key
from lat_lon_handler import get_lat_lon_distance

make_request = False
user = constants.user

def read_all_csv_in_dir(path):
  all_files = glob.glob(path + "/*.csv")
  frame = pd.DataFrame()
  df_list = []
  for file in all_files:
    df = pd.read_csv(file, index_col=None, header=0)
    df_list.append(df)
    df.columns = constants.cols
  frame = pd.concat(df_list)
  return frame

def discover_launcher(app_name):
  if app_name in constants.launcher_types:
    return constants.launcher_string
  return app_name

def get_time(timestamp):
  return datetime.fromtimestamp(timestamp/1000).strftime(constants.timestamp_format)

def get_closest_loc_type(res_list, lat1, lon1):
  closest_idx = 0
  closest_dist = 10000
  for i, res in enumerate(res_list):
    loc = res['geometry']['location']
    lat2, lon2 = loc['lat'], loc['lng']
    dist = get_lat_lon_distance(lat1, lon1, lat2, lon2)
    if dist < closest_dist:
      closest_dist = dist
      closest_idx = i

  return res_list[closest_idx]['types'][0]

def get_location_type(lat, long, cached):
  if lat == 0 and long == 0:
    print("Lat long were both 0")
    return "NA"

  if (lat, long) in cached:
    return cached[(lat, long)]

  if not make_request:
    return "NO REQUEST"

  print("Making request")
  req = requests.get("https://maps.googleapis.com/maps/api/place/nearbysearch/json?parameters",\
      params = {
        'key' : places_API_key,
        'location' : "{},{}".format(lat, long),
        'radius' : "{}.0".format(constants.radius),
        })

  try:
    loc_type = get_closest_loc_type(req.json()['results'], lat, long)
  except IndexError:
    loc_type = "NA"

  cached[(lat, long)] = loc_type

  return loc_type

def apply_most_common_loc_type(df):
  counter = Counter()
  for i, row in df.iterrows():
    lat, long = row.lat, row.long
    counter[(lat, long)] += 1

  most_freq_loc = counter.most_common(1)[0][0]
  print(most_freq_loc)

  # For now we assume that the most frequent location is home
  df['location_type'] = df.apply(lambda row: \
      "home" if (row.lat, row.long) == most_freq_loc else row.location_type, axis=1)

def apply_time_cluster(df):
  times = {}
  timeslot = 0
  prev_timestamp = datetime.strptime(df.iloc[0].timestamp, constants.timestamp_format)

  for i, row in df.iterrows():
    timestamp = datetime.strptime(row.timestamp, constants.timestamp_format)
    time_diff = timestamp - prev_timestamp
    time_diff_mins = int(round(time_diff.total_seconds() / 60))

    if time_diff_mins > constants.time_cluster_interval:
      timeslot += 1

    times[row.timestamp] = timeslot
    prev_timestamp = timestamp

  df['session_nr'] = df['timestamp'].apply(lambda stamp: times[stamp])

def main():
  df = read_all_csv_in_dir("./data/{}".format(user))
  print("Total number of features:", len(constants.cols))
  print("Total number of rows", df.shape[0])
  df.columns = constants.cols
  df = df.drop_duplicates()
  df = df.sort_values(by=['timestamp']) # Make sure the data is chronological

  cached_loc_types = {}

  df['timestamp'] = df['timestamp'].apply(get_time)

  df['activity_type'] = df['activity_type'].apply(lambda n: constants.int2activity[n])

  df['brightness_level'] = df['brightness_level'].apply(lambda x: x / 40000)

  df['app_name'] = df['app_name'].apply(discover_launcher)

  apply_time_cluster(df)

  df.to_csv("./data/{}/prepared_data/full_data.csv".format(user), index=False)

main()
