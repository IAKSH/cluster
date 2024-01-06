package me.iaksh.cluster.core.oscillator;

import me.iaksh.cluster.core.data.Frequency;

public class SinOscillator extends Oscillator {
    private double amplitude = 1.0;
    private double phaseShift = 1.0;

    public SinOscillator(int ms, Frequency frequency) {
        super(ms, frequency);
    }

    public SinOscillator() {
        super();
    }

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        short[] data = new short[samplesPerCycle];

        double maxAmplitude = Short.MAX_VALUE * amplitude;
        double phaseIncrement = (2 * Math.PI) / samplesPerCycle;
        double currentPhase = 0;

        for (int j = 0; j < data.length; j++) {
            double value = Math.sin(currentPhase);
            data[j] = (short) (value * maxAmplitude);

            currentPhase += phaseShift * phaseIncrement;
            if (currentPhase >= 2 * Math.PI) {
                currentPhase -= 2 * Math.PI;
            }
        }
        return data;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public double getPhaseShift() {
        return phaseShift;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public void setPhaseShift(double phaseShift) {
        this.phaseShift = phaseShift;
    }
}
