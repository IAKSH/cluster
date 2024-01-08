package me.iaksh.cluster.core.data;

public class BPM {
    private int bpm;

    public BPM(int bpm) {
        setBpm(bpm);
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        if(bpm <= 0)
            throw new IllegalArgumentException(String.format("bpm must be positive, but given = %d",bpm));
        this.bpm = bpm;
    }
}
