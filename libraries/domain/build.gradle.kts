import extension.addUnitTestDependencies

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.kotlinKaptPlugin)
    id(BuildPlugins.hiltPlugin)
    id(BuildPlugins.objectBoxPlugin)
}

android {
    compileSdkVersion(AndroidSdk.compileVersion)
    lintOptions {
        disable("MissingTranslation")
        isAbortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
    }

    defaultConfig {
        minSdkVersion(AndroidSdk.minVersion)
        targetSdkVersion(AndroidSdk.targetVersion)
        versionCode = AndroidVersion.versionCode
        versionName = AndroidVersion.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        create("stage") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            setMatchingFallbacks("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    }

    testOptions {
        execution = "ANDROID_TEST_ORCHESTRATOR"
        animationsDisabled = true
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile::class).all {
    android.kotlinOptions.freeCompilerArgs += listOf(
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi",
        "-Xopt-in=kotlin.ExperimentalStdlibApi"
    )
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

dependencies {
    implementation(project(Modules.AndroidLibrary.DATA))

    implementation(Libraries.ktxCore)

    //Kotlin
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutine)
    implementation(Libraries.coroutineRxJava3)
    implementation(Libraries.arrowCore)
    implementation(Libraries.arrowSyntax)
    kapt(Libraries.arrowMeta)
    compileOnly(Libraries.jetBrainAnnotation)

    // Play Services
    implementation(Libraries.playServiceFitness)
    implementation(Libraries.playServiceAuth)

    // Architecture Components
    implementation(Libraries.lifecycleExtension)
    implementation(Libraries.lifecycleCommon)
    implementation(Libraries.lifecycleLivedata)
    implementation(Libraries.lifecycleReactivestreams)
    implementation(Libraries.pagingRuntime)
    implementation(Libraries.pagingLiveData)
    implementation(Libraries.pagingRxJava3)

    //Hilt
    implementation(Libraries.daggerHiltAndroid)
    kapt(Libraries.daggerHiltAndroidCompiler)
    kapt(Libraries.hiltCompiler)

    //Room
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomRxJava3)
    kaptDebug(Libraries.roomCompiler)
    "kaptStage"(Libraries.roomCompiler)
    kaptRelease(Libraries.roomCompiler)

    //Firebase
    implementation(platform(Libraries.firebaseBoM))
    implementation(Libraries.firebaseCore)
    implementation(Libraries.firebaseConfig)
    implementation(Libraries.firebaseCrashlytics)

    //Store
    implementation(Libraries.store)
    implementation(Libraries.storeRxJava3)

    //Networking
    implementation(Libraries.okio)
    implementation(Libraries.okHttp)
    implementation(Libraries.okHttpLogging)
    implementation(Libraries.moshi)
    implementation(Libraries.moshiLazyAdapter)
    kapt(Libraries.moshiCodeGen)

    //RX
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.rxAndroid)
    implementation(Libraries.rxJava3Extensions)
    implementation(Libraries.rxJava3Bridge)

    //Object Box
    implementation(Libraries.objectboxAndroid)
    implementation(Libraries.objectboxKotlin)
    implementation(Libraries.objectboxRxJava3)
    kapt(Libraries.objectboxProcessor)

    //UI
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)

    //Other
    implementation(Libraries.threetenabp)
    implementation(Libraries.timberVersion)
    implementation(Libraries.nineOldAndroid)

    addUnitTestDependencies()
}