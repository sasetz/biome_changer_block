package tk.sasetz.biome_changer_block.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BlockItemPreset extends BlockItem {
    public BlockItemPreset(Block block, Properties properties) {
        super(block, properties);
    }
    
    public BlockItemPreset(Block block) {
        super(block, new Item.Properties().tab(ItemGroup.TAB_REDSTONE));
    }
}
