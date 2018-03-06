/**
 *  @author Ayoub Chouak (a.chouak)
 *  @brief  Static Configuration Manager
 *  @todo   Read the data from a XML file
 *
 */

package io.github.app.conf;

import java.util.*;

public class ConfigManager
{
    private static ConfigManager _cfgInstance = null;
    private final HashMap<ConfigKey, String> _CONFIG_MAP;

    private ConfigManager()
    {
        _CONFIG_MAP = new HashMap<ConfigKey, String>();

        // Spark Environment Port
        String sparkPortEnv = System.getenv("SPARK_PORT");
        _CONFIG_MAP.put(ConfigKey.KEY_SPARK_PORT, sparkPortEnv != null ? sparkPortEnv : "1337");
    }

    public static void bootstrap()
    {
        if (_cfgInstance == null)
        {
            _cfgInstance = new ConfigManager();
        }
        else
            throw new RuntimeException("ConfigManager need not be initialized more than once.");
    }

    public static String getKeyValue(ConfigKey key) {
        return _cfgInstance._CONFIG_MAP.get(key);
    }
}
