package com.thexfactor117.losteclipse.loot;

import java.util.Random;

import com.thexfactor117.losteclipse.items.magical.ItemLEMagical;
import com.thexfactor117.losteclipse.stats.weapons.Rarity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemGenerator 
{
	/** Creates a melee weapon/armor with randomized stats. */
	public static void create(ItemStack stack, NBTTagCompound nbt, BlockPos pos)
	{
		/*
		 * Set rarity
		 * Set level
		 * Generate attributes based on Rarity
		 * 		- Common: 0-1 attributes
		 * 		- Uncommon: 1-2 attributes
		 * 		- Rare: 2-3 attributes
		 * 		- Legendary: 3-4 attributes
		 * 		- Mythic: 4-5 attributes
		 * Generate base damage and base attack speed
		 * Generate name based on attributes + material/type
		 */
		
		if (Rarity.getRarity(nbt) == Rarity.DEFAULT)
		{
			Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, new Random())); // sets a random rarity
			nbt.setInteger("Level", (int) (Math.random() * 10 + 1)); // set level to current player level
			ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
			ItemGeneratorHelper.setAttributeModifiers(nbt, stack);
			nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
		}
	}
	
	/** Creates a magical weapon with randomized stats. */
	public static void createMagical(ItemStack stack, NBTTagCompound nbt, BlockPos pos)
	{
		if (Rarity.getRarity(nbt) == Rarity.DEFAULT && stack.getItem() instanceof ItemLEMagical)
		{
			ItemLEMagical wand = (ItemLEMagical) stack.getItem();
			
			Rarity.setRarity(nbt, Rarity.getRandomRarity(nbt, new Random()));
			nbt.setInteger("Level", (int) (Math.random() * 10 + 1));
			ItemGeneratorHelper.setRandomAttributes(stack, nbt, Rarity.getRarity(nbt));
			
			// handles setting weighted damage/attack speed and min/max damage
			double baseDamage = wand.getBaseDamage();
			double baseAttackSpeed = wand.getBaseAttackSpeed();
			double weightedDamage = ItemGeneratorHelper.getWeightedDamage(nbt, Rarity.getRarity(nbt), baseDamage);
			double weightedAttackSpeed = ItemGeneratorHelper.getWeightedAttackSpeed(Rarity.getRarity(nbt), baseAttackSpeed);
			
			ItemGeneratorHelper.setMinMaxDamage(nbt, weightedDamage);
			nbt.setDouble("AttackSpeed", weightedAttackSpeed);
			ItemGeneratorHelper.setRune(nbt);
			nbt.setInteger("HideFlags", 6); // hides Attribute Modifier and Unbreakable tags
		}
	}
}