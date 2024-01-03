package me.iaksh.cluster.core.notation;

import me.iaksh.cluster.core.waveform.effect.Effect;

/**
 * 直接指定频率的音符
 */
public class FreqNote extends Note {

    private int frequency;

    private void init() {
        this.frequency = frequency;
    }

    public FreqNote(float noteFraction, boolean dotted,int frequency) {
        super(noteFraction, dotted);
        init();
    }

    public FreqNote(float noteFraction, boolean dotted, int frequency, Effect effect) {
        super(noteFraction, dotted,effect);
        init();
    }

    /**
     * 直接获取频率
     * @return 频率
     */
    @Override
    public int getFreq() {
        return frequency;
    }
}
