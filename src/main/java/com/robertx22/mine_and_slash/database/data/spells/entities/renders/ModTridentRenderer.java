package com.robertx22.mine_and_slash.database.data.spells.entities.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.Projectile;

public class ModTridentRenderer extends EntityRenderer<Projectile> {
    public static final ResourceLocation TEXTURE = ResourceLocation.parse("textures/entity/trident.png");
    private final TridentModel model;

    public ModTridentRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);

        this.model = new TridentModel(ctx.bakeLayer(ModelLayers.TRIDENT));

    }

    @Override
    public void render(Projectile tridentEntity, float f, float g, PoseStack matrixStack,
            MultiBufferSource vertexConsumerProvider, int i) {
        matrixStack.pushPose();
        matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, tridentEntity.yRotO, tridentEntity.getYRot()) - 90.0F));
        matrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, tridentEntity.xRotO, tridentEntity.getXRot()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(vertexConsumerProvider,
                this.model.renderType(this.getTextureLocation(tridentEntity)), false, false);
        // 1.21: renderToBuffer now takes an ARGB int instead of 4 float color values\n
        // // -1 = 0xFFFFFFFF = white opaque (equivalent to r=1, g=1, b=1, a=1)\n
        // this.model.renderToBuffer(matrixStack, vertexConsumer, i,
        // OverlayTexture.NO_OVERLAY, -1);
        matrixStack.popPose();
        super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public ResourceLocation getTextureLocation(Projectile tridentEntity) {
        return TEXTURE;
    }

}
