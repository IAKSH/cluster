package me.iaksh.cluster.core.base;

public class PCMData {
    private short[] data;

    public PCMData(short[] data) {
        this.data = data;
    }

    public short[] toArray() {
        return data;
    }
}
