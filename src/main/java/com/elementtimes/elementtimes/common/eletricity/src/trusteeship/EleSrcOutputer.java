package com.elementtimes.elementtimes.common.eletricity.src.trusteeship;

import com.elementtimes.elementtimes.common.eletricity.info.UseInfo;
import com.elementtimes.elementtimes.common.eletricity.interfaces.IEleOutputer;
import com.elementtimes.elementtimes.common.eletricity.interfaces.IVoltage;
import com.elementtimes.elementtimes.common.eletricity.src.info.EnumVoltage;
import com.elementtimes.elementtimes.common.eletricity.src.tileentity.EleSrcCable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * MI提供的缺省输出托管
 * TODO clean old codes
 * @author EmptyDreams
 * @version V1.0
 */
public class EleSrcOutputer implements IEleOutputer {
	
	private static final ResourceLocation NAME =
			new ResourceLocation("elementtimes", "EleSrcOutputer");
	
	@Override
	public UseInfo output(TileEntity te, int energy, IVoltage voltage, boolean simulation) {
		UseInfo info = new UseInfo();
//		int real = te.getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(energy, simulation);
//		return info.setVoltage(EnumVoltage.ORDINARY).setEnergy(real);
		LazyOptional<IEnergyStorage> capability = te.getCapability(CapabilityEnergy.ENERGY, null);
		if (capability.isPresent()) {
			int real = capability.orElseThrow(RuntimeException::new).extractEnergy(energy, simulation);
			return info.setVoltage(EnumVoltage.ORDINARY).setEnergy(real);
		}
		return info;
	}

	@Override
	public boolean isAllowable(TileEntity te, Direction facing) {
//		return te.getCapability(CapabilityEnergy.ENERGY, facing).extractEnergy(Integer.MAX_VALUE, true) > 0;
		LazyOptional<IEnergyStorage> capability = te.getCapability(CapabilityEnergy.ENERGY);
		if (capability.isPresent()) {
			return capability.orElseThrow(RuntimeException::new).extractEnergy(Integer.MAX_VALUE, true) > 0;
		}
		return false;
	}
	
	@Override
	public boolean isAllowable(TileEntity now, IVoltage voltage) {
		return true;
	}
	
	@Override
	public int getOutput(TileEntity te) {
//		return te.getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(1, true);
		LazyOptional<IEnergyStorage> capability = te.getCapability(CapabilityEnergy.ENERGY);
		if (capability.isPresent()) {
			return capability.orElseThrow(RuntimeException::new).extractEnergy(1, true);
		}
		return 0;
	}
	
	@Override
	public ResourceLocation getName() {
		return NAME;
	}
	
	@Override
	public boolean contains(TileEntity te) {
//		IEnergyStorage cap = te.getCapability(CapabilityEnergy.ENERGY, null);
//		return cap != null && !(te instanceof EleSrcCable);
		return te instanceof EleSrcCable && te.getCapability(CapabilityEnergy.ENERGY).isPresent();
	}
}
