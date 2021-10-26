const val kotlinVersion = "1.5.31"
const val hiltVersion = "2.39.1"
const val navigationVersion = "2.3.5"
const val objectboxVersion = "3.0.1"
const val ktLintVersion = "0.41.0"
const val okHttpVersion = "4.9.1"
const val pagingVersion = "3.0.1"

object BuildPlugins {
    object Versions {
        const val buildToolsVersion = "7.0.3"
        const val spotlessVersion = "5.14.2"
        const val gmsVersion = "4.3.5"
        const val firebasePerfVersion = "1.4.0"
        const val firebaseCrashlyticsVersion = "2.7.1"
        const val easyLauncherVersion = "4.0.0"
        const val jacocoVersion = "0.8.7"
    }

    const val jacocoGradlePlugin = "org.jacoco:org.jacoco.core:${Versions.jacocoVersion}"
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val objectBoxGradlePlugin = "io.objectbox:objectbox-gradle-plugin:$objectboxVersion"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    const val navigationGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
    const val gmsGradlePlugin = "com.google.gms:google-services:${Versions.gmsVersion}"
    const val firebasePerfGradlePlugin =
        "com.google.firebase:perf-plugin:${Versions.firebasePerfVersion}"
    const val firebaseCrashlyticsGradlePlugin =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebaseCrashlyticsVersion}"
    const val easyLauncherGradlePlugin =
        "com.akaita.android:easylauncher:${Versions.easyLauncherVersion}"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinParcelize = "kotlin-parcelize"
    const val kotlinKaptPlugin = "kotlin-kapt"
    const val spotless = "com.diffplug.spotless"
    const val hiltPlugin = "dagger.hilt.android.plugin"
    const val navigationSageArgsPlugin = "androidx.navigation.safeargs.kotlin"
    const val googleServicePlugin = "com.google.gms.google-services"
    const val crashlyticsPlugin = "com.google.firebase.crashlytics"
    const val firebasePerfPlugin = "com.google.firebase.firebase-perf"
    const val easyLauncherPlugin = "com.starter.easylauncher"
    const val objectBoxPlugin = "io.objectbox"
}

object AndroidSdk {
    const val minVersion = 24
    const val compileVersion = 31
    const val targetVersion = compileVersion
}

object AndroidVersion {
    val applicationId = "org.fealous.theseed"
    val versionName = "1.0.0"
    val bitriseBuildNumber = System.getenv("BITRISE_BUILD_NUMBER")
    val versionCode = when {
        bitriseBuildNumber != null -> bitriseBuildNumber.toInt()
        else -> 1
    }
}

object Libraries {
    object Versions {
        const val coreDesugarVersion = "1.1.5"
        const val multidexVersion = "2.0.1"
        const val ktxVersion = "1.6.0"
        const val appCompatVersion = "1.3.1"
        const val cardViewVersion = "1.0.0"
        const val recyclerViewVersion = "1.2.1"
        const val gridLayoutVersion = "1.0.0"
        const val annotationVersion = "1.2.0"
        const val browserVersion = "1.3.0"
        const val constraintLayoutVersion = "2.1.0"
        const val materialVersion = "1.4.0"
        const val installReferrerVersion = "2.2"
        const val tinkVersion = "1.5.0"
        const val guavaVersion = "30.1.1-android"

        const val playServiceBaseVersion = "17.6.0"
        const val playServiceFitnessVersion = "20.0.0"
        const val playServiceAuthVersion = "19.0.0"
        const val playServiceIIDVersion = "17.0.0"
        const val playServiceTagManagerVersion = "17.0.0"
        const val playCoreVersion = "1.10.0"
        const val playCoreKtxVersion = "1.8.1"

        const val hiltJetpackVersion = "1.0.0"

        const val lifecycleVersion = "2.3.1"
        const val workManagerVersion = "2.7.0"
        const val activityVersion = "1.3.1"
        const val fragmentVersion = "1.3.6"
        const val securityCryptoVersion = "1.1.0-alpha03"
        const val viewPagerVersion = "1.1.0-beta01"
        const val startupVersion = "1.1.0"
        const val windowVersion = "1.0.0-beta01"
        const val biometricVersion = "1.1.0"
        const val preferenceVersion = "1.1.1"

        const val roomVersion = "2.3.0"

        const val firebaseBoMVersion = "28.4.2"

        const val coroutineVersion = "1.4.2"
        const val arrowVersion = "1.0.0"
        const val jetbrainAnnotationVersion = "13.0"

        const val storeVersion = "4.0.2-KT15"

        const val okioVersion = "2.10.0"
        const val moshiVersion = "1.12.0"
        const val moshiLazyAdapterVersion = "2.2"
        const val retrofitVersion = "2.9.0"

        const val rxJavaVersion = "3.0.13"
        const val rxKotlinVersion = "3.0.1"
        const val rxAndroidVersion = "3.0.0"
        const val rxBindingVersion = "4.0.0"
        const val rxJavaExtensionVersion = "3.0.1"
        const val rxJavaBridgeVersion = "3.0.0"
        const val rxRelayVersion = "3.0.0"
        const val rxPermission = "0.12"
        const val rxValidator = "1.3"

        const val epoxyVersion = "4.6.2"
        const val glideVersion = "4.12.0"
        const val shimmerVersion = "0.5.0"

        const val threeTenAbpVersion = "1.3.1"
        const val timberVersion = "4.7.1"
        const val nineOldAndroidVersion = "2.4.0"
        const val customActivityOnCrashVersion = "2.3.0"
        const val eventEmitterVersion = "1.2.0"

        const val chuckerVersion = "3.5.1"
    }

    const val coreDesugar = "com.android.tools:desugar_jdk_libs:${Versions.coreDesugarVersion}"

    // Google Library
    const val multiDex = "androidx.multidex:multidex:${Versions.multidexVersion}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompatVersion}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardViewVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerViewVersion}"
    const val gridLayout = "androidx.gridlayout:gridlayout:${Versions.gridLayoutVersion}"
    const val coreAnnotation = "androidx.annotation:annotation:${Versions.annotationVersion}"
    const val browser = "androidx.browser:browser:${Versions.browserVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val installReferer =
        "com.android.installreferrer:installreferrer:${Versions.installReferrerVersion}"
    const val tink = "com.google.crypto.tink:tink-android:${Versions.tinkVersion}"
    const val guava = "com.google.guava:guava:${Versions.guavaVersion}"

    // Play Services
    const val playServiceBase =
        "com.google.android.gms:play-services-base:${Versions.playServiceBaseVersion}"
    const val playServiceFitness =
        "com.google.android.gms:play-services-fitness:${Versions.playServiceFitnessVersion}"
    const val playServiceAuth =
        "com.google.android.gms:play-services-auth:${Versions.playServiceAuthVersion}"
    const val playServiceIID =
        "com.google.android.gms:play-services-iid:${Versions.playServiceIIDVersion}"
    const val playServiceTagManager =
        "com.google.android.gms:play-services-tagmanager:${Versions.playServiceTagManagerVersion}"
    const val playCore = "com.google.android.play:core:${Versions.playCoreVersion}"
    const val playCoreKtx = "com.google.android.play:core-ktx:${Versions.playCoreKtxVersion}"

    // Architecture Components
    const val lifecycleCommon =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleVersion}"
    const val lifecycleLivedata =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}"
    const val lifecycleReactivestreams =
        "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycleVersion}"
    const val lifecycleViewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}"
    const val lifecycleViewmodelSavedstate =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycleVersion}"
    const val pagingRuntime = "androidx.paging:paging-runtime-ktx:${pagingVersion}"
    const val pagingLiveData = "androidx.paging:paging-guava:${pagingVersion}"
    const val pagingRxJava3 = "androidx.paging:paging-rxjava3:${pagingVersion}"
    const val workRuntime = "androidx.work:work-runtime-ktx:${Versions.workManagerVersion}"
    const val workRxjava3 = "androidx.work:work-rxjava3:${Versions.workManagerVersion}"
    const val workGcm = "androidx.work:work-gcm:${Versions.workManagerVersion}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:$navigationVersion"
    const val navigationDynamic =
        "androidx.navigation:navigation-dynamic-features-fragment:$navigationVersion"
    const val activity = "androidx.activity:activity-ktx:${Versions.activityVersion}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragmentVersion}"
    const val securityCrypto = "androidx.security:security-crypto:${Versions.securityCryptoVersion}"
    const val viewPager = "androidx.viewpager2:viewpager2:${Versions.viewPagerVersion}"
    const val startup = "androidx.startup:startup-runtime:${Versions.startupVersion}"
    const val window = "androidx.window:window:${Versions.windowVersion}"
    const val biometric = "androidx.biometric:biometric:${Versions.biometricVersion}"
    const val preference = "androidx.preference:preference-ktx:${Versions.preferenceVersion}"

    //Hilt
    const val daggerHiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
    const val daggerHiltAndroidCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
    const val hiltNavigation =
        "androidx.hilt:hilt-navigation-fragment:${Versions.hiltJetpackVersion}"
    const val hiltWork = "androidx.hilt:hilt-work:${Versions.hiltJetpackVersion}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltJetpackVersion}"

    //Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomRxJava3 = "androidx.room:room-rxjava3:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    //Firebase
    const val firebaseBoM = "com.google.firebase:firebase-bom:${Versions.firebaseBoMVersion}"
    const val firebaseCore = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseConfig = "com.google.firebase:firebase-config-ktx"
    const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    const val firebasePerf = "com.google.firebase:firebase-perf-ktx"
    const val firebaseStorage = "com.google.firebase:firebase-storage-ktx"
    const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
    const val firebaseInappMessaging = "com.google.firebase:firebase-inappmessaging-display-ktx"
    const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx"
    const val firebaseAppIndexing = "com.google.firebase:firebase-appindexing"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"

    //Kotlin
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val coroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}"
    const val coroutineRxJava3 =
        "org.jetbrains.kotlinx:kotlinx-coroutines-rx3:${Versions.coroutineVersion}"
    const val jetBrainAnnotation = "org.jetbrains:annotations:${Versions.jetbrainAnnotationVersion}"
    const val arrowBoM = "io.arrow-kt:arrow-stack:${Versions.arrowVersion}"
    const val arrowCore = "io.arrow-kt:arrow-core"
    const val arrowOptic = "io.arrow-kt:arrow-optics"
    const val arrowMeta = "io.arrow-kt:arrow-meta:${Versions.arrowVersion}"

    //Store
    const val store = "com.dropbox.mobile.store:store4:${Versions.storeVersion}"
    const val storeRxJava3 = "com.dropbox.mobile.store:store-rx3:${Versions.storeVersion}"

    //Networking
    const val okio = "com.squareup.okio:okio:${Versions.okioVersion}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${okHttpVersion}"
    const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor:${okHttpVersion}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshiVersion}"
    const val moshiLazyAdapter =
        "com.serjltt.moshi:moshi-lazy-adapters:${Versions.moshiLazyAdapterVersion}"
    const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitConverterMoshi =
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"
    const val retroFitAdapterRxJava3 =
        "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofitVersion}"

    //RX
    const val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxJavaVersion}"
    const val rxKotlin = "io.reactivex.rxjava3:rxkotlin:${Versions.rxKotlinVersion}"
    const val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxAndroidVersion}"
    const val rxBindingCore =
        "com.jakewharton.rxbinding4:rxbinding-core:${Versions.rxBindingVersion}"
    const val rxBindingAppcompat =
        "com.jakewharton.rxbinding4:rxbinding-appcompat:${Versions.rxBindingVersion}"
    const val rxBindingMaterial =
        "com.jakewharton.rxbinding4:rxbinding-material:${Versions.rxBindingVersion}"
    const val rxBindingRecyclerView =
        "com.jakewharton.rxbinding4:rxbinding-recyclerview:${Versions.rxBindingVersion}"
    const val rxBindingSwipeRefreshLayout =
        "com.jakewharton.rxbinding4:rxbinding-swiperefreshlayout:${Versions.rxBindingVersion}"
    const val rxBindingViewpager =
        "com.jakewharton.rxbinding4:rxbinding-viewpager:${Versions.rxBindingVersion}"
    const val rxBindingViewpager2 =
        "com.jakewharton.rxbinding4:rxbinding-viewpager2:${Versions.rxBindingVersion}"
    const val rxJava3Extensions =
        "com.github.akarnokd:rxjava3-extensions:${Versions.rxJavaExtensionVersion}"
    const val rxJava3Bridge = "com.github.akarnokd:rxjava3-bridge:${Versions.rxJavaBridgeVersion}"
    const val rxRelay = "com.jakewharton.rxrelay3:rxrelay:${Versions.rxRelayVersion}"
    const val rxPermission = "com.github.tbruyelle:rxpermissions:${Versions.rxPermission}"
    const val rxValidator = "com.github.whalemare:RxValidator2:${Versions.rxValidator}"

    //Object Box
    const val objectboxAndroid = "io.objectbox:objectbox-android:$objectboxVersion"
    const val objectboxKotlin = "io.objectbox:objectbox-kotlin:$objectboxVersion"
    const val objectboxRxJava3 = "io.objectbox:objectbox-rxjava3:$objectboxVersion"
    const val objectboxProcessor = "io.objectbox:objectbox-processor:$objectboxVersion"

    //Chucker
    const val chucker = "com.github.ChuckerTeam.Chucker:library:${Versions.chuckerVersion}"
    const val chuckerNoOp =
        "com.github.ChuckerTeam.Chucker:library-no-op:${Versions.chuckerVersion}"

    //UI
    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxyVersion}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.epoxyVersion}"
    const val epoxyPaging = "com.airbnb.android:epoxy-paging3:${Versions.epoxyVersion}"
    const val epoxyGlidePreloading =
        "com.airbnb.android:epoxy-glide-preloading:${Versions.epoxyVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
    const val glideOkhttp3Integration =
        "com.github.bumptech.glide:okhttp3-integration:${Versions.glideVersion}"
    const val glideRecyclerviewIntegration =
        "com.github.bumptech.glide:recyclerview-integration:${Versions.glideVersion}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmerVersion}"

    //Other
    const val threetenabp = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTenAbpVersion}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val nineOldAndroid = "com.nineoldandroids:library:${Versions.nineOldAndroidVersion}"
    const val customActivityOnCrash =
        "cat.ereza:customactivityoncrash:${Versions.customActivityOnCrashVersion}"
    const val eventEmitter = "com.github.Zhuinden:event-emitter:${Versions.eventEmitterVersion}"
}

object TestLibraries {
    object Versions {
        const val jsonVersion = "20190722"
        const val jUnitVersion = "4.13.1"
        const val coreTestVersion = "1.4.0"
        const val testRunnerVersion = "1.4.0"
        const val testRuleVersion = "1.4.0"
        const val extJunitVersion = "1.1.3"
        const val truthVersion = "1.4.0"
        const val archCoreTestingVersion = "2.1.0"
        const val mockkVersion = "1.10.6"
        const val fakerVersion = "1.7.0"
        const val liveDataTestingVersion = "1.1.2"
        const val robolectricVersion = "4.5.1"

        const val espressoVersion = "3.4.0"
        const val idelingResourceVersion = "1.0.0"
        const val uiAutomatorVersion = "2.2.0"
        const val orchestratorVersion = "1.4.0"
    }

    const val json = "org.json:json:${Versions.jsonVersion}"
    const val junit = "junit:junit:${Versions.jUnitVersion}"
    const val coreTest = "androidx.test:core-ktx:${Versions.coreTestVersion}"
    const val testRunner = "androidx.test:runner:${Versions.testRunnerVersion}"
    const val testRule = "androidx.test:rules:${Versions.testRuleVersion}"
    const val extJunit = "androidx.test.ext:junit-ktx:${Versions.extJunitVersion}"
    const val truth = "androidx.test.ext:truth:${Versions.truthVersion}"
    const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTestingVersion}"
    const val pagingCommon = "androidx.paging:paging-common:${pagingVersion}"
    const val kotlinJunit = "org.jetbrains.kotlin:kotlin-test-junit:${kotlinVersion}"
    const val mockk = "io.mockk:mockk:${Versions.mockkVersion}"
    const val faker = "io.github.serpro69:kotlin-faker:${Versions.fakerVersion}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${okHttpVersion}"
    const val liveDataTesting = "com.jraska.livedata:testing-ktx:${Versions.liveDataTestingVersion}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
    const val robolectricShadowAPI = "org.robolectric:shadowapi:${Versions.robolectricVersion}"
    const val robolectricShadowPlayService =
        "org.robolectric:shadows-playservices:${Versions.robolectricVersion}"
    const val robolectricShadowHttpClient =
        "org.robolectric:shadows-httpclient:${Versions.robolectricVersion}"
    const val robolectricShadowMultiDex =
        "org.robolectric:shadows-multidex:${Versions.robolectricVersion}"
    const val hiltTesting = "com.google.dagger:hilt-android-testing:${hiltVersion}"

    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoVersion}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espressoVersion}"
    const val espressoAccessibility = "androidx.test.espresso:espresso-accessibility:${Versions.espressoVersion}"
    const val espressoWeb = "androidx.test.espresso:espresso-web:${Versions.espressoVersion}"
    const val espressoConcurrent = "androidx.test.espresso.idling:idling-concurrent:${Versions.espressoVersion}"
    const val espressoIdelingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.espressoVersion}"
    const val idelingResource = "com.jakewharton.espresso:okhttp3-idling-resource:${Versions.idelingResourceVersion}"
    const val uiAutomator = "androidx.test.uiautomator:uiautomator:${Versions.uiAutomatorVersion}"
    const val orchestrator = "androidx.test:orchestrator:${Versions.orchestratorVersion}"
}