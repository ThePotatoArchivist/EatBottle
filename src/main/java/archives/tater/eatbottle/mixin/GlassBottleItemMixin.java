package archives.tater.eatbottle.mixin;

import archives.tater.eatbottle.EatBottle;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GlassBottleItem.class)
public abstract class GlassBottleItemMixin extends Item {
    public GlassBottleItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyArg(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;<init>(Lnet/minecraft/item/Item$Settings;)V")
    )
    private static Settings setFood(Settings settings) {
        return settings.food(EatBottle.GLASS_BOTTLE_FOOD, EatBottle.GLASS_BOTTLE_CONSUMABLE);
    }

    @ModifyReturnValue(
            at = @At("RETURN"),
            method = "use")
    private ActionResult allowEat(ActionResult original, @Local(argsOnly = true) World world, @Local(argsOnly = true)PlayerEntity user, @Local(argsOnly = true) Hand hand) {
        return original == ActionResult.PASS ? super.use(world, user, hand) : original;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world instanceof ServerWorld serverWorld)
            user.damage(serverWorld, EatBottle.of(world, EatBottle.EAT_GLASS_DAMAGE_TYPE), 1f);
        return super.finishUsing(stack, world, user);
    }
}
