package me.iaksh.cluster.core.base;

public interface PCMOutput {
    void setPCM(PCMData pcm);

    default void setPCM(PCMInput input) {
        setPCM(input.getPCM());
    }

    default void setPCM(Waveform waveform) {
        setPCM(waveform.getPCM());
    }

    default void setPCM(WaveformInput input) {
        setPCM(input.getWaveform());
    }
}
