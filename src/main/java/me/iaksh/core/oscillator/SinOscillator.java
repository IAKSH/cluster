package me.iaksh.core.oscillator;

public class SinOscillator extends CroppingOscillator {
    private float amplitude = 0.5f;
    private float phaseShift = 1.0f;

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        short[] data = new short[samplesPerCycle];

        float maxAmplitude = Short.MAX_VALUE * amplitude;
        float phaseIncrement = (2 * (float) Math.PI) / samplesPerCycle;
        float currentPhase = 0;

        for (int j = 0; j < data.length; j++) {
            float value = (float) Math.sin(currentPhase);
            data[j] = (short) (value * maxAmplitude);

            currentPhase += phaseShift * phaseIncrement;
            if (currentPhase >= 2 * Math.PI) {
                currentPhase -= 2 * Math.PI;
            }
        }
        return data;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public float getPhaseShift() {
        return phaseShift;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public void setPhaseShift(float phaseShift) {
        this.phaseShift = phaseShift;
    }
}