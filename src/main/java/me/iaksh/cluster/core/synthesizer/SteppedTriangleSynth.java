package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.effects.Effect;
import me.iaksh.cluster.core.oscillator.Oscillator;
import me.iaksh.cluster.core.oscillator.SteppedTriangleOscillator;

public class SteppedTriangleSynth extends Synthesizer {
    @Override
    protected Oscillator createOscillator() {
        return new SteppedTriangleOscillator(getFrequency(),getDuration());
    }

    public SteppedTriangleSynth(BPM bpm) {
        super(bpm);
    }

    public SteppedTriangleSynth(BPM bpm, Effect effect) {
        super(bpm,effect);
    }
}
