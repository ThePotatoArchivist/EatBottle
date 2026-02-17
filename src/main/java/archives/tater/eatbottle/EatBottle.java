package archives.tater.eatbottle;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EatBottle implements ModInitializer {

	public static final String MOD_ID = "eatbottle";

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static FoodProperties GLASS_BOTTLE_FOOD = new FoodProperties.Builder()
			.nutrition(1)
			.alwaysEdible()
			.build();

	public static Consumable GLASS_BOTTLE_CONSUMABLE = Consumable.builder()
			.sound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.GLASS_BREAK))
			.build();

	public static final ResourceKey<DamageType> EAT_GLASS_DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, id("eat_glass"));

	public static DamageSource of(Level world, ResourceKey<DamageType> key) {
		return new DamageSource(world.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key));
	}

	@Override
	public void onInitialize() {
		DefaultItemComponentEvents.MODIFY.register(context ->
				context.modify(Items.GLASS_BOTTLE, builder -> builder
						.set(DataComponents.FOOD, GLASS_BOTTLE_FOOD)
						.set(DataComponents.CONSUMABLE, GLASS_BOTTLE_CONSUMABLE)
		));
	}
}
