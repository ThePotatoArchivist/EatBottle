package archives.tater.eatbottle;

import net.fabricmc.api.ModInitializer;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EatBottle implements ModInitializer {

	public static final String MOD_ID = "eatbottle";

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static FoodComponent GLASS_BOTTLE_FOOD = new FoodComponent.Builder()
			.nutrition(1)
			.alwaysEdible()
			.build();

	public static final RegistryKey<DamageType> EAT_GLASS_DAMAGE_TYPE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, id("eat_glass"));

	public static DamageSource of(World world, RegistryKey<DamageType> key) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
	}

	@Override
	public void onInitialize() {

	}
}
