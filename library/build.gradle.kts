plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ktlint)

    `maven-publish`
}

kotlin {
    applyDefaultHierarchyTemplate()

    // macOS
    macosX64 {
        compilations.getByName("main").apply {
            cinterops {
                val klopik by creating {
                    defFile("src/macosX64Main/cinterop/klopik.def")
                    compilerOpts("-I" + rootProject.file("golang/lib"))
                    extraOpts("-libraryPath", rootProject.file("golang/lib"))
                }
            }
        }
    }

    macosArm64 {
        compilations.getByName("main").apply {
            cinterops {
                val klopik by creating {
                    defFile("src/macosArm64Main/cinterop/klopik.def")
                    compilerOpts("-I" + rootProject.file("golang/lib"))
                    extraOpts("-libraryPath", rootProject.file("golang/lib"))
                }
            }
        }
    }

    // Linux
    linuxX64 {
        compilations.getByName("main").apply {
            cinterops {
                val klopik by creating {
                    defFile("src/linuxX64Main/cinterop/klopik.def")
                    compilerOpts("-I" + rootProject.file("golang/lib"))
                    extraOpts("-libraryPath", rootProject.file("golang/lib"))
                }
            }
        }
    }

    linuxArm64 {
        compilations.getByName("main").apply {
            cinterops {
                val klopik by creating {
                    defFile("src/linuxArm64Main/cinterop/klopik.def")
                    compilerOpts("-I" + rootProject.file("golang/lib"))
                    extraOpts("-libraryPath", rootProject.file("golang/lib"))
                }
            }
        }
    }

    // Windows
    mingwX64 {
        compilations.getByName("main").apply {
            cinterops {
                val klopik by creating {
                    defFile("src/mingwX64Main/cinterop/klopik.def")
                    compilerOpts("-I" + rootProject.file("golang/lib"))
                    extraOpts("-libraryPath", rootProject.file("golang/lib"))
                }
            }
        }
    }

    sourceSets {
        // Common
        commonMain.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.kermit)
            implementation(libs.kotlin.serialization)
            implementation(libs.okio)
            implementation(libs.slf4j)
        }

        commonTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.kotlin.test)
        }

        all {
            languageSettings.optIn("kotlin.js.ExperimentalJsExport")
        }
    }
}

// Workaround for KSP picking the wrong Java version
afterEvaluate {
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = JavaVersion.VERSION_17.toString()
        targetCompatibility = JavaVersion.VERSION_17.toString()
    }
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    additionalEditorconfig.set(
        mapOf("ktlint_code_style" to "intellij_idea"),
    )
    filter {
        exclude {
            it.file.path.contains("generated")
        }
    }
}

group = "io.vinicius.klopik"
version = System.getenv("VERSION") ?: "SNAPSHOT"

afterEvaluate {
    apply(from = "../publish.gradle.kts")
}