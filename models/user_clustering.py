import pandas as pd
import constants

from sklearn.cluster import DBSCAN
from lat_lon_handler import get_lat_lon_distance
from collections import defaultdict

def get_fistance_matrix(df):
  lat_lons = set()

  for i, row in df.iterrows():
    if row.activity_type == "STILL": # Only cluster still locations to avoid noise
      lat_lons.add((row.lat, row.long))

  lat_lons.remove((0,0))
  lat_lons = list(lat_lons)
  l = len(lat_lons)

  distance_matrix = [[0 for x in range(l)] for y in range(l)]

  for j, y in enumerate(lat_lons):
    for i, x in enumerate(lat_lons):
      lat1, lon1 = y
      lat2, lon2 = x
      dist = get_lat_lon_distance(lat1, lon1, lat2, lon2)
      distance_matrix[j][i] = dist

  return distance_matrix, lat_lons

def find_clusters(df):
  # TODO: DBSCAN is not finished
  distance_matrix, _ = get_fistance_matrix(df)

  db = DBSCAN(metric='precomputed').fit_predict(distance_matrix)
  return db

def find_simple_clusters(df):
  distance_matrix, lat_lons = get_fistance_matrix(df)
  clusters = defaultdict(lambda: len(clusters))
  print("Num unique lat/lons", len(distance_matrix))

  for j in range(len(distance_matrix)):
    found_cluster = False
    for i in range(len(distance_matrix)):
      dist = distance_matrix[j][i]
      if dist < constants.radius:
        clusters[lat_lons[i]]= clusters[lat_lons[j]] # Lat, lon nr i, belongs to cluster j
        found_cluster = True
        break

    if not found_cluster:
      clusters[lat_lons[i]] # Init new cluster

  return clusters

def add_cluster_type_column(df):
  clusters = find_simple_clusters(df)
  df['loc_cluster_type'] = df.apply(lambda row: clusters[(row.lat, row.long)], axis=1)
