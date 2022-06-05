import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class PanGrilleBat extends PanGrille {

	private List<Bateau> bateaux ;
	
	public PanGrilleBat(Fenetre fen) {
		super(fen) ;
		bateaux = new ArrayList<Bateau> () ;
	}
	
	public List<Bateau> getBateaux() {
		return bateaux ;
	}
	
	public void addBateau(Bateau bat) {
		
		//vérifie si tous les bateaux ont bien été placés
		if (bateaux.size() == 5) {
			JOptionPane.showMessageDialog(fen, "Vous avez placé tous vos bateaux !", "Erreur", JOptionPane.ERROR_MESSAGE) ;
			return ;
		}
		
		int i, j, nbPorteAvions = 0, nbCroiseurs = 0, nbTorpilleurs = 0 ;
		
		//test du nombre de bateaux de chaque type
		for (Bateau bat2 : bateaux) {
			if (bat2 instanceof PorteAvion)
				nbPorteAvions++ ;
			if (bat2 instanceof Croiseur)
				nbCroiseurs++ ;
			if (bat2 instanceof Torpilleur)
				nbTorpilleurs++ ;
		}
		
		//vérifie si tous les bateaux d'un type donné ont déjà été placés
		if (nbPorteAvions == 2 && bat instanceof PorteAvion) {
			JOptionPane.showMessageDialog(fen, "Vous avez déjà placé vos 2 porte-avions !", "Erreur", JOptionPane.ERROR_MESSAGE) ;
			return ;
		}
		if (nbCroiseurs == 2 && bat instanceof Croiseur) {
			JOptionPane.showMessageDialog(fen, "Vous avez déjà placé vos 2 croiseurs !", "Erreur", JOptionPane.ERROR_MESSAGE) ;
			return ;
		}
		if (nbTorpilleurs == 1 && bat instanceof Torpilleur) {
			JOptionPane.showMessageDialog(fen, "Vous avez déjà placé votre torpilleur !", "Erreur", JOptionPane.ERROR_MESSAGE) ;
			return ;
		}
		
		//vérifie que le bateau à placer ne dépasse pas les bords de la grille
		if (bat.isHorizontal() && bat.getCoordXHead() > 10) {
			JOptionPane.showMessageDialog(fen, "Le bateau dépasse le bord droit de la grille !", "Erreur", JOptionPane.ERROR_MESSAGE) ;
			return ;
		}
		if (!bat.isHorizontal() && bat.getCoordYHead() < 1) {
			JOptionPane.showMessageDialog(fen, "Le bateau dépasse le bord supérieur de la grille !", "Erreur", JOptionPane.ERROR_MESSAGE) ;
			return ;
		}
		
		//vérifie également que le bateau en question ne rentre pas en collison avec un autre bateau
		int[] x1, y1, x2, y2 ;
		for (Bateau bat2 : bateaux) {
			x1 = bat.getCoordX() ;
			y1 = bat.getCoordY() ;
			x2 = bat2.getCoordX() ;
			y2 = bat2.getCoordY() ;
			for (i = 0 ; i < x1.length ; i++)
				for (j = 0 ; j < x2.length ; j++)
					if (x1[i] == x2[j] && y1[i] == y2[j]) {
						JOptionPane.showMessageDialog(fen, "Le bateau rentre en collision avec un autre bateau !", "Erreur", JOptionPane.ERROR_MESSAGE) ;
						return ;
					}
		}	
		
		//si tout est OK
		bateaux.add(bat) ;
		drawBateau(bat) ;
		List<Bouton> list = getHistButs() ;
		list.add(tabBut[bat.getCoordY()[0]-1][bat.getCoordX()[0]-1]) ;
		if (list.size() == 1)
			fen.enableCancel1() ;
		if (bateaux.size() == 5)
			fen.enableGo() ;
	}
	
	public void removeBateau(Bateau bat) {
		if (bateaux.contains(bat)) {
			bateaux.remove(bat) ;
			int[] x = bat.getCoordX(), y = bat.getCoordY() ;
			if (bat.isHorizontal())
				for (int j : x) {
					tabBut[y[0]-1][j-1].setCurrCaseType(Bouton.WATER_CASE_TYPE) ;
					tabBut[y[0]-1][j-1].repaint() ;
				}
			else
				for (int i : y) {
					tabBut[i-1][x[0]-1].setCurrCaseType(Bouton.WATER_CASE_TYPE) ;
					tabBut[i-1][x[0]-1].repaint() ;
				}
		}
	}
	
	private void drawBateau(Bateau bat) {
		int[] x = bat.getCoordX(), y = bat.getCoordY() ;
		Bouton but ;
		if (bat.isHorizontal())
			for (int j : x) {
				but = tabBut[y[0]-1][j-1] ;
				if (j == x[0])
					but.setCurrCaseType(Bouton.WESTSIDE_CASE_TYPE) ;
				else
					if (j == bat.getCoordXHead())
						but.setCurrCaseType(Bouton.EASTSIDE_CASE_TYPE) ;
					else
						but.setCurrCaseType(Bouton.HORIZONTAL_CASE_TYPE) ;
				but.repaint() ;
			}
			
		else
			for (int i : y) {
				but = tabBut[i-1][x[0]-1] ;
				if (i == y[0])
					but.setCurrCaseType(Bouton.SOUTHSIDE_CASE_TYPE) ;
				else
					if (i == bat.getCoordYHead())
						but.setCurrCaseType(Bouton.NORTHSIDE_CASE_TYPE) ;
					else
						but.setCurrCaseType(Bouton.VERTICAL_CASE_TYPE) ;
				but.repaint() ;
			}
	}
	
}
