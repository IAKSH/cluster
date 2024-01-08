package me.iaksh.cluster;

import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.synthesizer.TriangleSynth;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Player player = new Player(0.25f);
        player.play(new TriangleSynth().getWaveform().getPCM().toArray());
    }
}
