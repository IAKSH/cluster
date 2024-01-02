package me.iaksh.cluster.core.mixer;

import me.iaksh.cluster.core.notation.Note;
import me.iaksh.cluster.core.notation.Section;
import me.iaksh.cluster.core.waveform.WaveGenerator;
import me.iaksh.cluster.core.waveform.effect.Effect;

import java.util.ArrayList;

public class Track {
    protected final ArrayList<Short> waveform;
    protected final int bpm;

    public Track(int bpm) {
        waveform = new ArrayList<>();
        this.bpm = bpm;
    }

    public ArrayList<Short> genWaveform(WaveGenerator generator, ArrayList<Section> sections){
        waveform.clear();
        for(Section section : sections) {
            for(Note note : section.getNotes()) {
                int durationMs = (int) (note.getNoteFraction() * section.getTimeSignature1() * 60000.0f / bpm);
                if(note.isDotted())
                    durationMs = (int)(durationMs * 1.5f);

                if(note.hasEffect()) {
                    Effect effect = note.getEffect();
                    if (!effect.hasGenerator())
                        effect.setGenerator(generator);
                    for (short s : effect.genWaveform(durationMs, note.getFreq()))
                        waveform.add(s);
                } else {
                    for(Short s : generator.genWaveform(durationMs,note.getFreq()))
                        waveform.add(s);
                }
            }
        }
        return waveform;
    }
}
