import extension.addCoreModuleDependencies
import extension.addUnitTestDependencies

plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.kotlinKaptPlugin)
    id(BuildPlugins.hiltPlugin)
    jacoco
}

jacoco {
    toolVersion = BuildPlugins.Versions.jacocoVersion
}

hilt {
    enableExperimentalClasspathAggregation = true
    enableTransformForLocalTests = true
}

android {
    compileSdkVersion(AndroidSdk.compileVersion)

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    lint {
        disable("MissingTranslation")
        isCheckReleaseBuilds = false /* https://dagger.dev/hilt/gradle-setup */
        isAbortOnError = false
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
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
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            matchingFallbacks.apply {
                add("debug")
            }
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
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
    implementation(project(Modules.AndroidLibrary.DOMAIN))

    addCoreModuleDependencies()
    addUnitTestDependencies()
}