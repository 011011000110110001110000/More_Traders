package more.traders;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreTraders implements ModInitializer {
    public static final String MOD_NAME = "More Traders";
    public static final String MOD_VERSION = "1.0";
    public static final String MOD_ID = "more-traders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static int spawnSuccessPercentage = 10;  //default value is vanilla value (see WanderingTraderSpawner class)
    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("Loaded {} v.{}", MOD_NAME, MOD_VERSION);
    }
}
