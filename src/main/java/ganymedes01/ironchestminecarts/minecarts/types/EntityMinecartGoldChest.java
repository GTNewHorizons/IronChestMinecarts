package ganymedes01.ironchestminecarts.minecarts.types;

import net.minecraft.world.World;

import cpw.mods.ironchest.IronChestType;
import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;

public class EntityMinecartGoldChest extends EntityMinecartIronChestAbstract {

    public EntityMinecartGoldChest(World world) {
        super(world);
    }

    public EntityMinecartGoldChest(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public IronChestType type() {
        return IronChestType.GOLD;
    }
}
