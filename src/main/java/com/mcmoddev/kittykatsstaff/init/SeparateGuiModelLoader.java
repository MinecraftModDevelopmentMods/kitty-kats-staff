package com.mcmoddev.kittykatsstaff.init;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mcmoddev.kittykatsstaff.KittyKatsStaff;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class SeparateGuiModelLoader implements ICustomModelLoader {

    public static ResourceLocation location(String path) {
        return new ResourceLocation(KittyKatsStaff.MODID, path);
    }

    public static final ResourceLocation FAKE_LOCATION = location("models/item/staff_of_kittys");


    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        // we don't cache anything here
    }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.equals(FAKE_LOCATION);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        return new Model();
    }

    public static class Model implements IModel {
        private final ResourceLocation baseModel;
        private final ResourceLocation guiModel;
        private final ImmutableMap<String, String> baseModelTextures;
        private final ImmutableMap<String, String> guiModelTextures;
        private final String particleTexture;

        public Model() {
            this.baseModel = null;
            this.guiModel = null;
            this.baseModelTextures = ImmutableMap.<String, String>builder().build();
            this.guiModelTextures = ImmutableMap.<String, String>builder().build();
            this.particleTexture = null;
        }

        public Model(@Nullable ResourceLocation baseModel, @Nullable ResourceLocation guiModel,
                     ImmutableMap<String, String> baseModelTextures, ImmutableMap<String, String> guiModelTextures,
                     String particleTexture) {
            this.baseModel = baseModel;
            this.guiModel = guiModel;
            this.baseModelTextures = baseModelTextures;
            this.guiModelTextures = guiModelTextures;
            this.particleTexture = particleTexture;
        }

        @Override
        public Collection<ResourceLocation> getDependencies() {
            Set<ResourceLocation> locs = Sets.newHashSet();
            if (baseModel != null) {
                locs.add(baseModel);
            }

            if (guiModel != null) {
                locs.add(guiModel);
            }
            return locs;
        }

        @Override
        public Collection<ResourceLocation> getTextures() {
            Set<ResourceLocation> locs = Sets.newHashSet();
            if (particleTexture != null) {
                locs.add(new ResourceLocation(particleTexture));
            }

            baseModelTextures.values().stream().filter(e -> !e.startsWith("#")).forEach(e -> locs.add(new ResourceLocation(e)));
            guiModelTextures.values().stream().filter(e -> !e.startsWith("#")).forEach(e -> locs.add(new ResourceLocation(e)));
            return locs;
        }

        @Override
        public IModel retexture(ImmutableMap<String, String> textures) {
            String tex = textures.get("particle");
            while (tex != null && tex.startsWith("#")) {
                tex = textures.get(tex);
            }

            return new Model(baseModel, guiModel, baseModelTextures, guiModelTextures, tex);
        }

        @Override
        public IModel process(ImmutableMap<String, String> customData) {
            ResourceLocation newBaseModel = baseModel;
            ResourceLocation newGuiModel = guiModel;
            ImmutableMap<String, String> newBaseTextures = baseModelTextures;
            ImmutableMap<String, String> newGuiTextures = guiModelTextures;

            if (customData.containsKey("base_model")) {
                String data = customData.get("base_model");
                String loc = new JsonParser().parse(data).getAsString();
                newBaseModel = loc.contains("#") ? new ModelResourceLocation(loc) : new ResourceLocation(loc);

                if (customData.containsKey("base_textures")) {
                    data = customData.get("base_textures");

                    ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();
                    JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
                    for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                        mapBuilder = mapBuilder.put(entry.getKey(), entry.getValue().getAsString());
                    }
                    newBaseTextures = mapBuilder.build();
                }
            }

            if (customData.containsKey("gui_model")) {
                String data = customData.get("gui_model");
                String loc = new JsonParser().parse(data).getAsString();

                newGuiModel = loc.contains("#") ? new ModelResourceLocation(loc) : new ResourceLocation(loc);

                if (customData.containsKey("gui_textures")) {
                    data = customData.get("gui_textures");

                    ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();
                    JsonObject obj = new JsonParser().parse(data).getAsJsonObject();
                    for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
                        mapBuilder = mapBuilder.put(entry.getKey(), entry.getValue().getAsString());
                    }
                    newGuiTextures = mapBuilder.build();
                }
            }

            return new Model(newBaseModel, newGuiModel, newBaseTextures, newGuiTextures, particleTexture);
        }

        @Override
        public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
            IModel baseIModel = ModelLoaderRegistry.getModelOrMissing(baseModel);
            if (!baseModelTextures.isEmpty()) {
                baseIModel = baseIModel.retexture(baseModelTextures);
            }

            IBakedModel baseBModel = baseIModel.bake(state, format, bakedTextureGetter);
            IModel guiIModel = ModelLoaderRegistry.getModelOrMissing(guiModel);
            if (!guiModelTextures.isEmpty()) {
                guiIModel = guiIModel.retexture(guiModelTextures);
            }

            IBakedModel guiBModel = guiIModel.bake(state, format, bakedTextureGetter);

            TextureAtlasSprite particleSprite = bakedTextureGetter.apply(new ResourceLocation(particleTexture));

            return new BakedModel(baseBModel, guiBModel, state, particleSprite);
        }
    }

    public static class BakedModel implements IBakedModel {
        private final IBakedModel baseModel;
        private final IBakedModel guiModel;
        private final IModelState state;
        private final TextureAtlasSprite particleTexture;

        public BakedModel(IBakedModel baseModel, IBakedModel guiModel, IModelState state, TextureAtlasSprite particleTexture) {
            this.baseModel = baseModel;
            this.guiModel = guiModel;
            this.state = state;
            this.particleTexture = particleTexture;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return Collections.emptyList();
        }

        @Override
        public boolean isAmbientOcclusion() {
            return false;
        }

        @Override
        public boolean isGui3d() {
            return false;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return particleTexture != null ? particleTexture : baseModel.getParticleTexture();
        }

        @Override
        public ItemOverrideList getOverrides() {
            return baseModel.getOverrides();
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            if (cameraTransformType == ItemCameraTransforms.TransformType.GUI) {
                return guiModel.handlePerspective(cameraTransformType);
            }

            return baseModel.handlePerspective(cameraTransformType);
        }
    }
}