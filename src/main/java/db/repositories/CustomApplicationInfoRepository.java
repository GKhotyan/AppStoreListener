package db.repositories;

public interface CustomApplicationInfoRepository {
    public void pushVersion(String objectId, String version);

    public String getMaxVersion(String objectId);
}
