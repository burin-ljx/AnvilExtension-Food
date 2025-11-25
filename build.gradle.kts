plugins {
    id("java")
    alias(libs.plugins.modDevGradle)
    alias(libs.plugins.lombok)
}

var buildNumber = ""
var buildType = ""
if (System.getenv("CI_BUILD") == "true") {
    buildType = "build"
    buildNumber = System.getenv("GITHUB_RUN_NUMBER")
}
if (System.getenv("PR_BUILD") == "true") {
    buildType = "pr"
    buildNumber = System.getenv("GITHUB_RUN_NUMBER")
}

group = getConfig("maven_group")
version = getConfig("mod_version") + if (buildType.isEmpty()) "" else "+${buildType}.${buildNumber}"
val modId = getConfig("mod_id")

repositories {
    mavenLocal()
    mavenCentral()
    maven { // Anvil Lib
        name = "Cjsah Maven"
        url = uri("https://server.cjsah.net:1002/maven/")
    }
    maven { // Modrinth File, LazyDFU, Jade
        name = "Modrinth Maven"
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }
    maven { // Mod Menu, EMI
        name = "TerraformersMC Maven"
        url = uri("https://maven.terraformersmc.com/releases/")
    }
    maven { // Cloth Config, REI
        name = "Shedaniel Maven"
        url = uri("https://maven.shedaniel.me/")
    }
    maven { // CurseForge File
        name = "CurseForge Maven"
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    maven { // Patchouli
        name = "Jared's maven"
        url = uri("https://maven.blamejared.com/")
    }
    maven { // Registrate Fork
        name = "Ithundxr Maven"
        url = uri("https://maven.ithundxr.dev/snapshots")
    }
    maven { // KubeJS, Rhino
        name = "Saps Maven"
        url = uri("https://maven.latvian.dev/releases")
        content {
            includeGroup("dev.latvian.mods")
            includeGroup("dev.latvian.apps")
        }
    }
    maven {
        url = uri("https://jitpack.io")
        content {
            includeGroup("com.github.rtyley")
        }
    }
    maven { // TOP
        name = "k-4u Maven"
        url = uri("https://maven.k-4u.nl")
    }
    maven { // Curios API
        name = "OctoStudios Maven"
        url = uri("https://maven.octo-studios.com/releases")
    }
    maven {
        url = uri("https://maven.su5ed.dev/releases")
    }
    maven { // Create Mod, Ponder
        name = "Create Mod Maven"
        url = uri("https://maven.createmod.net")
    }
    maven {
        url = uri("https://maven.latvian.dev/releases")
        content {
            includeGroup("dev.latvian.mods")
            includeGroup("dev.latvian.apps")
        }
    }
    maven { // Twilight Forest
        url = uri("https://maven.tamaized.com/releases")
    }
}

dependencies {
    implementation(libs.anvilcraft)
}

neoForge {
    version = libs.versions.neoForge.get()

    parchment {
        minecraftVersion.set(libs.versions.minecraft.get())
        mappingsVersion.set(libs.versions.parchment.get())
    }

    accessTransformers {
        files("src/main/resources/$modId.accesswidener")
    }

    runs {
        register("client") {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("data") {
            data()
            programArguments.addAll(
                "--mod",
                modId,
                "--all",
                "--output",
                file("src/generated/resources/").absolutePath,
                "--existing",
                file("src/main/resources/").absolutePath
            )
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel.set(org.slf4j.event.Level.DEBUG)
        }
    }

    mods {
        register(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

tasks.register<Jar>("sourceJar") {
    from(tasks["delombok"])
    dependsOn("delombok")
    archiveClassifier.set("sources")
}

base {
    archivesName.set("${project.name}-${libs.versions.parchment.get()}")
}

fun getConfig(key: String): String {
    return properties[key] as String
}

val contributor = getConfig("contributors")
val supporters = getConfig("supporters")

tasks.withType<ProcessResources>().configureEach {
    var replaceProperties = mapOf(
        "minecraft_version"       to libs.versions.minecraft.get(),
        "minecraft_version_range" to getConfig("minecraft_version_range"),
        "neo_version"             to libs.versions.neoForge.get(),
        "neo_version_range"       to getConfig("neo_version_range"),
        "loader_version_range"    to getConfig("loader_version_range"),
        "mod_id"                  to getConfig("mod_id"),
        "mod_name"                to getConfig("mod_name"),
        "mod_license"             to getConfig("mod_license"),
        "mod_version"             to getConfig("mod_version"),
        "mod_description"         to getConfig("mod_description"),
        "contributors"            to contributor,
        "supporters"              to supporters
    )
    inputs.properties(replaceProperties)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties)
    }
}

sourceSets.main {
    resources.srcDirs("src/generated/resources")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

lombok {
    version.set("1.18.38")
}