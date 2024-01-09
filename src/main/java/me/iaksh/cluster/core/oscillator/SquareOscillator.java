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

        for(int i = 0;i < samplesPerCycle;i++) {
            double x = i + phaseShift * samplesPerCycle;
            data[i] = (short) (1 - 2 * Math.ceil(x / samplesPerCycle - Math.floor(x / samplesPerCycle) - dutyCycle));
            data[i] = (short) (((double) data[i] - 0.5) * 2 * amplitude * Short.MAX_VALUE);
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
