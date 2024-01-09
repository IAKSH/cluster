package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.effects.Effect;
import me.iaksh.cluster.core.oscillator.Oscillator;
import me.iaksh.cluster.core.oscillator.SquareOscillator;

public class SquareSynth extends Synthesizer {
    @Override
    protected Oscillator createOscillator() {
        return new SquareOscillator(getFrequency(),getDuration());
    }

    public SquareSynth(BPM bpm) {
        super(bpm);
    }

    public SquareSynth(BPM bpm, Effect effect) {
        super(bpm,effect);
    }
}
