package com.thexfactor117.lsc.items.magical;

import com.thexfactor117.lsc.capabilities.api.IChunkLevel;
import com.thexfactor117.lsc.capabilities.api.IChunkLevelHolder;
import com.thexfactor117.lsc.capabilities.cap.CapabilityChunkLevel;
import com.thexfactor117.lsc.items.base.ISpecial;
import com.thexfactor117.lsc.items.base.ItemMagical;
import com.thexfactor117.lsc.loot.Attribute;
import com.thexfactor117.lsc.loot.Rarity;
import com.thexfactor117.lsc.loot.generation.ItemGeneratorHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

/**
 * 
 * @author TheXFactor117
 *
 */
public class ItemBlazefury extends ItemMagical implements ISpecial
{
	public ItemBlazefury(String name, double baseDamage, double attackSpeed, int manaPerUse, int durability) 
	{
		super(name, baseDamage, attackSpeed, manaPerUse, durability);
	}

	@Override
	public void createSpecial(ItemStack stack, NBTTagCompound nbt, World world, ChunkPos pos) 
	{
		IChunkLevelHolder chunkLevelHolder = world.getCapability(CapabilityChunkLevel.CHUNK_LEVEL, null);
		IChunkLevel chunkLevel = chunkLevelHolder.getChunkLevel(pos);
		int level = chunkLevel.getChunkLevel();
		
		nbt.setBoolean("IsSpecial", true);
		Rarity.setRarity(nbt, Rarity.EPIC);
		nbt.setInteger("Level", level);
		ItemGeneratorHelper.setRune(nbt);
		
		// Attributes
		Attribute.FIRE.addAttribute(nbt, world.rand, 3);
		Attribute.FROST.addAttribute(nbt, world.rand, 3);
		Attribute.LIGHTNING.addAttribute(nbt, world.rand, 3);
		
		// Damage and Attack Speed
		double baseDamage = this.getBaseDamage();
		double baseAttackSpeed = this.getBaseAttackSpeed();
		double weightedDamage = ItemGeneratorHelper.getWeightedDamage(level, Rarity.getRarity(nbt), baseDamage);
		double weightedAttackSpeed = ItemGeneratorHelper.getWeightedAttackSpeed(Rarity.getRarity(nbt), baseAttackSpeed);
		
		ItemGeneratorHelper.setMinMaxDamage(nbt, weightedDamage);
		nbt.setDouble("AttackSpeed", weightedAttackSpeed);
	}
}
