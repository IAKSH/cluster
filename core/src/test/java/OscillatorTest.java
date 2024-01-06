import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.oscillator.NoiseOscillator;
import me.iaksh.cluster.core.oscillator.SquareOscillator;
import me.iaksh.cluster.core.oscillator.TriangleOscillator;
import org.junit.jupiter.api.Test;

public class OscillatorTest {
    @Test
    public void testSquareOscillator() {
        Player player = new Player(0.25f);
        player.play(new SquareOscillator().getWaveform().getPCM().toArray());
    }

    @Test
    public void testTriangleOscillator() {
        Player player = new Player(0.25f);
        player.play(new TriangleOscillator().getWaveform().getPCM().toArray());
    }

    @Test
    public void testNoiseOscillator() {
        Player player = new Player(0.25f);
        player.play(new NoiseOscillator().getWaveform().getPCM().toArray());
    }
}
