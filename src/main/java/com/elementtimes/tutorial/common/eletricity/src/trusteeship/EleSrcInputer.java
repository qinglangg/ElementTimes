package com.elementtimes.tutorial.common.eletricity.src.trusteeship;

import com.elementtimes.tutorial.common.eletricity.interfaces.IEleInputer;
import com.elementtimes.tutorial.common.eletricity.interfaces.IVoltage;
import com.elementtimes.tutorial.common.eletricity.src.info.EnumVoltage;
import com.elementtimes.tutorial.common.eletricity.src.tileentity.EleSrcCable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * @author EmptyDreams
 * @version V1.0
 */
@SuppressWarnings("ConstantConditions")
public class EleSrcInputer implements IEleInputer {
	
	private static final ResourceLocation NAME =
			new ResourceLocation("elementtimes", "EleSrcInputer");
	
	@Override
	public int useEnergy(TileEntity now, int energy, IVoltage voltage) {
		return now.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(energy, false);
	}
	
	@Override
	public IVoltage getVoltage(TileEntity now, IVoltage voltage) {
		return EnumVoltage.ORDINARY;
	}
	
	/**
	 * @throws NullPointerException 传入的TE不符合要求
	 */
	@Override
	public int getEnergy(TileEntity te) {
		return te.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(Integer.MAX_VALUE, true);
	}
	
	@Override
	public int getEnergy(TileEntity now, int energy) {
		return now.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(energy, true);
	}
	
	@Override
	public boolean isAllowable(TileEntity now, EnumFacing facing) {
		return true;
	}
	
	@Override
	public ResourceLocation getName() {
		return NAME;
	}
	
	@Override
	public boolean contains(TileEntity te) {
		IEnergyStorage cap = te.getCapability(CapabilityEnergy.ENERGY, null);
		return cap != null && !(te instanceof EleSrcCable);
	}
	
}
