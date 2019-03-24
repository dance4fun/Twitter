# Twitter

A simple twitter client that is written fully in Kotlin and uses the Clean architecture (with MVVM as presentation layer). It uses 100% fake data and does not connect to any APIs.


## Features

  - Splash screen - on launch, if the user has logged in before there will be shown all tweets that are currently stored on the device (in a room database) and then will fetch "new" tweets (simply fetches 5 hardcoded tweets).
  - Login screen with error handling and intentional delay to simulate a network call.  The correct credentials to log in are:
    `username: adm@mail.com`
    `password: 123`
  - Home Screen to show a list of fake tweets. For simplicity, a tweet only contains a user image, username and a tweet message. For fetching new tweets user has ability to pull-to-refresh the list with tweets. User can send his own tweets by entering message in appropriate input field and tapping done on the keyboard. Also there is a logout option on the application nav bar.
  
# Running unit tests
  
## Running unit tests using Android Studio.

1. Download the project code, preferably using `git clone`.
1. In Android Studio, select *File* | *Open...* and point to the `./build.gradle` file.
1. Check out the relevant code:
    * The application code is located in `src/main/java`
    * Unit Tests are in `src/test/java`
1. Create a test configuration with the JUnit4 runner: `org.junit.runners.JUnit4`
    * Open *Run* menu | *Edit Configurations*
    * Add a new *JUnit* configuration
    * Choose module *app*
    * Select the class to run by using the *...* button
1. Run the newly created configuration

The unit test will be ran automatically.

## Use Gradle on the command line.

After downloading the projects code using `git clone` you'll be able to run the
unit tests using the command line:

    ./gradlew test

If all the unit tests have been successful you will get a `BUILD SUCCESSFUL`
message.

## See the report.

A report in HTML format is generated in `app/build/reports/tests`

# Running Android UI tests

This project uses the Gradle build system. You don't need an IDE to build and execute it but Android Studio is recommended.

1. Download the project code, preferably using `git clone`.
1. Open the Android SDK Manager (*Tools* Menu | *Android*) and make sure you have installed the *Android Support Repository* under *Extras*. (For more Information click [here](http://developer.android.com/tools/testing-support-library/index.html#setup))
1. In Android Studio, select *File* | *Open...* and point to the `./build.gradle` file.
1. Check out the relevant code:
    * The application under test is located in `src/main/java`
    * Tests are in `src/androidTest/java`
1. Create the test configuration with a custom runner: `androidx.test.runner.AndroidJUnitRunner`
    * Open *Run* menu | *Edit Configurations*
    * Add a new *Android Tests* configuration
    * Choose a module
    * Add a *Specific instrumentation runner*: `androidx.test.runner.AndroidJUnitRunner`
1. Connect a device or start an emulator
    * Turn animations off.
    (On your device, under Settings->Developer options disable the following 3 settings: "Window animation scale", "Transition animation scale" and "Animator duration scale")
1. Run the newly created configuration

The application will be started on the device/emulator and a series of actions will be performed automatically.

If you are using Android Studio, the *Run* window will show the test results.

# Code structure


#### Clean Architecture
The main purpose of this type of architecture is the separation of concerns by keeping business rules separate from how the data is obtained and presented, thus, making it possible to test it without depending on any external element.

#### Presentation Layer
In this layer is where the logic related to views and animation lays. Model View ViewModel (MVVM from now on) is used which allows us to keep UI logic completely separate from any other operations, such us retrieving data. There must not be any logic, other than view logic, in Views.
ViewModels will use UseCases internally. UseCases perform their logic in a separate thread so we can be sure that we won't be doing any heavy operation on the main thread.

#### Domain Layer
As mentioned before, this is where business rules are, all business logic should happen in this layer. Here is where the UseCases are created and also where the Repository interfaces are defined, this includes data model contracts.
This layer must not have any Android dependencies. All external components use interfaces when connecting to business objects.
A **UseCase** represents an action that we can do with our application e.g. "Create tweet", "Log In", "Log Out", etc and its main function is to orchestrate the business logic. The UseCase follows a **result pattern** so it emits the one of UseCase results that represents the different states this UseCase can be in (Loading, Success, Error etc.). Interactors can be used as UseCase feature facade.

#### Data Layer
All data needed for the application comes through this layer by implementing the Repository interfaces exposed in the Domain Layer, external objects will be mapped to the ones expected by the interface.


#### Third Party Libraries
The [RxJava2](https://github.com/ReactiveX/RxJava/tree/2.x) is used for communications between ViewModel, the domain layer, Repository and data source.

[Retrofit2](https://square.github.io/retrofit/), [Okhttp](https://square.github.io/okhttp/) & [Gson](https://github.com/google/gson) are used for constructing the REST API.

[Room](https://developer.android.com/topic/libraries/architecture/room) is used for persistence.

[Stetho](http://developer.android.com/tools/testing-support-library/index.html#setup) - as a debug bridge.

[Glide](https://github.com/bumptech/glide) - as image loader.

## Notes
 
The codebase exists only as an example of my codestyle. I'm improving developing skills from project to project, so it is not the last instance of my coding style.

Currently dependency injection uses only app-wide scope, the additional components should be created.

Usually I split the project into separate modules, for simplicity all layers are in a single module.