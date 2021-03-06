package db.repositories.impl;

import db.entities.BaseApplicationInfo;
import db.repositories.CustomApplicationInfoRepository;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class ApplicationInfoRepositoryImpl implements CustomApplicationInfoRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void pushVersion(String objectId, String version) {
        getMongoTemplate().updateFirst(
                Query.query(Criteria.where("id").is(objectId)),
                new Update().push("versions", version), BaseApplicationInfo.class);
    }

    @Override
    public String getMaxVersion(String objectId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(objectId));
        query.fields().include("versions");

        List<String> versions = getMongoTemplate().findOne(query, BaseApplicationInfo.class).getVersions();

        return versions.stream()
                        .max((e1, e2) -> new DefaultArtifactVersion(e1)
                        .compareTo(new DefaultArtifactVersion(e2))).orElse(null);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}
