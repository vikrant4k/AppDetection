import pandas as pd
import constants
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.lines import Line2D

from collections import Counter, defaultdict
from datetime import datetime

from dbscan import dbscan_location_cluster

draw_plot = True
num_timeslots = 8
user = constants.user
cluster_km_distance = 0.5

def get_app_times(df):
  """
  Returns a dictionary for each app, and a counter for which hour the app was used
  """
  app_times = defaultdict(Counter)

  for i, row in df.iterrows():
    app_times[row.app_name][datetime.strptime(row.timestamp, \
        constants.timestamp_format).hour] += 1

  return app_times

def get_app_activeness(df, num_timeslots):
  activeness = {}

  S = num_timeslots # Number of days
  F_ai = defaultdict(Counter)# [app][slot]
  F_i = Counter()
  timeslot = 1

  prev_day = datetime.strptime(df.iloc[0].timestamp, constants.timestamp_format).day
  for i, row in df.iterrows():
    curr_day = datetime.strptime(row.timestamp, constants.timestamp_format).day
    if curr_day != prev_day:
      timeslot += 1
    prev_day = curr_day

    if row.app_name != constants.launcher_string: # We don't count the launcher string
      F_ai[row.app_name][timeslot] += 1
      F_i[timeslot] += 1

  app_names = df['app_name'].unique()
  app_names = np.delete(app_names, np.where(app_names == constants.launcher_string)) # We ignore the launcher

  for app_name in app_names:
    activeness[app_name] = sum([F_ai[app_name][i]/F_i[i] for i in range(1, S+1)]) / S

  return activeness

def plot_histogram(labels, values, title, xlabel, ylabel):
  indexes = np.arange(len(labels))
  width = 1

  plt.bar(indexes, values, width)
  plt.xticks(indexes + width * 0.5, labels)
  plt.xlabel(xlabel)
  plt.ylabel(ylabel)
  plt.title(title)
  plt.show()

def plot_activeness(activeness):
  sorted_values = sorted(activeness.items(), key=lambda tup: tup[1], reverse=True)
  print("\nApp activeness\n")
  for key, val in sorted_values:
    print(key, val)

  labels = [i for i in range(len(sorted_values))]
  values = [t[1] for t in sorted_values]

  plot_histogram(labels, values, \
      "App activeness", "App activeness ranking", "Activeness percentage")

def plot_time_usage(counter, app_name):
  start_hour = 7
  rang = range(start_hour, start_hour + 24)

  labels = [str(x % 24).zfill(2) for x in rang]
  values = [counter[x % 24] for x in rang]

  plot_histogram(labels, values, app_name + " usage over time", "Hour of day", "Number of use")

def get_app_type_usage(df):
  type_counts = Counter()

  for i, row in df.iterrows():
    if row.app_name != constants.launcher_string:
      t = constants.app_type_map[row.app_name]
      type_counts[t] += 1

  return type_counts

def plot_type_pie_chart(type_counts, title):
  items = sorted(type_counts.items(), key=lambda t: t[1], reverse=True)

  labels = [t[0] for t in items]
  sizes = [t[1] for t in items]

  plt.pie(sizes, labels=labels, autopct='%1.1f%%', startangle=140)
  plt.title(title)
  plt.axis('equal')
  plt.show()

def map_type_to_activeness(activeness):
  type_activeness = defaultdict(float)
  counts = Counter()

  for app, acivity_level in activeness.items():
    type_activeness[constants.app_type_map[app]] += acivity_level
    counts[constants.app_type_map[app]] += 1

  average_type_activeness = {t : type_activeness[t] / counts[t] for t in counts.keys()}

  return average_type_activeness

def plot_lat_lon_to_type(df, loc2idx, centermost_points):
  ll_type_counter = defaultdict(Counter)

  for i, row in df.iterrows():
    if row.app_name != constants.launcher_string and row.lat != 0 and row.long != 0:
      centermost_point = centermost_points[loc2idx[(row.lat, row.long)]]
      app_type = constants.app_type_map[row.app_name]
      ll_type_counter[centermost_point][app_type] += 1

  x = []
  y = []
  colors = []
  areas = []

  for centermost_point, counter in ll_type_counter.items():
    total_counts = sum(counter.values())
    for app_type, count in counter.most_common():
      x.append(centermost_point[0])
      y.append(centermost_point[1])
      colors.append(constants.type2color[app_type])
      areas.append(15000 * (count/total_counts)**2)

  legend_elements = [Line2D([0], [0], marker='o', color=color, label=app_type)\
      for app_type, color in constants.type2color.items()]

  # Create the figure
  fig, ax = plt.subplots()
  ax.legend(handles=legend_elements)
  ax.scatter(x, y, s=areas, c=colors, alpha=0.5)
  ax.set_xlabel("Latitude")
  ax.set_ylabel("Longitude")
  plt.show()

def plot_location_cluster(df, loc2idx, title):
  color_list = constants.color_list

  lats = []
  lons = []
  colors = []

  for i, row in df.iterrows():
    if row.app_name != constants.launcher_string and row.lat != 0 and row.long != 0:
      lats.append(row.lat)
      lons.append(row.long)
      colors.append(color_list[loc2idx[(row.lat, row.long)]])

  plt.scatter(lats, lons, c=colors)
  plt.title(title)
  plt.xlabel("Latitude")
  plt.ylabel("Longitude")
  plt.show()

def print_stats(df):
  print("\n########## Printing metadata ##########")
  total_sessions = df['session_nr'].max()
  print("Total number of sessions:", total_sessions, "\n")

  top_10_apps = df['app_name'].value_counts()[:10]
  print("Top 10 used apps:")
  print(top_10_apps, "\n")

  activity_freq = df['activity_type'].value_counts()
  print("Activity type frequency:")
  print(activity_freq, "\n")

  most_used_app = top_10_apps.keys()[1]
  app_times = get_app_times(df)

  app_timings = Counter()
  for i, row in df.iterrows():
    hour = datetime.strptime(row.timestamp, constants.timestamp_format).hour
    app_timings[hour] += 1

  if draw_plot:
    plot_time_usage(app_timings, "All apps")

  activeness = get_app_activeness(df, num_timeslots)

  if draw_plot:
    plot_activeness(activeness)

  type_counts = get_app_type_usage(df)

  if draw_plot:
    plot_type_pie_chart(type_counts, "Percentage of usage per app type")

  loc2idx, centermost_points = dbscan_location_cluster(df, cluster_km_distance)

  if draw_plot:
    plot_location_cluster(df, loc2idx, "Location clusters")
    plot_lat_lon_to_type(df, loc2idx, centermost_points)

def main():
  df = pd.read_csv("./data/{}/prepared_data/full_data.csv".format(user))
  print_stats(df)

main()
