package tests_classes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import jeu_composants.Carte;


class testCarte {
    /**
     * @brief Teste attributionTeteBoeuf()
     */
    @Test
    void testAttributionTeteboeuf() {
        // V�rifier que la carte n�55 donne 7 t�tes de boeufs
        assertEquals(7, new Carte(55).getNumTeteDeBoeufs());
        
        // V�rifier qu'une carte se terminant par 5 donne 2 t�tes de boeufs
        assertEquals(2, new Carte(5).getNumTeteDeBoeufs());
        assertEquals(2, new Carte(15).getNumTeteDeBoeufs());
        assertEquals(2, new Carte(95).getNumTeteDeBoeufs());
        
        // V�rifier qu'une carte se terminant par 0 donne 3 t�tes de boeufs
        assertEquals(3, new Carte(10).getNumTeteDeBoeufs());
        assertEquals(3, new Carte(20).getNumTeteDeBoeufs());
        assertEquals(3, new Carte(100).getNumTeteDeBoeufs());
        
        /** 
         * V�rifier qu'une carte comportant deux chiffres �gaux (sauf 55) donne
         * 5 t�tes de boeufs
         */
        assertEquals(5, new Carte(11).getNumTeteDeBoeufs());
        assertEquals(5, new Carte(22).getNumTeteDeBoeufs()); 
        assertEquals(5, new Carte(44).getNumTeteDeBoeufs()); 
        
        /**
         * V�rifier que n'importe quelle carte ne remplissant pas les conditions
         * test�s ci dessus donne 1 t�te de boeuf
         */
        assertEquals(1, new Carte(1).getNumTeteDeBoeufs());
        assertEquals(1, new Carte(2).getNumTeteDeBoeufs());
        assertEquals(1, new Carte(12).getNumTeteDeBoeufs());
    }
    
    
    /**
     * @brief Teste toString()
     */
    @Test
    void testToString() {
        assertEquals("55 (7)", new Carte(55).toString());
        assertEquals("25 (2)", new Carte(25).toString());
        assertEquals("20 (3)", new Carte(20).toString());
        assertEquals("44 (5)", new Carte(44).toString());
        assertEquals("12", new Carte(12).toString());
    }
    

    /**
     * @brief Teste la m�thode equals()
     */
    @Test
    void testCarteEgale(){
    	// 2 Cartes de m�me valeurs sont �gales :
    	assertEquals(new Carte(1), new Carte(1));
    	assertEquals(new Carte(55), new Carte(55));
    	assertEquals(new Carte(10), new Carte(10));
    	
    	/** 
         * 2 cartes de valeurs diff�rentes mais au m�me nombre de t�tes de
         * boeufs sont diff�rentes
         */ 
    	assertNotEquals(new Carte(2), new Carte(1));
    	assertNotEquals(new Carte(10), new Carte(20));
    	assertNotEquals(new Carte(25), new Carte(65));
    	
    	/**
         * 2 cartes de valeurs et au nombre de t�tes de boeuf diff�rentes sont
         * diff�rentes
         */
    	assertNotEquals(new Carte(55), new Carte(1));
    	assertNotEquals(new Carte(55), new Carte(10));
    	assertNotEquals(new Carte(55), new Carte(25));
    	
    	// Comparer avec un objet qui n'est pas une carte ne renvoie pas d'erreur
    	assertNotEquals(new Carte(55), 1);
    	
    	// Comparer avec un objet nul ne renvoie pas d'erreur
    	assertNotEquals(new Carte(1), null);
    }
    
    /**
     * @brief Teste la m�thode compareCarte()
     */
    @Test
    void testCompareCarte(){
    	assertEquals(Carte.compareCarte(new Carte(2), new Carte(1)), 1);
    	assertEquals(Carte.compareCarte(new Carte(55), new Carte(55)), 0);
    }
}
