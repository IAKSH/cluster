package me.iaksh.cluster.core.waveform.oscillator;

/**
 * 阶梯化三角波振荡器
 * 生成原始的阶梯化三角波
 */
public class SteppedTriangleOscillator extends TriangleOscillator {

    private int ladderNum;

    @Override
    protected short[] genBasicWaveform(int samplesPerCycle) {
        short[] data = new short[samplesPerCycle];

        float maxAmplitude = Short.MAX_VALUE * amplitude;
        float phaseIncrement = (2 * (float) Math.PI) / samplesPerCycle;
        float currentPhase = 0;

        for (int j = 0; j < data.length; j++) {
            float value = (float) (Math.asin(Math.sin(currentPhase)) * 2 / Math.PI);
            int halfLadder = ladderNum / 2;
            data[j] = (short) ((float) Math.floor(halfLadder * value) / halfLadder * maxAmplitude);

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

    /**
     * 获取阶梯数
     * @return 阶梯数
     */
    public int getLadderNum() {
        return ladderNum;
    }

    /**
     * 设置阶梯数
     * @param ladderNum 阶梯数
     */
    public void setLadderNum(int ladderNum) {
        this.ladderNum = ladderNum;
    }
}
