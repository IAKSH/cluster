package me.iaksh.cluster.core.export;

import me.iaksh.cluster.core.base.*;
import me.iaksh.cluster.core.data.SimpleRate;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Player implements PCMOutput {
    private SourceDataLine line;
    private boolean isPaused;
    private PCMData pcmData;

    private void init() {
        try {
            AudioFormat format = new AudioFormat(SimpleRate.get(), 16, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public Player() {
        init();
    }

    public Player(float volume) {
        init();
        setVolume(volume);
    }

    public void play(float volume) {
        setVolume(volume);
        play();
    }

    public void play() {
        if(pcmData == null)
            throw new RuntimeException("missing pcm data");

        try {
            byte[] byteData = new byte[pcmData.toArray().length * 2];
            ByteBuffer.wrap(byteData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(pcmData.toArray());

            int i = 0;
            line.start();
            while (i < byteData.length) {
                while(isPaused)
                    Thread.sleep(1);
                line.write(byteData, i, Math.min(1024, byteData.length - i));
                i += 1024;
            }
            line.drain();
            line.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        isPaused = false;
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl =
                (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public void close() {
        line.close();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void forceStop() {
        line.stop();
        close();
        init();
    }

    @Override
    public void setPCM(PCMData pcm) {
        pcmData = pcm;
    }
}
