package db.repositories;

import db.entities.ApplicationInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
@EnableAutoConfiguration
public interface ApplicationInfoRepository extends MongoRepository<ApplicationInfo, BigInteger>,
        CustomApplicationInfoRepository
{

    List<ApplicationInfo> findByName(String name);

    @Query("{name : ?0}")
    public ApplicationInfo findByNameQuery(String name);


}
