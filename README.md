# klopik

**Klopik** is a batteries-included HTTP client for [Kotlin Multiplatform](https://github.com/Kotlin/multiplatform-library-template).

It supports the following targets/platforms (more coming soon):

![](https://img.shields.io/badge/macOS-000000?style=for-the-badge&logo=macos&logoColor=white) ![](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black) ![](https://img.shields.io/badge/Windows-0078D4?style=for-the-badge&logo=windows&logoColor=white)

## ⬇️ Installation

**klopik** is hosted in my own Maven repository, so before using it in your project you must add the repository `https://maven.vinicius.io` to your `settings.gradle.kts` file:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.vinicius.io")
    }
}
```

With the repository added, you just need to include the dependency in the file `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.vinicius.klopik:klopik:24.3.22")
}
```

## 🤖 Usage

Please visit the library's [website](https://vegidio.github.io/klopik) to find detailed instructions on how to use it in your project.

## 📝 License

**Klopik** is released under the MIT License. See [LICENSE](LICENSE) for details.

## 👨🏾‍💻 Author

Vinicius Egidio ([vinicius.io](http://vinicius.io))
