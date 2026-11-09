package com.example.hack.module;

import net.minecraft.block.entity.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

/**
 * Scans loaded chunks for clusters of player-style block entities
 * (chests, barrels, shulkers, furnaces, hoppers, beds, ...) and reports
 * the chunk coordinates. Cheap, since it only walks block-entity lists.
 * Expect false positives from villages, mineshafts, and other structures.
 */
public class BaseFinderModule extends Module {
    public int threshold = 4;       // minimum score for a chunk to be reported
    private int timer = 0;
    private final Map<ChunkPos, Integer> reported = new HashMap<>();

    public BaseFinderModule() {
        super("BaseFinder", GLFW.GLFW_KEY_B);
    }

    @Override public void onEnable() { reported.clear(); timer = 100; }
    @Override public void onDisable() { reported.clear(); }

    @Override
    public void onTick(MinecraftClient mc) {
        if (++timer < 100) return; // scan every ~5 seconds
        timer = 0;

        int radius = mc.options.getViewDistance().getValue();
        ChunkPos center = mc.player.getChunkPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                Chunk chunk = mc.world.getChunk(center.x + dx, center.z + dz, ChunkStatus.FULL, false);
                if (chunk == null) continue;

                int score = 0;
                for (BlockPos pos : chunk.getBlockEntityPositions()) {
                    score += weight(chunk.getBlockEntity(pos));
                }
                if (score < threshold) continue;

                ChunkPos cp = chunk.getPos();
                Integer prev = reported.get(cp);
                if (prev != null && prev >= score) continue;
                reported.put(cp, score);

                mc.player.sendMessage(Text.literal(String.format(
                    "[BaseFinder] score %d at x=%d z=%d", score, cp.getCenterX(), cp.getCenterZ())), false);
            }
        }
    }

    private int weight(BlockEntity be) {
        if (be == null) return 0;
        if (be instanceof ShulkerBoxBlockEntity) return 3;
        if (be instanceof ChestBlockEntity || be instanceof BarrelBlockEntity
            || be instanceof EnderChestBlockEntity) return 1;
        if (be instanceof AbstractFurnaceBlockEntity || be instanceof HopperBlockEntity
            || be instanceof DispenserBlockEntity || be instanceof BrewingStandBlockEntity
            || be instanceof BedBlockEntity || be instanceof BeaconBlockEntity
            || be instanceof EnchantingTableBlockEntity) return 1;
        return 0;
    }
}
