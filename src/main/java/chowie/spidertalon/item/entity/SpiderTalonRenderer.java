package chowie.spidertalon.item.entity;

import chowie.spidertalon.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpiderTalonRenderer extends EntityRenderer<SpiderTalonEntity> {
    private final ItemRenderer itemRenderer;

    public SpiderTalonRenderer(EntityRendererProvider.Context context) {
        super(context);
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SpiderTalonEntity entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, entity.yRotO, entity.getYRot()) - 90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, entity.xRotO, entity.getXRot()) - 135));
        itemRenderer.renderStatic(new ItemStack(ModItems.SPIDER_TALON), ItemDisplayContext.FIXED,
            i, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, f, g, poseStack, multiBufferSource, i);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiderTalonEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
