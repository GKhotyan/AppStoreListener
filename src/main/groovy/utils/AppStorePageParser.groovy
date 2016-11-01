package utils

import org.springframework.stereotype.Component

@Component
class AppStorePageParser implements PageParser{
    public String getVersionFromHtmlByUrl(String url) throws IOException {

//        @Grab('org.jsoup:jsoup:1.7.1')

        def doc = org.jsoup.Jsoup.parse(new URL(url), 30000)
        String version
        if(url.startsWith("https://play.google.com"))
            version = doc?.select("div[itemprop=datePublished]").first()?.text()
        else
            version = doc?.select("span[itemprop=softwareVersion]").first()?.text()
        return version
    }
}
