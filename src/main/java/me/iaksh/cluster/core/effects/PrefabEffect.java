package me.iaksh.cluster.core.effects;

import me.iaksh.cluster.core.base.Waveform;

public abstract class PrefabEffect extends Effect {
    private Waveform bindingWaveform;
    protected abstract Waveform trans(Waveform waveform);

    @Override
    public Waveform getWaveform() {
        if(bindingWaveform == null)
            throw new RuntimeException("missing binding waveform");
        return trans(bindingWaveform);
    }

    @Override
    public void setWaveform(Waveform waveform) {
        bindingWaveform = waveform;
    }
}
