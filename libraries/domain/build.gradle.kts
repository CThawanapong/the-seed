import extension.addUnitTestDependencies

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.kotlinKaptPlugin)
    id(BuildPlugins.hiltPlugin)
    id(BuildPlugins.objectBoxPlugin)
    jacoco
}

jacoco {
    toolVersion = BuildPlugins.Versions.jacocoVersion
}

hilt {
    enableAggregatingTask = true
}

android {
    compileSdk = AndroidSdk.compileVersion

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    lint {
        disable.add("MissingTranslation")
        abortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    defaultConfig {
        minSdk = AndroidSdk.minVersion
        targetSdk = AndroidSdk.targetVersion

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

            matchingFallbacks.apply {
                add("debug")
            }
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
        resources {
            excludes.apply {
                add("META-INF/LICENSE.md")
                add("META-INF/LICENSE-notice.md")
            }
        }
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
        "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
        "-Xopt-in=androidx.paging.ExperimentalPagingApi",
        "-Xopt-in=kotlin.ExperimentalStdlibApi"
    )
}

afterEvaluate {
    android.libraryVariants.forEach { variant ->
        val variantName = variant.name
        val testTaskName = "test${variantName.capitalize()}UnitTest"
        tasks.create(name = "${testTaskName}Coverage", type = JacocoReport::class) {
            dependsOn(testTaskName)
            group = "Reporting"
            description =
                "Generate Jacoco coverage reports for the ${variantName.capitalize()} build."
            reports {
                xml.required.set(true)
                xml.outputLocation.set(file("$buildDir/reports/jacoco/report.xml"))
                html.required.set(true)
                html.outputLocation.set(file("$buildDir/coverage-report"))
            }
            val exclude = listOf(
                "**/R.class",
                "**/R$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                "**/*Test*.*",
                "android/**/*.*",
                "**/*_MembersInjector.class",
                "**/Dagger*Component.class", // Covers component implementations
                "**/Dagger*Component\$Builder.class", // Covers component builders
                "**/*Module_*Factory.class",
                "**/di/**", // Dependencies Injection
                "**/*JsonAdapter.*", // Moshi Generate Adapter
                "**/*Model_.*", // Epoxy Generate model
                "**/controller/**", // Epoxy Model File
                "**/adapter/**", // Exclude pager adapters
                "**/relay/***", // Exclude view relay
                "**/*Activity*.*", // Exclude view testing
                "**/*Fragment*.*",
                "**/*Module*.*", // Exclude module files
                "**/*DialogFragment*.*",
                "**/*PagerAdapter*.*",
                "**/*Directions*", // Exclude Nav Direction
                "**/com/bumptech/glide/**"
            )
            val javaClasses = fileTree(
                mapOf(
                    "dir" to variant.javaCompileProvider.get().destinationDirectory.asFile.orNull,
                    "excludes" to exclude
                )
            )
            val kotlinClasses = fileTree(
                mapOf(
                    "dir" to "$buildDir/tmp/kotlin-classes/$variantName",
                    "excludes" to exclude
                )
            )
            afterEvaluate {
                classDirectories.setFrom(files(listOf(javaClasses, kotlinClasses)))
                sourceDirectories.setFrom(
                    files(
                        listOf(
                            "$project.projectDir/src/main/java",
                            "$project.projectDir/src/$variantName/java"
                        )
                    )
                )
                executionData.setFrom(files("${project.buildDir}/jacoco/$testTaskName.exec"))
            }
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(Modules.AndroidLibrary.DATA))

    implementation(Libraries.ktxCore)

    //Kotlin
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.coroutine)
    implementation(Libraries.coroutineRxJava3)
    implementation(platform(Libraries.arrowBoM))
    implementation(Libraries.arrowCore)
    implementation(Libraries.arrowOptic)
    kapt(Libraries.arrowMeta)
    compileOnly(Libraries.jetBrainAnnotation)

    // Play Services
    implementation(Libraries.playServiceFitness)
    implementation(Libraries.playServiceAuth)
    implementation(Libraries.playCore)
    implementation(Libraries.playCoreKtx)

    // Architecture Components
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
    implementation(Libraries.firebaseAuth)
    implementation(Libraries.firebaseStorage)
    implementation(Libraries.firebaseFirestore)
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
    implementation(Libraries.timber)
    implementation(Libraries.nineOldAndroid)

    addUnitTestDependencies()
}