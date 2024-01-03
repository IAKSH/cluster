package me.iaksh.cluster.core.waveform.effect;

/**
 * 反向指数渐变效果器（渐进）
 */
public class ReverseExpGradientEffect extends ExpGradientEffect {
    @Override
    protected float gradientCoefficient(int waveformLen, int i) {
        float a = 1.0f - super.gradientCoefficient(waveformLen,i);
        return a;
    }
}
