package com.elementtimes.elementtimes.common.eletricity.src.trusteeship;

import javax.annotation.Nullable;

import com.elementtimes.elementtimes.common.eletricity.info.EleLineCache;
import com.elementtimes.elementtimes.common.eletricity.info.PathInfo;
import com.elementtimes.elementtimes.common.eletricity.interfaces.IEleInputer;
import com.elementtimes.elementtimes.common.eletricity.interfaces.IEleTransfer;
import com.elementtimes.elementtimes.common.eletricity.interfaces.IVoltage;
import com.elementtimes.elementtimes.common.eletricity.src.info.WireLinkInfo;
import com.elementtimes.elementtimes.common.eletricity.src.tileentity.EleSrcCable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author EmptyDreams
 * @version V1.0
 */
@SuppressWarnings("unused")
public class EleSrcTransfer implements IEleTransfer {
	
	@Override
	public PathInfo findPath(TileEntity start, TileEntity user, IEleInputer inputer) {
		EleLineCache cache = getLineCache(start);
		PathInfo info = null;
		if (cache != null) info = cache.read(start, user, inputer);
		if (info != null) return info;
		info = WireLinkInfo.calculate((EleSrcCable) start, user, inputer);
		if (info == null) return null;
		if (cache != null) cache.writeInfo(info);
		return info;
	}
	
	@Override
	public Object transfer(TileEntity now, int energy, IVoltage voltage, Object info) {
		return null;
	}
	
	@Override
	public void cleanTransfer(TileEntity now) {
		((EleSrcCable) now).clearTransfer();
	}
	
	@Override
	public boolean link(TileEntity now, TileEntity target) {
		return ((EleSrcCable) now).link(target.getPos());
	}
	
	@Override
	public boolean isLink(TileEntity now, TileEntity target) {
		EleSrcCable cable = (EleSrcCable) now;
		return target.equals(cable.getNext()) ||
				       target.equals(cable.getPrev()) ||
				       cable.getLinkedBlocks().contains(target);
	}
	
	@Override
	public boolean canLink(TileEntity now, TileEntity tgte) {
		return ((EleSrcCable) now).canLink(tgte);
	}
	
	@Override
	public int getEnergyLoss(TileEntity now, int energy, IVoltage voltage) {
		return ((EleSrcCable) now).getLoss(voltage);
	}
	
	@Nullable
	@Override
	public EleLineCache getLineCache(TileEntity now) {
		return ((EleSrcCable) now).getCache();
	}
	
	@Override
	public void setLineCache(TileEntity now, EleLineCache cache) {
		((EleSrcCable) now).setCache((WireLinkInfo) cache);
	}
	
	@Override
	public EleLineCache createLineCache(TileEntity now) {
		return new WireLinkInfo();
	}
	
	@Override
	public int getLinkAmount(TileEntity now) {
		return getLineCache(now).getOutputerAmount();
	}
	
	@Override
	public ResourceLocation getName() {
		return new ResourceLocation("elementtimes", "srcTransfer");
	}
	
	@Override
	public boolean contains(TileEntity te) {
		return te instanceof EleSrcCable;
	}
	
}
