import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.synthesizer.NoiseSynth;
import me.iaksh.cluster.core.synthesizer.SquareSynth;
import me.iaksh.cluster.core.synthesizer.TriangleSynth;
import org.junit.jupiter.api.Test;

public class SynthTest {
    @Test
    public void testSquareSynth() {
        Player player = new Player(0.25f);
        player.play(new SquareSynth().getWaveform().getPCM().toArray());
    }

    @Test
    public void testTriangleSynth() {
        Player player = new Player(0.25f);
        player.play(new TriangleSynth().getWaveform().getPCM().toArray());
    }

    @Test
    public void testNoiseSynth() {
        Player player = new Player(0.25f);
        player.play(new NoiseSynth().getWaveform().getPCM().toArray());
    }
}
