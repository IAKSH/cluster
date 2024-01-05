package me.iaksh.cluster.core.mixer;

import me.iaksh.cluster.core.notation.Section;
import me.iaksh.cluster.core.waveform.oscillator.NoiseOscillator;
import me.iaksh.cluster.core.waveform.oscillator.Oscillator;
import me.iaksh.cluster.core.waveform.oscillator.SquareOscillator;
import me.iaksh.cluster.core.waveform.oscillator.TriangleOscillator;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

/***
 * 类似NES的2A07的合成器
 */
public class NESLikeSynthesizer extends Synthesizer implements Exporter {

    private boolean[] disableChannel = new boolean[4];

    public NESLikeSynthesizer(int bpm) {
        for(int i = 0;i < 4;i++)
            tracks.add(new Track(bpm));
    }

    @Override
    public void saveWaveform(String path,short[] data) {
        byte[] byteBuffer = new byte[data.length * 2];
        ByteBuffer.wrap(byteBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(data);

        AudioFormat format = new AudioFormat(Oscillator.getSampleRate(), 16, 1, true, false);

        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, data.length);

        File file = new File(path);
        try {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public short[] genWaveform(ArrayList<ArrayList<Section>> sections) {
        ArrayList<ArrayList<Short>> channels = new ArrayList<>();
        if(!disableChannel[0])
            channels.add(tracks.get(0).genWaveform(new SquareOscillator(),sections.get(0)));
        if(!disableChannel[1])
            channels.add(tracks.get(1).genWaveform(new SquareOscillator(),sections.get(1)));
        if(!disableChannel[2])
            //channels.add(tracks.get(2).genWaveform(new SteppedTriangleOscillator(),sections.get(2)));
            channels.add(tracks.get(2).genWaveform(new TriangleOscillator(),sections.get(2)));
        if(!disableChannel[3])
            channels.add(tracks.get(3).genWaveform(new NoiseOscillator(),sections.get(3)));
        return Mixer.mix(channels);
    }

    public void setDisableChannel(int i,boolean b) {
        disableChannel[i] = b;
    }
}
