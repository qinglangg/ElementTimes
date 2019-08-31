//package com.elementtimes.tutorial.other.pipeline;
//
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.eventhandler.Event;
//import net.minecraftforge.fml.common.eventhandler.EventBus;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent;
//import net.minecraftforge.fml.relauncher.Side;
//
//import java.util.Map;
//
//import static com.elementtimes.tutorial.other.pipeline.PLStorage.*;
//
///**
// * 管道与事件有关的内容
// * @author luqin2007
// */
//public class PLEvent extends Event {
//
//    @Mod.EventBusSubscriber
//    public static class TickEventHandler {
//        private static boolean start = false;
//
//        private static void start(World world) {
//            if (!start && world != null && !world.isRemote) {
//                ELEMENTS.forEach(e -> e.tickStart(world));
//                start = true;
//            }
//        }
//
//        private static void end() {
//            if (start) {
//                ELEMENTS.addAll(ELEMENTS_ADD);
//                ELEMENTS.removeAll(ELEMENTS_REMOVE);
//                ELEMENTS.forEach(PLElement::tickEnd);
//                System.out.println("add " + ELEMENTS_ADD.size() + ", remove " + ELEMENTS_REMOVE.size() + ", total " + ELEMENTS.size());
//                ELEMENTS_ADD.clear();
//                ELEMENTS_REMOVE.clear();
//                start = false;
//            }
//        }
//
//        @SubscribeEvent
//        public static void onTick(TickEvent.WorldTickEvent event) {
//            if (event.side == Side.SERVER) {
//                if (event.phase == TickEvent.Phase.START) {
//                    start(event.world);
//                } else if (event.phase == TickEvent.Phase.END) {
//                    end();
//                }
//                PLStorage.load(event.world).markDirty();
//            }
//        }
//    }
//
//    public static final EventBus PLEventBus = new EventBus();
//
//    public static <T extends PLEvent> T post(T event) {
//        PLEventBus.post(event);
//        return event;
//    }
//
//    public static void register(Object target) {
//        PLEventBus.register(target);
//    }
//
//    @HasResult
//    public static class PLElementSend extends PLEvent {
//        public final PLElement element;
//        public PLElementSend(PLElement element) {
//            this.element = element;
//        }
//    }
//
//    public static boolean onElementSend(PLElement element) {
//        PLElementSend event = post(new PLElementSend(element));
//        return event.getResult() != Result.DENY;
//    }
//
//    @HasResult
//    public static class PLElementRemove extends PLEvent {
//        public final PLElement element;
//        public PLElementRemove(PLElement element) {
//            this.element = element;
//        }
//    }
//
//    public static boolean onElementRemove(PLElement element) {
//        PLElementRemove event = post(new PLElementRemove(element));
//        return event.getResult() != Result.DENY;
//    }
//
//    public static class PLPathFindEvent extends PLEvent {
//        public final PLElement element;
//        public final Map<BlockPos, PLPath> pathMap;
//        public final BlockPos from;
//        public final World world;
//
//        public PLPathFindEvent(PLElement element, Map<BlockPos, PLPath> pathMap, BlockPos from, World world) {
//            this.element = element;
//            this.pathMap = pathMap;
//            this.from = from;
//            this.world = world;
//        }
//
//        @HasResult
//        public static class Pre extends PLPathFindEvent {
//            public Pre(PLElement element, Map<BlockPos, PLPath> pathMap, BlockPos from, World world) {
//                super(element, pathMap, from, world);
//            }
//        }
//
//        public static class Post extends PLPathFindEvent {
//            public Post(PLElement element, Map<BlockPos, PLPath> pathMap, BlockPos from, World world) {
//                super(element, pathMap, from, world);
//            }
//        }
//    }
//
//    public static boolean onPathFindPre(PLElement element, Map<BlockPos, PLPath> pathMap, BlockPos from, World world) {
//        PLPathFindEvent event = post(new PLPathFindEvent.Pre(element, pathMap, from, world));
//        return event.getResult() != Result.DENY;
//    }
//
//    public static void onPathFindPost(PLElement element, Map<BlockPos, PLPath> pathMap, BlockPos from, World world) {
//        post(new PLPathFindEvent.Post(element, pathMap, from, world));
//    }
//
//    public static class PLPathTickEvent extends PLEvent {
//        public final PLPath path;
//        public PLPathTickEvent(PLPath path) {
//            this.path = path;
//        }
//
//        @HasResult
//        public static class Pre extends PLPathTickEvent {
//            public final World world;
//            public Pre(PLPath path, World world) {
//                super(path);
//                this.world = world;
//            }
//        }
//
//        public static class Stay extends PLPathTickEvent {
//            public final World world;
//            public Stay(PLPath path, World world) {
//                super(path);
//                this.world = world;
//            }
//
//            public static class Pause extends Stay {
//                public Pause(PLPath path, World world) {
//                    super(path, world);
//                }
//            }
//
//            public static class Increase extends Stay {
//                public Increase(PLPath path, World world) {
//                    super(path, world);
//                }
//            }
//        }
//
//        public static class Transfer extends PLPathTickEvent {
//            public final World world;
//            public final PLInfo plPos;
//            public final PLElement element;
//            public Transfer(PLPath path, World world, PLInfo plPos, PLElement element) {
//                super(path);
//                this.world = world;
//                this.plPos = plPos;
//                this.element = element;
//            }
//
//            @HasResult
//            public static class Output extends Transfer {
//                public Output(PLPath path, World world, PLInfo plPos, PLElement element) {
//                    super(path, world, plPos, element);
//                }
//            }
//
//            @HasResult
//            public static class Send extends Transfer {
//                public PLInfo next;
//                public Send(PLPath path, World world, PLInfo plPos, PLElement element, PLInfo next) {
//                    super(path, world, plPos, element);
//                    this.next = next;
//                }
//            }
//        }
//
//        @HasResult
//        public static class InvalidPath extends PLPathTickEvent {
//            public final World world;
//            public final PLElement element;
//            public InvalidPath(PLPath path, World world, PLElement element) {
//                super(path);
//                this.world = world;
//                this.element = element;
//            }
//        }
//
//        @HasResult
//        public static class Post extends PLPathTickEvent {
//            public Post(PLPath path) {
//                super(path);
//            }
//        }
//    }
//
//    public static boolean onPathTickPre(PLPath path, World world) {
//        PLPathTickEvent event = post(new PLPathTickEvent.Pre(path, world));
//        return event.getResult() != Result.DENY;
//    }
//
//    public static void onPathTickPause(PLPath path, World world) {
//        post(new PLPathTickEvent.Stay.Pause(path, world));
//    }
//
//    public static void onPathTickIncrease(PLPath path, World world) {
//        post(new PLPathTickEvent.Stay.Increase(path, world));
//    }
//
//    public static boolean onPathTickOutput(PLPath path, World world, PLInfo plPos, PLElement element) {
//        PLPathTickEvent.Transfer.Output out = post(new PLPathTickEvent.Transfer.Output(path, world, plPos, element));
//        return out.getResult() != Result.DENY;
//    }
//
//    public static PLPathTickEvent.Transfer.Send onPathTickSend(PLPath path, World world, PLInfo plPos, PLElement element, PLInfo next) {
//        return post(new PLPathTickEvent.Transfer.Send(path, world, plPos, element, next));
//    }
//
//    public static boolean onPathTickInvalid(PLPath path, World world, PLElement element) {
//        PLPathTickEvent event = post(new PLPathTickEvent.InvalidPath(path, world, element));
//        return event.getResult() != Result.DENY;
//    }
//
//    public static void onPathTickPost(PLPath path) {
//        post(new PLPathTickEvent.Post(path));
//    }
//
//    public static class PLPathBackEvent extends PLEvent {
//        public final PLPath path;
//        public final World world;
//        public final PLElement element;
//        public PLPathBackEvent(PLPath path, World world, PLElement element) {
//            this.path = path;
//            this.world = world;
//            this.element = element;
//        }
//
//        @HasResult
//        public static class ForContinue extends PLPathBackEvent {
//            public final Map<BlockPos, PLPath> pathMap;
//            public final PLPath nextSub;
//            public ForContinue(PLPath path, World world, PLElement element, Map<BlockPos, PLPath> pathMap, PLPath nextSub) {
//                super(path, world, element);
//                this.pathMap = pathMap;
//                this.nextSub = nextSub;
//            }
//        }
//
//        public static class ToStart extends PLPathBackEvent {
//            public final Object before;
//            public ToStart(PLPath path, World world, PLElement element, Object before) {
//                super(path, world, element);
//                this.before = before;
//            }
//        }
//    }
//
//    public static boolean onBackAndContinue(PLPath path, World world, PLElement element, Map<BlockPos, PLPath> pathMap, PLPath nextSub) {
//        PLPathBackEvent event = new PLPathBackEvent.ForContinue(path, world, element, pathMap, nextSub);
//        PLEventBus.post(event);
//        return event.getResult() != Result.DENY;
//    }
//
//    public static void onBackToStart(PLPath path, World world, PLElement element, Object before) {
//        post(new PLPathBackEvent.ToStart(path, world, element, before));
//    }
//
//    @HasResult
//    public static class PLPathAppend extends PLEvent {
//        public final PLPath path;
//        public final PLInfo next;
//        public final BlockPos out;
//        public PLPathAppend(PLPath path, PLInfo next, BlockPos out) {
//            this.path = path;
//            this.next = next;
//            this.out = out;
//        }
//    }
//
//    public static boolean onPathAppend(PLPath path, PLInfo next, BlockPos out) {
//        PLPathAppend event = post(new PLPathAppend(path, next, out));
//        return event.getResult() != Result.DENY;
//    }
//}
