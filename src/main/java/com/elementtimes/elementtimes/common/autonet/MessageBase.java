package com.elementtimes.elementtimes.common.autonet;

import com.elementtimes.elementcore.api.annotation.ModSimpleNetwork;
import com.elementtimes.elementcore.api.annotation.part.Method;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author EmptyDreams
 * @version V1.0
 */
@ModSimpleNetwork(
		encoder = @Method(value = MessageBase.class, name = "encoder"),
		decoder = @Method(value = MessageBase.class, name = "decoder"),
		handler = @Method(value = MessageBase.class, name = "handler")
)
public class MessageBase {

	private final CompoundNBT mCompound;
	private final BlockPos mPos;

	public MessageBase(BlockPos pos, CompoundNBT compound) {
		Objects.requireNonNull(compound, "compound is null!");
		Objects.requireNonNull(pos, "pos is null!");
		mCompound = compound;
		mPos = pos;
	}

	public static void encoder(MessageBase message, PacketBuffer buffer) {
		Objects.requireNonNull(message.mCompound, "没有给信息类设定要传输的信息");
		buffer.writeBlockPos(message.mPos);
		buffer.writeCompoundTag(message.mCompound);
	}

	public BlockPos getPos() {
		return mPos;
	}

	public CompoundNBT getCompound() {
		return mCompound;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MessageBase that = (MessageBase) o;

		if (!Objects.equals(mCompound, that.mCompound)) {
			return false;
		}
		return Objects.equals(mPos, that.mPos);
	}

	@Override
	public int hashCode() {
		int result = mCompound.hashCode();
		result = 31 * result + mPos.hashCode();
		return result;
	}

	public static MessageBase decoder(PacketBuffer buffer) {
		return new MessageBase(buffer.readBlockPos(), buffer.readCompoundTag());
	}

	public static void handler(MessageBase message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
			onClient(message, context);
		} else {
			onServer(message, context);
		}
	}

	private static void onClient(MessageBase message, NetworkEvent.Context context) {
		WaitList.addMessageToClientList(message);
	}

	private static void onServer(MessageBase message, NetworkEvent.Context context) {
		ServerWorld world = Objects.requireNonNull(context.getSender()).getServerWorld();
		IAutoNetwork et = (IAutoNetwork) world.getTileEntity(message.mPos);
		Objects.requireNonNull(et, "et is null");
		et.receive(message.mCompound);
	}
}
// TODO clean old codes
//public class MessageBase implements IMessage {
//
//	public MessageBase() { }
//
//	public MessageBase(CompoundNBT compound) {
//		WaitList.checkNull(compound, "compound");
//		this.compound = compound;
//	}
//
//	private CompoundNBT compound = null;
//
//	/**
//	 * 设置要传输的Tag，若已存在一个Tag，会覆盖原来的信息
//	 */
//	public void writeNBT(CompoundNBT compound) {
//		WaitList.checkNull(compound, "compound");
//		this.compound = compound;
//	}
//
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		compound = ByteBufUtils.readTag(buf);
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		if (compound == null) throw new NullPointerException("没有给信息类设定要传输的信息");
//		ByteBufUtils.writeTag(buf, compound);
//	}
//
//	public CompoundNBT getCompound() {
//		return compound;
//	}
//
//	public int getDimension() {
//		return compound.getInteger("_world");
//	}
//
//	public BlockPos getBlockPos() {
//		int[] pos = compound.getIntArray("_pos");
//		return new BlockPos(pos[0], pos[1], pos[2]);
//	}
//
//	//------------------------------处理信息的内部类------------------------------//
//
//	/**
//	 * 客户端处理
//	 */
//	public static final class ClientHandler implements IMessageHandler<MessageBase, IMessage> {
//
//		@Override
//		public IMessage onMessage(MessageBase message, MessageContext ctx) {
//			WaitList.addMessageToClientList(message);
//			return null;
//		}
//	}
//
//	/**
//	 * 服务端处理类
//	 */
//	public static final class ServiceHandler implements IMessageHandler<MessageBase, IMessage> {
//		@Override
//		public IMessage onMessage(MessageBase message, MessageContext ctx) {
//			IAutoNetwork et = (IAutoNetwork) FMLCommonHandler.instance().getMinecraftServerInstance()
//					                               .getWorld(message.getDimension())
//					                               .getTileEntity(message.getBlockPos());
//			WaitList.checkNull(et, "et");
//			et.receive(message.getCompound());
//			return null;
//		}
//	}
//
//}
