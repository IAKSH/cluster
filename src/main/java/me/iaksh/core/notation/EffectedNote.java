package me.iaksh.core.notation;

import me.iaksh.core.waveform.effect.Effect;

public interface EffectedNote {
    boolean hasEffect();
    Effect getEffect();
}
