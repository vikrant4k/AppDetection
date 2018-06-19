import pandas as pd
import constants
import numpy as np
import matplotlib.pyplot as plt

from collections import Counter, defaultdict
from datetime import datetime

draw_plot = True

def print_stats(df):
  print("\n########## Printing metadata ##########")
  total_sessions = df['session_nr'].max()
  print("Total number of sessions:", total_sessions, "\n")

  top_10_apps = df['app_name'].value_counts()[:10]
  print("Top 10 used apps:")
  print(top_10_apps, "\n")

  different_locations = df['loc_cluster_type'].max()
  print("Number of different locations:", different_locations, "\n")

  activity_freq = df['activity_type'].value_counts()
  print("Activity type frequency:")
  print(activity_freq, "\n")

  app_times = get_app_times(df)

  for app_name in top_10_apps.keys()[1:]: # Skip launcher in top 10
    most_common_hours_for_app = app_times[app_name].most_common(5)
    print(app_name, most_common_hours_for_app)

  most_used_app = top_10_apps.keys()[1]
  print(most_used_app)

  # if draw_plot:
  #   plot_time_usage(app_times[most_used_app], most_used_app)

  app_timings = Counter()
  for i, row in df.iterrows():
    hour = datetime.strptime(row.timestamp, constants.timestamp_format).hour
    app_timings[hour] += 1

  if draw_plot:
    plot_time_usage(app_timings, "All apps")

def get_app_times(df):
  """
  Returns a dictionary for each app, and a counter for which hour the app was used
  """
  app_times = defaultdict(Counter)

  for i, row in df.iterrows():
    app_times[row.app_name][datetime.strptime(row.timestamp, \
        constants.timestamp_format).hour] += 1

  return app_times

def plot_time_usage(counter, app_name):
  start_hour = 7
  rang = range(start_hour, start_hour + 24)

  labels = [str(x % 24).zfill(2) for x in rang]
  values = [counter[x % 24] for x in rang]

  indexes = np.arange(len(labels))
  width = 1

  plt.bar(indexes, values, width)
  plt.xticks(indexes + width * 0.5, labels)
  plt.title(app_name + " usage over time")
  plt.show()

df = pd.read_csv("./data/prepared_data/full_concat_data_2.csv")
print_stats(df)
