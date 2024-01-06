package me.iaksh.cluster.core.effects;

import me.iaksh.cluster.core.base.PCMData;
import me.iaksh.cluster.core.base.Waveform;

public class ExpAttenuationEffect extends PrefabEffect {
    private double expCoefficient;

    public ExpAttenuationEffect() {
        expCoefficient = 4.0;
    }

    public ExpAttenuationEffect(double expCoefficient) {
        this.expCoefficient = expCoefficient;
    }

    public double getExpCoefficient() {
        return expCoefficient;
    }

    public void setExpCoefficient(double expCoefficient) {
        this.expCoefficient = expCoefficient;
    }

    @Override
    protected Waveform trans(Waveform waveform) {
        short[] pcm = waveform.getPCM().toArray();
        long len = pcm.length;
        for(int i = 0;i < len;i++) {
            pcm[i] = (short) (pcm[i] * Math.exp(-expCoefficient * ((double) i / len)));
        }
        return new Waveform(new PCMData(pcm));
    }
}
