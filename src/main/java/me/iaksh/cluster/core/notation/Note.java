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

    public float getNoteFraction() {
        return noteFraction;
    }

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

    public abstract int getFreq();
}
