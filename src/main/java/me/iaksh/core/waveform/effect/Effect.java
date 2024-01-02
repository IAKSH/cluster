package me.iaksh.core.waveform.effect;

import me.iaksh.core.waveform.WaveGenerator;
import me.iaksh.core.waveform.oscillator.Oscillator;

public abstract class Effect extends WaveGenerator {
    protected final WaveGenerator generator;
    public Effect(WaveGenerator generator) {
        this.generator = generator;
    }
}
