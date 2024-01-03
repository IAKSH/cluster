package me.iaksh.cluster.core.player;

import me.iaksh.cluster.core.waveform.oscillator.Oscillator;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Player {

    private SourceDataLine line;

    private void init() {
        try {
            AudioFormat format = new AudioFormat(Oscillator.getSampleRate(), 16, 1, true, false);
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

    /**
     * 播放16bit PCM数据
     * @param volume 音量
     * @param data 16bit PCM数据
     */
    public void play(float volume, short[] data) {
        setVolume(volume);
        byte[] byteData = new byte[data.length * 2];
        ByteBuffer.wrap(byteData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(data);
        line.start();
        line.write(byteData, 0, byteData.length);
        line.drain();
        line.stop();
    }

    /**
     * 播放16bit PCM数据
     * @param data 16bit PCM数据
     */
    public void play(short[] data) {
        byte[] byteData = new byte[data.length * 2];
        ByteBuffer.wrap(byteData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(data);
        line.start();
        line.write(byteData, 0, byteData.length);
        line.drain();
        line.stop();
    }

    /***
     * 设置音量
     * @param volume 音量[0.0f,1.0f]
     */
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl =
                (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    /**
     * 关闭播放器
     */
    public void close() {
        line.close();
    }
}