package me.iaksh.cluster.core.oscillator;

public class SteppedTriangleOscillator extends TriangleOscillator {

    private int ladderNum;

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        short[] data = new short[samplesPerCycle];

        double maxAmplitude = Short.MAX_VALUE * amplitude;
        double phaseIncrement = (2 * Math.PI) / samplesPerCycle;
        double currentPhase = 0;

        for (int j = 0; j < data.length; j++) {
            double value = Math.asin(Math.sin(currentPhase)) * 2 / Math.PI;
            int halfLadder = ladderNum / 2;
            data[j] = (short) (Math.floor(halfLadder * value) / halfLadder * maxAmplitude);

            currentPhase += phaseShift * phaseIncrement;
            if (currentPhase >= 2 * Math.PI) {
                currentPhase -= 2 * Math.PI;
            }
        }
        return data;
    }

    public SteppedTriangleOscillator() {
        ladderNum = 16;
    }

    public SteppedTriangleOscillator(int ladderNum) {
        this.ladderNum = ladderNum;
    }

    public int getLadderNum() {
        return ladderNum;
    }

    public void setLadderNum(int ladderNum) {
        this.ladderNum = ladderNum;
    }
}
