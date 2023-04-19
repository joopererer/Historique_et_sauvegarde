package Plateau;

import java.io.Serializable;

public class ActionEfface extends Action implements Serializable {

    private int[] data_efface;
    private int ligne_min;
    private int col_min;
    private int ligne_max;
    private int col_max;

    public ActionEfface(int ligne_min, int col_min, int ligne_max, int col_max, int[] data_efface) {
        super(TYPE_ACT_EFFACE);
        this.ligne_min = ligne_min;
        this.col_min = col_min;
        this.ligne_max = ligne_max;
        this.col_max = col_max;
        this.data_efface = data_efface;
    }

    public int getLigne_min() {
        return ligne_min;
    }

    public void setLigne_min(int ligne_min) {
        this.ligne_min = ligne_min;
    }

    public int getCol_min() {
        return col_min;
    }

    public void setCol_min(int col_min) {
        this.col_min = col_min;
    }

    public int getLigne_max() {
        return ligne_max;
    }

    public void setLigne_max(int ligne_max) {
        this.ligne_max = ligne_max;
    }

    public int getCol_max() {
        return col_max;
    }

    public void setCol_max(int col_max) {
        this.col_max = col_max;
    }

    public int[] getData_efface() {
        return data_efface;
    }

    public void setData_efface(int[] data_efface) {
        this.data_efface = data_efface;
    }
}
