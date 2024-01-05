package me.iaksh.cluster.core.waveform.effect;

import me.iaksh.cluster.core.waveform.WaveGenerator;

/**
 * 线性渐变效果器（渐退）
 */
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
