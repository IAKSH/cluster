import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.effects.ExpAttenuationEffect;
import me.iaksh.cluster.core.effects.LinearAttenuationEffect;
import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.export.WavExporter;
import me.iaksh.cluster.core.synthesizer.NoiseSynth;
import me.iaksh.cluster.core.synthesizer.SquareSynth;
import me.iaksh.cluster.core.synthesizer.SteppedTriangleSynth;
import me.iaksh.cluster.core.synthesizer.TriangleSynth;
import org.junit.jupiter.api.Test;

public class SynthTest {
    @Test
    public void testSquareSynth() {
        new Player(0.25f).play(new SquareSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }

    @Test
    public void testSquareSynthSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new SquareSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform());
        exporter.save("./square_synth_test.wav");
    }

    @Test
    public void testTriangleSynth() {
        new Player(0.25f).play(new TriangleSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }

    @Test
    public void testTriangleSynthSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new TriangleSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform());
        exporter.save("./triangle_synth_test.wav");
    }

    @Test
    public void testSteppedTriangleSynth() {
        new Player(0.25f).play(new SteppedTriangleSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }

    @Test
    public void testSteppedTriangleSynthSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new SteppedTriangleSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform());
        exporter.save("./stepped_triangle_synth_test.wav");
    }

    @Test
    public void testNoiseSynth() {
        new Player(0.25f).play(new NoiseSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }

    @Test
    public void testNoiseSynthSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new NoiseSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform());
        exporter.save("./noise_synth_test.wav");
    }
}
