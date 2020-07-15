package org.bonge.convert.bossbar;

import org.bonge.convert.Converter;
import org.bukkit.boss.BarStyle;
import org.spongepowered.api.boss.BossBarOverlay;
import org.spongepowered.api.boss.BossBarOverlays;

import java.io.IOException;

public class StyleConverter implements Converter<org.bukkit.boss.BarStyle, org.spongepowered.api.boss.BossBarOverlay> {
    @Override
    public Class<BossBarOverlay> getSpongeClass() {
        return BossBarOverlay.class;
    }

    @Override
    public Class<BarStyle> getBukkitClass() {
        return BarStyle.class;
    }

    @Override
    public BossBarOverlay from(BarStyle value) throws IOException {
        switch (value){
            case SOLID: return BossBarOverlays.PROGRESS.get();
            case SEGMENTED_6: return BossBarOverlays.NOTCHED_6.get();
            case SEGMENTED_10: return BossBarOverlays.NOTCHED_10.get();
            case SEGMENTED_12: return BossBarOverlays.NOTCHED_12.get();
            case SEGMENTED_20: return BossBarOverlays.NOTCHED_20.get();
            default: throw new IOException("Unknown style of " + value.name());
        }
    }

    @Override
    public BarStyle to(BossBarOverlay overlay) throws IOException{
        if(overlay.equals(BossBarOverlays.NOTCHED_6.get())){
            return BarStyle.SEGMENTED_6;
        }else if(overlay.equals(BossBarOverlays.NOTCHED_10.get())){
            return BarStyle.SEGMENTED_10;
        }else if(overlay.equals(BossBarOverlays.NOTCHED_12.get())){
            return BarStyle.SEGMENTED_12;
        }else if(overlay.equals(BossBarOverlays.NOTCHED_20.get())) {
            return BarStyle.SEGMENTED_20;
        }else if(overlay.equals(BossBarOverlays.PROGRESS.get())){
            return BarStyle.SOLID;
        }else{
            throw new IOException("Unknown style of " + overlay.getKey().getValue());
        }
    }
}
