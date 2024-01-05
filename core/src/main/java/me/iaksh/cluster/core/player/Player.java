package me.iaksh.cluster.core.player;

import me.iaksh.cluster.core.waveform.oscillator.Oscillator;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Player {

    private SourceDataLine line;
    private boolean isPaused;

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
        play(data);
    }

    /**
     * 播放16bit PCM数据
     * @param data 16bit PCM数据
     */
    public void play(short[] data) {
        try {
            byte[] byteData = new byte[data.length * 2];
            ByteBuffer.wrap(byteData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(data);

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

    /**
     * 暂停播放
     */
    public void pause() {
        isPaused = true;
    }

    /**
     * 恢复播放
     */
    public void resume() {
        isPaused = false;
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

    public boolean isPaused() {
        return isPaused;
    }

    public void forceStop() {
        line.stop();
        close();
        init();
    }
}