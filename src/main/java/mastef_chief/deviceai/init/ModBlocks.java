package mastef_chief.deviceai.init;

import mastef_chief.deviceai.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {

    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block AI_BLOCK = new BlockBase("ai_block", Material.IRON);

}
