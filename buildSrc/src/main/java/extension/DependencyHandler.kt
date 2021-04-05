package extension

import Libraries
import TestLibraries
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.addAppModuleDependencies() {
    coreLibraryDesugaring(Libraries.coreDesugar)

    // Google Library
    implementation(Libraries.multiDex)
    implementation(Libraries.ktxCore)
    implementation(Libraries.appCompat)
    implementation(Libraries.cardView)
    implementation(Libraries.recyclerView)
    implementation(Libraries.gridLayout)
    implementation(Libraries.coreAnnotation)
    implementation(Libraries.browser)
    implementation(Libraries.legacyPreference)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.material)
    implementation(Libraries.viewPager)
    implementation(Libraries.installReferer)
    implementation(Libraries.tink)
    implementation(Libraries.guava)

    // Play Services
    implementation(Libraries.playServiceBase)
    implementation(Libraries.playServiceFitness)
    implementation(Libraries.playServiceAuth)
    implementation(Libraries.playServiceIID)
    implementation(Libraries.playServiceTagManager)

    // Architecture Components
    implementation(Libraries.lifecycleExtension)
    implementation(Libraries.lifecycleCommon)
    implementation(Libraries.lifecycleLivedata)
    implementation(Libraries.lifecycleReactivestreams)
    implementation(Libraries.lifecycleViewmodel)
    implementation(Libraries.lifecycleViewmodelSavedstate)
    implementation(Libraries.pagingRuntime)
    implementation(Libraries.pagingLiveData)
    implementation(Libraries.pagingRxJava3)
    implementation(Libraries.workRuntime)
    implementation(Libraries.workRxjava3)
    implementation(Libraries.workGcm)
    implementation(Libraries.navigationUi)
    implementation(Libraries.navigationFragment)
    implementation(Libraries.navigationRuntime)
    implementation(Libraries.navigationDynamic)
    implementation(Libraries.activity)
    implementation(Libraries.fragment)
    implementation(Libraries.securityCrypto)
    implementation(Libraries.startup)
    implementation(Libraries.window)

    //Hilt
    implementation(Libraries.daggerHiltAndroid)
    kapt(Libraries.daggerHiltAndroidCompiler)
    implementation(Libraries.hiltLifecycleViewModel)
    implementation(Libraries.hiltNavigation)
    implementation(Libraries.hiltWork)
    kapt(Libraries.hiltCompiler)

    //Room
    implementation(Libraries.roomRuntime)
    implementation(Libraries.roomRxJava3)
    kaptDebug(Libraries.roomCompiler)
    kaptStage(Libraries.roomCompiler)
    kaptRelease(Libraries.roomCompiler)

    //Firebase
    implementation(platform(Libraries.firebaseBoM))
    implementation(Libraries.firebaseCore)
    implementation(Libraries.firebaseConfig)
    implementation(Libraries.firebaseAuth)
    implementation(Libraries.firebasePerf)
    implementation(Libraries.firebaseStorage)
    implementation(Libraries.firebaseMessaging)
    implementation(Libraries.firebaseInappMessaging)
    implementation(Libraries.firebaseFirestore)
    implementation(Libraries.firebaseAppIndexing)
    implementation(Libraries.firebaseCrashlytics)

    //Kotlin
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutine)
    implementation(Libraries.coroutineRxJava3)
    implementation(Libraries.arrowCore)
    implementation(Libraries.arrowSyntax)
    kapt(Libraries.arrowMeta)
    compileOnly(Libraries.jetBrainAnnotation)

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
    implementation(Libraries.rxBindingCore)
    implementation(Libraries.rxBindingAppcompat)
    implementation(Libraries.rxBindingMaterial)
    implementation(Libraries.rxBindingRecyclerView)
    implementation(Libraries.rxBindingSwipeRefreshLayout)
    implementation(Libraries.rxBindingViewpager)
    implementation(Libraries.rxBindingViewpager2)
    implementation(Libraries.rxJava3Extensions)
    implementation(Libraries.rxJava3Bridge)
    implementation(Libraries.rxRelay)
    implementation(Libraries.rxPermission)
    implementation(Libraries.rxValidator)

    //Object Box
    implementation(Libraries.objectboxAndroid)
    implementation(Libraries.objectboxKotlin)
    implementation(Libraries.objectboxRxJava3)
    kapt(Libraries.objectboxProcessor)

    //Chucker
    debugImplementation(Libraries.chucker)
    stageImplementation(Libraries.chucker)
    releaseImplementation(Libraries.chuckerNoOp)

    //Hyperion
    debugImplementation(Libraries.hyperionCore)
    debugImplementation(Libraries.hyperionPhoenix)
    debugImplementation(Libraries.hyperionGeiger)
    debugImplementation(Libraries.hyperionTimber)
    stageImplementation(Libraries.hyperionCore)
    stageImplementation(Libraries.hyperionPhoenix)
    stageImplementation(Libraries.hyperionGeiger)
    stageImplementation(Libraries.hyperionTimber)
    releaseImplementation(Libraries.hyperionNoOp)

    //UI
    implementation(Libraries.epoxy)
    kapt(Libraries.epoxyProcessor)
    implementation(Libraries.epoxyPaging)
    implementation(Libraries.epoxyGlidePreloading)
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)
    implementation(Libraries.glideOkhttp3Integration)
    implementation(Libraries.glideRecyclerviewIntegration)
    implementation(Libraries.shimmer)

    //Other
    implementation(Libraries.threetenabp)
    implementation(Libraries.timberVersion)
    implementation(Libraries.nineOldAndroid)

    //Other UI
    implementation(Libraries.customActivityOnCrash)
    implementation(Libraries.eventEmitter)
}

fun DependencyHandler.addCoreModuleDependencies() {
    coreLibraryDesugaring(Libraries.coreDesugar)

    // Google Library
    implementation(Libraries.ktxCore)
    implementation(Libraries.appCompat)
    implementation(Libraries.cardView)
    implementation(Libraries.recyclerView)
    implementation(Libraries.gridLayout)
    implementation(Libraries.coreAnnotation)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.material)
    implementation(Libraries.viewPager)

    // Architecture Components
    implementation(Libraries.lifecycleExtension)
    implementation(Libraries.lifecycleCommon)
    implementation(Libraries.lifecycleLivedata)
    implementation(Libraries.lifecycleReactivestreams)
    implementation(Libraries.lifecycleViewmodel)
    implementation(Libraries.lifecycleViewmodelSavedstate)
    implementation(Libraries.pagingRuntime)
    implementation(Libraries.pagingLiveData)
    implementation(Libraries.pagingRxJava3)
    implementation(Libraries.navigationUi)
    implementation(Libraries.navigationFragment)
    implementation(Libraries.navigationRuntime)
    implementation(Libraries.navigationDynamic)
    implementation(Libraries.activity)
    implementation(Libraries.fragment)

    //Hilt
    implementation(Libraries.daggerHiltAndroid)
    kapt(Libraries.daggerHiltAndroidCompiler)
    implementation(Libraries.hiltLifecycleViewModel)
    implementation(Libraries.hiltNavigation)
    kapt(Libraries.hiltCompiler)

    //Kotlin
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutine)
    implementation(Libraries.coroutineRxJava3)
    implementation(Libraries.arrowCore)
    implementation(Libraries.arrowSyntax)
    kapt(Libraries.arrowMeta)
    compileOnly(Libraries.jetBrainAnnotation)

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
    implementation(Libraries.rxBindingCore)
    implementation(Libraries.rxBindingAppcompat)
    implementation(Libraries.rxBindingMaterial)
    implementation(Libraries.rxBindingRecyclerView)
    implementation(Libraries.rxBindingSwipeRefreshLayout)
    implementation(Libraries.rxBindingViewpager)
    implementation(Libraries.rxBindingViewpager2)
    implementation(Libraries.rxJava3Extensions)
    implementation(Libraries.rxJava3Bridge)

    //UI
    implementation(Libraries.epoxy)
    kapt(Libraries.epoxyProcessor)
    implementation(Libraries.epoxyPaging)
    implementation(Libraries.epoxyGlidePreloading)
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)
    implementation(Libraries.glideOkhttp3Integration)
    implementation(Libraries.glideRecyclerviewIntegration)
    implementation(Libraries.shimmer)

    //Other
    implementation(Libraries.threetenabp)
    implementation(Libraries.timberVersion)
    implementation(Libraries.eventEmitter)
}

fun DependencyHandler.addBaseDynamicFeatureModuleDependencies() {
    coreLibraryDesugaring(Libraries.coreDesugar)

    // Google Library
    implementation(Libraries.ktxCore)
    implementation(Libraries.appCompat)
    implementation(Libraries.cardView)
    implementation(Libraries.recyclerView)
    implementation(Libraries.gridLayout)
    implementation(Libraries.coreAnnotation)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.material)
    implementation(Libraries.viewPager)

    // Architecture Components
    implementation(Libraries.lifecycleExtension)
    implementation(Libraries.lifecycleCommon)
    implementation(Libraries.lifecycleLivedata)
    implementation(Libraries.lifecycleReactivestreams)
    implementation(Libraries.lifecycleViewmodel)
    implementation(Libraries.lifecycleViewmodelSavedstate)
    implementation(Libraries.pagingRuntime)
    implementation(Libraries.pagingLiveData)
    implementation(Libraries.pagingRxJava3)
    implementation(Libraries.navigationUi)
    implementation(Libraries.navigationFragment)
    implementation(Libraries.navigationRuntime)
    implementation(Libraries.navigationDynamic)
    implementation(Libraries.activity)
    implementation(Libraries.fragment)

    //Hilt
    implementation(Libraries.daggerHiltAndroid)
    kapt(Libraries.daggerHiltAndroidCompiler)
    implementation(Libraries.hiltLifecycleViewModel)
    implementation(Libraries.hiltNavigation)
    kapt(Libraries.hiltCompiler)

    //Kotlin
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutine)
    implementation(Libraries.coroutineRxJava3)
    implementation(Libraries.arrowCore)
    implementation(Libraries.arrowSyntax)
    kapt(Libraries.arrowMeta)
    compileOnly(Libraries.jetBrainAnnotation)

    //RX
    implementation(Libraries.rxJava)
    implementation(Libraries.rxKotlin)
    implementation(Libraries.rxAndroid)
    implementation(Libraries.rxBindingCore)
    implementation(Libraries.rxBindingAppcompat)
    implementation(Libraries.rxBindingMaterial)
    implementation(Libraries.rxBindingRecyclerView)
    implementation(Libraries.rxBindingSwipeRefreshLayout)
    implementation(Libraries.rxBindingViewpager)
    implementation(Libraries.rxBindingViewpager2)
    implementation(Libraries.rxJava3Extensions)
    implementation(Libraries.rxJava3Bridge)

    //UI
    implementation(Libraries.epoxy)
    kapt(Libraries.epoxyProcessor)
    implementation(Libraries.epoxyPaging)
    implementation(Libraries.epoxyGlidePreloading)
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)
    implementation(Libraries.glideOkhttp3Integration)
    implementation(Libraries.glideRecyclerviewIntegration)
    implementation(Libraries.shimmer)

    //Other
    implementation(Libraries.threetenabp)
    implementation(Libraries.timberVersion)
}

fun DependencyHandler.addUnitTestDependencies() {
    testImplementation(TestLibraries.json)
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.coreTest)
    testImplementation(TestLibraries.testRunner)
    testImplementation(TestLibraries.testRule)
    testImplementation(TestLibraries.extJunit)
    testImplementation(TestLibraries.truth)
    testImplementation(TestLibraries.archCoreTest)
    testImplementation(TestLibraries.pagingCommon)
    testImplementation(TestLibraries.kotlinJunit)
    testImplementation(TestLibraries.mockitoCore)
    testImplementation(TestLibraries.mockitoInline)
    testImplementation(TestLibraries.mockitoKotlin)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.mockWebServer)
    testImplementation(TestLibraries.liveDataTesting)
    testImplementation(TestLibraries.robolectric)
    testImplementation(TestLibraries.robolectricShadowAPI)
    testImplementation(TestLibraries.robolectricShadowPlayService)
    testImplementation(TestLibraries.robolectricShadowHttpClient)
    testImplementation(TestLibraries.robolectricShadowMultiDex)

    testImplementation(TestLibraries.hiltTesting)
    kaptTest(Libraries.hiltCompiler)
    kaptTest(Libraries.daggerHiltAndroidCompiler)

    testCompileOnly(Libraries.jetBrainAnnotation)
}

fun DependencyHandler.addUITestDependencies() {
    androidTestImplementation(TestLibraries.hiltTesting)
    kaptAndroidTest(Libraries.hiltCompiler)
    kaptAndroidTest(Libraries.daggerHiltAndroidCompiler)

    androidTestUtil(TestLibraries.orchestrator)
}

/*
 * These extensions mimic the extensions that are generated on the fly by Gradle.
 * They are used here to provide above dependency syntax that mimics Gradle Kotlin DSL
 * syntax in module\build.gradle.kts files.
 */
private fun DependencyHandler.coreLibraryDesugaring(dependencyNotation: Any): Dependency? =
    add("coreLibraryDesugaring", dependencyNotation)

private fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

private fun DependencyHandler.compileOnly(dependencyNotation: Any): Dependency? =
    add("compileOnly", dependencyNotation)

private fun DependencyHandler.runtimeOnly(dependencyNotation: Any): Dependency? =
    add("runtimeOnly", dependencyNotation)

private fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

private fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.kaptDebug(dependencyNotation: Any): Dependency? =
    add("kaptDebug", dependencyNotation)

private fun DependencyHandler.kaptStage(dependencyNotation: Any): Dependency? =
    add("kaptStage", dependencyNotation)

private fun DependencyHandler.kaptRelease(dependencyNotation: Any): Dependency? =
    add("kaptRelease", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.debugImplementation(dependencyNotation: Any): Dependency? =
    add("debugImplementation", dependencyNotation)

private fun DependencyHandler.stageImplementation(dependencyNotation: Any): Dependency? =
    add("stageImplementation", dependencyNotation)

private fun DependencyHandler.releaseImplementation(dependencyNotation: Any): Dependency? =
    add("releaseImplementation", dependencyNotation)

private fun DependencyHandler.testRuntimeOnly(dependencyNotation: Any): Dependency? =
    add("testRuntimeOnly", dependencyNotation)

private fun DependencyHandler.testCompileOnly(dependencyNotation: Any): Dependency? =
    add("testCompileOnly", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

private fun DependencyHandler.kaptTest(dependencyNotation: Any): Dependency? =
    add("kaptTest", dependencyNotation)

private fun DependencyHandler.kaptAndroidTest(dependencyNotation: Any): Dependency? =
    add("kaptAndroidTest", dependencyNotation)

private fun DependencyHandler.androidTestUtil(dependencyNotation: Any): Dependency? =
    add("androidTestUtil", dependencyNotation)

private fun DependencyHandler.project(
    path: String,
    configuration: String? = null
): ProjectDependency {
    val notation = if (configuration != null) {
        mapOf("path" to path, "configuration" to configuration)
    } else {
        mapOf("path" to path)
    }

    return uncheckedCast(project(notation))
}

@Suppress("unchecked_cast", "nothing_to_inline", "detekt.UnsafeCast")
private inline fun <T> uncheckedCast(obj: Any?): T = obj as T