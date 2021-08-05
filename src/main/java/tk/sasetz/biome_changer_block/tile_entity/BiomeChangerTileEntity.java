package tk.sasetz.biome_changer_block.tile_entity;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.NetworkInstance;
import net.minecraftforge.fml.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tk.sasetz.biome_changer_block.init.RegistryHandler;

public class BiomeChangerTileEntity extends TileEntity {
    private static final Logger LOGGER = LogManager.getLogger();

    public BiomeChangerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public BiomeChangerTileEntity() {
        super(RegistryHandler.BIOME_CHANGER_TILE_ENTITY.get());
    }

    public void sendLog() {
        if (!level.isClientSide()) {
            Biome[] biomes = level.getChunkAt(worldPosition).getBiomes().biomes;
            sendToChat("This chunk has " + biomes.length + " biomes in it:");
            for (int i = 0, biomesLength = biomes.length; i < biomesLength; i++) {
                Biome biome = biomes[i];
                sendToChat("" + i + ": " + biome.getRegistryName());
            }
        }
    }

    public void changeBiome() {
        if(!level.isClientSide()){
            // Now this piece of code runs only on server side
            final Chunk chunk = level.getChunkAt(worldPosition);
            Biome[] biomes = chunk.getBiomes().biomes;
            for (int i = 0, biomesLength = biomes.length; i < biomesLength; i++) {
                DynamicRegistries reg = level.registryAccess();
                MutableRegistry<Biome> a = reg.registry(Registry.BIOME_REGISTRY).get();
                Biome biome = a.get(Biomes.DESERT.location());
                biomes[i] = biome;
            }
            // This line does basically the same that PacketDistributor#trackingChunk does
            ((ServerChunkProvider)level.getChunkSource()).chunkMap.getPlayers(chunk.getPos(), false)
                    .forEach(e -> e.connection.send(new SChunkDataPacket(chunk, 65535)));
        }
        else {
            // Update chunk?
        }
    }

    private void sendToChat(String message) {
        Minecraft.getInstance().player.sendMessage(new StringTextComponent(message), null);
    }
}
