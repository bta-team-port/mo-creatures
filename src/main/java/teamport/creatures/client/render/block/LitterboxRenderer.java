package teamport.creatures.client.render.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.Block;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.MoCreatures;
import teamport.creatures.core.block.LitterboxEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class LitterboxRenderer extends TileEntityRenderer<LitterboxEntity> {
	private final ModelBase model = ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/litterbox.json", LitterboxModel.class);
	private final Minecraft mc = Minecraft.getMinecraft(Minecraft.class);

	@Override
	public void doRender(Tessellator tessellator, LitterboxEntity tileEntity, double x, double y, double z, float partialTick) {
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslated(x + 0.5, y, z + 0.5);

		try {
			float scale = 0.0625f;
			GL11.glEnable(32826);
			GL11.glScalef(-1, -1, 1);
			GL11.glTranslatef(0.0f, -24.0f * scale - 0.0078125f, 0.0f);
			loadTexture(tileEntity.isFilthy ? "/assets/creatures/textures/entity/litterbox/1.png" : "/assets/creatures/textures/entity/litterbox/0.png");

			Block block = tileEntity.worldObj.getBlock(tileEntity.x, tileEntity.y, tileEntity.z);
			int blockMeta = tileEntity.worldObj.getBlockMetadata(tileEntity.x, tileEntity.y, tileEntity.z);

			if (block != null) {
				switch (blockMeta) {
					case 3:
						GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
						break;
					case 4:
						GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
						break;
					case 5:
						GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
						break;
				}
			}

			GL11.glEnable(3008);
			model.render(0, 0, 1, 0, 0, scale);
			GL11.glDisable(32826);
		} catch (Exception e) {
			MoCreatures.LOGGER.error(e.getMessage(), e);
		}

		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}
}
