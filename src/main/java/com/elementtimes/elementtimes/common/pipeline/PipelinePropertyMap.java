package com.elementtimes.elementtimes.common.pipeline;

import com.google.common.collect.Sets;
import net.minecraft.util.Direction;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;



public class PipelinePropertyMap implements Map<Direction, PipelineSideProperty> {

    private final PipelineSideProperty[] mProperties;

    public PipelinePropertyMap() {
        mProperties = new PipelineSideProperty[6];
        for (int i = 0; i < 6; i++) {
            mProperties[i] = new PipelineSideProperty(Direction.byIndex(i));
        }
    }

    @Override
    public int size() {
        return 6;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof Direction;
    }

    @Override
    public boolean containsValue(Object value) {
        return ArrayUtils.contains(mProperties, value);
    }

    @Override
    public PipelineSideProperty get(Object key) {
        return mProperties[((Direction) key).getIndex()];
    }

    @Override
    public PipelineSideProperty put(Direction key, PipelineSideProperty value) { return null; }

    @Override
    public PipelineSideProperty remove(Object key) { return null; }

    @Override
    public void putAll(Map<? extends Direction, ? extends PipelineSideProperty> m) { }

    @Override
    public void clear() { }

    private Set<Direction> keySet = null;
    @Override
    public Set<Direction> keySet() {
        if (keySet == null) {
            keySet = Sets.newHashSet(Direction.values());
        }
        return keySet;
    }

    private Collection<PipelineSideProperty> values = null;
    @Override
    public Collection<PipelineSideProperty> values() {
        if (values == null) {
            values = Arrays.asList(mProperties);
        }
        return values;
    }

    private Set<Entry<Direction, PipelineSideProperty>> entrySet = null;
    @Override
    public Set<Entry<Direction, PipelineSideProperty>> entrySet() {
        if (entrySet == null) {
            entrySet = new HashSet<>();
            for (int i = 0; i < mProperties.length; i++) {
                Direction direction = Direction.byIndex(i);
                entrySet.add(new AbstractMap.SimpleEntry<>(direction, mProperties[i]));
            }
        }
        return entrySet;
    }
}
