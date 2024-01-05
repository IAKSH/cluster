package me.iaksh.cluster.core.notation;

import java.util.ArrayList;

public class Section {
    private final int timeSignature0;
    private final int timeSignature1;
    private ArrayList<Note> notes;

    public Section(int ts0,int ts1) {
        timeSignature0 = ts0;
        timeSignature1 = ts1;
        notes = new ArrayList<>();
    }

    /**
     * 获取音符ArrayList
     * @return 音符ArrayList
     */
    public ArrayList<Note> getNotes() {
        return notes;
    }

    /**
     * 获取x/y拍中的x，例如4/4拍
     * @return x
     */
    public int getTimeSignature0() {
        return timeSignature0;
    }

    /**
     * 获取x/y拍中的y，例如4/4拍
     * @return y
     */
    public int getTimeSignature1() {
        return timeSignature1;
    }
}
