configure<PublishingExtension> {
    publications {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/vegidio/ffmpeg-kmp")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }

        all {
            getByName<MavenPublication>(name) {
                val newArtifact = when {
                    name == "kotlinMultiplatform" -> "ffmpeg"
                    name.contains("android") -> "android"
                    else -> name.lowercase()
                }
                artifactId = newArtifact.lowercase()
            }
        }
    }
}