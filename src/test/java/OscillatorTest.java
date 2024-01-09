import me.iaksh.cluster.core.data.DurationMs;
import me.iaksh.cluster.core.data.Frequency;
import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.oscillator.NoiseOscillator;
import me.iaksh.cluster.core.oscillator.SquareOscillator;
import me.iaksh.cluster.core.oscillator.SteppedTriangleOscillator;
import me.iaksh.cluster.core.oscillator.TriangleOscillator;
import org.junit.jupiter.api.Test;

public class OscillatorTest {
    @Test
    public void testSquareOscillator() {
        Player player = new Player(0.25f);
        player.play(new SquareOscillator(new Frequency(100),new DurationMs(1000)).getWaveform().getPCM().toArray());
    }

    @Test
    public void testTriangleOscillator() {
        Player player = new Player(0.25f);
        player.play(new TriangleOscillator(new Frequency(100),new DurationMs(1000)).getWaveform().getPCM().toArray());
    }

    @Test
    public void testSteppedTriangleOscillator() {
        Player player = new Player(0.25f);
        player.play(new SteppedTriangleOscillator(new Frequency(100),new DurationMs(1000)).getWaveform().getPCM().toArray());
    }

    @Test
    public void testNoiseOscillator() {
        Player player = new Player(0.25f);
        player.play(new NoiseOscillator(new Frequency(100),new DurationMs(1000)).getWaveform().getPCM().toArray());
    }
}
