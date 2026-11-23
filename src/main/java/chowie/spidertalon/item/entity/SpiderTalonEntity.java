package chowie.spidertalon.item.entity;

import chowie.spidertalon.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class SpiderTalonEntity extends ThrownTrident {
    public SpiderTalonEntity(EntityType<? extends ThrownTrident> entityType, Level level) {
        super(entityType, level);
    }

    public SpiderTalonEntity(Level level, double x, double y, double z, ItemStack itemStack) {
        super(ModItemEntities.SPIDER_TALON, level);
        setPos(x, y, z);
        setPickupItemStack(itemStack.copy());
        initTrident(itemStack);
    }

    public SpiderTalonEntity(Level level, LivingEntity owner, ItemStack itemStack) {
        super(ModItemEntities.SPIDER_TALON, level);
        setOwner(owner);
        setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        setPickupItemStack(itemStack.copy());
        initTrident(itemStack);
    }

    private void initTrident(ItemStack stack) {
        if (level() instanceof ServerLevel level) {
            int loyalty = EnchantmentHelper.getTridentReturnToOwnerAcceleration(level, stack, this);
            entityData.set(ThrownTrident.ID_LOYALTY, (byte) Mth.clamp(loyalty, 0, 127));
        }
        entityData.set(ThrownTrident.ID_FOIL, stack.hasFoil());
    }

    @Override
    protected @NotNull ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.SPIDER_TALON);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = 8.0F;
        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.damageSources().trident(this, entity2 == null ? this : entity2);
        if (this.level() instanceof ServerLevel serverLevel) {
            f = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, f);
        }

        if (entity.hurt(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damageSource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingEntity) {
                this.doKnockback(livingEntity, damageSource);
                this.doPostHurtEffects(livingEntity);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 10 * 20));
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }
}
