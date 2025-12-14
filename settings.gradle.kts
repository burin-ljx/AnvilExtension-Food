pluginManagement {
    repositories {
        maven {
            name = "Aliyun Maven"
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        mavenLocal()
        gradlePluginPortal()
        maven { url = uri("https://maven.neoforged.net/releases") }
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            name = "Aliyun Maven"
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        mavenCentral()
    }
}

rootProject.name = "anvilextension_food-neoforge"