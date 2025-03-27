package archives.tater.eatbottle.mixin;

import archives.tater.eatbottle.EatBottle;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GlassBottleItem.class)
public abstract class GlassBottleItemMixin extends Item {
    public GlassBottleItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyReturnValue(
            at = @At("RETURN"),
            method = "use")
    private TypedActionResult<ItemStack> allowEat(TypedActionResult<ItemStack> original, @Local(argsOnly = true) World world, @Local(argsOnly = true)PlayerEntity user, @Local(argsOnly = true) Hand hand) {
        return original.getResult() == ActionResult.PASS ? super.use(world, user, hand) : original;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.BLOCK_GLASS_BREAK;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.damage(EatBottle.of(world, EatBottle.EAT_GLASS_DAMAGE_TYPE), 1f);
        return super.finishUsing(stack, world, user);
    }
}
