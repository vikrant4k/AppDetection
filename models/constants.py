radius = 50 # meters
time_cluster_interval = 20 # minutes
launcher_string = "launcher instance"
timestamp_format = '%Y-%m-%d %H:%M:%S'

int2activity = {
    0 : "IN_VEHICLE",
    1 : "ON_BICYCLE",
    2 : "ON_FOOT",
    8 : "RUNNING",
    3 : "STILL",
    5 : "TILTING",
    4 : "UNKNOWN",
    7 : "WALKING"
  }

cols = [
    "timestamp",
    "app_name",
    "activity_type",
    "lat",
    "long",
    "bluetooth_connected",
    "audio_connected",
    "wifi_connected",
    "brightness_level",
    "is_weekday",
    "is_charging"
  ]

launcher_types = set([
    "Action Launcher",
    "ADW Launcher",
    "ADWLauncher EX",
    "Apex Launcher",
    "APUS Launcher",
    "Atom Launcher",
    "Aviate",
    "BIG Launcher",
    "Buzz Launcher",
    "C Launcher",
    "C Launcher 3D",
    "Catapult Launcher",
    "Chameleon Launcher",
    "CM Launcher",
    "Espier Launcher",
    "EverythingMe",
    "Evie Launcher",
    "Facebook Home",
    "GO Launcher",
    "Google Now Launcher",
    "HayaiLauncher",
    "Hola Launcher",
    "Pixel Launcher",
    "Infinity Launcher",
    "Kids Place - Parental Control",
    "KISS Launcher",
    "LauncherPro",
    "Microsoft Launcher",
    "Lawnchair Launcher",
    "Lightning Launcher",
    "LINE Launcher",
    "Nemus Launcher",
    "Nova Launcher",
    "Nova Launcher Prime",
    "OpenLauncher",
    "Pear Launcher",
    "",
    "Pear Launcher Pro",
    "Peek Launcher",
    "Phonotto",
    "QM Launcher",
    "Showself Launcher",
    "Smart Launcher",
    "Solo Launcher",
    "SPB Shell 3D",
    "ssLauncher",
    "Themer",
    "Trebuchet Launcher",
    "Turbo Launcher",
    "Vire Launcher",
    "Wave Launcher",
    "WP Launcher",
    "Z Launcher",
    "Zeam Launcher",
    "TSF Launcher 3D"
    ])

  # app_types = [
  #     "other",
  #     "social",
  #     "productivity",
  #     "utility",
  #     "music",
  #     "phone",
  #     "fitness",
  #     "browser",
  #     "entertainment",
  #     ]

type2color = {
    "other" : "gray",
    "social" : "red",
    "productivity" : "gold",
    "utility" : "chartreuse",
    "music" : "lightseagreen",
    "phone" : "royalblue",
    "fitness" : "darkorchid",
    "browser" : "m",
    "entertainment" : "yellow",
    }

app_type_map = {
    "Messenger" : "social",
    "reddit is fun golden platinum" : "entertainment",
    "nsuns 5/3/1" : "fitness",
    "Snapchat" : "social",
    "Chrome" : "utility",
    "Any.do" : "productivity",
    "Clock" : "productivity",
    "WhatsApp" : "social",
    "Maps" : "utility",
    "Spotify" : "music",
    "Camera" : "utility",
    "System UI" : "other",
    "Google" : "other",
    "Gmail" : "productivity",
    "Crypto Tracker" : "other",
    "Keep" : "productivity",
    "AppDetection" : "other",
    "Phone" : "phone",
    "Android System" : "other",
    "Photos" : "entertainment",
    "Google Play Store" :"other",
    "Slack" : "social",
    "Settings" : "other",
    "Habits" : "productivity",
    "YouTube" : "entertainment",
    "Drive" : "productivity",
    "Sheets" : "productivity",
    "Headspace" : "other",
    "TripAdvisor" : "productivity",
    "Translate" : "utility",
    "Fit" : "fitness",
    "Pocket Casts" : "entertainment",
    "Netflix" : "entertainment",
    "Files" : "utility",
    "Messages" : "social",
    "Wallpapers" : "other",
    "Docs" : "productivity",
    "Google Play services" : "other",
    "Calculator" : "productivity",
    "Package installer" : "other",
    "Google Opinion Rewards" : "other",
    }

def get_color_list():
  import random
  from matplotlib import colors as mcolors

  colors = dict(mcolors.BASE_COLORS, **mcolors.CSS4_COLORS)
  names = [color_name for color_name, _ in colors.items()]
  random.seed(5)
  random.shuffle(names)
  return names

  # Sort colors by hue, saturation, value and name.
  by_hsv = sorted((tuple(mcolors.rgb_to_hsv(mcolors.to_rgba(color)[:3])), name)
                  for name, color in colors.items())
  return [name for hsv, name in by_hsv]

color_list = get_color_list()
