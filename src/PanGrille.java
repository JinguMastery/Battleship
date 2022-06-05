import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanGrille extends JPanel {

	protected Bouton[][] tabBut ;		//tableau de dimension 2 (matrice) contenant tous les boutons n�cessaires au panneau (soit l'ensemble des 100 cases de la grille)
	protected Fenetre fen ;
	private List<Bouton> histButs ;		//cette liste correspond � l'historique de tous les boutons de la grille ayant d�clench� un ou plusieurs �v�nements ;
										//par exemple, un m�me bouton ayant d�clench� 2 �v�nements appara�tra 2 fois dans la liste
	private boolean[][] tabCross ;
	private int nCross ;
	
	public PanGrille(Fenetre fen) {
		tabBut = new Bouton[10][10] ;
		this.fen = fen ;
		histButs = new ArrayList<Bouton> () ;
		tabCross = new boolean[10][10] ;
		setLayout(new GridLayout(10, 10)) ;
		for (int i = 0 ; i < 10 ; i++)
			for (int j = 0 ; j < 10 ; j++) {
				tabBut[i][j] = new Bouton(j, i) ;
				tabBut[i][j].addActionListener(fen) ;
				add(tabBut[i][j]) ;
			}
	}
	
	public List<Bouton> getHistButs() {
		return histButs ;
	}
	
	public void addCross(int x, int y, boolean b1, boolean b2) {
		
		//v�rifie si un tir a d�j� �t� effectu� sur la case de coordonn�es donn�es
		if (tabCross[y][x]) {
			JOptionPane.showMessageDialog(fen, "Il y a d�j� eu un tir sur cette case !", "Erreur", JOptionPane.ERROR_MESSAGE) ;
			return ;
		}
		
		//si tout est OK
		tabCross[y][x] = true ;
		nCross++ ;
		Bouton but = tabBut[y][x] ;
		List<Bateau> listBat = fen.getPanGrid1().getBateaux() ;
		if (but.getCurrCaseType() != Bouton.WATER_CASE_TYPE)
			for (Bateau bat : listBat) {
				int[] x2 = bat.getCoordX(), y2 = bat.getCoordY() ;
				for (int i = 0 ; i < x2.length ; i++)
					if (x2[i]-1 == x && y2[i]-1 == y) {
						bat.hit(x+1, y+1) ;
						if (bat.isSinked())
							JOptionPane.showMessageDialog(fen, "Un bateau a �t� coul� !", "Information", JOptionPane.PLAIN_MESSAGE) ;
						else
							JOptionPane.showMessageDialog(fen, "Un bateau a �t� touch� !", "Information", JOptionPane.PLAIN_MESSAGE) ;
					}
			}
		if (b1)
			but.setCurrCaseType(Bouton.REDCROSS_CASE_TYPE) ;
		else
			but.setCurrCaseType(Bouton.WHITECROSS_CASE_TYPE) ;
		but.repaint() ;
		histButs.add(but) ;
		if (histButs.size() == 1)
			if (b2)
				fen.enableCancel1() ;
			else
				fen.enableCancel2() ;
		
		//v�rifie si tous les bateaux ont �t� coul�s
		for (Bateau bat : listBat)
			if (!bat.isSinked())
				return ;
		
		//fin du jeu
		JOptionPane.showMessageDialog(fen, "Votre adversaire a neutralis� toute votre flotte ! Vous avez perdu !", "Information", JOptionPane.PLAIN_MESSAGE) ;
		System.exit(0) ;
	}
	
	public void removeCross(int x, int y, boolean b) {
		if (tabCross[y][x]) {
			tabCross[y][x] = false ;
			nCross-- ;
			tabBut[y][x].setCurrCaseType(Bouton.WATER_CASE_TYPE) ;
			tabBut[y][x].repaint() ;
		}
	}
	
}
