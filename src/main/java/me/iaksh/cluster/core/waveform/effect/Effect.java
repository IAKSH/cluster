package me.iaksh.cluster.core.waveform.effect;

import me.iaksh.cluster.core.waveform.WaveGenerator;

/**
 * 对波形进行处理的效果器
 */
public abstract class Effect extends WaveGenerator {

    protected WaveGenerator generator;

    public Effect(WaveGenerator generator) {
        this.generator = generator;
    }

    public Effect() {}

    /**
     * 检查是否有上级波源
     * @return boolean
     */
    public boolean hasGenerator() {
        return generator != null;
    }

    /**
     * 设置上级波源
     * @param generator 上级波源
     */
    public void setGenerator(WaveGenerator generator) {
        this.generator = generator;
    }
}
