import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class Fenetre extends JFrame implements ActionListener, ListSelectionListener {
	
	private final static String[] labBateaux = {"Porte-avion", "Croiseur", "Torpilleur"} ;
	private final static int largeurFen = 1000, hauteurFen = 800 ;
	private JPanel pan1, pan2, panBut1, panBut2 ;
	private JMenuBar barMenu ;
	private JMenu partie ;
	private JMenuItem nouveau, quitter ;
	private JList<String> listBat ;
	private JScrollPane panScr ;
	private PanGrilleBat panGrid1 ;
	private PanGrille panGrid2 ;
	private JRadioButton vertical, horizontal, inWater, hitten ;
	private JTabbedPane tabPan ;
	private JButton go, cancel1, cancel2, delete1, delete2 ;
	private String labBateau ;		//label du bateau actuellement sélectionné sur la liste déroulante ('null' si aucun bateau n'est sélectionné)
	private boolean isGoEnabled ;
	
	public Fenetre() {
		setTitle("BATAILLE NAVALE") ;
		setSize(largeurFen, hauteurFen) ;
		setLocation(Main.largeur/2-500, Main.hauteur/2-400) ;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		setResizable(false) ;
		setCursor(new Cursor(Cursor.HAND_CURSOR)) ;
		
		//gestion des panneaux (grille des bateaux et des tirs, ainsi que l'interface composée de la liste déroulante ainsi que des boutons)
		pan1 = new JPanel(new BorderLayout()) ;
		pan2 = new JPanel(new BorderLayout()) ;
		panGrid1 = new PanGrilleBat(this) ;
		panGrid2 = new PanGrille(this) ;
		Dimension dim1 = new Dimension(Main.largeur-200, Main.largeur-200), dim2 = new Dimension(50, Main.hauteur-200) ;
		panGrid1.setPreferredSize(dim1) ;
		panGrid2.setPreferredSize(dim1) ;
		pan1.add(panGrid1, BorderLayout.CENTER) ;
		pan2.add(panGrid2, BorderLayout.CENTER) ;
		panBut1 = new JPanel() ;
		panBut2 = new JPanel() ;
		panBut1.setLayout(new BoxLayout(panBut1, BoxLayout.Y_AXIS)) ;
		panBut2.setLayout(new BoxLayout(panBut2, BoxLayout.Y_AXIS)) ;
		panBut1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)) ;
		panBut2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)) ;
		panBut1.setPreferredSize(dim2) ;
		panBut2.setPreferredSize(dim2) ;
		pan1.add(panBut1, BorderLayout.EAST) ;
		pan2.add(panBut2, BorderLayout.EAST) ;
		tabPan = new JTabbedPane() ;
		tabPan.addTab("Grille des bateaux", pan1) ;
		tabPan.addTab("Grille des tirs", pan2) ;
		tabPan.setEnabledAt(1, false) ;
		
		//gestion de la liste déroulante
		listBat = new JList<String> (labBateaux) ;
		listBat.setSelectionMode(ListSelectionModel.SINGLE_SELECTION) ;
		listBat.setSelectedIndex(0) ;
		labBateau = labBateaux[0] ;
		listBat.addListSelectionListener(this) ;
		panScr = new JScrollPane(listBat) ;
		panScr.setMaximumSize(new Dimension(200, 100)) ;
		panScr.setAlignmentX(CENTER_ALIGNMENT) ;
		panBut1.add(panScr) ;
		panBut1.add(Box.createRigidArea(new Dimension(0, 50))) ;
		
		//gestion des boutons radio pour permettre la sélection de l'orientation des bateaux à placer
		horizontal = new JRadioButton("Horizontal") ;
		vertical = new JRadioButton("Vertical") ;
		vertical.setSelected(true) ;
		panBut1.add(horizontal) ;
		panBut1.add(Box.createRigidArea(new Dimension(0, 5))) ;
		panBut1.add(vertical) ;
		inWater = new JRadioButton("Dans l'eau") ;
		hitten = new JRadioButton("Touché") ;
		inWater.setSelected(true) ;
		panBut2.add(inWater) ;
		panBut2.add(Box.createRigidArea(new Dimension(0, 5))) ;
		panBut2.add(hitten) ;
		ButtonGroup butGroup1 = new ButtonGroup(), butGroup2 = new ButtonGroup() ;
		butGroup1.add(horizontal) ;
		butGroup1.add(vertical) ;
		butGroup2.add(inWater) ;
		butGroup2.add(hitten) ;
		
		//gestion des menus
		barMenu = new JMenuBar() ;
		setJMenuBar(barMenu) ;
		partie = new JMenu("Partie") ;
		barMenu.add(partie) ;
		nouveau = new JMenuItem("Nouveau") ;
		quitter = new JMenuItem("Quitter") ;
		partie.add(nouveau) ;
		partie.add(quitter) ;
		
		//gestion des boutons utilitaires pour permettre les 3 choses suivantes : la validation du placement des bateaux sur la grille, l'annulation
		//de la dernière action faite par l'utilisateur au niveau de la grille, et la suppression d'un bateau ou d'une croix quelconques.
		go = new JButton("GO !") ;
		go.setEnabled(false) ;
		go.addActionListener(this) ;
		cancel1 = new JButton("Cancel") ;
		cancel1.setEnabled(false) ;
		cancel1.addActionListener(this) ;
		cancel2 = new JButton("Cancel") ;
		cancel2.setEnabled(false) ;
		cancel2.addActionListener(this) ;
		delete1 = new JButton("Delete") ;
		delete1.addActionListener(this) ;
		delete2 = new JButton("Delete") ;
		delete2.addActionListener(this) ;
		panBut1.add(Box.createRigidArea(new Dimension(0, 50))) ;
		panBut1.add(cancel1) ;
		panBut1.add(Box.createRigidArea(new Dimension(0, 25))) ;
		panBut1.add(delete1) ;
		panBut2.add(Box.createRigidArea(new Dimension(0, 50))) ;
		panBut2.add(cancel2) ;
		panBut2.add(Box.createRigidArea(new Dimension(0, 25))) ;
		panBut2.add(delete2) ;
		
		//ajout de composants au conteneur de la fenêtre
		
		Container cont = getContentPane() ;
		cont.add(tabPan, BorderLayout.CENTER) ;
		cont.add(go, BorderLayout.SOUTH) ;
		
	}
	
	public PanGrilleBat getPanGrid1() {
		return panGrid1 ;
	}

	public void enableGo() {
		go.setEnabled(true) ;
	}
	
	public void enableCancel1() {
		cancel1.setEnabled(true) ;
	}
	
	public void enableCancel2() {
		cancel2.setEnabled(true) ;
	}

	@Override
	public void valueChanged(ListSelectionEvent ev) {
		if (!ev.getValueIsAdjusting()) {
			String str = listBat.getSelectedValue() ;
			labBateau = null ;
			for (int i = 0 ; i < labBateaux.length ; i++)
				if (str.equals(labBateaux[i])) {
					labBateau = labBateaux[i] ;
					break ;
				}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ev) {		
		String labCase = ev.getActionCommand() ;
		switch (labCase) {
		case "GO !" :		//si l'utilisateur a cliqué sur le bouton "GO !"
			isGoEnabled = true ;
			tabPan.setEnabledAt(1, true) ;
			tabPan.setSelectedIndex(1) ;
			panGrid1.getHistButs().clear() ;
			panBut1.setVisible(false) ;
			panBut2.validate();
			panBut2.repaint();
			cancel1.setEnabled(false) ;
			remove(go) ;
			break ;
		case "Cancel" :		//si l'utilisateur a cliqué sur le bouton "Cancel"
			int n ;
			if (tabPan.getSelectedIndex() == 0) {
				List<Bouton> buts = panGrid1.getHistButs() ;
				n = buts.size()-1 ;
				buts.get(n).setToPreCaseType() ;
				buts.get(n).repaint() ;
				buts.remove(n) ;
				if (buts.size() == 0)
					cancel1.setEnabled(false) ;
				if (!isGoEnabled) {
					List<Bateau> listBat = panGrid1.getBateaux() ;
					panGrid1.removeBateau(listBat.get((listBat.size()-1))) ;
					go.setEnabled(false) ;
				}
			}
			else {
				List<Bouton> buts = panGrid2.getHistButs() ;
				n = buts.size()-1 ;
				buts.get(n).setToPreCaseType() ;
				buts.get(n).repaint() ;
				buts.remove(n) ;
				if (buts.size() == 0)
					cancel2.setEnabled(false) ;
			}
			break ;
		case "Delete" :		//si l'utilisateur a cliqué sur le bouton "Delete"
			
			break ;
		default :
			int x = labCase.charAt(1)-48, y = labCase.charAt(4)-48 ;
			boolean b = horizontal.isSelected() ;
			Bateau bat ;
			if (labBateau.equals(labBateaux[0]))
				bat = new PorteAvion(x+1, y+1, b) ;
			else
				if (labBateau.equals(labBateaux[1]))
					bat = new Croiseur(x+1, y+1, b) ;
				else
					bat = new Torpilleur(x+1, y+1, b) ;
			
			if (isGoEnabled)
				if (tabPan.getSelectedIndex() == 0)
					panGrid1.addCross(x, y, hitten.isSelected(), true) ;
				else
					panGrid2.addCross(x, y, hitten.isSelected(), false) ;
			else
				panGrid1.addBateau(bat) ;
			
		}
	}

}
