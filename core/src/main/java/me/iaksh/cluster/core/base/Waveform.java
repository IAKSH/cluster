package me.iaksh.cluster.core.base;

public class Waveform implements PCMInput {
    private final PCMData pcmData;

    public Waveform(PCMData pcmData) {
        this.pcmData = pcmData;
    }

    @Override
    public PCMData getPCM() {
        return pcmData;
    }
}