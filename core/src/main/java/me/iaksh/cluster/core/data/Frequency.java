package me.iaksh.cluster.core.data;

public class Frequency {
    private float freq;

    public Frequency(float freq) {
        if(freq < 0)
            throw new IllegalArgumentException(String.format("Frequency must be positive, but given = %f",freq));
        this.freq = freq;
    }

    public Frequency() {}

    public float getFloat() {
        return freq;
    }
}
