package me.iaksh.cluster.core.oscillator;

import me.iaksh.cluster.core.base.PCMData;
import me.iaksh.cluster.core.base.Waveform;
import me.iaksh.cluster.core.base.WaveformInput;
import me.iaksh.cluster.core.data.Frequency;
import me.iaksh.cluster.core.data.SimpleRate;

public abstract class Oscillator implements WaveformInput {

    private int ms;
    private Frequency frequency;

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

    public Oscillator(int ms, Frequency frequency) {
        if(ms < 0)
            throw new IllegalArgumentException(String.format("ms must be positive, but given = %d",ms));
        this.ms = ms;
        this.frequency = frequency;
    }

    public Oscillator() {
        ms = 1000;
        frequency = new Frequency(100);
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public int getMs() {
        return ms;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }

    @Override
    public final Waveform getWaveform() {
        return new Waveform(new PCMData(crop(ms,(int) frequency.getFloat())));
    }
}
