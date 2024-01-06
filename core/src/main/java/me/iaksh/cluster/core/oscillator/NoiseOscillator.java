package me.iaksh.cluster.core.oscillator;

import java.util.Random;

public class NoiseOscillator extends Oscillator {
    private final Random random;
    private double amplitude = 0.5;

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        // TODO: 几乎不是噪声波
        short[] data = new short[samplesPerCycle];

        for (int i = 0; i < data.length; i++) {
            data[i] = (short) (random.nextGaussian() % amplitude * Short.MAX_VALUE);
        }
        return data;
    }

    public NoiseOscillator() {
        random = new Random();
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }
}
