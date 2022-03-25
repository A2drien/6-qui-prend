package tests_classes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import jeu_composants.Carte;
import jeu_composants.Joueur;
import jeu_composants.Paquet;
import jeu_composants.Serie;

class testJoueur {
	/**
	 * @brief V�rifie que la syntaxe du d�but de la partie soit la bonne
	 */
	@Test
	void testDebutPartie() {
		Paquet p = Paquet.initialisationPioche();
		ArrayList<Joueur> joueurs = new ArrayList<>();
		
		joueurs.add(new Joueur("Jean", p));
		joueurs.add(new Joueur("Leon", p));
		joueurs.add(new Joueur("Pierre", p));
		
		assertEquals("Les 3 joueurs sont Jean, Leon et Pierre. " +
		"Merci de jouer � 6 qui prend !", Joueur.debutPartie(joueurs));	
	}
	
	
	/**
	 * @brief V�rifie que la syntaxe du d�but du tour du joueur
	 */
	@Test
	void testAnnonce() {
		Paquet p = Paquet.initialisationPioche();
		assertEquals("A Jean de jouer.", new Joueur("Jean", p).annonce());
		assertEquals("A Marie de jouer.", new Joueur("Marie", p).annonce());
		assertEquals("A 12%;/ de jouer.", new Joueur("12%;/", p).annonce());
	}
	
	
	/**
	 * @brief V�rifie que la carte est jou�e dans la bonne s�rie
	 */
	@Test
	void testJoueDans() {
		// Si le joueur joue une carte dans une s�rie pleine
		Paquet paquet = new Paquet();

		for (int i=1; i<5; ++i) {
			paquet.add(new Carte(i));	
		}
		
		Serie[] tabS = Serie.initialisationSeries(paquet);
		
		// Remplir la s�rie
		tabS[0].ajoute(5);
		tabS[0].ajoute(10);
		tabS[0].ajoute(22);
		tabS[0].ajoute(55);

		for (int i=50; i<60; ++i) {
			paquet.add(new Carte(i));	
		}
		
		ArrayList<Joueur> j = new ArrayList<>();
		
		j.add(new Joueur("Marc", paquet));
		j.get(0).choisi(54);
		j.get(0).joueDans(tabS[0]);

		assertTrue(tabS[0].nCarteMax() == 54);
		assertEquals("Marc a ramass� 18 t�tes de boeufs", Joueur.scoreFinTour(j));
		assertEquals("** Score final"+ System.lineSeparator() +
				      "Marc a ramass� 18 t�tes de boeufs", Joueur.scoreFinal(j));


		/**
		 * Si le joueur joue une carte dans une s�rie avec une carteMax 
		 * plus grande que son choix
		 */
		tabS[1].ajoute(98);
		j.get(0).choisi(52);
		j.get(0).joueDans(tabS[1]);

		assertTrue(tabS[1].nCarteMax() == 52);
		assertEquals("Marc a ramass� 2 t�tes de boeufs", Joueur.scoreFinTour(j));
		assertEquals("** Score final"+ System.lineSeparator() +
				      "Marc a ramass� 20 t�tes de boeufs", Joueur.scoreFinal(j));


		/**
		 * Si le joueur joue une carte dans une s�rie avec une carteMax 
		 * plus faible que son choix
		 */
		j.get(0).choisi(59);
		j.get(0).joueDans(tabS[1]);


		assertTrue(tabS[1].nCarteMax() == 59);
		assertEquals("Aucun joueur ne ramasse de t�te de boeufs.",
						Joueur.scoreFinTour(j));
		assertEquals("** Score final"+ System.lineSeparator() +
				      "Marc a ramass� 20 t�tes de boeufs", Joueur.scoreFinal(j));
	}
	
	
	/**
	 * @brief V�rifie peutJouerDeSuite()
	 */
	@Test
	void testPeutJouerDeSuite() {
		/**
		 * Cas o� le choix de la carte est sup�rieure � la carte max d'UNE SEULE
		 * s�rie
		 */
		Paquet paquet = new Paquet();
		
		for(int i = 1; i < 5; i++) {
			paquet.add(new Carte(i*20));
		}
		
		Paquet main = new Paquet();
		for(int i = 1; i < 11; i++) {
			main.add(new Carte(i*7));
		}
		
		Serie[] tabS = Serie.initialisationSeries(paquet);
		
		Joueur j1 = new Joueur("Jean", main);
		j1.choisi(21);
		assertTrue(j1.peutJouerDeSuite(tabS));
		

		// Cas ou le choix est sup�rieur � la carte max de PLUSIEURS s�ries
		j1.choisi(70);
		assertTrue(j1.peutJouerDeSuite(tabS));
		


		//Cas ou le choix de la carte ne peut aller dans aucune s�rie.
		j1.choisi(7);
		assertFalse(j1.peutJouerDeSuite(tabS));
	}
	
	
	/**
	 * @brief V�rifie toStringMain()
	 */
	@Test
	void testToStringMain() {
		Paquet pioche = new Paquet();
		
		for (int i=1; i<11; ++i) {
			pioche.add(new Carte(i));
		}
		
		Joueur j = new Joueur("Denis", pioche);
		
		j.choisi(5);
		
		assertEquals("Pour poser la carte 5, Denis doit choisir la s�rie qu'il va ramasser.",
				j.debutChoixSerie());
	}
	
	
	/**
	 * @brief V�rifie debutChoixSerie()
	 */
	@Test
	void testDebutChoixSerie() {
		Paquet main = new Paquet();
		for(int i = 1; i < 11; i++) {
			main.add(new Carte(i*7));
		}
		
		Joueur j1 = new Joueur("Jean", main);
		
		j1.choisi(70);
		
		assertEquals("Pour poser la carte 70, " +
						"Jean doit choisir la s�rie qu'il doit ramasser.",
							j1.debutChoixSerie());
	}
	
	
	/**
	 * @brief V�rifie la syntaxe et l'ordre de la cha�ne de caract�re retourn�e
	 */
	@Test
	void testPrePosageCarte() {
		Paquet paquet = new Paquet();
		ArrayList<Joueur> tabJ = new ArrayList<>();
		
		for (int i=1; i<11; ++i) {
			paquet.add(new Carte(i));
		}
		tabJ.add(new Joueur("Jean", paquet));
		tabJ.get(0).choisi(1);
		
		
		for (int i=50; i<60; ++i) {
			paquet.add(new Carte(i));
		}
		tabJ.add(new Joueur("Paul", paquet));
		tabJ.get(1).choisi(55);
		
		
		for (int i=40; i<50; ++i) {
			paquet.add(new Carte(i));
		}
		tabJ.add(new Joueur("Marc", paquet));
		tabJ.get(2).choisi(40);
		
		assertEquals("Les cartes 1 (Jean), 40 (Marc) et 55 (Paul) vont �tre pos�es.",
						Joueur.prePosageCarte(tabJ));
	}
	
	
	/**
	 * @brief V�rifie la syntaxe et l'ordre de la cha�ne de caract�re retourn�e
	 */
	@Test
	void testPostPosageCarte() {
		Paquet paquet = new Paquet();
		ArrayList<Joueur> tabJ = new ArrayList<>();
		
		for (int i=1; i<11; ++i) {
			paquet.add(new Carte(i));
		}
		tabJ.add(new Joueur("Jean", paquet));
		tabJ.get(0).choisi(1);
		
		
		for (int i=50; i<60; ++i) {
			paquet.add(new Carte(i));
		}
		tabJ.add(new Joueur("Paul", paquet));
		tabJ.get(1).choisi(55);
		
		
		for (int i=40; i<50; ++i) {
			paquet.add(new Carte(i));
		}
		tabJ.add(new Joueur("Marc", paquet));
		tabJ.get(2).choisi(40);
		
		assertEquals("Les cartes 1 (Jean), 40 (Marc) et 55 (Paul) ont �t� pos�es.",
						Joueur.postPosageCarte(tabJ));
	}
}
