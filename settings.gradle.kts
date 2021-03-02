include(":core")
include(":domain")
include(":data")
include(":app")

project(":data").projectDir = File(rootDir, "libraries/data/")
project(":domain").projectDir = File(rootDir, "libraries/domain/")
project(":core").projectDir = File(rootDir, "libraries/core/")

rootProject.name = "The Seed"