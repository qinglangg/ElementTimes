package com.elementtimes.tutorial.test_animation;

import net.minecraftforge.client.model.animation.AnimationTESR;
import net.minecraftforge.common.animation.Event;

import static com.elementtimes.tutorial.annotation.util.MessageUtil.warn;

public class BlockAnimatedTESR extends AnimationTESR<TileBlockAnimated> {

    @Override
    public void handleEvents(TileBlockAnimated te, float time, Iterable<Event> pastEvents) {
        warn("Time: {}, {}, events: ", time, te);
        pastEvents.forEach(event -> {
            warn("\t\tevent: {}, offset {}", event.event(), event.offset());
        });
        super.handleEvents(te, time, pastEvents);
    }
}
