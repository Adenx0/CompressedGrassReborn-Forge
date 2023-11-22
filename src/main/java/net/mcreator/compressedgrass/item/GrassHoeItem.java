
package net.mcreator.compressedgrass.item;

import net.minecraft.world.entity.ai.attributes.Attributes;

public class GrassHoeItem extends HoeItem {
	public GrassHoeItem() {
		super(new Tier() {
			public int getUses() {
				return 1164;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return 4f;
			}

			public int getLevel() {
				return 6;
			}

			public int getEnchantmentValue() {
				return 42;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(CompressedGrassModItems.GRASS_ORE_INGOT.get()));
			}
		}, 0, -3f, new Item.Properties());
	}
}
