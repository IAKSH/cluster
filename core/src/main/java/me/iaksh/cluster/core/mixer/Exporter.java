package me.iaksh.cluster.core.mixer;

import me.iaksh.cluster.core.notation.Section;

import java.util.ArrayList;

public interface Exporter {
    /**
     * 保存波形到文件
     * @param path 保存路径
     * @param data 16bit PCM波形数据
     */
    void saveWaveform(String path,short[] data);

    /***
     * 从多组简谱小节生成波形
     * @param sections 简谱（小节的组合的组合）
     * @return 16bit PCM波形数据
     */
    short[] genWaveform(ArrayList<ArrayList<Section>> sections);
}
