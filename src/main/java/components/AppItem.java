package components;

/**
 * Created by user on 05.09.2016.
 */
public class AppItem {

    private String name = null;
    private String version = null;
    private String httpPath = null;
    private String htmlPath = null;
    private int errorCount = 0;

    public AppItem(String name, String httpPath) {
        this.name = name;
        this.httpPath = httpPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
}
