package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.oscillator.NoiseOscillator;

public class NoiseSynth extends Synthesizer {
    public NoiseSynth(BPM bpm) {
        super(bpm,new NoiseOscillator());
    }
}
