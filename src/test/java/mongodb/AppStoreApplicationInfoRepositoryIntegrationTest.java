package mongodb;

import com.mongodb.Mongo;
import db.entities.AppStoreApplicationInfo;
import db.repositories.AppStoreApplicationInfoRepository;
import db.repositories.ApplicationInfoRepository;
import db.repositories.impl.AppStoreApplicationInfoRepositoryImpl;
import db.repositories.impl.ApplicationInfoRepositoryImpl;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
public class AppStoreApplicationInfoRepositoryIntegrationTest {
    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "itest";
    private static final int MONGO_TEST_PORT = 27028;

    AppStoreApplicationInfoRepositoryImpl appStoreRepository;

    private MongoTemplate template;
    private static MongodExecutable mongodExecutable;
    private static MongodProcess mongoProcess;
    private static Mongo mongo;

    @BeforeClass
    public static void initializeDB() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(MONGO_TEST_PORT, Network.localhostIsIPv6()))
                .build();
        mongodExecutable = starter.prepare(mongodConfig);
        mongoProcess = mongodExecutable.start();

        mongo = new Mongo(LOCALHOST, MONGO_TEST_PORT);
        mongo.getDB(DB_NAME);
    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
        mongo.close();
        mongodExecutable.stop();
    }


    @Before
    public void setUp() throws Exception {
        appStoreRepository = new AppStoreApplicationInfoRepositoryImpl();
        template = new MongoTemplate(mongo, DB_NAME);
        appStoreRepository.setMongoTemplate(template);
    }

    @After
    public void tearDown() throws Exception {
        template.dropCollection(AppStoreApplicationInfo.class);
    }

    @Test
    public void testSave() {
        AppStoreApplicationInfo applicationInfo = new AppStoreApplicationInfo();
        applicationInfo.setName("TestName");
        applicationInfo.setUrl("TestUrl");

        template.save(applicationInfo);
        int samplesInCollection = template.findAll(AppStoreApplicationInfo.class).size();

        assertEquals("Only 1 Sample should exist in collection, but there are "
                     + samplesInCollection, 1, samplesInCollection);
    }

    @Test
    public void testPushVersion() {
        AppStoreApplicationInfo applicationInfo = new AppStoreApplicationInfo();
        applicationInfo.setName("Test2Name");
        applicationInfo.setUrl("Test2Url");

        template.save(applicationInfo);
        AppStoreApplicationInfo appStoreApplicationInfo = appStoreRepository.findByName("Test2Name");
        appStoreRepository.pushVersion(appStoreApplicationInfo.getId(), "1.1.0");

        appStoreApplicationInfo = appStoreRepository.findByName("Test2Name");

        assertEquals("Pushed version should exist ",
                appStoreApplicationInfo.getVersions().get(0), "1.1.0");
    }
}
