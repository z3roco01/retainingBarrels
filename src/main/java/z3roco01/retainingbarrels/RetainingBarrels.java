package z3roco01.retainingbarrels;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetainingBarrels implements ModInitializer {
	public static final String MODID = "retaining_barrels";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("init !");
	}
}