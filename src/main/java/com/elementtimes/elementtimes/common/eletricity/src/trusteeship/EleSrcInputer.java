package com.elementtimes.elementtimes.common.eletricity.src.trusteeship;

import com.elementtimes.elementtimes.common.eletricity.interfaces.IEleInputer;
import com.elementtimes.elementtimes.common.eletricity.interfaces.IVoltage;
import com.elementtimes.elementtimes.common.eletricity.src.info.EnumVoltage;
import com.elementtimes.elementtimes.common.eletricity.src.tileentity.EleSrcCable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * TODO clean old codes
 * @author EmptyDreams
 * @version V1.0
 */
public class EleSrcInputer implements IEleInputer {
	
	private static final ResourceLocation NAME =
			new ResourceLocation("elementtimes", "EleSrcInputer");
	
	@Override
	public int useEnergy(TileEntity now, int energy, IVoltage voltage) {
//		return now.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(energy, false);
		return now.getCapability(CapabilityEnergy.ENERGY).map(s -> s.receiveEnergy(energy, false)).orElse(0);
	}
	
	/**
	 * @throws NullPointerException 传入的TE不符合要求
	 */
	@Override
	public int getEnergy(TileEntity te) {
//		return te.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(Integer.MAX_VALUE, true);
		return te.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
	}
	
	@Override
	public int getEnergy(TileEntity now, int energy) {
//		return now.getCapability(CapabilityEnergy.ENERGY, null).receiveEnergy(energy, true);
		return now.getCapability(CapabilityEnergy.ENERGY).map(s -> s.receiveEnergy(energy, true)).orElse(0);
	}
	
	@Override
	public IVoltage getVoltage(TileEntity te) {
		return EnumVoltage.ORDINARY;
	}
	
	@Override
	public boolean isAllowable(TileEntity now, Direction facing) {
		return true;
	}
	
	@Override
	public ResourceLocation getName() {
		return NAME;
	}
	
	@Override
	public boolean contains(TileEntity te) {
//		IEnergyStorage cap = te.getCapability(CapabilityEnergy.ENERGY, null);
//		return cap != null && !(te instanceof EleSrcCable);
		return !(te instanceof EleSrcCable) && te.getCapability(CapabilityEnergy.ENERGY).isPresent();
	}
	
}
