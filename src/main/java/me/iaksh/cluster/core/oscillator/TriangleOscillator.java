package me.iaksh.cluster.core.oscillator;

import me.iaksh.cluster.core.data.DurationMs;
import me.iaksh.cluster.core.data.Frequency;

public class TriangleOscillator extends Oscillator {
    protected double amplitude = 1.0;
    protected double phaseShift = 1.0;

    public TriangleOscillator(Frequency frequency, DurationMs durationMs) {
        super(frequency,durationMs);
    }

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        short[] data = new short[samplesPerCycle];

        double maxAmplitude = Short.MAX_VALUE * amplitude;
        double phaseIncrement = (2 * Math.PI) / samplesPerCycle;
        double currentPhase = 0;

        for (int j = 0; j < data.length; j++) {
            double value = Math.asin(Math.sin(currentPhase)) * 2 / Math.PI;
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
