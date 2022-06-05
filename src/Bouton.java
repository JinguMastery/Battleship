import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;



@SuppressWarnings("serial")
public class Bouton extends JButton {

	public final static int WATER_CASE_TYPE = 0, SOUTHSIDE_CASE_TYPE = 1, NORTHSIDE_CASE_TYPE = 2, WESTSIDE_CASE_TYPE = 3, EASTSIDE_CASE_TYPE = 4,
			HORIZONTAL_CASE_TYPE = 5, VERTICAL_CASE_TYPE = 6, WHITECROSS_CASE_TYPE = 7, REDCROSS_CASE_TYPE = 8 ;
	//on définit ici des constantes qui définissent la façon dont la case va être dessinée (0 si rien ne sera dessiné ; 1, 2, 3 et 4 si on dessine
	//le bord d'un bateau dont la face est tournée vers le sud, nord, ouest et est respectivement ; 5 et 6 si on dessine une des parties centrales
	//horizontales et verticales du bateau respectivement ; 7 et 8 si on dessine une croix blanche (tir dans l'eau) et rouge (tir réussi sur un
	//bateau) respectivement.
	
	private List<Integer> caseTypes ;		//liste contenant tous les états (ils concernent les types de cases ici) pris par le bouton au cours de
											//son existence (c'est à dire tant que le composant 'parent', soit la grille, existe).
	private int currCaseType ;		//c'est l'état, soit le type de case, courant du bouton (initialement, il vaut 0)
	
	public Bouton(int x, int y) {
		super("(" + x + ", " + y + ")") ;
		caseTypes = new ArrayList<Integer> () ;
		caseTypes.add(WATER_CASE_TYPE) ;
		setBackground(Color.CYAN) ;
	}
	
	public void paintComponent(Graphics g) {
		int l = getWidth(), h = getHeight() ;
		setForeground(Color.GRAY) ;
		switch (currCaseType) {
		case WATER_CASE_TYPE :
			setForeground(Color.CYAN) ;
			g.fillRect(0, 0, l, h) ;
			break ;
		case SOUTHSIDE_CASE_TYPE :
			g.fillRect(l/4, 0, l/2, 3*h/4) ;
			break ;
		case NORTHSIDE_CASE_TYPE :
			g.fillRect(l/4, h/4, l/2, 3*h/4) ;
			break ;
		case WESTSIDE_CASE_TYPE :
			g.fillRect(l/4, h/4, 3*l/4, h/2) ;
			break ;
		case EASTSIDE_CASE_TYPE :
			g.fillRect(0, h/4, 3*l/4, h/2) ;
			break ;
		case HORIZONTAL_CASE_TYPE :
			g.fillRect(0, h/4, l, h/2) ;
			break ;
		case VERTICAL_CASE_TYPE :
			g.fillRect(l/4, 0, l/2, h) ;
			break ;
		case WHITECROSS_CASE_TYPE :
			setForeground(Color.WHITE) ;
			Graphics2D g2 = (Graphics2D)g ;
			g2.setStroke(new BasicStroke(4)) ;
			g2.drawLine(0, 0, l, h) ;
			g2.drawLine(0, h, l, 0) ;
			break ;
		case REDCROSS_CASE_TYPE :
			setForeground(Color.RED) ;
			Graphics2D g3 = (Graphics2D)g ;
			g3.setStroke(new BasicStroke(4)) ;
			g3.drawLine(0, 0, l, h) ;
			g3.drawLine(0, h, l, 0) ;
		}
	}
	
	public void setCurrCaseType(int n) {
		caseTypes.add(n) ;
		currCaseType = n ;
	}

	public int getCurrCaseType() {
		return currCaseType ;
	}
	
	public void setToPreCaseType() {
		int n = caseTypes.size() ;
		if (n > 1) {
			currCaseType = caseTypes.get(n-2) ;
			caseTypes.remove(n-1) ;
		}
	}

}
