package com.elementtimes.tutorial.common.eletricity.src.trusteeship;

import com.elementtimes.tutorial.common.eletricity.info.UseInfo;
import com.elementtimes.tutorial.common.eletricity.interfaces.IEleOutputer;
import com.elementtimes.tutorial.common.eletricity.interfaces.IVoltage;
import com.elementtimes.tutorial.common.eletricity.src.info.EnumVoltage;
import com.elementtimes.tutorial.common.eletricity.src.tileentity.EleSrcCable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * MI提供的缺省输出托管
 * @author EmptyDreams
 * @version V1.0
 */
public class EleSrcOutputer implements IEleOutputer {
	
	private static final ResourceLocation NAME =
			new ResourceLocation("elementtimes", "EleSrcOutputer");
	
	@Override
	public UseInfo output(TileEntity te, int energy, IVoltage voltage, boolean simulation) {
		UseInfo info = new UseInfo();
		int real = te.getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(energy, simulation);
		return info.setVoltage(EnumVoltage.ORDINARY).setEnergy(real);
	}
	
	@Override
	public boolean isAllowable(TileEntity te, EnumFacing facing) {
		return te.getCapability(CapabilityEnergy.ENERGY, facing).extractEnergy(Integer.MAX_VALUE, true) > 0;
	}
	
	@Override
	public boolean isAllowable(TileEntity now, IVoltage voltage) {
		return true;
	}
	
	@Override
	public int getOutput(TileEntity te) {
		return te.getCapability(CapabilityEnergy.ENERGY, null).extractEnergy(1, true);
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
