package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.effects.Effect;
import me.iaksh.cluster.core.base.Waveform;
import me.iaksh.cluster.core.effects.ExpAttenuationEffect;
import me.iaksh.cluster.core.oscillator.Oscillator;
import me.iaksh.cluster.core.oscillator.SquareOscillator;

public class SquareSynth extends Synthesizer {
    private Oscillator oscillator = new SquareOscillator();
    private Effect effect = new ExpAttenuationEffect();

    void updateOscillator() {
        // TODO:
    }

    @Override
    public Waveform getWaveform() {
        updateOscillator();
        effect.setWaveform(oscillator.getWaveform());
        return effect.getWaveform();
    }
}
