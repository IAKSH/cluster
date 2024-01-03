package me.iaksh.cluster.core.waveform;

/**
 * 波源
 */
public abstract class WaveGenerator {
    /**
     * 获取全局采样率
     * @return 全局采样率
     */
    public static int getSampleRate() {
        return 44100;
    }

    /**
     * 生成持续指定时长的指定频率的波形
     * @param ms 持续时间（毫秒）
     * @param freq 频率
     * @return 16bit PCM数据
     */
    public abstract short[] genWaveform(int ms,int freq);
}
