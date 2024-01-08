package me.iaksh.cluster.core.data;

public class TimeSignature {
    private int standardNotesPerBar;
    private TimeFraction standardTimeFraction;

    public TimeSignature(int standardNotesPerBar,TimeFraction standardTimeFraction) {
        setStandardNotesPerBar(standardNotesPerBar);
        this.standardTimeFraction = standardTimeFraction;
    }

    public int getStandardNotesPerBar() {
        return standardNotesPerBar;
    }

    public TimeFraction getStandardTimeFraction() {
        return standardTimeFraction;
    }

    public void setStandardNotesPerBar(int standardNotesPerBar) {
        if(standardNotesPerBar <= 0)
            throw new IllegalArgumentException(String.format("standardNotesPerBar must be positive, but given = %d",standardNotesPerBar));
        this.standardNotesPerBar = standardNotesPerBar;
    }

    public void setStandardTimeFraction(TimeFraction standardTimeFraction) {
        this.standardTimeFraction = standardTimeFraction;
    }
}
