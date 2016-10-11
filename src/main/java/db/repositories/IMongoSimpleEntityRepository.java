package db.repositories;

import db.entities.SimpleEntity;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableAutoConfiguration
public interface IMongoSimpleEntityRepository extends CrudRepository<SimpleEntity, String>
{
    public SimpleEntity findById(int id);
}