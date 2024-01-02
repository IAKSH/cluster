package me.iaksh.cluster.core.waveform.effect;

public class ReverseLinearGradientEffect extends LinearGradientEffect {
    @Override
    protected float gradientCoefficient(int waveformLen,int i) {
        return 1.0f - super.gradientCoefficient(waveformLen,i);
    }
}
