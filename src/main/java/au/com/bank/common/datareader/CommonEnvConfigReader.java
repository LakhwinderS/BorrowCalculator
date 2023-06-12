package au.com.bank.common.datareader;

import au.com.bank.common.datatypes.CommonEnvConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class CommonEnvConfigReader {
    private static CommonEnvConfig envConfig;
    private CommonEnvConfigReader(){

    }
     public static CommonEnvConfig getEnvConfig () {
            if (envConfig == null) {
                envConfig = mapEnvConfig();
                if(null!=envConfig){
                    envConfig.setEnvConfigKey("envConfig");
                }
            }
            return envConfig;
        }

        public static CommonEnvConfig mapEnvConfig () {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                /* Returning Login data from Json file
                 */
                return mapper.readValue(new File("src/main/resources/EnvConfig.json"), CommonEnvConfig.class);
            } catch (Exception exp) {
                return null;
            }
        }

}



