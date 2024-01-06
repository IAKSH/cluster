package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.base.WaveformInput;
import me.iaksh.cluster.core.data.*;

public abstract class Synthesizer implements WaveformInput {
    private TimeSignature currentTimeSignature;
    private TimeFraction currentTimeFraction;
    private Frequency currentFrequency;
    private OctaveShift currentOctaveShift;
    private SemitoneShift currentSemitoneShift;
    private boolean currentDotted;

    public void setCurrentFrequency(Frequency currentFrequency) {
        this.currentFrequency = currentFrequency;
    }

    public void setCurrentDotted(boolean currentDotted) {
        this.currentDotted = currentDotted;
    }

    public void setCurrentOctaveShift(OctaveShift currentOctaveShift) {
        this.currentOctaveShift = currentOctaveShift;
    }

    public void setCurrentSemitoneShift(SemitoneShift currentSemitoneShift) {
        this.currentSemitoneShift = currentSemitoneShift;
    }

    public void setCurrentTimeFraction(TimeFraction currentTimeFraction) {
        this.currentTimeFraction = currentTimeFraction;
    }

    public void setCurrentTimeSignature(TimeSignature currentTimeSignature) {
        this.currentTimeSignature = currentTimeSignature;
    }

    public Frequency getCurrentFrequency() {
        return currentFrequency;
    }

    public OctaveShift getCurrentOctaveShift() {
        return currentOctaveShift;
    }

    public SemitoneShift getCurrentSemitoneShift() {
        return currentSemitoneShift;
    }

    public TimeFraction getCurrentTimeFraction() {
        return currentTimeFraction;
    }

    public TimeSignature getCurrentTimeSignature() {
        return currentTimeSignature;
    }

    public boolean isCurrentDotted() {
        return currentDotted;
    }
}
