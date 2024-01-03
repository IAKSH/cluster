package me.iaksh.cluster.core.waveform.oscillator;

import java.util.Random;

/**
 * 噪声震荡器
 * 生成原始噪声波
 */
public class NoiseOscillator extends CroppingOscillator {
    private final Random random;
    private float amplitude = 0.5f;

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        short[] data = new short[samplesPerCycle];

        for (int i = 0; i < data.length; i++) {
            data[i] = (short) (random.nextGaussian() % amplitude * Short.MAX_VALUE);
        }
        return data;
    }

    public NoiseOscillator() {
        random = new Random();
    }

    /**
     * 获取振幅系数
     * @return 振幅系数
     */
    public float getAmplitude() {
        return amplitude;
    }

    /**
     * 设置振幅系数
     * @param amplitude 振幅系数
     */
    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }
}
