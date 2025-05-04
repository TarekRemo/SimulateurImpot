package com.kerware.simulateurreusine.calculateurs;

import com.kerware.ParametresImposition;

/**
 * EXG_IMPOT_06 : Calcule la décote pour situations modestes.
 */
public class CalculateurDecote {

    /**
     * Calcule le montant de la décote à appliquer en 
     * fonction des paramètres d'imposition.
     * @see ParametresImposition.java
     * @param impotBrut  impôt brut avant décote
     * @param nombrePartsDeclarants 1 si personne seule, 2 si couple
     * @return montant de la décote (arrondi)
     */
    public static double calculerDecote(double impotBrut, double nombrePartsDeclarants) {
        double decote = 0.0;

        if (nombrePartsDeclarants == 1.0) {
            // personne seule
            if (impotBrut < ParametresImposition.DECOTE_SEUIL_PERSONNE_SEULE) {
                decote = ParametresImposition.DECOTE_MAX_PERSONNE_SEULE 
                - (impotBrut * ParametresImposition.TAUX_DECOTE);
            }
        } else if (nombrePartsDeclarants == 2.0) {
            // couple
            if (impotBrut < ParametresImposition.DECOTE_SEUIL_COUPLE) {
                decote = ParametresImposition.DECOTE_MAX_COUPLE 
                - (impotBrut * ParametresImposition.TAUX_DECOTE);
            }
        }
        
        // On arrondit la décote
        decote = Math.round(decote);
        
        // La décote ne peut pas dépasser le montant de l'impôt
        if (decote > impotBrut) {
            decote = impotBrut;
        }

        return decote;
    }
}

