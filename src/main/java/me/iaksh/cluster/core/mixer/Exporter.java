package me.iaksh.cluster.core.mixer;

import me.iaksh.cluster.core.notation.Section;

import java.util.ArrayList;

public interface Exporter {
    void saveToWav(String path,ArrayList<ArrayList<Section>> sections);
    short[] genWavform(ArrayList<ArrayList<Section>> sections);
}
