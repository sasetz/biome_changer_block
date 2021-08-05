package tk.sasetz.biome_changer_block.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.Level;
import tk.sasetz.biome_changer_block.init.RegistryHandler;
import tk.sasetz.biome_changer_block.tile_entity.BiomeChangerTileEntity;

import javax.annotation.Nullable;

public class BiomeChangerBlock extends Block {
    public BiomeChangerBlock() {
        super(Properties.of(Material.HEAVY_METAL)
                .strength(5)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return RegistryHandler.BIOME_CHANGER_TILE_ENTITY.get().create();
    }

    @Override
    public ActionResultType use(BlockState state, World level, BlockPos blockPos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        TileEntity te = level.getBlockEntity(blockPos);
        if (te instanceof BiomeChangerTileEntity) {
            BiomeChangerTileEntity biomeChanger = (BiomeChangerTileEntity) te;
            if (player.isCrouching()) {
                biomeChanger.changeBiome();
            } else
                biomeChanger.sendLog();
        }
        LOGGER.log(Level.INFO, "Block got clicked: " + state.getBlock().getRegistryName());
        return ActionResultType.SUCCESS;
    }
}
