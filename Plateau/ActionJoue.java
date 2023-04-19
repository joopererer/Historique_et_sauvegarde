package Plateau;

import java.io.Serializable;

public class ActionJoue extends Action implements Serializable {

    private int valeur_old;
    private int valeur_new;

    private int ligne;

    private int col;

    public ActionJoue(int valeur_old, int valeur_new, int i, int j){
        super(TYPE_ACT_JOUE);
        this.valeur_old = valeur_old;
        this.valeur_new = valeur_new;
        this.ligne = i;
        this.col = j;
    }

    public int getValeur_old() {
        return valeur_old;
    }

    public void setValeur_old(int valeur_old) {
        this.valeur_old = valeur_old;
    }

    public int getValeur_new() {
        return valeur_new;
    }

    public void setValeur_new(int valeur_new) {
        this.valeur_new = valeur_new;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

}
