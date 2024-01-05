package me.iaksh.cluster.core.notation;

import me.iaksh.cluster.core.waveform.effect.Effect;

public abstract class Note implements EffectedNote {

    private Effect effect;

    private void init(float noteFraction,boolean dotted) {
        this.noteFraction = noteFraction;
        this.dotted = dotted;
    }

    protected float noteFraction;
    protected boolean dotted;

    public Note(float noteFraction,boolean dotted) {
        init(noteFraction,dotted);
    }

    public Note(float noteFraction,boolean dotted,Effect effect) {
        init(noteFraction,dotted);
        this.effect = effect;
    }

    /**
     * 获取x分音符
     * @return 1.0f/x
     */
    public float getNoteFraction() {
        return noteFraction;
    }

    /**
     * 是否为附点音符
     * @return boolean
     */
    public boolean isDotted() {
        return dotted;
    }

    @Override
    public boolean hasEffect() {
        return effect != null;
    }

    @Override
    public Effect getEffect() {
        return effect;
    }

    /**
     * 获取音符的频率
     * @return 频率
     */
    public abstract int getFreq();
}
