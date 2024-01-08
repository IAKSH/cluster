package me.iaksh.cluster.core.oscillator;

import me.iaksh.cluster.core.base.PCMData;
import me.iaksh.cluster.core.base.Waveform;
import me.iaksh.cluster.core.base.WaveformInput;
import me.iaksh.cluster.core.data.DurationMs;
import me.iaksh.cluster.core.data.Frequency;
import me.iaksh.cluster.core.data.SimpleRate;

public abstract class Oscillator implements WaveformInput {

    private final Frequency bindingFrequency;
    private final DurationMs bindingDurationMs;

    private short[] crop(int ms,int freq) {
        short[] croppedData = new short[ms * SimpleRate.get() / 1000];
        if(freq == 0) {
            return croppedData;
        }

        int samplesPerCycle = SimpleRate.get() / freq;
        short[] data = genBasicWaveform(samplesPerCycle);

        if(croppedData.length > samplesPerCycle) {
            for(int i = 0;i < croppedData.length;i++) {
                croppedData[i] = data[i % data.length];
            }
        } else {
            System.arraycopy(data, 0, croppedData, 0, croppedData.length);
        }

        return croppedData;
    }

    protected abstract short[] genBasicWaveform(int samplesPerCycle);

    public Oscillator(Frequency frequency,DurationMs duration) {
        this.bindingFrequency = frequency;
        this.bindingDurationMs = duration;
    }

    public Frequency getBindingFrequency() {
        return bindingFrequency;
    }

    public DurationMs getBindingDurationMs() {
        return bindingDurationMs;
    }

    @Override
    public final Waveform getWaveform() {
        return new Waveform(new PCMData(crop(bindingDurationMs.getMs(),(int) bindingFrequency.getFreq())));
    }
}
