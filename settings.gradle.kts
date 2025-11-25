pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven { url = uri("https://maven.neoforged.net/releases") }
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "anvilextension_food-neoforge"