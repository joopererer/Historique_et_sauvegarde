package Plateau;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ActionEfface extends Action {

    private int[] data_efface;
    private int ligne_min;
    private int col_min;
    private int ligne_max;
    private int col_max;

    public ActionEfface(){
        super(TYPE_ACT_EFFACE);
    }

    public ActionEfface(int ligne_min, int col_min, int ligne_max, int col_max, int[] data_efface) {
        this();
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

    @Override
    public void write(FileOutputStream fos) throws Exception {
        fos.write(ligne_min);
        fos.write(col_min);
        fos.write(ligne_max);
        fos.write(col_max);
        fos.write(data_efface.length);
        for(int data : data_efface){
            fos.write(data);
        }
    }

    public Action read(FileInputStream fis) throws Exception {
        ligne_min = fis.read();
        col_min = fis.read();
        ligne_max = fis.read();
        col_max = fis.read();
        int data_len = fis.read();
        data_efface = new int[data_len];
        for(int i=0; i<data_len; i++){
            data_efface[i] = fis.read();
        }
        return this;
    }

}
