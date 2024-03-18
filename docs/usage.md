# Usage

With the library properly [installed](installation.md), you just need to initialize the class `FFmpeg()` passing the URL of the website that you want to fetch media information. **ffmpeg-kmp** will automatically detect what site/content you're trying to fetch media information; if the URL is not supported then it will throw an exception.

If everything goes well and **ffmpeg-kmp** detects the URL, you can use the instantiated object to call the methods below:

## queryMedia()

=== "Kotlin"

    ```kotlin linenums="1"
    import io.vinicius.ffmpeg.FFmpeg

    val ffmpeg = FFmpeg("https://www.reddit.com/user/atomicbrunette18")
    val response = ffmpeg.queryMedia()
    ```

=== "Swift"

    ```swift linenums="1"
    import FFmpeg

    let ffmpeg = FFmpeg(url: "https://coomer.su/onlyfans/user/atomicbrunette18")
    let response = try? await ffmpeg.queryMedia()
    ```

=== "TypeScript"

    Coming soon...
