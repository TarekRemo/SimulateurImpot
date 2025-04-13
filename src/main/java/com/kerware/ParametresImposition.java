package com.kerware;


/**
 * Paramètres d'imposition pour le calcule de l'impôts par le simulateur.
 */
public class ParametresImposition {
    
    // EXG_IMPOT_02 : Abattement 10%, max 14171, min 495
    public static final double TAUX_ABATTEMENT = 0.10;
    public static final int ABATTEMENT_MAX = 14171;
    public static final int ABATTEMENT_MIN = 495;

    
    // EXG_IMPOT_04 : Barème progressif principal
    // Tranches
    public static final int[] BAREME_SEUILS = {
        0,       
        11294,
        28797,
        82341,
        177106,
        Integer.MAX_VALUE 
    };
    
    //Taux
    public static final double[] BAREME_TAUX = {
        0.0,   
        0.11,  
        0.30,  
        0.41,  
        0.45   
    };

    // EXG_IMPOT_05 : Plafond de la baisse d'impôt par demi-part
    public static final double PLAFOND_BAISSE_PAR_DEMI_PART = 1759;

    // EXG_IMPOT_06 : Décote
    // Seuils de déclenchement et calcul
    public static final double DECOTE_SEUIL_PERSONNE_SEULE = 1929;
    public static final double DECOTE_SEUIL_COUPLE = 3191;
    public static final double DECOTE_MAX_PERSONNE_SEULE = 873;
    public static final double DECOTE_MAX_COUPLE = 1444;
    public static final double TAUX_DECOTE = 0.4525;

    // EXG_IMPOT_07 : Contribution exceptionnelle sur les hauts revenus (CEHR)
    // Tranches
    public static final int[] CEHR_SEUILS = {
        0,
        250000,
        500000,
        1000000,
        Integer.MAX_VALUE
    };

    /**
     * Taux CEHR pour une personne seule : 
     * (c'est un barème par tranche)
     */
    public static final double[] CEHR_TAUX_CELIBATAIRE = {
        0.0,
        0.03,
        0.04,
        0.04
    };

    /**
     * Taux CEHR pour un couple :
     * (c'est un barème par tranche)
     */
    public static final double[] CEHR_TAUX_COUPLE = {
        0.0,
        0.0,
        0.03,
        0.04
    };
}
