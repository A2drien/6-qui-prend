package jeu_composants;

import java.util.Collections;
import java.util.Random;

public class Serie {
    private static final int NB_CARTES_MAX_SERIE = 5;
    private static final int NB_SERIES = 4;
    
    private int numeroSerie;        // Num�ro de la s�rie
    private Paquet paq;             // Paquet de carte
    

    /**
     * @brief Constructeur de Serie, initialise le num�ro et sa premi�re carte
     * @param n Num�ro de la s�rie
     * @param p Paquet dans lequel piocher
     */
    private Serie(int n, Paquet p) {
        this.numeroSerie = n;
        initialisationPaquet(p);
    }
    
    
    /**
     * @void Ins�re une carte dans la s�rie
     * @param p Paquet dans lequel piocher
     */
    private void initialisationPaquet(Paquet p) {
        this.paq = new Paquet();

        int idxCarte = new Random().nextInt(p.size());
        Carte c = p.get(idxCarte);
        
        this.paq.add(c);
        p.remove(idxCarte);
    }
    

    /**
     * @brief Vide la s�rie
     */
    public void vider() {
        this.paq.clear();
    }
    

    /**
     * @brief V�rifie si la s�rie est pleine
     * @return true si la taille de la s�rie est �gale �
     * {@value NB_CARTES_MAX_SERIE}, false sinon
     */
    public boolean estPleine() {
        return this.paq.size() == NB_CARTES_MAX_SERIE;
    }
    

    /**
     * @brief Renvoie le num�ro de la derni�re carte
     * @pre La s�rie n'est pas vide
     * @return Num�ro de la derni�re carte de la s�rie
     */
    public int nCarteMax() throws RuntimeException{
        if (this.paq.size() == 0){
            throw new RuntimeException("Une s�rie n'est pas cens�e rester vide");
        }

        int idxCarteMax = this.paq.size()-1;
        Carte carteMax = this.paq.get(idxCarteMax);
        return carteMax.getNumCarte();
    }

    
    /**
     * @brief Retourne le num�ro de s�rie ainsi que ses cartes
     * @return S�rie n� [num�ro de la s�rie] : [paquet de carte de la s�rie]
     */
    public String toString(){
        return "- s�rie n� " + numeroSerie + " : " + paq;
    }
    
    
    /**
     * @brief Renvoie le nombre de points de la s�rie
     * @return Nombre de points de la s�rie
     */
    public int nbPoints() {
        int nbTeteDeBoeuf = 0;
        
        for (Carte c : this.paq) {
            nbTeteDeBoeuf += c.getNumTeteDeBoeufs();
        }
        
        return nbTeteDeBoeuf;
    }

    
    /**
     * @brief Renvoie l'ensemble des s�ries sous forme de cha�ne de caract�re
     * @return Ensemble des s�ries
     */
    public static String toStringSeries(Serie[] tabS) {
        StringBuilder sb = new StringBuilder();
        
        for (int idxS = 0; idxS<tabS.length; ++idxS) {
            sb.append(tabS[idxS]);
            
            if (idxS < tabS.length-1) {
                sb.append(System.lineSeparator());
            }
        }
        
        return sb.toString();
    }


    /**
     * @brief Ajoute une carte � la s�rie
     * @param c Carte � ajouter
     * @pre La s�rie n'est pas pleine et le num�ro de la carte est sup�rieur �
     * l'actuelle carte de plus haute valeur
     */
    public void ajoute(int c) throws RuntimeException{
        if (this.estPleine()){
            throw new RuntimeException("S�rie " + this + "est d�j� pleine");
        }

        else if (this.paq.size() != 0 && this.nCarteMax() >= c){
            throw new RuntimeException("Carte n�" + c + "inf�rieure ou �gale � "
                                        + this.nCarteMax());
        }

        this.paq.add(new Carte(c));
    }
    

    /**
     * @brief Compare Les 2 cartes maximales de 2 s�ries
     * @param s S�rie dont la plus grande carte est � comparer
     * @return true si la s�rie de gauche a un num�ro de carte maximale plus
     * �lev�e que la s�rie de droite
     */
    public boolean aCarteMaxPlusEleveeQue(Serie s){
        return this.nCarteMax() > s.nCarteMax();
    }


    /**
     * @brief Trie le paquet de carte de la s�rie
     */
    public void tri(){
        Collections.sort(this.paq, Carte::compareCarte);
    }


    /**
     * @brief Constructeur de 4 S�ries
     * @param pioche Paquet dans lequel piocher les cartes
     */
    public static Serie[] initialisationSeries(Paquet pioche){
        Serie[] tabS = new Serie[NB_SERIES];
        
        for (int i=0; i<NB_SERIES; ++i){
            tabS[i] = new Serie(i+1, pioche);
        }

        return tabS;
    }
}
