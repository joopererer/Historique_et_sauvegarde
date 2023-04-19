package Plateau;

import java.io.Serializable;

public abstract class Action implements Serializable {

    static int TYPE_ACT_JOUE = 0x01;
    static int TYPE_ACT_EFFACE = 0x02;

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
}
