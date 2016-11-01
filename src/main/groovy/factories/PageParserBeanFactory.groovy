package factories

import db.entities.AppStoreApplicationInfo
import db.entities.BaseApplicationInfo
import db.entities.PlayStoreApplicationInfo
import org.springframework.beans.factory.annotation.Autowired
import utils.AppStorePageParser
import utils.PageParser
import utils.PlayStorePageParser

class PageParserBeanFactory {

    @Autowired
    private AppStorePageParser appStorePageParser;

    @Autowired
    private PlayStorePageParser playStorePageParser;

    public PageParser getPageParser(BaseApplicationInfo applicationInfo) {

        if (applicationInfo instanceof AppStoreApplicationInfo) {
            return appStorePageParser;
        } else if (applicationInfo instanceof PlayStoreApplicationInfo) {
            return playStorePageParser;
        }
    }
}
