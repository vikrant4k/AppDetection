import pandas as pd
import numpy as np
from sklearn.cluster import DBSCAN
from geopy.distance import great_circle
from shapely.geometry import MultiPoint

def get_centermost_point(cluster):
  centroid = (MultiPoint(cluster).centroid.x, MultiPoint(cluster).centroid.y)
  centermost_point = min(cluster, key=lambda point: great_circle(point, centroid).m)
  return tuple(centermost_point)

def dbscan_location_cluster(df):
  # Only consider locations where the user is still and remove 0,0 lat lon
  still_df = df.loc[df['activity_type'] == 'STILL']
  still_df = df.loc[df['lat'] != 0.0]
  still_df = df.loc[df['long'] != 0.0]

  coords = still_df.as_matrix(columns=['lat', 'long'])

  kms_per_radian = 6371.0088
  kms = 1 # 1000m
  epsilon = kms / kms_per_radian
  db = DBSCAN(eps=epsilon, min_samples=1, algorithm='ball_tree', \
      metric='haversine').fit(np.radians(coords))
  cluster_labels = db.labels_
  num_clusters = len(set(cluster_labels))
  clusters = [coords[cluster_labels == n] for n in range(num_clusters)]
  centermost_points = pd.Series(clusters).map(get_centermost_point)

  loc2idx = {}
  for i, cluster in enumerate(clusters):
    for coor in cluster:
      loc2idx[(coor[0], coor[1])] = i

  print('Number of clusters: {}'.format(num_clusters))

  return loc2idx, centermost_points
