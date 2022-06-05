import javax.swing.JOptionPane;

public class Bateau {

	private int size ;
	private int[] coordX, coordY ;
	private boolean[] hittenCases ;
	private boolean isHorizontal, isSinked ;
	
	public Bateau(int x, int y, boolean b, int n) {		// x et y sont les coordonnées (strictement positives) de la partie arrière du bateau à placer, b est un
														//booléen qui spécifie si le bateau doit être placé horizontalement ou verticalement sur la grille,
														//et n est la taille du bateau (c'est à dire le nombre de cases que celui-ci occupe sur la grille)
		size = n ;
		isHorizontal = b ;
		coordX = new int[size] ;
		coordY = new int[size] ;
		hittenCases = new boolean[size] ;
		for (int i = 0 ; i < size ; i++)
			if (isHorizontal) {
				coordX[i] = x + i ;
				coordY[i] = y ;
			}
			else {
				coordX[i] = x ;
				coordY[i] = y - i ;
			}
	}
	
	public int getSize() {
		return size ;
	}

	public int[] getCoordX() {
		return coordX ;
	}
	
	public int[] getCoordY() {
		return coordY ;
	}
	
	public int getCoordXHead() {
		return coordX[size-1] ;
	}
	
	public int getCoordYHead() {
		return coordY[size-1] ;
	}
	
	public boolean[] getHittenCases() {
		return hittenCases ;
	}

	public boolean isHorizontal() {
		return isHorizontal ;
	}
	
	public void hit(int x, int y) {		//retourne vrai si le bateau a été coulé et faux s'il a simplement été touché
		int i ;
		for (i = 0 ; i < size ; i++)
			if (coordX[i] == x && coordY[i] == y) {
				hittenCases[i] = true ;
				break ;
			}
		for (i = 0 ; i < size ; i++)
			if (hittenCases[i] == false)
				return ;
		isSinked = true ;
	}

	public boolean isSinked() {
		return isSinked ;
	}

}
