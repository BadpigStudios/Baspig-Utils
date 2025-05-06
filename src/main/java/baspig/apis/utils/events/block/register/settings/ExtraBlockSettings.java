package baspig.apis.utils.events.block.register.settings;

import com.mojang.serialization.Codec;

/**
 * This applies the given settings for Baspig Register Block Presets
 *
 * @author Baspig_
 */
public class ExtraBlockSettings {
    public static final Codec<ExtraBlockSettings> CODEC = Codec.unit((baspig.apis.utils.events.block.register.settings.ExtraBlockSettings::create));

    float explosionRadius;
    int fuse;

    public ExtraBlockSettings(){
    }

    public Codec<ExtraBlockSettings> getCodec(){
        return CODEC;
    }

    public static ExtraBlockSettings create() {
        return new ExtraBlockSettings();
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public ExtraBlockSettings explosionRadius(float explosionRadius) {
        this.explosionRadius = explosionRadius;
        return this;
    }

    public int getFuse() {
        return fuse;
    }

    public ExtraBlockSettings fuse(int fuse) {
        this.fuse = fuse;
        return this;
    }

    public ExtraBlockSettings build(){
        return this;
    }
}
