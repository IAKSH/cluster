package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.effects.ExpAttenuationEffect;
import me.iaksh.cluster.core.oscillator.SquareOscillator;

public class SquareSynth extends Synthesizer {
    public SquareSynth(BPM bpm) {
        super(bpm,new SquareOscillator(),new ExpAttenuationEffect());
    }
}
