package utils

import org.springframework.stereotype.Component

@Component
class PlayStorePageParser implements PageParser{
    public String getVersionFromHtmlByUrl(String url) throws IOException {

        def doc = org.jsoup.Jsoup.parse(new URL(url), 30000)
        String version = doc?.select("div[itemprop=datePublished]").first()?.text()
        return version
    }
}
