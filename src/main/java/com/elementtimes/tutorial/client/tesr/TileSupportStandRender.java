package com.elementtimes.tutorial.client.tesr;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 复制了调用EntityItem.doRender方法需要的所有方法,然而结果还是有问题QAQ
 * @author KSGFK create in 2019/6/12
 */
public class TileSupportStandRender extends FastTESR<TileSupportStand> {
    private ItemStack willRender = new ItemStack(Items.DIAMOND);
    private EntityItem ei = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, willRender);

    private RenderEntityItem renderEntityItem;
    private static boolean isInit = false;

    private final Random random = new Random();
    protected boolean renderOutlines = true;

    private void init() {
        RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        Map<Class<? extends Entity>, Render<? extends Entity>> map = rm.entityRenderMap;
        renderEntityItem = (RenderEntityItem) map.get(EntityItem.class);
        isInit = true;
    }

    @Override
    public void renderTileEntityFast(TileSupportStand te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        if (!isInit) {
            init();
        }

        World world = te.getWorld();
        if (!world.isRemote) {
            return;
        }

        //ItemStack stack = ei.getItem();

        //BlockPos pos = te.getPos();
        //ei.posX = pos.getX();
        //ei.posY = pos.getY();
        //ei.posZ = pos.getZ();

        //ei.prevRotationYaw = 45;
        //GlStateManager.pushMatrix();
        //GlStateManager.rotate(45, 0, 1, 0);

        //ElementTimes.getLogger().info("pos:{},{},{}", (float)x, (float)y, (float)z);

        //GlStateManager.translate(0.5, 0, 0);
        //ItemStack willRender = new ItemStack(Items.DIAMOND);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.5, -0.25, 0.5);
        //EntityItem ei = new EntityItem(te.getWorld());
        ei.setItem(new ItemStack(Item.getItemFromBlock(ElementtimesBlocks.rubberLog)));//调试的时候换成树干了
        //内部还是在实例化Tessellator和BufferBuilder,我把它连根抽出来会出问题...
        renderEntityItem.doRender(ei, x, y, z, 0, partialTicks);
        //this.doRender(ei, x, y, z, partialTicks, buffer);//就是这句话
        GlStateManager.popMatrix();
        //GlStateManager.popMatrix();


        //RenderManager rm = Minecraft.getMinecraft().getRenderManager();
        //rm.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        //以下来自
        //https://github.com/TartaricAcid/BakaInTouhou/blob/master/src/main/java/com/github/tartaricacid/bakaintouhou/client/render/danmaku/EntityNormalDanmakuRender.java
        //但没啥用
        /*
        GlStateManager.disableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);

        GlStateManager.translate(x, y, z);
        buffer.pos(-1.2, 1.2, 0).tex((0 + 0) / 16, (0 + 32) / 16).endVertex();
        buffer.pos(-1.2, -1.2, 0).tex((0 + 0) / 16, (0 + 0) / 16).endVertex();
        buffer.pos(1.2, -1.2, 0).tex((0 + 32) / 16, (0 + 0) / 16).endVertex();
        buffer.pos(1.2, 1.2, 0).tex((0 + 32) / 16, (0 + 32) / 16).endVertex();

        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        */
    }

    public void doRender(EntityItem entity, double x, double y, double z, float partialTicks, BufferBuilder buffer) {
        ItemStack itemstack = entity.getItem();
        int i = itemstack.isEmpty() ? 187 : Item.getIdFromItem(itemstack.getItem()) + itemstack.getMetadata();
        this.random.setSeed((long) i);
        boolean flag = false;

        if (this.bindEntityTexture(entity)) {
            Minecraft.getMinecraft().getRenderManager().renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            flag = true;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.pushMatrix();
        IBakedModel ibakedmodel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(itemstack, entity.world, (EntityLivingBase) null);
        int j = this.transformModelCount(entity, x, y, z, partialTicks, ibakedmodel);
        boolean flag1 = ibakedmodel.isGui3d();

        if (!flag1) {
            float f3 = -0.0F * (float) (j - 1) * 0.5F;
            float f4 = -0.0F * (float) (j - 1) * 0.5F;
            float f5 = -0.09375F * (float) (j - 1) * 0.5F;
            GlStateManager.translate(f3, f4, f5);
        }

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            //GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        for (int k = 0; k < j; ++k) {
            if (flag1) {
                GlStateManager.pushMatrix();

                if (k > 0) {
                    float f7 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f9 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f6 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    GlStateManager.translate(shouldSpreadItems() ? f7 : 0, shouldSpreadItems() ? f9 : 0, f6);
                }

                IBakedModel transformedModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
                this.renderItem(itemstack, transformedModel, buffer);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.pushMatrix();

                if (k > 0) {
                    float f8 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    float f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    GlStateManager.translate(f8, f10, 0.0F);
                }

                IBakedModel transformedModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
                this.renderItem(itemstack, transformedModel, buffer);
                GlStateManager.popMatrix();
                GlStateManager.translate(0.0F, 0.0F, 0.09375F);
            }
        }

        if (this.renderOutlines) {
           GlStateManager.disableOutlineMode();
           GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);

        if (flag) {
            Minecraft.getMinecraft().getRenderManager().renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
        }
    }

    private ResourceLocation getEntityTexture(EntityItem entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    private boolean bindEntityTexture(EntityItem entity) {
        ResourceLocation resourcelocation = this.getEntityTexture(entity);

        if (resourcelocation == null) {
            return false;
        } else {
            this.bindTexture(resourcelocation);
            return true;
        }
    }

    public void bindTexture(ResourceLocation location) {
        Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(location);
    }

    private int transformModelCount(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
        ItemStack itemstack = itemIn.getItem();
        Item item = itemstack.getItem();

        if (item == null) {
            return 0;
        } else {
            boolean flag = p_177077_9_.isGui3d();
            int i = this.getModelCount(itemstack);
            float f = 0.25F;
            float f1 = shouldBob() ? MathHelper.sin(((float) itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F : 0;
            float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
            GlStateManager.translate((float) p_177077_2_, (float) p_177077_4_ + f1 + 0.25F * f2, (float) p_177077_6_);

            if (flag || Minecraft.getMinecraft().getRenderManager().options != null) {
                float f3 = (((float) itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI);
                GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return i;
        }
    }

    protected int getModelCount(ItemStack stack) {
        int i = 1;

        if (stack.getCount() > 48) {
            i = 5;
        } else if (stack.getCount() > 32) {
            i = 4;
        } else if (stack.getCount() > 16) {
            i = 3;
        } else if (stack.getCount() > 1) {
            i = 2;
        }

        return i;
    }

    public boolean shouldBob() {
        return true;
    }

    public boolean shouldSpreadItems() {
        return true;
    }

    public void renderItem(ItemStack stack, IBakedModel model, BufferBuilder buffer) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);

            if (model.isBuiltInRenderer()) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableRescaleNormal();
                stack.getItem().getTileEntityItemStackRenderer().renderByItem(stack);
            } else {
                this.renderModel(model, -1, stack, buffer);

                //if (stack.hasEffect()) {
                //    this.renderEffect(model);
                //}
            }

            GlStateManager.popMatrix();
        }
    }

    private void renderModel(IBakedModel model, int color, ItemStack stack, BufferBuilder buffer) {
        //Tessellator tessellator = Tessellator.getInstance();
        //BufferBuilder bufferbuilder = tessellator.getBuffer();
        //bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

        for (EnumFacing enumfacing : EnumFacing.values()) {
            this.renderQuads(buffer, model.getQuads((IBlockState) null, enumfacing, 0L), color, stack);
        }

        this.renderQuads(buffer, model.getQuads((IBlockState) null, (EnumFacing) null, 0L), color, stack);
        //tessellator.draw();
    }

    private void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, ItemStack stack) {
        boolean flag = color == -1 && !stack.isEmpty();
        int i = 0;

        for (int j = quads.size(); i < j; ++i) {
            BakedQuad bakedquad = quads.get(i);
            int k = color;

            if (flag && bakedquad.hasTintIndex()) {
                k = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, bakedquad.getTintIndex());

                if (EntityRenderer.anaglyphEnable) {
                    k = TextureUtil.anaglyphColor(k);
                }

                k = k | -16777216;
            }

            net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, k);
        }
    }

    protected int getTeamColor(EntityItem entityIn) {
        int i = 16777215;
        ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam) entityIn.getTeam();

        if (scoreplayerteam != null) {
            String s = FontRenderer.getFormatFromString(scoreplayerteam.getPrefix());

            if (s.length() >= 2) {
                i = this.getFontRendererFromRenderManager().getColorCode(s.charAt(1));
            }
        }

        return i;
    }

    public FontRenderer getFontRendererFromRenderManager() {
        return Minecraft.getMinecraft().getRenderManager().getFontRenderer();
    }
/*
    private void renderEffect(IBakedModel model) {
        GlStateManager.depthMask(false);
        GlStateManager.depthFunc(514);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
        this.textureManager.bindTexture(RES_ITEM_GLINT);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        float f = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F / 8.0F;
        GlStateManager.translate(f, 0.0F, 0.0F);
        GlStateManager.rotate(-50.0F, 0.0F, 0.0F, 1.0F);
        this.renderModel(model, -8372020);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 8.0F, 8.0F);
        float f1 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F / 8.0F;
        GlStateManager.translate(-f1, 0.0F, 0.0F);
        GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
        this.renderModel(model, -8372020);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableLighting();
        GlStateManager.depthFunc(515);
        GlStateManager.depthMask(true);
        this.textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
    }
 */
}
