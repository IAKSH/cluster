package me.iaksh.cluster.core.waveform;

public abstract class WaveGenerator {
    public static int getSampleRate() {
        return 44100;
    }
    public abstract short[] genWaveform(int ms,int freq);
}
