package me.iaksh.core.notation;

import me.iaksh.core.waveform.effect.Effect;

public class EqualTempNote extends Note {
    private int simpleScore;
    private int octaveShift;
    private int semitoneShift;

    private void init(int simpleScore,int octaveShift,int semitoneShift) {
        this.simpleScore = simpleScore;
        this.octaveShift = octaveShift;
        this.semitoneShift = semitoneShift;
    }

    public int toFreq(int simpleScore,int octaveShift,int semitoneShift) {
        double[] equalTemperaments = {0,261.63,293.66,329.63,349.23,392.00,440.00,493.88};
        double baseFreq = equalTemperaments[simpleScore];
        double freq = baseFreq *
                Math.pow(2, octaveShift) *
                Math.pow(2, semitoneShift / 12.0);
        return (int) freq;
    }

    public EqualTempNote(float fra, boolean dot,int score, int oct, int semi) {
        super(fra,dot);
        init(score,oct,semi);
    }

    public EqualTempNote(float fra, boolean dot, int score, int oct, int semi, Effect effect) {
        super(fra,dot,effect);
        init(score,oct,semi);
    }

    @Override
    public int getFreq() {
        return toFreq(simpleScore,octaveShift,semitoneShift);
    }
}
