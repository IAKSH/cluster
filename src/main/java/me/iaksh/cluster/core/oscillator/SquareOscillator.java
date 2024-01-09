package me.iaksh.cluster.core.oscillator;

import me.iaksh.cluster.core.data.DurationMs;
import me.iaksh.cluster.core.data.Frequency;

public class SquareOscillator extends Oscillator {
    private double dutyCycle = 0.25;
    private double phaseShift = 1.0;
    private double amplitude = 1.0;

    public SquareOscillator(Frequency frequency, DurationMs duration) {
        super(frequency,duration);
    }

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        short[] data = new short[samplesPerCycle];
        int halfSamples = (int) (samplesPerCycle * dutyCycle);
        int phaseSamples = (int) (samplesPerCycle * phaseShift);

        for (int i = 0; i < samplesPerCycle; i++) {
            if ((i + phaseSamples) % samplesPerCycle < halfSamples) {
                data[i] = (short) (Short.MAX_VALUE * amplitude);
            } else {
                data[i] = (short) (Short.MIN_VALUE * amplitude);
            }
        }
        return data;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public double getDutyCycle() {
        return dutyCycle;
    }

    public double getPhaseShift() {
        return phaseShift;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public void setDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public void setPhaseShift(double phaseShift) {
        this.phaseShift = phaseShift;
    }
}
