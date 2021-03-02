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
    id(BuildPlugins.easyLauncherPlugin)
    id(BuildPlugins.spotless) version (BuildPlugins.Versions.spotlessVersion)
}

android {
    compileSdkVersion(AndroidSdk.compileVersion)

    lintOptions {
        disable("MissingTranslation")
        isAbortOnError = false
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = AndroidVersion.applicationId
        minSdkVersion(AndroidSdk.minVersion)
        targetSdkVersion(AndroidSdk.targetVersion)
        versionCode = AndroidVersion.versionCode
        versionName = AndroidVersion.versionName

        multiDexEnabled = true
        resConfigs("en", "th")
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(mutableMapOf("enableParallelEpoxyProcessing" to "true"))
            }
        }
    }

    dexOptions {
        preDexLibraries = true
        maxProcessCount = 8
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        create("release") {
            storeFile = file("release.keystore")
            storePassword = "deny-step-stare"
            keyAlias = "nongcalcal"
            keyPassword = "verna-skill-cape"
        }
    }

    buildTypes {
        getByName("debug") {
            (this as ExtensionAware).extra["alwaysUpdateBuildId"] = false
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            firebaseCrashlytics {
                mappingFileUploadEnabled = false
            }

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
            isZipAlignEnabled = true
            setMatchingFallbacks("debug")
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
            isZipAlignEnabled = true
        }
    }

    flavorDimensions("api")

    productFlavors {
        create("dev") {
            signingConfig = signingConfigs.getByName("debug")
            versionName = "${AndroidVersion.versionName}-DEV"
            dimension = "api"

            setManifestPlaceholders(
                mapOf()
            )

            buildConfigField("String", "DEEPLINK_SCHEMA", "\"theseed://\"")
            buildConfigField("String", "API_ENDPOINT", "\"\"")
        }

        create("prod") {
            signingConfig = signingConfigs.getByName("release")
            dimension = "api"

            setManifestPlaceholders(
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
        exclude("META-INF/LICENSE")
        exclude("META-INF/MANIFEST.MF")
        exclude("META-INF/proguard/coroutines.pro")
        exclude("META-INF/core_release.kotlin_module")
        exclude("META-INF/library_release.kotlin_module")
        exclude("META-INF/kotlinx-coroutines-core.kotlin_module")
        exclude("META-INF/maven/com.google.code.findbugs/jsr305/pom.properties")
        exclude("META-INF/maven/com.google.code.findbugs/jsr305/pom.xml")
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
        "-Xopt-in=kotlin.ExperimentalStdlibApi"
    )
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

easylauncher {
    val iconNamesMethod =
        javaClass.getMethod("iconName", String::class.java).apply { isAccessible = true }
    iconNamesMethod.invoke(this, "@mipmap/ic_launcher_foreground")
    val foregroundIconNamesMethod =
        javaClass.getMethod("foregroundIconNames", Array<String>::class.java)
            .apply { isAccessible = true }
    foregroundIconNamesMethod.invoke(this, arrayOf("@mipmap/ic_launcher_foreground"))
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