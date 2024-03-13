package archives.tater.eatbottle.mixin;

import archives.tater.eatbottle.EatBottle;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Items.class)
public class ItemsMixin {
	@ModifyArg(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/item/GlassBottleItem;<init>(Lnet/minecraft/item/Item$Settings;)V"),
			method = "<clinit>",
			index = 0
	)
	private static Item.Settings makeGlassEdible(Item.Settings settings) {
		return settings.food(EatBottle.GLASS_BOTTLE_FOOD);
	}

}
