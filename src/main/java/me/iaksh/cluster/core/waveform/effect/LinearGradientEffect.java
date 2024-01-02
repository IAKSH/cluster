package me.iaksh.cluster.core.waveform.effect;

import me.iaksh.cluster.core.waveform.WaveGenerator;

public class LinearGradientEffect extends GradientEffect {

    public LinearGradientEffect(WaveGenerator generator) {
        super(generator);
    }

    public LinearGradientEffect() {}

    @Override
    protected float gradientCoefficient(int waveformLen,int i) {
        return 1.0f - (float) i / (float) waveformLen;
    }
}
