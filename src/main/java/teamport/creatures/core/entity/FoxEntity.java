package teamport.creatures.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

import java.util.List;

public class FoxEntity extends EntityAnimal {
	boolean angry;
	int angerCounter;

	public FoxEntity(World world) {
		super(world);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/textures/entity/fox/0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/textures/entity/fox/0.png";
	}

	@Override
	public void tick() {
		super.tick();
        angry = angerCounter-- > 0;
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
        if (attacker instanceof EntityPlayer) {
			angerCounter = 400;
		}
        return super.hurt(attacker, damage, type);
	}

	@Override
	protected Entity findPlayerToAttack() {
        return angry ? world.getClosestPlayerToEntity(this, 16.0D) : null;
    }

	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (!(entity instanceof EntityItem)) {
			if (!(distance > 2.0F) || !(distance < 6.0F) || random.nextInt(10) != 0) {
				if ((double)distance < 1.5 && entity.bb.maxY > bb.minY && entity.bb.minY < bb.maxY) {
					attackTime = 20;

					entity.hurt(this, 2, DamageType.COMBAT);
				}
			} else if (onGround) {
				double d = entity.x - x;
				double d1 = entity.z - z;
				float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
				xd = d / (double)f1 * 0.5 * 0.8F + xd * 0.2F;
				zd = d1 / (double)f1 * 0.5 * 0.8F + zd * 0.2F;
				yd = 0.4F;
			}
		}
	}

	// Due to every entity the fox for check for, I added this function to compensate.
	private void checkForEntitiesToAttack(Class<? extends Entity> entityClass) {
		if (entityToAttack == null && !hasPath() && world.rand.nextInt(100) == 0) {
			List<Entity> nearbyEntities = world
				.getEntitiesWithinAABB(entityClass, AABB.getBoundingBoxFromPool(x, y, z, x + 1.0, y + 1.0, z + 1.0)
					.expand(16.0, 4.0, 16.0)
				);

			if (!nearbyEntities.isEmpty()) setTarget(nearbyEntities.get(world.rand.nextInt(nearbyEntities.size())));
		}
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();

		checkForEntitiesToAttack(BirdEntity.class);
		checkForEntitiesToAttack(EntityChicken.class);
		checkForEntitiesToAttack(DuckEntity.class);
		checkForEntitiesToAttack(BunnyEntity.class);

		// EXPERIMENTAL //
		// Player follow code for the upcoming 7.3 release. Follow item is: eggs.
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0);
		if (player != null && (player.distanceToSqr(x, y, z) > 4.0)) {
			ItemStack heldStack = player.getCurrentEquippedItem();
			if (heldStack != null && heldStack.itemID == Item.eggChicken.id) {
				faceEntity(player, 30.0F, 30.0F);
				moveForward = 1.0F;

				if (player.distanceToSqr(this) <= 12.0)
					moveForward = 0.0F;
			}
		}
	}

	@Override
	public int getTalkInterval() {
		return 360;
	}

	@Override
	public String getLivingSound() {
		return "creatures.foxcall";
	}

	@Override
	protected String getHurtSound() {
		return "creatures.foxhurt";
	}

	@Override
	protected String getDeathSound() {
		return "creatures.foxdying";
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Anger", angerCounter);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		angerCounter = tag.getInteger("Anger");
	}
}
