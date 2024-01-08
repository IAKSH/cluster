import me.iaksh.cluster.core.effects.ExpAttenuationEffect;
import me.iaksh.cluster.core.effects.LinearAttenuationEffect;
import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.oscillator.SquareOscillator;
import me.iaksh.cluster.core.synthesizer.Synthesizer;
import org.junit.jupiter.api.Test;

public class EffectTest {
    @Test
    public void testExpAttenuationEffect() {
        Player player = new Player(0.25f);
        player.play(new Synthesizer(new SquareOscillator(),new ExpAttenuationEffect()).getWaveform().getPCM().toArray());
    }

    @Test
    public void LinearAttenuationEffect() {
        Player player = new Player(0.25f);
        player.play(new Synthesizer(new SquareOscillator(),new LinearAttenuationEffect()).getWaveform().getPCM().toArray());
    }
}
