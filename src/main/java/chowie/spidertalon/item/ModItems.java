package chowie.spidertalon.item;

import chowie.spidertalon.SpiderTalon;
import chowie.spidertalon.item.custom.SpiderTalonItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ModItems {

    public static final Item SPIDER_TALON = register(
            new SpiderTalonItem(
                    new Item.Properties()
                            .durability(500)
                            .component(DataComponents.TOOL, TridentItem.createToolProperties())
                            .attributes(
                                    ItemAttributeModifiers.builder()
                                            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                                                    Item.BASE_ATTACK_DAMAGE_ID, 5.5, AttributeModifier.Operation.ADD_VALUE
                                            ), EquipmentSlotGroup.MAINHAND)
                                            .add(Attributes.ATTACK_SPEED, new AttributeModifier(
                                                    Item.BASE_ATTACK_SPEED_ID, -2.6, AttributeModifier.Operation.ADD_VALUE
                                            ), EquipmentSlotGroup.MAINHAND)
                                            .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
                                                    SpiderTalon.id("base_interaction_range"), 1, AttributeModifier.Operation.ADD_VALUE
                                            ), EquipmentSlotGroup.MAINHAND)
                                            .build())
            ),
            "spider_talon"
    );

    public static final ResourceKey<CreativeModeTab> SPIDER_TALON_GROUP_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), SpiderTalon.id("spider_talon_group")
    );
    public static final CreativeModeTab SPIDER_TALON_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.SPIDER_TALON))
            .title(Component.translatable("itemGroup.spider-talon"))
            .build();

    public static Item register(Item item, String id) {
        ResourceLocation itemId = SpiderTalon.id(id);

        return Registry.register(BuiltInRegistries.ITEM, itemId, item);
    }

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SPIDER_TALON_GROUP_KEY, SPIDER_TALON_GROUP);

        ItemGroupEvents.modifyEntriesEvent(SPIDER_TALON_GROUP_KEY).register(itemGroup ->
            itemGroup.accept(ModItems.SPIDER_TALON)
        );
    }
}
