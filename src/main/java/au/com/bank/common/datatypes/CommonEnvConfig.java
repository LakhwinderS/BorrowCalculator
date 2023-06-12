package au.com.bank.common.datatypes;

public class CommonEnvConfig {
    private String envConfigKey;
    private String environmentName;
    private String url;

    public String getEnvConfigKey() {
        return envConfigKey;
    }

    public void setEnvConfigKey(String envConfigKey) {
        this.envConfigKey = envConfigKey;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CommonEnvConfig{" +
                "envConfigKey='" + envConfigKey + '\'' +
                ", environmentName='" + environmentName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
