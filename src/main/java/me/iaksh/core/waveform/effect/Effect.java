package me.iaksh.core.waveform.effect;

import me.iaksh.core.waveform.WaveGenerator;

public abstract class Effect extends WaveGenerator {

    protected WaveGenerator generator;

    public Effect(WaveGenerator generator) {
        this.generator = generator;
    }

    public Effect() {}

    public boolean hasGenerator() {
        return generator != null;
    }

    public void setGenerator(WaveGenerator generator) {
        this.generator = generator;
    }
}
