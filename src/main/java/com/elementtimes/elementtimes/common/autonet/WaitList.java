package com.elementtimes.elementtimes.common.autonet;

import com.elementtimes.elementcore.api.utils.CommonUtils;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

/**
 * 所有客户端/服务端需要等待处理的消息存放在此处
 *
 * @author EmptyDreams
 * @version V1.0
 */
@Mod.EventBusSubscriber
public class WaitList {
	
	/** 已经传输的信息总数 */
	private static int amount = 0;
	
	@SubscribeEvent
	public static void runAtTickEndService(TickEvent.ServerTickEvent event) {
		sendAll(false);
	}
	
	@SubscribeEvent
	public static void runAtTickEndClient(TickEvent.ClientTickEvent event) {
		reClient();
		sendAll(true);
	}
	
	/** 存储客户端待处理的消息 */
	private static Set<MessageBase> client = new LinkedHashSet<>();

	// TODO clean old codes: 同步锁可以用该类本身的 class，不同的 client 也不同的
//	/** 操作client时的锁，因为client可变，所以重新建立一个常量 */
//	private static final Object CLIENT_LOCK = new Object();
	
	/** 获取已经执行的信息总数 */
	public static int getAmount() {
		return amount;
	}
	
	public static void addMessageToClientList(MessageBase base) {
		synchronized (WaitList.class) {
			Objects.requireNonNull(base, "base is null");
			client.add(base);
		}
	}
	
	public static void toString(StringBuilder sb) {
		synchronized (WaitList.class) {
			for (MessageBase base : client) {
				sb.append('\t').append(base).append('\n');
			}
		}
	}

	/**
	 * TODO clean old codes：Server to Client 由 Te 自行传输
	 */
	private static void sendAll(boolean isClient) {
		if (isClient) {
			NetworkRegister.forEach(true, network -> {
				CompoundNBT message = network.send();
//				if (message != null) {
//					TileEntity te = (TileEntity) network;
//					message.setIntArray("_pos", new int[] {
//							te.getPos().getX(),
//							te.getPos().getY(),
//							te.getPos().getZ()});
//					message.putInt("_world", te.getWorld().provider.getDimension());
//					sendToService(new MessageBase(message));
//				}
				if (message != null) {
					TileEntity te = (TileEntity) network;
					MessageBase messageBase = new MessageBase(te.getPos(), message);
					ElementTimes.CONTAINER.elements.sendToServer(messageBase);
				}
			});
//		} else {
//			try {
//				NetworkRegister.forEach(false, network -> {
//					CompoundNBT message = network.send();
//					if (message != null) {
//						TileEntity te = (TileEntity) network;
////						message.setIntArray("_pos", new int[] {
////								te.getPos().getX(),
////								te.getPos().getY(),
////								te.getPos().getZ()});
////						message.putInt("_world", te.getWorld().provider.getDimension());
//						ServerWorld world = (ServerWorld) te.getWorld();
//						if (world != null) {
//							ListNBT playerList = message.getList("players", Constants.NBT.TAG_LONG);
//							Iterator<INBT> iterator = playerList.iterator();
//							while (iterator.hasNext()) {
//								long most = ((LongNBT) iterator.next()).getLong();
//								long least = ((LongNBT) iterator.next()).getLong();
//								UUID uuid = new UUID(most, least);
//								ServerPlayerEntity player = (ServerPlayerEntity) world.getPlayerByUuid(uuid);
//								Objects.requireNonNull(player, "指定玩家不存在！[" + uuid + "]");
//								ElementTimes.CONTAINER.elements.sendTo(new MessageBase(te.getPos(), message), player);
//							}
//						}
////						int amount = message.getInt("playerAmount");
////						if (amount > 0) {
////							Set<ServerPlayerEntity> players = new HashSet<>(amount);
////							for (int i = 0; i < amount; ++i) {
////								ServerPlayerEntity player = (ServerPlayerEntity) te.getWorld().getPlayerEntityByName(
////										message.getString("player" + i));
////								te.getWorld().getPlayerByUuid(message.getUniqueId("player" + i));
////								if (player == null) throw new ClassCastException("指定玩家不存在！[" +
////										                              message.getString("player" + i) + "]");
////								players.add(player);
////							}
////							sendToClient(new MessageBase(message), players);
////						}
//					}
//				});
//			} catch (ClassCastException | NullPointerException e) {
//				System.err.println("网络传输名单中存在违规信息！");
//				throw e;
//			}
		}
	}
	
	/**
	 * 尝试清空client消息列表，只能在客户端调用
	 */
	@OnlyIn(Dist.CLIENT)
	public static void reClient() {
		if (CommonUtils.getWorldClient() == null) {
			amount = 0;
			return;
		}
		Set<MessageBase> clientMessage = new LinkedHashSet<>();
		synchronized (WaitList.class) {
			client.forEach(mb -> {
				IAutoNetwork et = (IAutoNetwork) CommonUtils.getWorldClient().getTileEntity(mb.getPos());
				if (et == null) {
					clientMessage.add(mb);
				} else {
					++amount;
					et.receive(mb.getCompound());
				}
			});
			client = clientMessage;
		}
	}

	// TODO clean old codes
//	/**
//	 * 发送信息到客户端
//	 *
//	 * @param message 要发送的信息
//	 * @param players 指定玩家
//	 *
//	 * @throws NullPointerException 如果message和player任意一个为null
//	 * @throws IllegalArgumentException 如果player不能强制转换为PlayerEntityMP[]
//	 */
//	public static void sendToClient(IMessage message, PlayerEntity... players) {
//		checkNull(players, "players");
//		checkNull(message, "message");
//
//		for (PlayerEntity player : players) {
//			if (!(player instanceof PlayerEntityMP))
//				throw new IllegalArgumentException("player应该可以被强制转换为PlayerEntityMP");
//			NetworkLoader.instance().sendTo(message, (PlayerEntityMP) player);
//		}
//	}

	// TODO clean old codes
//	/**
//	 * 发送信息到客户端
//	 *
//	 * @param message 要发送的信息
//	 * @param player 指定玩家
//	 *
//	 * @throws NullPointerException 如果message和player任意一个为null
//	 * @throws IllegalArgumentException 如果player中的元素不能强制转换为PlayerEntityMP
//	 */
//	public static void sendToClient(IMessage message, Iterable<? extends PlayerEntityMP> player) {
//		checkNull(player, "player");
//		checkNull(message, "message");
//		player.forEach(p -> {
//			if (p instanceof PlayerEntityMP) {
//				NetworkLoader.instance().sendTo(message, p);
//			} else {
//				throw new IllegalArgumentException("player：" + p);
//			}
//		});
//	}

	// TODO clean old codes
//	/**
//	 * 发送信息到客户端
//	 *
//	 * @param message 要发送的信息
//	 * @param players 指定玩家
//	 *
//	 * @throws NullPointerException 如果message和player任意一个为null
//	 */
//	public static void sendToClient(IMessage message, PlayerEntityMP... players) {
//		checkNull(message, "message");
//		checkNull(players, "players");
//
//		for (PlayerEntityMP player : players)
//			NetworkLoader.instance().sendTo(message, player);
//	}

	// TODO clean old codes
//	/**
//	 * 发送信息到服务端
//	 * @param message 要发送的信息
//	 *
//	 * @throws NullPointerException 如果message为null
//	 */
//	@OnlyIn(Dist.CLIENT)
//	public static void sendToService(IMessage message) {
//		if (message == null) throw new NullPointerException("message == null");
//		NetworkLoader.instance().sendToServer(message);
//	}
	
	/**
	 * 检查参数是否为null
	 *
	 * @throws NullPointerException 如果参数为null
	 * @throws IllegalArgumentException 如果objects与name长度不一样
	 */
	public static void checkNull(Object[] objects, String[] name) {
		if (objects == null) {
			throw new NullPointerException("objects == null");
		}
		if (name == null) {
			throw new NullPointerException("name == null");
		}
		if (objects.length != name.length) {
			throw new IllegalArgumentException("objects的长度与name的不相等");
		}
		for (int i = 0; i < name.length; ++i) {
			Objects.requireNonNull(objects[i], name[i] + " == null");
		}
	}

	// TODO clean old codes: use java.util.Objects.requireNonNull(T, String)
//	/**
//	 * 检查参数是否为null
//	 *
//	 * @throws NullPointerException 如果参数为null
//	 */
//	public static void checkNull(Object object, String name) {
//		if (object == null) throw new NullPointerException(name + " == null");
//	}
}
