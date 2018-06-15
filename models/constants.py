radius = 50
launcher_string = "launcher instance"

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