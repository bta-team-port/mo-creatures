package teamport.creatures.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

public class DeerEntity extends EntityAnimal {
	public boolean isScared;
	public List<WeightedRandomLootObject> burningMobDrops = new ArrayList<>();

	private boolean isBuck;
	private int scaredTick = 0;

	public DeerEntity(World world) {
		super(world);
		setSize(0.3f, 2.0f);

		heartsHalvesLife = 10;
		if (random.nextInt(2) == 0) isBuck = true;
		mobDrops.add(new WeightedRandomLootObject(Item.foodPorkchopRaw.getDefaultStack(), 1, 2));
		burningMobDrops.add(new WeightedRandomLootObject(Item.foodPorkchopCooked.getDefaultStack(), 1, 2));
	}

	@Override
	public String getEntityTexture() {
		return isBuck ? "/assets/creatures/textures/entity/deer/b_0.png" : "/assets/creatures/textures/entity/deer/f_0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/textures/entity/deer/b_0.png";
	}

	@Override
	public String getLivingSound() {
		return "creatures.deerfgrunt";
	}

	@Override
	protected String getHurtSound() {
		return "creatures.deerhurt";
	}

	@Override
	protected String getDeathSound() {
		return "creatures.deerdying";
	}


	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0f);

		// Just a simple flee system;
		// Checks if the player isn't null, isn't sneaking, and isn't in creative
		// or if it's in a fear state
		if (player != null && !player.isSneaking() && player.gamemode.areMobsHostile()) {
			faceEntity(player, 0, 0);

			speed = 0.2f;
			roamRandomPath();
		} else if (isScared) {
			speed = 0.2f;
			roamRandomPath();
		} else {
			speed = 0.1f;
		}

		if (scaredTick > 0) {
			isScared = true;
			scaredTick--;
		}
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		scaredTick = 1200;
		return super.hurt(attacker, damage, type);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("ScaredTick", scaredTick);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		scaredTick = tag.getInteger("ScaredTick");
	}

	@Override
	protected List<WeightedRandomLootObject> getMobDrops() {
		return remainingFireTicks > 0 ? burningMobDrops : mobDrops;
	}
}
