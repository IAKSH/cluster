package me.iaksh.cluster.core.data;

public class SemitoneShift {
    private int shift;

    public SemitoneShift(int shift) {
        setShift(shift);
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}