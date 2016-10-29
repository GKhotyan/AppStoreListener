import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class PlayMarketParserTest {
    @Test
    public void getUpdateData(){
        String url = "https://play.google.com/store/apps/details?id=com.ubercab&hl=en";
        try {
            Document doc = org.jsoup.Jsoup.parse(new URL(url), 30000);
            String version = doc.select("div[itemprop=datePublished]").first().text();
            assertNotNull(version);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
