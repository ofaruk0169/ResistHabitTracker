﻿# Resist Habit Tracker
## A custom habit tracker for users to measure how much time they have abstained from their chosen habit.  

I use habit trackers personally with my Android phone and thought it would be a great idea to recreate their functionality with a habit tracker of my own! This habit tracker uses XML with Views for the user interface. Furthermore, it implements modern Android features with Kotlin such as Coroutines and Flows to allow for the dynamic asyncronous counting functionality of the application. The apps state is saved using the MVVM architecture and DataStore to persist the user's current time for when they revisit the application. Within the fragment of the application the user can switch between their habit name and a motivational quote, the motivational quote used Ktor to fetch the quote of the day from a remore API to display to the user. 

This project taught me a lot about architectural patterns, the Android ecosystem and Process death. I am really proud I have gotten it working and upon completion it solidified my interest in Android development as something I want to pursue as a career in my life. 

Below is a YouTube link to the project so you yourself can get a feel for my vision. Upon starting the application a timer will start indicating to the user how long they have abstained from their chosen habit. Users are able to rename their habit, see a motivational quote of the day and reset their time if they have broken their habit. 

[![ResistHabitTracker](https://img.youtube.com/vi/M9-UF7ztn6A/0.jpg)](https://www.youtube.com/shorts/M9-UF7ztn6A)

## Installation Guide

If you are interested in installing this application onto your local computer for a personalised demo, follow these instructions. 

1. Clone the repository using the following command on your terminal - `git clone https://github.com/ofaruk0169/ResistHabitTracker.git`
2. Open Android Studio on your computer
3. In Android Studio select "File" > "Open" and navigate to the directory where you cloned the repository. Select the "ResistHabitTracker" directory and click "OK" to import the project into Android Studio.
4. Once the project is imported, Android Studio may automatically start syncing the project with Gradle. If not, you can manually trigger the sync process by clicking on the "Sync Project with Gradle Files" icon in the toolbar.
5. Set up the emulator, if you haven't set up an emulator yet, you can do so by going to "Tools" > "AVD Manager" in Android Studio. Create a new virtual device according to your preferences and specifications.
6. Once the project is successfully imported and synced, you can run the application by clicking the "Run" button in the toolbar. Choose the emulator you created in the previous step, and Android Studio will build the app and install it on the emulator. After installation, the app should automatically start running on the emulator.

Now you can explore the Resist Habit Tracker app running in the emulator. You can interact with it as you would with any other Android app, testing its features and functionality.

