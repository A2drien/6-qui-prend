
package jeu_composants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Joueur {
    private static final int NB_CARTES_JOUEUR = 10; // Nombre de cartes par joueur

    private String nom;             // Nom du joueur
    private Paquet main;            // Main du joueur
    private int nbPointsTotal;      // Nombre de points du joueur

    private int nbPointsTour;       // Nombre de points du joueur sur son tour
    private int choixCarte;         // Num�ro du choix de la carte du joueur


    /**
     * @brief Constructeur de Joueur, initialise son nom et sa main
     * @param nomJoueur Nom du joueur
     * @param paquet Paquet dans lequel piocher les cartes de la main
     */
    public Joueur(String nomJoueur, Paquet paquet){
        this.nom = nomJoueur;
        this.main = new Paquet();
        this.piocheDans(paquet);
        this.nbPointsTotal = 0;
    }


    /**
     * @brief Pioche le nombre de carte n�cessaire
     * @param p Pioche dans laquelle piocher
     * @pre La pioche doit avoir suffisamment de cartes (NB_CARTES_JOUEUR)
     */
    private void piocheDans(Paquet p) throws RuntimeException {
        if (p.size() < NB_CARTES_JOUEUR){
            throw new RuntimeException("Pioche insuffisante : " + p.size() +
                                        " cartes sur les " + NB_CARTES_JOUEUR +
                                        "n�cessaires");
        }
        
        for (int i=0; i<NB_CARTES_JOUEUR; ++i){
            int idxCarte = new Random().nextInt(p.size());
            Carte c = new Carte(p.get(idxCarte).getNumCarte());

            p.remove(idxCarte);
            this.main.add(c);
        }

        Collections.sort(this.main, Carte::compareCarte);
    }


    /**
     * @brief Joue une carte dans la s�rie et la vide si elle est pleine ou si
     * la carte choisie n'est pas compatible.
     * @param s S�rie dans laquelle jouer
     */
    public void joueDans(Serie s){
        this.nbPointsTour = 0;
        
        if (s.estPleine() || s.nCarteMax() > this.choixCarte) {
            this.ajoutPoints(s);
            s.vider();
        }

        s.ajoute(this.choixCarte);
        this.main.remove(new Carte(this.choixCarte));

        s.tri();
    }
    
    
    /**
     * @brief Ajoute les points contenu dans la s�rie au joueur
     * @param s S�rie d'o� prendre les points
     */
    private void ajoutPoints(Serie s) {
        this.nbPointsTotal += s.nbPoints();
        this.nbPointsTour = s.nbPoints();
    }
    
    
    /**
     * @brief V�rifie si le joueur poss�de la carte qu'il souhaite jouer.
     * @param c Num�ro de la carte
     * @pre La carte doit �tre poss�d�e par le joueur
     */
    public void choisi(int c) throws RuntimeException {
        if (!this.main.contains(new Carte(c))){
            throw new RuntimeException("Carte n�" + c + "non poss�d�e par " +
                                        this);
        }

        this.choixCarte = c;
    }


    /**
     * @brief V�rifie si le joueur peut jouer une carte dans la s�rie de son
     * choix. Le fait s'il le peut.
     * @param tabS Tableau de s�ries
     * @return true si la carte courante est sup�rieure ou �gale � la carte
     * minimum de la s�rie, sinon false
     */
    public boolean peutJouerDeSuite(Serie[] tabS){
        Serie serieAJouer = tabS[0];
        boolean peutJouer = false;

        for (Serie serieATester : tabS) {
            // Sa carte est plus grande que celle de la s�rie
            if (this.choixCarte > serieATester.nCarteMax()){
                if (!peutJouer){
                    peutJouer = true;
                    serieAJouer = serieATester;
                }

                /** S'il a le choix entre 2 s�rie, s�lectionne celle avec la
                 * plus petite diff�rence entre les 2 cartes
                 */
                else if (serieATester.aCarteMaxPlusEleveeQue(serieAJouer)){
                    serieAJouer = serieATester;
                }
            }
        }

        // Joue s'il le peut
        if (peutJouer){
            this.joueDans(serieAJouer);
        }

        return peutJouer;
    }
    

    /**
     * @brief Compare 2 joueurs afin de les trier par ordre croissant de num�ro
     * de carte choisi
     * @param j1 Joueur 1
     * @param j2 Joueur 2
     */
    private static int compareChoixJoueur(Joueur j1, Joueur j2) {
         return j1.choixCarte- j2.choixCarte;
    }
    
    
    /**
     * @brief Compare 2 joueurs afin de les trier par ordre croissant nombre
     * total de points, et par ordre lexicographique en cas d'�galit�
     * @param j1 Joueur 1
     * @param j2 Joueur 2
     */
    private static int compareScoreFinal(Joueur j1, Joueur j2) {
        // S'ils ont le m�me nombre de points, passer � l'ordre lexicographique
    	if (j1.nbPointsTotal == j2.nbPointsTotal) {
    		return String.valueOf(j1.nom).compareTo(j2.nom);
    	}
    	
    	else {
    		return j1.nbPointsTotal - j2.nbPointsTotal;
    	}
    }
    
    
    /**
     * @brief Renvoie une copie tri�e d'un tableau de Joueur
     * @param tabJ ArrayList de Joueur
     * @return Copie tri�e par ordre croissant de num�ro de carte choisie
     */
    public static ArrayList<Joueur> copieTriee(ArrayList<Joueur> tabJ){
        ArrayList<Joueur> newTabJ = new ArrayList<>();
        
        /** Copie les �l�ments de la liste et non la liste pour sauvegarder
         *  l'originale
         */ 
        for (Joueur j : tabJ) {
            newTabJ.add(j);
        }
        
        Collections.sort(newTabJ, Joueur::compareChoixJoueur); // Trie la copie
        
        return newTabJ;
    }

    
    /**
     * @brief M�thode toString()
     * @return [nom]
     */
    public String toString(){
        return nom;
    }
    

    /**
     * @brief Retourne le message de d�but d'un tour d'un joueur
     * @return A [nom du joueur] de jouer
     */
    public String annonce() {
        return "A " + this + " de jouer.";
    }
    

    /**
     * @brief Retourne la main du joueur
     * @return Vos cartes : [main du joueur]
     */
    public String toStringMain() {
        return "- Vos cartes : " + this.main;
    }

    
    /**
     * @brief Retourne le message pr�c�dant le choix d'une s�rie
     * @return Pour poser la carte [choixCarte], [nom du joueur] doit choisir la
     * s�rie qu'il va ramasser.
     */
    public String debutChoixSerie(){
        return "Pour poser la carte " + this.choixCarte + ", " + this +
        " doit choisir la s�rie qu'il va ramasser.";
    }
    

    /**
     * @brief Retourne le message des cartes choisies par leurs joueurs
     * @param tabJ ArrayList de Joueur
     * @return Les cartes [cartes choisies par le joueur]
     */
    private static String posageCarte(ArrayList<Joueur> tabJ) {
        StringBuilder sb = new StringBuilder();

        ArrayList<Joueur> tabJ2 = copieTriee(tabJ);

        sb.append("Les cartes ");
        
        for (int i = 0; i < tabJ.size(); i++) {
            sb.append(tabJ2.get(i).choixCarte + " (" + tabJ2.get(i) + ")");
        
            if (i < tabJ2.size() - 2) {
                sb.append(", ");
            }
            
            else if (i == tabJ2.size() - 2){
                sb.append(" et ");
            }
        }
        return sb.toString();
    }


    /**
     * @brief Retourne le message de d�but de partie
     * @param tabJ Tableau de joueur
     * @return Les [nombre de joueurs] sont [noms des joueurs].
     * Merci de jouer � 6 qui prend ! (sans sauts de ligne)
     */
    public static String debutPartie(ArrayList<Joueur> tabJ){
        int nbJoueurs = tabJ.size();
        StringBuilder sb = new StringBuilder("Les " + nbJoueurs +
        " joueurs sont ");

        for (int i=0; i<nbJoueurs; ++i){
            sb.append(tabJ.get(i));

            if (i < nbJoueurs-2){
                sb.append(", ");
            }

            else if (i < nbJoueurs-1){
                sb.append(" et ");
            }
        }

        return sb.toString() + ". Merci de jouer � 6 qui prend !";
    }
    
    
    /**
     * @brief Retourne le message pr�c�dant le posage des cartes
     * @param tabJ ArrayList de Joueur
     * @return Les cartes [cartes choisies par les joueurs] vont �tre pos�es.
     */
    public static String prePosageCarte(ArrayList<Joueur> tabJ) {
        return posageCarte(tabJ) + " vont �tre pos�es.";
    }
    

    /**
     * @brief Retourne le message pr�c�dant le posage des cartes
     * @param tabJ ArrayList de Joueur
     * @return Les cartes [cartes choisies par les joueurs] ont �t� pos�es.
     */
    public static String postPosageCarte(ArrayList<Joueur> tabJ) {
        return posageCarte(tabJ) + " ont �t� pos�es.";
    }


    /**
     * @brief Retourne le nombre de t�tes de boeuf obtenu pour le joueur
     * @param nbPoints Nombre de point du joueur
     * @return [nom du joueur] a ramass� [nbPoints] t�te(s) de boeuf
     */
    private String scoreIndividuel(int nbPoints){
        StringBuilder sb = new StringBuilder();

        sb.append(this + " a ramass� " + nbPoints + " t�te");
                
        if (nbPoints > 1){
            sb.append("s");
        }
        
        return sb.toString()+ " de boeufs";
    }


    /**
     * @brief Retourne le nombre de t�tes ramass�es par les joueurs durant le
     * tour
     * @param tabJ ArrayList de Joueur
     * @return Pour chaque joueur : "[nom du joueur] a ramass� X t�te(s) de
     * boeufs", ou "Aucun joueur ne ramasse de t�te de boeufs." si personne
     * n'en a ramass�.
     */
    public static String scoreFinTour(ArrayList<Joueur> tabJ) {
        StringBuilder sb = new StringBuilder();
        boolean aucunPoints = true;

        for (Joueur j : copieTriee(tabJ)) {
            if (j.nbPointsTour > 0) {
                if (!aucunPoints){
                    sb.append(System.lineSeparator());
                }

                else{
                    aucunPoints = false;
                }

                sb.append(j.scoreIndividuel(j.nbPointsTour));
            }
        }

        if (aucunPoints){
            return "Aucun joueur ne ramasse de t�te de boeufs.";
        }

        else {
            return sb.toString();
        }
    }


    /**
     * @brief Retourne le score final de tous les joueurs
     * @param tabJ ArrayList de Joueur
     * @return **Score final + Score final de tous les joueurs
     */
    public static String scoreFinal(ArrayList<Joueur> tabJ){
        Collections.sort(tabJ, Joueur::compareScoreFinal);
        StringBuilder sb = new StringBuilder();
        boolean premierJoueur = true;

        sb.append("** Score final" + System.lineSeparator());
        for (Joueur j : tabJ) {
            if (!premierJoueur){
                sb.append(System.lineSeparator());
            }

            else{
                premierJoueur = false;
            }
            
            sb.append(j.scoreIndividuel(j.nbPointsTotal));
        }

        return sb.toString();
    }
    

    /**
     * @brief Getter de NB_CARTES_JOUEURS
     * @return NB_CARTES_JOUEUR
     */
    public static int getNbCartesJoueurs() {
        return NB_CARTES_JOUEUR;
    }
}
