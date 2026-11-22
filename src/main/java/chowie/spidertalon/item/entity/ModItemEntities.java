package chowie.spidertalon.item.entity;

import chowie.spidertalon.SpiderTalon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModItemEntities {
    public static final EntityType<SpiderTalonEntity> SPIDER_TALON = Registry.register(
            BuiltInRegistries.ENTITY_TYPE, SpiderTalon.id("spider_talon"),
            EntityType.Builder.<SpiderTalonEntity>of(SpiderTalonEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).eyeHeight(0.13f)
                    .clientTrackingRange(4).updateInterval(20)
                    .build("spider_talon"));

    public static void init() {

    }
}
