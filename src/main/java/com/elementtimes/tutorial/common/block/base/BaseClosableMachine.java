package com.elementtimes.tutorial.common.block.base;

import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;

/**
 * 拥有 on/off 两种状态的机器
 * @author luqin2007
 */
public class BaseClosableMachine<T extends BaseMachine> extends BlockTileBase<T> {

    public BaseClosableMachine(int gui, Class<T> entityClass, boolean addFullEnergyBlock) {
        super(gui, entityClass, addFullEnergyBlock);

        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(IS_RUNNING, false));
    }

    public static IProperty<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static IProperty<Boolean> IS_RUNNING = PropertyBool.create("running");


}
