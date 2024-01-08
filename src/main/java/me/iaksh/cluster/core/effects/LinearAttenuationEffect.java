package me.iaksh.cluster.core.effects;

import me.iaksh.cluster.core.base.PCMData;
import me.iaksh.cluster.core.base.Waveform;

public class LinearAttenuationEffect extends PrefabEffect {
    @Override
    protected Waveform trans(Waveform waveform) {
        short[] pcm = waveform.getPCM().toArray();
        long len = pcm.length;
        for(int i = 0;i < len;i++) {
            pcm[i] = (short) (pcm[i] * (1.0f - (double) i / len));
        }
        return new Waveform(new PCMData(pcm));
    }
}
