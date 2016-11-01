package db.repositories;

import db.entities.PlayStoreApplicationInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
@EnableAutoConfiguration
public interface PlayStoreApplicationInfoRepository extends ApplicationInfoRepository<PlayStoreApplicationInfo, BigInteger> {
}
