package ganymedes01.ironchestminecarts.minecarts.types;

import net.minecraft.world.World;

import cpw.mods.ironchest.IronChestType;
import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;

public class EntityMinecartDirtChest extends EntityMinecartIronChestAbstract {

    public EntityMinecartDirtChest(World world) {
        super(world);
    }

    public EntityMinecartDirtChest(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public IronChestType type() {
        return IronChestType.DIRTCHEST9000;
    }
}
