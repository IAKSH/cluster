package me.iaksh.cluster.core.waveform.oscillator;

/**
 * 方波振荡器
 * 生成原始方波
 */
public class SquareOscillator extends CroppingOscillator {
    private float dutyCycle = 0.5f;
    private float phaseShift = 1.0f;
    private float amplitude = 0.5f;

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

    /**
     * 获取振幅系数
     * @return 振幅系数
     */
    public float getAmplitude() {
        return amplitude;
    }

    /**
     * 获取占空比
     * @return 占空比
     */
    public float getDutyCycle() {
        return dutyCycle;
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
     * 设置占空比
     * @param dutyCycle 占空比
     */
    public void setDutyCycle(float dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    /**
     * 设置相位偏移
     * @param phaseShift 相位偏移
     */
    public void setPhaseShift(float phaseShift) {
        this.phaseShift = phaseShift;
    }
}
