package me.iaksh.cluster.core.export;

import me.iaksh.cluster.core.data.SimpleRate;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WavExporter extends Exporter {

    @Override
    public void save(String path) {
        if(pcmData == null)
            throw new RuntimeException("pcmData is null");

        short[] pcm = pcmData.toArray();
        byte[] byteBuffer = new byte[pcm.length * 2];
        ByteBuffer.wrap(byteBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(pcm);
        AudioFormat format = new AudioFormat(SimpleRate.get(), 16, 1, true, false);

        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, pcm.length);

        File file = new File(path);
        try {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
