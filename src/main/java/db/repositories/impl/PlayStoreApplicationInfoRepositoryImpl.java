package db.repositories.impl;

import db.entities.PlayStoreApplicationInfo;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class PlayStoreApplicationInfoRepositoryImpl extends ApplicationInfoRepositoryImpl {

    @Override
    public void pushVersion(String objectId, String version) {
        getMongoTemplate().updateFirst(
                Query.query(Criteria.where("id").is(objectId)),
                new Update().push("versions", version), PlayStoreApplicationInfo.class);
    }

    @Override
    public String getMaxVersion(String objectId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(objectId));
        query.fields().include("versions");

        List<String> versions = getMongoTemplate().findOne(query, PlayStoreApplicationInfo.class).getVersions();

        return versions.stream()
                .max((e1, e2) -> new DefaultArtifactVersion(e1)
                        .compareTo(new DefaultArtifactVersion(e2))).orElse(null);
    }
}
