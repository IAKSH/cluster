package me.iaksh.core.oscillator;

public abstract class Oscillator implements WaveGenerator {
    public static int getSampleRate() {
        return 44100;
    }
}
