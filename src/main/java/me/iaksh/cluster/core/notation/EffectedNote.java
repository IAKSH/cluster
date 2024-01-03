package me.iaksh.cluster.core.notation;

import me.iaksh.cluster.core.waveform.effect.Effect;

/**
 * 带有效果器的音符
 */
public interface EffectedNote {
    /**
     * 检查该音符是否带有效果器
     * @return boolean
     */
    boolean hasEffect();

    /**
     * 获取效果器
     * @return 效果器
     */
    Effect getEffect();
}
