package jeu_composants;

public class Carte {
    private static final int NB_CARTES_JEU = 104;// Nombre de cartes dans le jeu
    
    private int numero;             // Num�ro de la carte
    private int nbTeteDeBoeufs;     // Nombre de t�tes de boeuf
    

    /**
     * @brief Constructeur de Carte, initialise son num�ro et son nombre de t�te
     * de boeuf
     * @param n Le num�ro de la carte � cr�er
     * @pre Le num�ro doit �tre compris entre 1 et NB_CARTES_JEU
     */
    public Carte(int n) throws RuntimeException {
        if (n < 1 || n > NB_CARTES_JEU){
            throw new RuntimeException(n + " n'est pas entre 1 et " +
                                       NB_CARTES_JEU);
        }
        
        this.numero = n;
        this.attributionTeteDeBoeufs();
    }
    
    
    /**
     * @brief Getter de numero
     * @return numero
     */
    public int getNumCarte() {
        return numero;
    }


    public static int getNbCartesJeu(){
        return NB_CARTES_JEU;
    }
    
    
    /**
     * @brief Getter de nbTeteDeBoeufs
     * @return nbTeteDeBoeufs
     */
    public int getNumTeteDeBoeufs() {
        return nbTeteDeBoeufs;
    }
    
    
    /**
     * @brief Attribue le bon nombre de t�te de boeufs � une carte
     */
    private void attributionTeteDeBoeufs() {
        int dizaine = numero/10;
        int unite = numero%10;

        if (numero == 55) {
            this.nbTeteDeBoeufs = 7;
        }
        else if (dizaine == unite) {
            this.nbTeteDeBoeufs = 5;
        }
        else if (unite == 0) {
            this.nbTeteDeBoeufs = 3;
        }
        else if (unite == 5) {
            this.nbTeteDeBoeufs = 2;
        }
        else {
            this.nbTeteDeBoeufs = 1;
        }
    }
    
    
    /**
     * @brief Retourne le num�ro de la carte, ainsi que son nombre de t�te de
     * boeuf entre parenth�ses si diff�rent de 1.
     * @return [num�ro de la carte] ([nombre de t�tes de boeuf (si plus d'une)])
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.numero);
        
        if (this.nbTeteDeBoeufs > 1){
            sb.append(" (" + this.nbTeteDeBoeufs + ")");
        }
        
        return sb.toString();
    }
    
    
    /**
     * @brief Compare 2 cartes afin de les trier par ordre croissant de num�ro
     * @param c1 Carte 1
     * @param c2 Carte 2
     */
    public static int compareCarte(Carte c1, Carte c2) {
        return c1.numero - c2.numero;
    }
    
    
    /**
     * @brief Compare 2 cartes
     * @param obj Objet (normalement une carte) � comparer
     * @return vrai si le num�ro (et donc aussi le nombre de t�te de boeufs) de
     * la carte courante est �gal au num�ro de carte de l'objet cibl�.
     */
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
          return false;
        }
        
        Carte c = (Carte) obj;
        
        return this.numero == c.numero;
    }
}
