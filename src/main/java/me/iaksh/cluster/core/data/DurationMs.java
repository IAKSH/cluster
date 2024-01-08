package me.iaksh.cluster.core.data;

public class DurationMs {
    private int ms;

    public DurationMs(int ms) {
        setMs(ms);
    }

    public void setMs(int ms) {
        if(ms < 0)
            throw new IllegalArgumentException(String.format("duration ms must be positive, but given = %d",ms));
        this.ms = ms;
    }

    public int getMs() {
        return ms;
    }
}
