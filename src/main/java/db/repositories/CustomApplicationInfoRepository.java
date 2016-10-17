package db.repositories;

public interface CustomApplicationInfoRepository {
    public void pushVersion(String objectId, String version);
}
