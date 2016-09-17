package utils

class JsoupPageParser implements PageParser{
    public String getVersionFromHtmlByUrl(String url) throws IOException {

//        @Grab('org.jsoup:jsoup:1.7.1')

        def doc = org.jsoup.Jsoup.parse(new URL(url), 30000)
        String version = doc?.select("span[itemprop=softwareVersion]").first()?.text()
        return version
    }
}
