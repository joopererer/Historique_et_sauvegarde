package Plateau;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public abstract class Action {

    public static final int TYPE_ACT_JOUE = 0x01;
    public static final int TYPE_ACT_EFFACE = 0x02;

    private int action;

    public Action(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public abstract void write(FileOutputStream fos) throws Exception;

    public abstract Action read(FileInputStream fis) throws Exception;

}
