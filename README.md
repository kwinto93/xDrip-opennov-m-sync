# Fork description

## Problems

- xDrip seems to not reading opennov automatically - you need to have the app opened in foreground.
- Diabetes M has a nightscout integration but for some reason I am unable to make it work, but xDrip works properly with the same server.

## Goals

- Allows xDrip to collect opennov in the background.
- Auto-sync xDrip's opennov to Diabetes M <https://github.com/sirmamedical/diabetes-m-addon-example> without the nightscout server.

## Current status

| Is it done?            | Description                             | Note                                                                                                                                                                             |
|------------------------|-----------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| :white_check_mark:     | opennov reading in the background       | scanning opennov opens system window to choose xdrip, sadly I don't see a way to fully automate it in the background                                                             |
| :white_check_mark:     | auto-sync to Diabetes M as a new entry  | every new entry in xDrip is sync to Diabetes: M; hard to say how well will work DST time for a dose timestamp                                                                    |
| :yellow_circle:        | merge with the last entry in Diabetes M | is it possible?; probably not, no API for removing entry in Diabetes: M, but sometimes they are merged together if there is a small gap between glucose reading and insulin dose |
| :white_check_mark:     | support for priming dose set in xDrip   | priming dose setting is respected when syncing to Diabetes M                                                                                                                     |
| :white_square_button:  | upgrade target and compile SDK          | now, you see a warning that you are trying to install app compiled for very old API; also some things like flatDirs are not recommended anymore                                  |
| :white_square_button:  | DST timezone change support             | verify whether the syncing to Diabetes M are working correctly for DST changes                                                                                                   |

## Disclaimer

This fork is mostly to fit my needs and requirements, but since it may help also you, I'm sharing it publicly.

**No Android APP builds will be shared. You need to clone and build the app at your own risk, so you are taking the full responsibility. There is no warranty.
This fork may not work with any of CGMs. It is not tested. Especially, planned upgrade of target SDK level may break many features.
Always make decisions about your treatment together with health care professionals.**

You can use any code available here under the same license as the original project, so sadly GPLv3.

# / BELOW ORIGINAL README /

# Nightscout xDrip+
> Enhanced personal research version of xDrip

 <img align="right" src="Documentation/images/download-xdrip-plus-qr-code.png">
 Info page and APK download: https://jamorham.github.io/#xdrip-plus

<img align="right" src="https://travis-ci.org/jamorham/xDrip-plus.svg?branch=master"><a align="right" title="Crowdin" target="_blank" href="https://crowdin.com/project/xdrip"><img align="right" src="https://badges.crowdin.net/xdrip/localized.svg"></a>

## Features
* Voice, Keypad or Watch input of Treatments (Insulin/Carbs/Notes)
* Visualization of Insulin and Carb action curves + Undo/Redo
* Improved alerts and predictive low forecasting feature
* Instant data synchronization between phones and tablets
* Support for many different data sources
* Published by the Nightscout Foundation

 <img align="middle" src="https://jamorham.github.io/images/jamorham-natural-language-treatments-two-web.png">

## What does it do?

xDrip+ is an unofficial and independent Android app which works as data hub and processor between many different devices.

It supports wireless connections to G4, G5, G6, G7, Medtrum A6, Libre via NFC and Bluetooth, 630G, 640G, 670G pumps, CareSens Air and Eversense CGM via companion apps. Bluetooth Glucose Meters such as the Contour Next One, AccuChek Guide, Verio Flex & Diamond Mini as well as devices like the Pendiq 2.0 Insulin Pen.

Heart-rate and step counter data is processed from Android Wear, Garmin, Fitbit and Pebble smart-watches and watch-faces for those that show glucose values and graphs.

On some Android Wear watches, it is possible for the G5 or G6 to talk directly to the watch so it can display values even when out of range of the phone.

The app contains sophisticated charting, customization and data entry features as well as a predictive simulation model.

Instant two-way synchronization is possible by linking follower handsets, data can also be uploaded and downloaded to a Nightscout web service or uploaded directly to Tidepool, MongoDB or InfluxDB.

Customization allows for different options to configure alarms, vocalize readings, change the display preferences etc. International users can update translations from within the app too.

Your data is yours and can be exported in many different ways. xDrip also intercommunicates with other apps, for example sending and receiving live data with AndroidAPS.


## Ethos
* Developed using Rapid Prototyping methodology
* Immediate results favoured to prove concepts
* Designed to support my personal research goals
* User Choice always a high priority
* No registration or Internet access required
* Community testing and collaboration appreciated!

## Roadmap
* Calibration improvements
* Supporting the large family of devices
* Increasing automation and data backup and sync options
* More Nightscout and APS integration

## Collaboration
We are very happy if people want to collaborate with this project. Please contact us at [Discussions](https://github.com/NightscoutFoundation/xDrip/discussions) if you want to get involved and study the [collaboration guidelines](CONTRIBUTING.md) before submitting any patches or pull requests.

## Thanks
None of this would be possible without all the hard work of the xDrip and Nightscout communities who have developed such excellent software and allowed us to build upon it.

