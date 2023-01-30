package ganymedes01.ironchestminecarts.minecarts.types;

import net.minecraft.world.World;

import cpw.mods.ironchest.IronChestType;
import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;

public class EntityMinecartCopperChest extends EntityMinecartIronChestAbstract {

    public EntityMinecartCopperChest(World world) {
        super(world);
    }

    public EntityMinecartCopperChest(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public IronChestType type() {
        return IronChestType.COPPER;
    }
}
