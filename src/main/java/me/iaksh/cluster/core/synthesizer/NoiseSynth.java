package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.effects.Effect;
import me.iaksh.cluster.core.oscillator.NoiseOscillator;
import me.iaksh.cluster.core.oscillator.Oscillator;

public class NoiseSynth extends Synthesizer {
    @Override
    protected Oscillator createOscillator() {
        return new NoiseOscillator(getFrequency(),getDuration());
    }

    public NoiseSynth(BPM bpm) {
        super(bpm);
    }

    public NoiseSynth(BPM bpm, Effect effect) {
        super(bpm,effect);
    }
}
