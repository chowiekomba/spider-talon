package chowie.spidertalon;

import chowie.spidertalon.item.ModItems;
import chowie.spidertalon.item.entity.ModItemEntities;
import chowie.spidertalon.item.entity.SpiderTalonRenderer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpiderTalon implements ModInitializer {
	public static final String MOD_ID = "spider-talon";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing " + MOD_ID + "!");
		ModItems.init();
		ModItemEntities.init();
		EntityRendererRegistry.register(ModItemEntities.SPIDER_TALON, SpiderTalonRenderer::new);

		ItemProperties.register(
				ModItems.SPIDER_TALON,
				ResourceLocation.withDefaultNamespace("throwing"),
				(itemStack, clientLevel, livingEntity, i) ->
						livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F
		);
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
