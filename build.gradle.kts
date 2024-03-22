plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

detekt {
    config.setFrom("$rootDir/config/detekt.yml")
    source.setFrom(
        "$rootDir/library/src/commonMain/kotlin",
        "$rootDir/library/src/linuxArm64Main/kotlin",
        "$rootDir/library/src/linuxX64Main/kotlin",
        "$rootDir/library/src/macosArm64Main/kotlin",
        "$rootDir/library/src/macosX64Main/kotlin",
        "$rootDir/library/src/mingwX64Main/kotlin",
    )
}