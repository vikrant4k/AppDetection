import pandas as pd

df = pd.read_csv("./data/prepared_data/full_concat_data_2.csv")

print("\n########## Printing metadata ##########")
total_sessions = df['session_nr'].max()
print("Total number of sessions:", total_sessions, "\n")

app_usage = df['app_name'].value_counts()[:10]
print("Top 10 used apps:")
print(app_usage, "\n")

different_locations = df['loc_cluster_type'].max()
print("Number of different locations:", different_locations, "\n")

activity_freq = df['activity_type'].value_counts()
print("Activity type frequency:")
print(activity_freq, "\n")
