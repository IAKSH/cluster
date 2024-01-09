package me.iaksh.cluster.core.base;

public interface WaveformOutput {
    void setWaveform(Waveform waveform);

    default void setWaveform(WaveformInput input) {
        setWaveform(input.getWaveform());
    }
}
