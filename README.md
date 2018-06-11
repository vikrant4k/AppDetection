# AppDetection

## About
This is an app made by two students at the University of Amsterdam used for the course Project AI. The purpose of
this app is to collect data on which app the user used when, and eventually use AI techniques to predict what app the
user will open next. As of now we have a very raw alpha version of the app, and the goal is to eventually have a 
nice user interface where the most likely apps are shown to the user. 
The data collected is anonymized and will only be used for research purposes. 
[More information can be found here.](https://docs.google.com/document/d/1uJVTQhSDp-7_7NJsT6z8BZuqR41wSltXwpf5GCpU8PY/edit?usp=sharing)

## Install instructions

1. Since the App is not in the playstore yet, you will have to manually add the .apk file to you phone and install it.
In order to do this, you must first allow sources from outside the Play Store to install on you device. 
Follow the instructions found [here](https://www.applivery.com/docs/troubleshooting/android-unknown-sources)

2. Next, download the .apk file to your phone. The file can be found 
[here](https://l.messenger.com/l.php?u=https%3A%2F%2Fwww.dropbox.com%2Fs%2Fddrjqg2cno2n21j%2Fapp-debug.apk%3Fdl%3D0&h=ATO5VFpI_fEtt4PmYgoGcXv1p3X0osr7sExYkcCyse9a02p5NB43h0_6k1IQoenwhaapaZvkhhnTyKz25-24x0Hw1veqHh97RbD-T13gcERHNw).
Press the file once downloaded, and the app should install on your Android device.

3. The first time you open the app, you will be provided with a Sign-in screen. Press `Create New Account` in the bottom
left corner, which will provide you with a sign-up screen as shown below. 
Please select a username and a password, as well as an email address 
(this email address will be used to send you the confirmation code). Given name and phone number is **not** required.
After pressing `sign up`, wait a little while and you should receive a confirmation code with the email you provided.
If you do not get the confirmation code, please send us an email with you username and we will fix it.

![Image of sign-up screen](https://github.com/vikrant4k/AppDetection/raw/master/readme_images/sign_up.png)

4. Once you have successfully signed up, you will get an Android pop up saying 
`Allow AppDetection to access this device's location?`
Press `Allow`.

5. Once you have allowed the app to access location, you will be presented with the following screen:

![Image of sign-up screen](https://github.com/vikrant4k/AppDetection/raw/master/readme_images/permissions.png)

Press AppDetection and toggle `Permit usage access` as shown below. 

![Image of sign-up screen](https://github.com/vikrant4k/AppDetection/raw/master/readme_images/permissions_correct.png)

6. Once you have allowed usage access, press the back arrow to go back, and back once more to go to the app.
You should now be successfully logged in, and presented with a screen thanking you for logging in. 

7. You are now done! You can hit the back arrow once more to close the app, as it will now be running in the background
collecting data. We have made app so that it only updates when the screen is on, and only every two seconds in
order to minimize power consumption. We will
only collect data every 24 hours, so network usage should also be minimal.
If the app shuts down for some reason, please reopen it. You should still be logged in and will not have to do anything else.

Thank you for installing AppDetection and helping us. The data collected is anonymized and will only be used for research
purposes. 

