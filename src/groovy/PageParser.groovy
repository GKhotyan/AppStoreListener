
class PageParser {
    public String getVersion(String url) {
//        @Grab('org.jsoup:jsoup:1.7.1')

        def doc = org.jsoup.Jsoup.connect(url)
                .userAgent("Mozilla")
                .get()

        String version = doc.select("span[itemprop=softwareVersion]").first()?.text()

        return version
    }
}
