import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.effects.ExpAttenuationEffect;
import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.synthesizer.NoiseSynth;
import me.iaksh.cluster.core.synthesizer.SquareSynth;
import me.iaksh.cluster.core.synthesizer.SteppedTriangleSynth;
import me.iaksh.cluster.core.synthesizer.TriangleSynth;
import org.junit.jupiter.api.Test;

public class SynthTest {
    @Test
    public void testSquareSynth() {
        Player player = new Player(0.25f);
        new Player(0.25f).play(new SquareSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }

    @Test
    public void testTriangleSynth() {
        Player player = new Player(0.25f);
        new Player(0.25f).play(new TriangleSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }

    @Test
    public void testSteppedTriangleSynth() {
        Player player = new Player(0.25f);
        new Player(0.25f).play(new SteppedTriangleSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }

    @Test
    public void testNoiseSynth() {
        Player player = new Player(0.25f);
        new Player(0.25f).play(new NoiseSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }
}
