package com.elementtimes.tutorial.common.eletricity.src.tileentity;

import com.elementtimes.tutorial.common.eletricity.BlockPosUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * 传输助手
 * @author EmptyDreams
 */
@Mod.EventBusSubscriber
public class TransferHelper implements IEnergyStorage {
	
	private static final List<TileEntity> INS = new LinkedList<>();
	
	private final EleSrcCable cable;
	
	public TransferHelper(EleSrcCable cable) {
		this.cable = cable;
	}
	
	private boolean isNext = true, isPrev = true;
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		TileEntity te;
		IEnergyStorage storage;
		int k = 0;
		for (BlockPos block : cable.linkedBlocks) {
			te = cable.getWorld().getTileEntity(block);
			if (te == null) continue;
			storage = te.getCapability(CapabilityEnergy.ENERGY, BlockPosUtil.whatFacing(block, cable.getPos()));
			if (storage == null) continue;
			int i = storage.receiveEnergy(maxReceive, true);
			if (i <= 0) continue;
			if (i == maxReceive) {
				if (!simulate) storage.receiveEnergy(maxReceive, false);
				return maxReceive;
			}
			if (i < maxReceive) {
				k += i;
				if (!simulate) storage.receiveEnergy(i, false);
				maxReceive -= i;
			}
		}
		
		if (maxReceive <= 0) return k;
		if (isNext && cable.getNextPos() != null && (te = cable.getNext()) != null &&
				(storage = te.getCapability(CapabilityEnergy.ENERGY,
						BlockPosUtil.whatFacing(cable.getNextPos(), cable.getPos()))) != null) {
			isNext = false;
			if (!INS.contains(te)) {
				INS.add(te);
				int i = storage.receiveEnergy(maxReceive, true);
				if (i == maxReceive) {
					if (!simulate) storage.receiveEnergy(maxReceive, false);
					isNext = true;
					return k + maxReceive;
				}
				if (i < maxReceive) {
					k += i;
					if (!simulate) storage.receiveEnergy(i, false);
					maxReceive -= i;
				}
			}
		}
		if (maxReceive <= 0) {
			isNext = true;
			return k;
		}
		if (isPrev && cable.getPrevPos() != null && (te = cable.getPrev()) != null &&
				(storage = te.getCapability(CapabilityEnergy.ENERGY,
						BlockPosUtil.whatFacing(cable.getPrevPos(), cable.getPos()))) != null) {
			isPrev = false;
			if (!INS.contains(te)) {
				INS.add(te);
				int i = storage.receiveEnergy(maxReceive, true);
				if (i == maxReceive) {
					if (!simulate) storage.receiveEnergy(maxReceive, false);
					isPrev = true;
					return k + maxReceive;
				}
				if (i < maxReceive) {
					k += i;
					if (!simulate) storage.receiveEnergy(i, false);
				}
			}
		}
		isNext = isPrev = true;
		return k;
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}
	
	@Override
	public int getEnergyStored() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public int getMaxEnergyStored() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public boolean canExtract() {
		return false;
	}
	
	@Override
	public boolean canReceive() {
		return true;
	}
	
	@SubscribeEvent
	public static void clean(TickEvent.ServerTickEvent event) {
		INS.clear();
	}
	
}
