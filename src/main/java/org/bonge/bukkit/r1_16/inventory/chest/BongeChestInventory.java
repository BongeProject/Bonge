package org.bonge.bukkit.r1_16.inventory.chest;

import org.array.utils.ArrayUtils;
import org.bonge.Bonge;
import org.bonge.bukkit.r1_16.inventory.BongeInventory;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.block.entity.carrier.chest.Chest;
import org.spongepowered.api.item.inventory.type.BlockEntityInventory;

import java.io.IOException;
import java.util.List;

public class BongeChestInventory extends BongeInventory<BlockEntityInventory<Chest>> {

    public BongeChestInventory(BlockEntityInventory<Chest> value) {
        super(value);
    }

    @Override
    public @NotNull List<HumanEntity> getViewers() {
        return ArrayUtils.convert(e -> Bonge.getInstance().convert(e), this.spongeValue.viewers());
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public @Nullable org.bukkit.block.Chest getHolder() {
        try {
            return Bonge.getInstance().convert(org.bukkit.block.Chest.class, this.spongeValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public @Nullable Location getLocation() {
        org.bukkit.block.Chest chest = this.getHolder();
        if (chest == null) {
            return null;
        }
        return chest.getLocation();
    }
}
