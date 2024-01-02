package me.iaksh.cluster.core.waveform.effect;

import me.iaksh.cluster.core.waveform.WaveGenerator;

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

    public float getExpCoefficient() {
        return expCoefficient;
    }

    public void setExpCoefficient(float expCoefficient) {
        this.expCoefficient = expCoefficient;
    }
}
