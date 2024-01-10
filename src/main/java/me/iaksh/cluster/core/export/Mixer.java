package me.iaksh.cluster.core.export;

import me.iaksh.cluster.core.base.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Mixer implements WaveformInput,WaveformOutput {
    private ArrayList<PCMInput> pcmInputs = new ArrayList<>();

    private short[] mix() {
        if (pcmInputs.isEmpty()) {
            return new short[0];
        }

        int maxLength = 0;
        for (PCMInput pcmInput : pcmInputs) {
            maxLength = Math.max(maxLength, pcmInput.getPCM().toArray().length);
        }

        // double may prevent overflow (maybe...)
        double[] mixed = new double[maxLength];
        for (PCMInput pcmInput : pcmInputs) {
            short[] data = pcmInput.getPCM().toArray();
            for (int i = 0; i < data.length; i++) {
                mixed[i] += data[i];
            }
        }

        short[] result = new short[maxLength];
        double max = Short.MAX_VALUE;
        for (int i = 0; i < maxLength; i++) {
            // normalize
            result[i] = (short)(mixed[i] * max / Math.max(max, Math.abs(mixed[i])));
        }

        return result;
    }

    public Mixer(PCMInput... waves) {
        pcmInputs.addAll(Arrays.asList(waves));
    }

    public Mixer() {}

    public void add(PCMInput pcmInput) {
        pcmInputs.add(pcmInput);
    }

    public void clear() {
        pcmInputs.clear();
    }

    @Override
    public Waveform getWaveform() {
        return new Waveform(new PCMData(mix()));
    }

    @Override
    public void setWaveform(Waveform waveform) {
        add(waveform);
    }
}
