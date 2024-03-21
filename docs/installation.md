# Installation

**klopik** is hosted in my own Maven repository, so before using it in your project you must add the repository `https://maven.vinicius.io` to your `settings.gradle.kts` file:

```kotlin title="settings.gradle.kts" linenums="1" hl_lines="5"
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.vinicius.io")
    }
}
```

With the repository added, you just need to include the dependency in the file `build.gradle.kts`:

```kotlin title="build.gradle.kts" linenums="1"
dependencies {
    implementation("io.vinicius.klopik:klopik:{{ version }}")
}
```