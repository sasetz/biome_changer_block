package tk.sasetz.biome_changer_block.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tk.sasetz.biome_changer_block.block.BiomeChangerBlock;
import tk.sasetz.biome_changer_block.item.BlockItemPreset;
import tk.sasetz.biome_changer_block.tile_entity.BiomeChangerTileEntity;
import tk.sasetz.biome_changer_block.util.Utils;

import java.util.HashSet;
import java.util.Objects;

public class RegistryHandler {
    // Create all the deferred registers
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Utils.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Utils.MOD_ID);

    // Handle register events
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent) {
            BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                itemRegistryEvent.getRegistry().register(new BlockItemPreset(block)
                        .setRegistryName(Objects.requireNonNull(block.getRegistryName())));
            });
        }
    }
    
    // Subscribe deferred registers to the mod event bus
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TILE_ENTITIES.register(bus);
    }
    
    // ****************************************************** //
    // ******************* Register stuff ******************* //
    // ****************************************************** //
    
    public static final RegistryObject<Block> BIOME_CHANGER_BLOCK = BLOCKS.register("biome_changer_block",
            BiomeChangerBlock::new);
    public static final RegistryObject<TileEntityType<BiomeChangerTileEntity>> BIOME_CHANGER_TILE_ENTITY = TILE_ENTITIES
            .register("biome_changer_block", () -> TileEntityType.Builder
                    .of(BiomeChangerTileEntity::new, BIOME_CHANGER_BLOCK
                                    .get()).build(null));
}
