package me.iaksh.cluster.core.synthesizer;

import me.iaksh.cluster.core.base.Waveform;
import me.iaksh.cluster.core.base.WaveformInput;
import me.iaksh.cluster.core.data.*;
import me.iaksh.cluster.core.effects.Effect;
import me.iaksh.cluster.core.oscillator.Oscillator;

public abstract class Synthesizer implements WaveformInput {
    private static final double[] equalTemperaments = {0,261.63,293.66,329.63,349.23,392.00,440.00,493.88};

    private int scaleStep;
    private final BPM bpm;
    private final DurationMs duration = new DurationMs(1000);
    private final TimeSignature timeSignature = new TimeSignature(4,new TimeFraction(4));
    private final TimeFraction timeFraction = new TimeFraction(4);
    private final Frequency frequency = new Frequency(100);
    private final OctaveShift octaveShift = new OctaveShift(0);
    private final SemitoneShift semitoneShift = new SemitoneShift(0);
    private boolean dotted;

    protected Oscillator oscillator;
    protected Effect effect;

    private void updateDuration() {
        int ms = (int) (timeFraction.getFraction() *
                timeSignature.getStandardTimeFraction().getFraction() * 60000.0f / bpm.getBpm());
        if(dotted)
            ms = (int)(ms * 1.5f);
        duration.setMs(ms);
    }

    private void updateFrequency() {
        double baseFreq = equalTemperaments[scaleStep];
        double freq = baseFreq *
                Math.pow(2, octaveShift.getShift()) *
                Math.pow(2, semitoneShift.getShift() / 12.0);
        frequency.setFreq(freq);
    }

    protected abstract Oscillator createOscillator();

    public Synthesizer(BPM bpm) {
        this.bpm = bpm;
        this.oscillator = createOscillator();
    }

    public Synthesizer(BPM bpm,Effect effect) {
        this.bpm = bpm;
        this.effect = effect;
        this.oscillator = createOscillator();
    }

    public void setScaleStep(int scaleStep) {
        if(scaleStep < 1 || scaleStep > 7)
            throw new IllegalArgumentException(String.format("scale step must be in range of [1,7], but given = %d",scaleStep));
        this.scaleStep = scaleStep;
        updateFrequency();
    }

    public void setDotted(boolean dotted) {
        this.dotted = dotted;
        updateDuration();
    }

    public void setOctaveShift(int octaveShift) {
        this.octaveShift.setShift(octaveShift);
        updateFrequency();
    }

    public void setSemitoneShift(int semitoneShift) {
        this.semitoneShift.setShift(semitoneShift);
        updateFrequency();
    }

    public void setTimeFraction(int timeFraction) {
        this.timeFraction.setFraction(timeFraction);
        updateDuration();
    }

    public void setTimeSignature(int standardTimeFraction,int standardNotePerBar) {
        timeSignature.getStandardTimeFraction().setFraction(standardTimeFraction);
        timeSignature.setStandardNotesPerBar(standardNotePerBar);
        updateDuration();
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public OctaveShift getOctaveShift() {
        return octaveShift;
    }

    public SemitoneShift getSemitoneShift() {
        return semitoneShift;
    }

    public TimeFraction getTimeFraction() {
        return timeFraction;
    }

    public TimeSignature getTimeSignature() {
        return timeSignature;
    }

    public DurationMs getDuration() {
        return duration;
    }

    public boolean isDotted() {
        return dotted;
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
