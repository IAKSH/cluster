package me.iaksh.cluster.core.waveform.effect;

import me.iaksh.cluster.core.waveform.WaveGenerator;

/**
 * 指数渐变效果器（渐退）
 */
public class ExpGradientEffect extends GradientEffect {

    private float expCoefficient = 1;

    public ExpGradientEffect(WaveGenerator generator) {
        super(generator);
    }

    public ExpGradientEffect() {}

    public ExpGradientEffect(WaveGenerator generator, float expCoefficient) {
        super(generator);
        this.expCoefficient = expCoefficient;
    }

    @Override
    protected float gradientCoefficient(int waveformLen, int i) {
        // y=e^{-ax}, a = expCoefficient
        return (float) Math.exp(-expCoefficient * ((float) i / (float) waveformLen));
    }

    /**
     * 获取指数系数 ( y=e^{-ax} )
     * @return 指数系数 a
     */
    public float getExpCoefficient() {
        return expCoefficient;
    }

    /**
     * 设置指数系数 ( y=e^{-ax} )
     * @param expCoefficient 指数系数 a
     */
    public void setExpCoefficient(float expCoefficient) {
        this.expCoefficient = expCoefficient;
    }
}
