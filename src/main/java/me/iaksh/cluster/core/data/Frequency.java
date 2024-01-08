package me.iaksh.cluster.core.data;

public class Frequency {
    private double freq;

    public Frequency(double freq) {
        setFreq(freq);
    }

    public double getFreq() {
        return freq;
    }

    public void setFreq(double freq) {
        if(freq < 0)
            throw new IllegalArgumentException(String.format("Frequency must be positive, but given = %f",freq));
        this.freq = freq;
    }
}
