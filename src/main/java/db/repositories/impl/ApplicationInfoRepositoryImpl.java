package db.repositories.impl;

import db.entities.ApplicationInfo;
import db.repositories.CustomApplicationInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class ApplicationInfoRepositoryImpl implements CustomApplicationInfoRepository {
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public void pushVersion(String objectId, String version) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(objectId)),
                new Update().push("versions", version), ApplicationInfo.class);
    }
}
