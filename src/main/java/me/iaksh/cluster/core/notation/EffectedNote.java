package me.iaksh.cluster.core.notation;

import me.iaksh.cluster.core.waveform.effect.Effect;

public interface EffectedNote {
    boolean hasEffect();
    Effect getEffect();
}
