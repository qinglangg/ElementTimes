package com.elementtimes.tutorial.interfaces.tileentity;

import java.util.Set;
import java.util.function.Predicate;

/**
 * 定义机器工作的生命周期
 *  机器停止工作（!IMachineTickable#isWorking&!onPause）时，每 tick 都会执行 onCheckStart() 方法，直到返回 true；
 *  当 onCheckStart()=true 时，会立即依次执行 onStart() 和 onLoop()
 *  机器运行时（IMachineTickable#isWorking），每 tick 都会执行一次 onLoop() 和 onCheckFinish()，直到 onCheckFinish() 返回 true
 *  当 onCheckFinish()=true 时，会立即调用 onFinish() 和 onCheckStart()-onStart()（为了极时更新状态，减少方块更新）
 *  当 onLoop 中有返回 false 时，会立刻调用 onPause，并在之后每 tick 调用 onCheckResume() 直到返回 true
 *  当 onCheckResume()=true 时，会立即调用 onResume()
 *
 * working 循环：
 *  onCheckStart -> onStart -> onLoop -> onCheckFinish -> onFinish
 *   ↑       ↓                   ↑             ↓            ↓
 *   ↑---<---↓                   ↑------<------↓            ↓
 *   ↑                                                      ↓
 *   ↑---<------<------<------<------<------<------<------<-↓
 *
 *
 * resume 循环：
 *  (onWorking) -> onPause -> onCheckResume -> onResume
 *      ↑                        ↑      ↓         ↓
 *      ↑                        ↑--<---↓         ↓
 *      ↑                                         ↓
 *      ↑---<------<------<------<------<-------<-↓
 *
 * 以上仅为 TileMachine 的默认实现
 *
 * @author luqin2007
 */
public interface IMachineLifecycle {

    /**
     * 检查机器是否可以启动，通常工作是检查是否有可用合成，检查能量和输入是否足够.
     * @return 机器是否可以启动
     */
    default boolean onCheckStart() { return true; }

    /**
     * 机器启动。通常用于为合成做准备，比如提前消耗材料，修改状态等.
     */
    default void onStart() {}

    /**
     * 每 tick 合成操作，比如更新进度，能量消耗等.
     * @return 合成状态是否出错。返回 false 则会进入 resume 循环
     */
    default boolean onLoop() {return true;}

    /**
     * 检查合成是否结束.
     * @return 返回 true 则进入 onFinish()
     */
    default boolean onCheckFinish() {return true;}

    /**
     * 进入暂停前的准备.
     */
    default void onPause() {}

    /**
     * 查看是否可以从暂停状态恢复.
     * @return true 可从暂停恢复
     */
    default boolean onCheckResume() {return true;}

    /**
     * 恢复暂停状态.
     */
    default void onResume() {}

    /**
     * 合成结束。
     * @return true 表示合成结束过程正常，如产物完全排出。
     *         false 表示结束阶段未执行完，机器还不能开始下一轮合成，而是进入 Pause 循环
     */
    default boolean onFinish() { return true; }

    /**
     * 每 Tick 生命周期开始时执行
     */
    default void onTickStart() {}

    /**
     * 每 Tick 生命周期结束时执行
     */
    default void onTickFinish() {}

    interface IMachineLifecycleManager extends IMachineLifecycle {
        Set<IMachineLifecycle> getAllLifecycles();

        default void addLifeCycle(IMachineLifecycle lifeCycle) {
            getAllLifecycles().add(lifeCycle);
        }

        default void removeLifecycle(IMachineLifecycle lifeCycle) {
            getAllLifecycles().remove(lifeCycle);
        }

        default void removeLifecycle(Class<? extends IMachineLifecycle> clazz) {
            getAllLifecycles().removeIf(lc -> lc == null || clazz.isAssignableFrom(lc.getClass()));
        }

        default void removeLifecycle(Predicate<? super IMachineLifecycle> filter) {
            getAllLifecycles().removeIf(filter);
        }

        @Override
        default boolean onCheckStart() {
            boolean ret = true;
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                ret = ret && lifecycle.onCheckStart();
            }
            return ret;
        }

        @Override
        default void onStart() {
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                lifecycle.onStart();
            }
        }

        @Override
        default boolean onLoop() {
            boolean ret = true;
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                ret = ret && lifecycle.onLoop();
            }
            return ret;
        }

        @Override
        default boolean onCheckFinish() {
            boolean ret = true;
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                ret = ret && lifecycle.onCheckFinish();
            }
            return ret;
        }

        @Override
        default void onPause() {
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                lifecycle.onPause();
            }
        }

        @Override
        default boolean onCheckResume() {
            boolean ret = true;
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                ret = ret && lifecycle.onCheckResume();
            }
            return ret;
        }

        @Override
        default void onResume() {
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                lifecycle.onResume();
            }
        }

        @Override
        default boolean onFinish() {
            boolean ret = true;
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                ret = ret && lifecycle.onFinish();
            }
            return ret;
        }

        @Override
        default void onTickStart() {
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                lifecycle.onTickStart();
            }
        }

        @Override
        default void onTickFinish() {
            for (IMachineLifecycle lifecycle : getAllLifecycles()) {
                lifecycle.onTickFinish();
            }
        }
    }
}
