import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.data.DurationMs;
import me.iaksh.cluster.core.data.Frequency;
import me.iaksh.cluster.core.effects.ExpAttenuationEffect;
import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.export.WavExporter;
import me.iaksh.cluster.core.oscillator.NoiseOscillator;
import me.iaksh.cluster.core.oscillator.SquareOscillator;
import me.iaksh.cluster.core.oscillator.SteppedTriangleOscillator;
import me.iaksh.cluster.core.oscillator.TriangleOscillator;
import me.iaksh.cluster.core.synthesizer.SquareSynth;
import org.junit.jupiter.api.Test;

public class OscillatorTest {
    @Test
    public void testSquareOscillator() {
        Player player = new Player(0.25f);
        player.setPCM(new SquareOscillator(new Frequency(440),new DurationMs(1000)));
        player.play();
    }

    @Test
    public void testSquareOscillatorSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new SquareOscillator(new Frequency(440),new DurationMs(1000)).getWaveform());
        exporter.save("./square_oscillator_test.wav");
    }

    @Test
    public void testTriangleOscillator() {
        Player player = new Player(0.25f);
        player.setPCM(new TriangleOscillator(new Frequency(440),new DurationMs(1000)));
        player.play();
    }

    @Test
    public void testTriangleOscillatorSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new TriangleOscillator(new Frequency(440),new DurationMs(1000)).getWaveform());
        exporter.save("./triangle_oscillator_test.wav");
    }

    @Test
    public void testSteppedTriangleOscillator() {
        Player player = new Player(0.25f);
        player.setPCM(new SteppedTriangleOscillator(new Frequency(440),new DurationMs(1000)));
        player.play();
    }

    @Test
    public void testSteppedTriangleOscillatorSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new SteppedTriangleOscillator(new Frequency(440),new DurationMs(1000)).getWaveform());
        exporter.save("./stepped_triangle_oscillator_test.wav");
    }

    @Test
    public void testNoiseOscillator() {
        Player player = new Player(0.25f);
        player.setPCM(new NoiseOscillator(new Frequency(440),new DurationMs(1000)));
        player.play();
    }

    @Test
    public void testNoiseOscillatorSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new NoiseOscillator(new Frequency(440),new DurationMs(1000)).getWaveform());
        exporter.save("./noise_oscillator_test.wav");
    }
}
