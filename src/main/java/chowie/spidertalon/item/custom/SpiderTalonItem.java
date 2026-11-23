package chowie.spidertalon.item.custom;

import chowie.spidertalon.item.entity.SpiderTalonEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpiderTalonItem extends TridentItem {
    public SpiderTalonItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }
        if (EnchantmentHelper.getTridentSpinAttackStrength(itemStack, player) > 0) {
            super.releaseUsing(itemStack, level, livingEntity, i);
            return;
        }
        if (this.getUseDuration(itemStack, livingEntity) - i < 10) {
            return;
        }
        if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
            return;
        }

        if (!level.isClientSide) {
            itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
            SpiderTalonEntity thrown = new SpiderTalonEntity(level, player, itemStack);
            thrown.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
            if (player.hasInfiniteMaterials()) thrown.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            level.addFreshEntity(thrown);
            level.playSound(null, thrown, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            if (!player.hasInfiniteMaterials()) {
                player.getInventory().removeItem(itemStack);
            }
        }
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 10 * 20));
        return super.hurtEnemy(itemStack, livingEntity, livingEntity2);
    }

    @Override
    public @NotNull Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        SpiderTalonEntity thrownTrident = new SpiderTalonEntity(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
        thrownTrident.pickup = AbstractArrow.Pickup.ALLOWED;
        return thrownTrident;
    }
}
