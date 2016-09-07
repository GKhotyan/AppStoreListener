import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

/**
 * Created by user on 06.09.2016.
 */
public class PageParser {
    public String getVersion(String url) throws IOException {
        Document doc = org.jsoup.Jsoup.parse(new URL(url), 30000);

        Element element = doc.select("span[itemprop=softwareVersion]").first();

        return element==null?null:element.text();

    }
}
