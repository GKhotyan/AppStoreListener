package db.repositories;

import db.entities.AppStoreApplicationInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
@EnableAutoConfiguration
public interface AppStoreApplicationInfoRepository extends ApplicationInfoRepository<AppStoreApplicationInfo, BigInteger>
{

}
