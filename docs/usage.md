# Usage

With the library properly [installed](installation.md), you just need to initialize the class `Klopik()` passing the URL of the website that you want to fetch media information. **klopik** will automatically detect what site/content you're trying to fetch media information; if the URL is not supported then it will throw an exception.

If everything goes well and **klopik** detects the URL, you can use the instantiated object to call the methods below:

## queryMedia()

=== "Kotlin"

    ```kotlin linenums="1"
    import io.vinicius.klopik.Klopik

    val klopik = Klopik("https://www.reddit.com/user/atomicbrunette18")
    val response = klopik.queryMedia()
    ```

=== "Swift"

    ```swift linenums="1"
    import Klopik

    let klopik = Klopik(url: "https://coomer.su/onlyfans/user/atomicbrunette18")
    let response = try? await klopik.queryMedia()
    ```

=== "TypeScript"

    Coming soon...
