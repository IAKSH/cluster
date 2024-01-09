package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.effects.Effect;
import me.iaksh.cluster.core.oscillator.Oscillator;
import me.iaksh.cluster.core.oscillator.TriangleOscillator;

public class TriangleSynth extends Synthesizer {
    @Override
    protected Oscillator createOscillator() {
        return new TriangleOscillator(getFrequency(),getDuration());
    }

    public TriangleSynth(BPM bpm) {
        super(bpm);
    }

    public TriangleSynth(BPM bpm, Effect effect) {
        super(bpm,effect);
    }
}
