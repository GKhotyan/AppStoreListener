package config

import factories.PageParserBeanFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean

//def properties = new Properties()
//properties.load(new ClassPathResource('private.properties').inputStream);
YamlPropertiesFactoryBean ymlProperties = new YamlPropertiesFactoryBean()
ymlProperties.setResources(new ClassPathResource('application.yml'))
java.util.Properties properties = ymlProperties.getObject()
def config = new ConfigSlurper().parse(properties)

Resource mongeezConfig = new ClassPathResource('mongeez/mongeez.xml')

beans {
    mongoURI(com.mongodb.MongoURI, config.spring.data.mongodb.uri)
    mongeez(org.mongeez.Mongeez,
            file: mongeezConfig,
            dbName: config.spring.data.mongodb.database)

    pageParserBeanFactory(PageParserBeanFactory)
    scheduledTasks(common.ScheduledTasks,
            pageParserBeanFactory: pageParserBeanFactory,
            mongoURI: mongoURI,
            mongeez: mongeez
    )
    configObject(ConfigObject)

}