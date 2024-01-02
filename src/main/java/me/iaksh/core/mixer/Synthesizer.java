package me.iaksh.core.mixer;

import java.util.ArrayList;

public abstract class Synthesizer {

    protected ArrayList<Track> tracks;

    public Synthesizer() {
        tracks = new ArrayList<>();
    }
}
