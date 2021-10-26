import extension.addAppModuleDependencies
import extension.addUnitTestDependencies

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinParcelize)
    id(BuildPlugins.kotlinKaptPlugin)
    id(BuildPlugins.hiltPlugin)
    id(BuildPlugins.navigationSageArgsPlugin)
    id(BuildPlugins.googleServicePlugin)
    id(BuildPlugins.crashlyticsPlugin)
    id(BuildPlugins.firebasePerfPlugin)
    id(BuildPlugins.objectBoxPlugin)
    id(BuildPlugins.easyLauncherPlugin) version (BuildPlugins.Versions.easyLauncherVersion)
    id(BuildPlugins.spotless) version (BuildPlugins.Versions.spotlessVersion)
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
        disable("MissingTranslation")
        isAbortOnError = false
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = AndroidVersion.applicationId
        minSdk = AndroidSdk.minVersion
        targetSdk = AndroidSdk.targetVersion
        versionCode = AndroidVersion.versionCode
        versionName = AndroidVersion.versionName

        multiDexEnabled = true

        resourceConfigurations.apply {
            add("en")
            add("th")
        }

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
//                arguments(mutableMapOf("enableParallelEpoxyProcessing" to "true"))
            }
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
        }
    }

    buildTypes {
        getByName("debug") {
            (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            withGroovyBuilder {
                "FirebasePerformance" {
                    invokeMethod("setInstrumentationEnabled", false)
                }
            }
        }
        create("stage") {
            initWith(getByName("debug"))
            versionNameSuffix = "-stage"
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
            isJniDebuggable = false
            isRenderscriptDebuggable = false
            renderscriptOptimLevel = 3
            matchingFallbacks.apply {
                add("debug")
            }
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
            isJniDebuggable = false
            isRenderscriptDebuggable = false
            renderscriptOptimLevel = 3
        }
    }

    flavorDimensions.apply {
        add("api")
    }

    productFlavors {
        create("dev") {
            signingConfig = signingConfigs.getByName("debug")
            versionName = "${AndroidVersion.versionName}-DEV"
            dimension = "api"

            addManifestPlaceholders(
                mapOf()
            )

            buildConfigField("String", "DEEPLINK_SCHEMA", "\"theseed://\"")
            buildConfigField("String", "API_ENDPOINT", "\"\"")
        }

        create("prod") {
            signingConfig = signingConfigs.getByName("release")
            dimension = "api"

            addManifestPlaceholders(
                mapOf()
            )

            buildConfigField("String", "DEEPLINK_SCHEMA", "\"theseed://\"")
            buildConfigField("String", "API_ENDPOINT", "\"\"")
        }
    }

    variantFilter {
        ignore = (name.contains("dev") && buildType.name == "release")
    }

    packagingOptions {
        resources {
            excludes.apply {
                add("META-INF/LICENSE")
                add("META-INF/MANIFEST.MF")
                add("META-INF/proguard/coroutines.pro")
                add("META-INF/core_release.kotlin_module")
                add("META-INF/library_release.kotlin_module")
                add("META-INF/kotlinx-coroutines-core.kotlin_module")
                add("META-INF/maven/com.google.code.findbugs/jsr305/pom.properties")
                add("META-INF/maven/com.google.code.findbugs/jsr305/pom.xml")
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
    android.applicationVariants.forEach { variant ->
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

easylauncher {
    iconNames.set(listOf("@mipmap/ic_launcher_foreground"))

    productFlavors {
        create("dev") {
            setFilters(grayRibbonFilter("Dev"))
        }
        create("prod") {}
    }
}

spotless {
    kotlin {
        target("**/*.kt")
        trimTrailingWhitespace()
        ktlint(ktLintVersion).userData(
            mapOf(
                "android" to "true",
                "color" to "true",
                "insert_final_newline" to "false",
                "reporter" to "checkstyle",
                "disabled_rules" to "no-wildcard-imports,max-line-length,import-ordering"
            )
        )
    }
    kotlinGradle {
        target("*.gradle.kts", "additionalScripts/*.gradle.kts")
        ktlint(ktLintVersion).userData(
            mapOf(
                "android" to "true",
                "color" to "true",
                "insert_final_newline" to "false",
                "reporter" to "checkstyle",
                "disabled_rules" to "no-wildcard-imports,max-line-length,import-ordering"
            )
        )
    }
    format("xml") {
        target(
            fileTree(".") {
                include("**/*.xml")
                exclude("**/build/**")
            }
        )
        indentWithSpaces()
        trimTrailingWhitespace()
    }
}

dependencies {
    implementation(project(Modules.AndroidLibrary.DATA))
    implementation(project(Modules.AndroidLibrary.DOMAIN))
    implementation(project(Modules.AndroidLibrary.CORE))

    addAppModuleDependencies()
    addUnitTestDependencies()
}
