# Universal Media Downloader

**ffmpeg-kmp** is a [Kotlin Multiplatform library](https://github.com/Kotlin/multiplatform-library-template) to easily extract links from media files hosted on popular websites.

It supports the following targets/platforms:

![](https://img.shields.io/badge/macOS-000000?style=for-the-badge&logo=macos&logoColor=white)

## ⬇️ Installation

**ffmpeg-kmp** is hosted in my own Maven repository, so before using it in your project you must add the repository `https://maven.vinicius.io` to your `settings.gradle.kts` file:

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
    implementation("io.vinicius.ffmpeg:ffmpeg:{{ version }}")
}
```

## 🤖 Usage

Please visit the library's [website](https://vegidio.github.io/ffmpeg-kmp) to find detailed instructions on how to use it in your project.

## 📝 License

**ffmpeg-kmp** is released under the MIT License. See [LICENSE](LICENSE) for details.

## 👨🏾‍💻 Author

Vinicius Egidio ([vinicius.io](http://vinicius.io))
