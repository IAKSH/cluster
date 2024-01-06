package me.iaksh.cluster.core.data;

public class TimeFraction {
    private int fraction;

    public TimeFraction(int fraction) {
        if(fraction <= 0)
            throw new IllegalArgumentException(String.format("fraction must be positive, but given = %d",fraction));
        this.fraction = fraction;
    }

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public double toCoefficient() {
        return 1.0 / fraction;
    }
}
