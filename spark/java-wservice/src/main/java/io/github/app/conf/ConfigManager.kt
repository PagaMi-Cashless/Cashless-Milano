/**
 * @author Ayoub Chouak (a.chouak)
 * @brief  Static Configuration Manager
 * @todo   Read the data from a XML file
 */

package io.github.app.conf

import java.util.*

class ConfigManager private constructor()
{
    private val CONFIG_MAP: HashMap<ConfigKey, String> = HashMap()

    init {

        // Spark Environment Port
        val sparkPortEnv = System.getenv("SPARK_PORT")
        CONFIG_MAP.put(ConfigKey.KEY_SPARK_PORT, sparkPortEnv ?: "1337")
    }

    companion object {
        private var _cfgInstance: ConfigManager? = null

        fun bootstrap() {
            if (_cfgInstance == null) {
                _cfgInstance = ConfigManager()
            } else
                throw RuntimeException("ConfigManager need not be initialized more than once.")
        }

        fun getKeyValue(key: ConfigKey): String? {
            return _cfgInstance!!.CONFIG_MAP[key]
        }
    }
}
