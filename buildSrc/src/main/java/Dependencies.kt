@file:Suppress("MayBeConstant")

import org.gradle.api.JavaVersion

object Versions {
  val compileSdk = 29
  val targetSdk = 29
  val minSdk = 21

  val gradlePlugin = "3.3.2"
  val kotlin = "1.3.50"

  // Android
  val androidSupportLibrary = "28.0.0"
  val supportConstraint = "1.1.3"
  val archComponents = "1.1.1"

  // DI
  val dagger = "2.14.1"

  // Reactive
  val rxJava2 = "2.2.6"
  val rxAndroid2 = "2.1.1"

  // Networking
  val okHttp = "3.14.3"
  val retrofit = "2.6.1"

  // Database
  val room = "1.1.1"

  // Images
  val glide = "4.7.1"

  // Leak detections
  val leakCanary = "2.0-beta-3"

  // Inspections
  val stetho = "1.5.0"

  // Testing
  val junit = "4.12"
  val mockito = "2.28.2"
  val supportTestRunner = "1.0.2"
  val supportTestRules = "1.0.2"
  val mockitoKotlin = "1.5.0"
  val hamcrest = "1.3"

  // UI testing
  val uiautomator = "2.1.3"
  val espresso = "3.0.2"

  val androidSourceCompatibility = JavaVersion.VERSION_1_8
  val androidTargetCompatibility = JavaVersion.VERSION_1_8
}

object Libs {
  // Android
  val supportV7 = "com.android.support:appcompat-v7:${Versions.androidSupportLibrary}"
  val supportAnnotations = "com.android.support:support-annotations:${Versions.androidSupportLibrary}"
  val supportConstraint = "com.android.support.constraint:constraint-layout:${Versions.supportConstraint}"
  val supportDesign = "com.android.support:design:${Versions.androidSupportLibrary}"
  val archComponentsExtensions = "android.arch.lifecycle:extensions:${Versions.archComponents}"

  // Reactive
  val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
  val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid2}"

  // Leak detections
  val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

  // Images
  val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
  val glideAnnotationProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"

  // Dagger
  val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

  val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
  val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"

  // Kotlin
  val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
  val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
  val kotlinTestJUnit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

  // Networking
  val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
  val retrofitRx2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
  val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
  val okHttpMockServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
  val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

  // Database
  val roomRuntime = "android.arch.persistence.room:runtime:${Versions.room}"
  val roomRxJava2 = "android.arch.persistence.room:rxjava2:${Versions.room}"
  val roomAnnotationProcessor = "android.arch.persistence.room:compiler:${Versions.room}"

  // Gradle
  val gradlePlugin = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
  val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

  // Inspections
  val stetho = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"

  // Testing
  val junit = "junit:junit:${Versions.junit}"
  val mockito = "org.mockito:mockito-core:${Versions.mockito}"
  val supportTestRunner = "com.android.support.test:runner:${Versions.supportTestRunner}"
  val supportTestRules = "com.android.support.test:rules:${Versions.supportTestRules}"
  val mockitoKotlin = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
  val archComponentsTesting = "android.arch.core:core-testing:${Versions.archComponents}"

  val hamcrest = "org.hamcrest:hamcrest-core:${Versions.hamcrest}"
  val hamcrestLib = "org.hamcrest:hamcrest-library:${Versions.hamcrest}"
  val hamcrestIntegration = "org.hamcrest:hamcrest-integration:${Versions.hamcrest}"

  // UI testing
  val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
  val uiautomator = "com.android.support.test.uiautomator:uiautomator-v18:${Versions.uiautomator}"
}
