import glob
import pandas as pd
import datetime
import requests
import json
from collections import Counter

import constants
from api_key import places_API_key

make_request = True

def read_all_csv_in_dir(path):
  all_files = glob.glob(path + "/*.csv")
  frame = pd.DataFrame()
  list_ = []
  for file in all_files:
    df = pd.read_csv(file, index_col=None, header=0)
    list_.append(df)
  frame = pd.concat(list_)
  return frame

def discover_launcher(app_name):
  if app_name in constants.launcher_types:
    return constants.launcher_string
  return app_name

def get_time(timestamp):
  return datetime.datetime.fromtimestamp(timestamp/1000).strftime('%Y-%m-%d %H:%M:%S')

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
    loc_type = req.json()['results'][0]['types'][0]
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

def main():
  # df = read_all_csv_in_dir("./data")
  df = pd.read_csv("./data/12-Jun-2018.csv")
  print("Total number of features:", len(constants.cols))
  print("Total number of rows", df.shape[0])
  df.columns = constants.cols

  cached_loc_types = {}

  df['timestamp'] = df['timestamp'].apply(get_time)

  df['activity_type'] = df['activity_type'].apply(lambda n: constants.int2activity[n])

  df['brightness_level'] = df['brightness_level'].apply(lambda x: x / 40000)

  df['app_name'] = df['app_name'].apply(discover_launcher)

  df['location_type'] = df.apply(lambda row: \
      get_location_type(row['lat'], row['long'], cached_loc_types), axis=1)

  # TODO: Get a better estimate of what implies a user is outside
  df['is_outside'] = df['brightness_level'].apply(lambda level: True if level < 0.5 else False)

  apply_most_common_loc_type(df)

  df['location_type'] = df.apply(lambda row: \
      'moving' if row['activity_type'] != "STILL" else row['location_type'], axis=1)

  df.to_csv("./data/concat_data.csv", index=False)

main()
