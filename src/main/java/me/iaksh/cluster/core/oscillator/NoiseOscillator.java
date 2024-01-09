package me.iaksh.cluster.core.oscillator;

import me.iaksh.cluster.core.data.DurationMs;
import me.iaksh.cluster.core.data.Frequency;

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

    public NoiseOscillator(Frequency frequency, DurationMs durationMs) {
        super(frequency,durationMs);
        random = new Random();
    }

    public double getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }
}
