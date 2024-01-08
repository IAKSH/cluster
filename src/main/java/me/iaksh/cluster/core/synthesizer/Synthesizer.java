package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.base.Waveform;
import me.iaksh.cluster.core.base.WaveformInput;
import me.iaksh.cluster.core.data.*;
import me.iaksh.cluster.core.effects.Effect;
import me.iaksh.cluster.core.oscillator.Oscillator;

public abstract class Synthesizer implements WaveformInput {
    private static final double[] equalTemperaments = {0,261.63,293.66,329.63,349.23,392.00,440.00,493.88};
    private int scaleStep;

    // TODO: init these
    private BPM bpm;
    private DurationMs duration;
    private TimeSignature currentTimeSignature;
    private TimeFraction currentTimeFraction;
    private Frequency currentFrequency;
    private OctaveShift currentOctaveShift;
    private SemitoneShift currentSemitoneShift;
    private boolean currentDotted;

    protected Oscillator oscillator;
    protected Effect effect;

    private void updateDuration() {
        int ms = (int) (currentTimeFraction.getFraction() *
                currentTimeSignature.getStandardTimeFraction().getFraction() * 60000.0f / bpm.getBpm());
        if(currentDotted)
            ms = (int)(ms * 1.5f);
        duration.setMs(ms);
    }

    private void updateFrequency() {
        double baseFreq = equalTemperaments[scaleStep];
        double freq = baseFreq *
                Math.pow(2, currentOctaveShift.getShift()) *
                Math.pow(2, currentSemitoneShift.getShift() / 12.0);
        currentFrequency.setFreq(freq);
    }

    public Synthesizer(BPM bpm,Oscillator oscillator) {
        this.bpm = bpm;
        this.oscillator = oscillator;
    }

    public Synthesizer(BPM bpm,Oscillator oscillator,Effect effect) {
        this.bpm = bpm;
        this.oscillator = oscillator;
        this.effect = effect;
    }

    public void setScaleStep(int scaleStep) {
        if(scaleStep < 1 || scaleStep > 7)
            throw new IllegalArgumentException(String.format("scale step must be in range of [1,7], but given = %d",scaleStep));
        this.scaleStep = scaleStep;
        updateFrequency();
    }

    public void setCurrentDotted(boolean currentDotted) {
        this.currentDotted = currentDotted;
        updateDuration();
    }

    public void setCurrentOctaveShift(OctaveShift currentOctaveShift) {
        this.currentOctaveShift = currentOctaveShift;
        updateFrequency();
    }

    public void setCurrentSemitoneShift(SemitoneShift currentSemitoneShift) {
        this.currentSemitoneShift = currentSemitoneShift;
        updateFrequency();
    }

    public void setCurrentTimeFraction(TimeFraction currentTimeFraction) {
        this.currentTimeFraction = currentTimeFraction;
        updateDuration();
    }

    public void setCurrentTimeSignature(TimeSignature currentTimeSignature) {
        this.currentTimeSignature = currentTimeSignature;
        updateDuration();
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

    @Override
    public final Waveform getWaveform() {
        if(effect != null) {
            effect.setWaveform(oscillator.getWaveform());
            return effect.getWaveform();
        } else {
            return oscillator.getWaveform();
        }
    }
}
