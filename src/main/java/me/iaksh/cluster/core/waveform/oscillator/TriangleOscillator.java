package me.iaksh.cluster.core.waveform.oscillator;

/**
 * 三角波振荡器
 * 生成原始三角波
 */
public class TriangleOscillator extends CroppingOscillator {
    protected float amplitude = 0.5f;
    protected float phaseShift = 1.0f;

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        short[] data = new short[samplesPerCycle];

        float maxAmplitude = Short.MAX_VALUE * amplitude;
        float phaseIncrement = (2 * (float) Math.PI) / samplesPerCycle;
        float currentPhase = 0;

        for (int j = 0; j < data.length; j++) {
            float value = (float) (Math.asin(Math.sin(currentPhase)) * 2 / Math.PI);
            data[j] = (short) (value * maxAmplitude);

            currentPhase += phaseShift * phaseIncrement;
            if (currentPhase >= 2 * Math.PI) {
                currentPhase -= 2 * Math.PI;
            }
        }
        return data;
    }

    /**
     * 获取振幅系数
     * @return 振幅系数
     */
    public float getAmplitude() {
        return amplitude;
    }

    /**
     * 获取相位偏移
     * @return 相位偏移
     */
    public float getPhaseShift() {
        return phaseShift;
    }

    /**
     * 设置振幅系数
     * @param amplitude 振幅系数
     */
    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    /**
     * 设置相位偏移
     * @param phaseShift 相位偏移
     */
    public void setPhaseShift(float phaseShift) {
        this.phaseShift = phaseShift;
    }
}
