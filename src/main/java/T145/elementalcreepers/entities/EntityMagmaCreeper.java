package T145.elementalcreepers.entities;

import T145.elementalcreepers.config.ModConfig;
import T145.elementalcreepers.entities.base.EntityBaseCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMagmaCreeper extends EntityBaseCreeper {

	public EntityMagmaCreeper(World world) {
		super(world);
		this.isImmuneToFire = true;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (!world.isRemote && (Math.round(posX + 0.5D) != Math.round(prevPosX + 0.5D) || Math.round(posY) != Math.round(prevPosY) || Math.round(posZ + 0.5D) != Math.round(prevPosZ + 0.5D))) {
			BlockPos pos = new BlockPos(Math.round(prevPosX), Math.round(prevPosY), Math.round(prevPosZ));

			if (world.isAirBlock(pos) && Blocks.FIRE.canPlaceBlockAt(world, pos)) {
				world.setBlockState(pos, Blocks.FIRE.getDefaultState());
			}
		}

		if (isWet()) {
			attackEntityFrom(DamageSource.DROWN, 1.0F);
		}
	}

	@Override
	public void createExplosion(int explosionPower, boolean griefingEnabled) {
		int radius = getPowered() ? ModConfig.explosionRadii.magmaCreeperRadius * explosionPower : ModConfig.explosionRadii.magmaCreeperRadius;

		if (ModConfig.general.domeExplosion) {
			domeExplosion(radius, Blocks.LAVA);
		} else {
			wildExplosion(radius, Blocks.LAVA);
		}
	}
}