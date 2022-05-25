package com.chimericdream.minekea.settings;

import com.chimericdream.minekea.ModInfo;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.Map;

public abstract class MinekeaBlockSettings<T extends MinekeaBlockSettings<?>> extends FabricBlockSettings {
    protected String mainMaterial;
    protected Map<String, Identifier> materials;
    protected String modId = ModInfo.MOD_ID;
    protected Identifier blockId;
    protected int burnSpread = 0;
    protected int burnTime = 0;
    protected int fuelTime = 0;
    protected String defaultTranslation;
    protected Block baseBlock;

    protected MinekeaBlockSettings(Block block) {
        this(FabricBlockSettings.copyOf(block));

        this.baseBlock = block;
    }

    protected MinekeaBlockSettings(DefaultSettings settings) {
        super(settings);

        copySettings(settings);
    }

    protected MinekeaBlockSettings(AbstractBlock.Settings settings) {
        super(settings);

        if (settings instanceof DefaultSettings) {
            copySettings((DefaultSettings) settings);
        }
    }

    protected void copySettings(DefaultSettings settings) {
        this.mainMaterial = settings.mainMaterial;
        this.materials = settings.materials;
        this.modId = settings.modId;
        this.blockId = settings.blockId;
        this.burnTime = settings.burnTime;
        this.burnSpread = settings.burnSpread;
        this.fuelTime = settings.fuelTime;
        this.baseBlock = settings.baseBlock;
    }

    abstract public Identifier getBlockId();

    public Map<String, Identifier> getMaterials() {
        return this.materials;
    }

    public Block getBaseBlock() {
        return this.baseBlock;
    }

    public T baseBlock(Block baseBlock) {
        this.baseBlock = baseBlock;
        // noinspection unchecked
        return (T) this;
    }

    public T burnSpread(int spread) {
        this.burnSpread = spread;
        // noinspection unchecked
        return (T) this;
    }

    public T burnTime(int time) {
        this.burnTime = time;
        // noinspection unchecked
        return (T) this;
    }

    public T fuelTime(int time) {
        this.fuelTime = time;
        // noinspection unchecked
        return (T) this;
    }

    public T materials(Map<String, Identifier> materials) {
        validateMaterials(materials);
        this.materials = materials;
        // noinspection unchecked
        return (T) this;
    }

    public T material(String material) {
        this.mainMaterial = material;
        // noinspection unchecked
        return (T) this;
    }

    public T translation(String defaultTranslation) {
        this.defaultTranslation = defaultTranslation;
        // noinspection unchecked
        return (T) this;
    }

    public void validateMaterials(Map<String, Identifier> materials) {
        String[] keys = new String[]{"main"};

        for (String key : keys) {
            if (!materials.containsKey(key)) {
                throw new IllegalArgumentException(String.format("The materials must contain a '%s' key", key));
            }
        }
    }

    public static class DefaultSettings extends MinekeaBlockSettings<DefaultSettings> {
        public DefaultSettings(Block block) {
            super(block);
        }

        @Override
        public Identifier getBlockId() {
            return null;
        }
    }
}