import me.iaksh.cluster.core.data.BPM;
import me.iaksh.cluster.core.effects.ExpAttenuationEffect;
import me.iaksh.cluster.core.effects.LinearAttenuationEffect;
import me.iaksh.cluster.core.export.Player;
import me.iaksh.cluster.core.export.WavExporter;
import me.iaksh.cluster.core.synthesizer.SquareSynth;
import org.junit.jupiter.api.Test;

public class EffectTest {
    @Test
    public void testExpAttenuationEffect() {
        Player player = new Player(0.25f);
        player.setPCM(new SquareSynth(new BPM(120),new ExpAttenuationEffect()));
        player.play();
    }

    @Test
    public void testExpAttenuationEffectSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new SquareSynth(new BPM(120),new ExpAttenuationEffect()).getWaveform());
        exporter.save("./exp_attenuation_effect_test.wav");
    }

    @Test
    public void testLinearAttenuationEffect() {
        Player player = new Player(0.25f);
        player.setPCM(new SquareSynth(new BPM(120), new LinearAttenuationEffect()));
        player.play();
    }

    @Test
    public void testLinearAttenuationEffectSaveToWav() {
        WavExporter exporter = new WavExporter();
        exporter.setPCM(new SquareSynth(new BPM(120),new LinearAttenuationEffect()).getWaveform());
        exporter.save("./linear_attenuation_effect_test.wav");
    }
}
