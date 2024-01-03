package me.iaksh.cluster.core.waveform.effect;

/**
 * 反向线性渐变效果器（渐进）
 */
public class ReverseLinearGradientEffect extends LinearGradientEffect {
    @Override
    protected float gradientCoefficient(int waveformLen,int i) {
        return 1.0f - super.gradientCoefficient(waveformLen,i);
    }
}
