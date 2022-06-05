import java.awt.Toolkit;

public class Main {

	public final static Toolkit kit = Toolkit.getDefaultToolkit() ;
	public final static String projet = System.getProperty("user.dir") ;
	public final static int largeur = kit.getScreenSize().width, hauteur = kit.getScreenSize().height ;
	
	public static void main(String[] args) {
		Fenetre fen = new Fenetre() ;
		fen.setVisible(true) ;
	}

}
