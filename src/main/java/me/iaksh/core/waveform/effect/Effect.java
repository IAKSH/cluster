package me.iaksh.core.waveform.effect;

import me.iaksh.core.waveform.WaveGenerator;
import me.iaksh.core.waveform.oscillator.Oscillator;

public abstract class Effect extends WaveGenerator {
    protected final Oscillator oscillator;
    public Effect(Oscillator oscillator) {
        this.oscillator = oscillator;
    }
}
