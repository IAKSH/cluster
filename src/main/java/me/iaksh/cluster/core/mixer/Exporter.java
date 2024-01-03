package me.iaksh.cluster.core.mixer;

import me.iaksh.cluster.core.notation.Section;

import java.util.ArrayList;

public interface Exporter {
    void saveWaveform(String path,short[] data);
    short[] genWaveform(ArrayList<ArrayList<Section>> sections);
}
