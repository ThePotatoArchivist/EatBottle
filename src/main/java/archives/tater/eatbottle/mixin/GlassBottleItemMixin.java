package archives.tater.eatbottle.mixin;

import archives.tater.eatbottle.EatBottle;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BottleItem.class)
public abstract class GlassBottleItemMixin extends Item {
    public GlassBottleItemMixin(Properties settings) {
        super(settings);
    }

    @ModifyReturnValue(
            at = @At("RETURN"),
            method = "use")
    private InteractionResult allowEat(InteractionResult original, @Local(argsOnly = true) Level world, @Local(argsOnly = true)Player user, @Local(argsOnly = true) InteractionHand hand) {
        return original == InteractionResult.PASS ? super.use(world, user, hand) : original;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (world instanceof ServerLevel serverWorld)
            user.hurtServer(serverWorld, EatBottle.of(world, EatBottle.EAT_GLASS_DAMAGE_TYPE), 1f);
        return super.finishUsingItem(stack, world, user);
    }
}
