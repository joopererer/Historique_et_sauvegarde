package Plateau;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static Plateau.Action.TYPE_ACT_EFFACE;
import static Plateau.Action.TYPE_ACT_JOUE;

public class PlateauConcret extends PlateauAbstrait {

	int current_step = -1;  // noter le steps current dans l'hisotry
	List<Action> histories;

	public PlateauConcret(int i, int j) {
		super(i,j);
		histories = new ArrayList<>();
	}

	public PlateauConcret(String fichier) {
		try {
			FileInputStream fis = new FileInputStream(fichier);

			// lire map data
			int lignes = fis.read();
			int cols = fis.read();
			cases = new int[lignes][cols];

//			int isVide = fis.read();
//			if(isVide==0){
				for (int i=0; i<lignes; i++)
					for (int j=0; j<cols; j++)
						fixeValeurCase(fis.read(), i, j);
//			}

			// lire l'histoire
			current_step = fis.read();
			histories = readHistory(fis);

			fis.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.err.println("size_his:"+histories.size()+", steps_cur:"+current_step);

//		for(int i=0; i<=current_step; i++){
//			refais(i);
//		}
	}

	public void joue(int valeur, int i, int j) {
		if(coupJouable(valeur, i, j)){
			int valeur_old = valeurCase(i, j);
			super.joue(valeur, i, j);
			mettre_a_jour_history(new ActionJoue(valeur_old, valeur, i, j));
		}
	}

	public void efface(int ligne_min, int colonne_min, int ligne_max, int colonne_max) {
		if(!in(ligne_min, 0, ligne_max)
				|| !in(colonne_min, 0, colonne_max)
				|| !in(ligne_max, ligne_min, cases.length)
				|| !in(colonne_max, colonne_min, cases[0].length))
		{
			System.err.println("index out of array !");
			return;
		}

		// obtenir les data vont être effacées
		int[] data_efface = new int[(ligne_max-ligne_min+1)*(colonne_max-colonne_min+1)];
		int id = 0;
		for (int i=ligne_min; i<=ligne_max; i++) {
			for (int j = colonne_min; j <= colonne_max; j++) {
				data_efface[id++] = cases[i][j];
			}
		}

		// effectuer l'action
		super.efface(ligne_min, colonne_min, ligne_max, colonne_max);

		// mettre à jour l'histoire
		mettre_a_jour_history(new ActionEfface(ligne_min, colonne_min, ligne_max, colonne_max, data_efface));
	}

	private void mettre_a_jour_history(Action action){
		if(current_step!=-1 && current_step!=histories.size()-1){
			// efface les histories suivantes
			while(histories.size()-1!=current_step){
				histories.remove(current_step+1);
			}
		}
		histories.add(action);
		current_step = histories.size()-1;
		System.err.println("[mettre_a_jour_history] size_his:"+histories.size()+", steps_cur:"+current_step);
	}

	public int valeurCase(int i, int j) {
		if(in(i, 0, cases.length) && in(j, 0, cases[0].length)){
			return super.valeurCase(i, j);
		}
		return 0;
	}

	public boolean peutAnnuler() {
		if(current_step>=0){
			return true;
		}
		return false;
	}

	public boolean peutRefaire() {
		if(current_step<histories.size()-1){
			return true;
		}
		return false;
	}

	public void annule() {
		if(peutAnnuler()){
			Action action = histories.get(current_step);
			current_step -= 1;
			if(action instanceof ActionJoue){
				ActionJoue actJoue = (ActionJoue) action;
				System.err.println("action_efface:"+actJoue.getValeur_old()+","+actJoue.getLigne()+","+actJoue.getCol());
				fixeValeurCase(actJoue.getValeur_old(), actJoue.getLigne(), actJoue.getCol());
			}else
			if(action instanceof ActionEfface){
				ActionEfface actEfface = (ActionEfface) action;
				System.err.println("action_joue:"+actEfface.getLigne_min()+","+actEfface.getCol_min()
						+","+actEfface.getLigne_max()+","+actEfface.getCol_max());
				int[] data_efface = actEfface.getData_efface();
				int nb_cols = (actEfface.getCol_max()-actEfface.getCol_min()) + 1;
				for(int i=0; i<data_efface.length; i++){
					int ligne = actEfface.getLigne_min() + (i/nb_cols);
					int col = actEfface.getCol_min() + (i%nb_cols);
					fixeValeurCase(data_efface[i], ligne, col);
				}
			}
			System.err.println("size_his:"+histories.size()+", steps_cur:"+current_step);
		}
	}

	public void refais() {
		if(peutRefaire()){
			current_step += 1;
			refais(current_step);
		}
	}

	private void refais(int step) {
		Action action = histories.get(step);
		if(action instanceof ActionJoue){
			ActionJoue actJoue = (ActionJoue) action;
			super.joue(actJoue.getValeur_new(), actJoue.getLigne(), actJoue.getCol());
		}else
		if(action instanceof ActionEfface){
			ActionEfface actEfface = (ActionEfface) action;
			super.efface(actEfface.getLigne_min(), actEfface.getCol_min(), actEfface.getLigne_max(), actEfface.getCol_max());
		}
	}

	public void sauve(String fichier) throws Exception {
		System.err.println("sauve:"+fichier);
		FileOutputStream fos = new FileOutputStream(fichier);

		// écrire map data (comme au debut, il est vide)
		fos.write(nbLignes());
		fos.write(nbColonnes());
//		boolean isVide = isPlateauVide();
//		fos.write(isVide?1:0);
//		if(!isVide){
			for (int i=0; i<nbLignes(); i++)
				for (int j=0; j<nbColonnes(); j++)
					fos.write(valeurCase(i, j));
//		}

		// écrire l'histoire
		fos.write(current_step);
		writeHistory(fos, histories);

		fos.close();
	}

	private boolean isPlateauVide(){
		for (int i=0; i<nbLignes(); i++)
			for (int j=0; j<nbColonnes(); j++)
				if(valeurCase(i, j)!=0)
					return false;
		return true;
	}

	private void writeHistory(FileOutputStream fos, List<Action> histories) throws Exception {
		fos.write(histories.size());
		for(int i=0; i<histories.size(); i++){
			Action action = histories.get(i);
			fos.write(action.getAction());
			action.write(fos);
		}
		fos.flush();
	}

	private List<Action> readHistory(FileInputStream fis) throws Exception {
		int size = fis.read();
		List<Action> list = new ArrayList<>(size);
		for(int i=0; i<size; i++){
			int actType = fis.read();
			Action action = null;
			switch (actType) {
				case TYPE_ACT_JOUE:
					action = new ActionJoue().read(fis);
					break;
				case TYPE_ACT_EFFACE:
					action = new ActionEfface().read(fis);
					break;
			}
			if(action!=null){
				list.add(action);
			}
		}
		return list;
	}

}
