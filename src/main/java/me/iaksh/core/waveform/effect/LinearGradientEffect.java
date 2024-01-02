package me.iaksh.core.waveform.effect;

import me.iaksh.core.waveform.WaveGenerator;

public class LinearGradientEffect extends GradientEffect {
    public LinearGradientEffect(WaveGenerator generator) {
        super(generator);
    }

    @Override
    protected float gradientCoefficient(int waveformLen,int i) {
        return 1.0f - (float) i / (float) waveformLen;
    }
}
