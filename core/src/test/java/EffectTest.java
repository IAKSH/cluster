import me.iaksh.cluster.core.base.Waveform;
import me.iaksh.cluster.core.effects.Effect;
import me.iaksh.cluster.core.effects.ExpAttenuationEffect;
import me.iaksh.cluster.core.effects.LinearAttenuationEffect;
import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.oscillator.Oscillator;
import me.iaksh.cluster.core.oscillator.SquareOscillator;
import me.iaksh.cluster.core.synthesizer.Synthesizer;
import org.junit.jupiter.api.Test;

public class EffectTest {
    @Test
    public void testExpAttenuationEffect() {
        Player player = new Player(0.25f);
        player.play(new Synthesizer(){
            private Oscillator oscillator = new SquareOscillator();
            private Effect effect = new ExpAttenuationEffect();

            @Override
            public Waveform getWaveform() {
                effect.setWaveform(oscillator.getWaveform());
                return effect.getWaveform();
            }
        }.getWaveform().getPCM().toArray());
    }

    @Test
    public void LinearAttenuationEffect() {
        Player player = new Player(0.25f);
        player.play(new Synthesizer(){
            private Oscillator oscillator = new SquareOscillator();
            private Effect effect = new LinearAttenuationEffect();

            @Override
            public Waveform getWaveform() {
                effect.setWaveform(oscillator.getWaveform());
                return effect.getWaveform();
            }
        }.getWaveform().getPCM().toArray());
    }
}
