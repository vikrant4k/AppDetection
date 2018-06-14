import glob
import pandas as pd
import datetime

from constants import launcher_string, int2activity, cols, launcher_types

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
  if app_name in launcher_types:
    return launcher_string
  return app_name

def get_time(timestamp):
  return datetime.datetime.fromtimestamp(timestamp/1000).strftime('%Y-%m-%d %H:%M:%S')

def main():
  # df = read_all_csv_in_dir("./data")
  df = pd.read_csv("./data/12-Jun-2018.csv")
  print("Total number of features:", len(cols))
  print("Total number of rows", df.shape[0])
  df.columns = cols


  df['timestamp'] = df['timestamp'].apply(get_time)
  df['activity_type'] = df['activity_type'].apply(lambda n: int2activity[n])
  df['brightness'] = df['brightness'].apply(lambda x: x / 40000)
  df['app_name'] = df['app_name'].apply(discover_launcher)

  for i, row in df.iterrows():
    print(row.timestamp)


main()
