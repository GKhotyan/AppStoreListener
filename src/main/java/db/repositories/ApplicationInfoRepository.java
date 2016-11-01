package db.repositories;

import db.entities.BaseApplicationInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
@EnableAutoConfiguration
public interface ApplicationInfoRepository<T extends BaseApplicationInfo, BigInteger extends Serializable>
        extends MongoRepository<T, BigInteger>, CustomApplicationInfoRepository
{
    List<T> findByName(String name);

    @Query("{name : ?0}")
    public BaseApplicationInfo findByNameQuery(String name);

}
