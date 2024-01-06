package me.iaksh.cluster.core.export;

import me.iaksh.cluster.core.base.PCMData;
import me.iaksh.cluster.core.base.PCMOutput;

public abstract class Exporter implements PCMOutput {

    protected PCMData pcmData;

    @Override
    public final void setPCM(PCMData pcmData) {
        this.pcmData = pcmData;
    }

    public abstract void save(String path);
}
