package com.kerware.simulateurreusine.calculateurs;

import com.kerware.ParametresImposition;

/**
 * EXG_IMPOT_04 : Calcule l'impôt selon le barème progressif (par tranches).
 */
public class CalculateurBaremeProgressif {

    /**
     * Calcule l'impôt brut pour le foyer
     * @param revenuFiscalRef revenu fiscal de référence du foyer
     * @param nombreParts nombre de parts fiscales du foyer
     * @return montant d'impôt brut (arrondi)
     */
    public static double calculerImpotBrutFoyer(double revenuFiscalRef, double nombreParts) {
        double revenuParPart = revenuFiscalRef / nombreParts;
        
        // Calcul de l'impôt "pour 1 part"
        double impotPour1Part = calculerImpotPourUnePart(revenuParPart);
        
        // Puis on multiplie par le nombre de parts
        double impotBrut = impotPour1Part * nombreParts;

        // EXG_IMPOT_01 : arrondi à l'euro
        return Math.round(impotBrut);
    }

    /**
     * Calcule l'impôt pour 1 part (barème progressif) en fonction des paramètres d'imposition.
     * @see ParametresImposition.java
     * @param revenuParPart le revenu imposable sur 1 part
     * @return impôt sur 1 part
     */
    private static double calculerImpotPourUnePart(double revenuParPart) {
        double[] taux = ParametresImposition.BAREME_TAUX;
        int[] seuils = ParametresImposition.BAREME_SEUILS;

        double impot = 0.0;

        // On parcourt les tranches : si le revenuParPart dépasse la tranche,
        // on applique le taux sur la fraction correspondante, puis on passe à la suivante, etc.
        for (int i = 0; i < taux.length; i++) {
            // tant qu'on n'a pas la dernière tranche
            if (i == taux.length - 1) {
                // dernière tranche
                if (revenuParPart > seuils[i]) {
                    impot += (revenuParPart - seuils[i]) * taux[i];
                }
            } else {
                // tranche i
                int borneInf = seuils[i];
                int borneSup = seuils[i + 1];
                if (revenuParPart <= borneInf) {
                    // Le revenu ne dépasse pas la borne inférieure de cette tranche
                    break;
                }
                double partieImposable = Math.min(revenuParPart, borneSup) - borneInf;
                if (partieImposable < 0) {
                    partieImposable = 0;
                }
                impot += partieImposable * taux[i];
            }
        }
        return impot;
    }
}
