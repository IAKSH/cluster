package me.iaksh.cluster.core.data;

public class OctaveShift {
    private int shift;

    public OctaveShift(int shift) {
        setShift(shift);
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}