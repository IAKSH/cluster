package me.iaksh.cluster.core.waveform.effect;

public class ReverseExpGradientEffect extends ExpGradientEffect {
    @Override
    protected float gradientCoefficient(int waveformLen, int i) {
        float a = 1.0f - super.gradientCoefficient(waveformLen,i);
        return a;
    }
}
