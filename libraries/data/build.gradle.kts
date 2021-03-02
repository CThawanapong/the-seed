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

        buildConfigField("String", "CLOUDINARY_NAME", "\"cal-cal\"")
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

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

dependencies {
    implementation(Libraries.ktxCore)

    //Kotlin
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutine)
    implementation(Libraries.coroutineRxJava3)
    compileOnly(Libraries.jetBrainAnnotation)

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
    implementation(Libraries.firebaseCore)
    implementation(Libraries.firebaseConfig)

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
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitConverterMoshi)
    implementation(Libraries.retroFitAdapterRxJava3)

    //RX
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.rxAndroid)

    //Object Box
    implementation(Libraries.objectboxAndroid)
    implementation(Libraries.objectboxKotlin)
    implementation(Libraries.objectboxRxJava3)
    kapt(Libraries.objectboxProcessor)

    //Chucker
    debugImplementation(Libraries.chucker)
    "stageImplementation"(Libraries.chucker)
    releaseImplementation(Libraries.chuckerNoOp)

    //UI
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)

    //Other
    implementation(Libraries.threetenabp)
    implementation(Libraries.timberVersion)
    implementation(Libraries.nineOldAndroid)

    addUnitTestDependencies()
}