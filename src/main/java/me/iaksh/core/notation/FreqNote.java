package me.iaksh.core.notation;

import me.iaksh.core.waveform.effect.Effect;

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

    @Override
    public int getFreq() {
        return frequency;
    }
}
