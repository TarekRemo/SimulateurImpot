package com.kerware.simulateurreusine.calculateurs;

import com.kerware.ParametresImposition;

/**
 * EXG_IMPOT_07 : Contribution exceptionnelle sur les hauts revenus (CEHR).
 */
public class CalculateurContributionExceptionnelle {

    /**
     * Calcule le montant de la contribution exceptionnelle (CEHR) en fonction des paramètres d'imposition.
     * @see ParametresImposition.java
     */
    public static double calculerContribution(double revenuFiscalRef, double nombrePartsDeclarants) {
        // On distingue personne seule (1 part) et couple (2 parts) pour choisir les taux adaptés
        double[] taux = (nombrePartsDeclarants == 1.0)
                ? ParametresImposition.CEHR_TAUX_CELIBATAIRE
                : ParametresImposition.CEHR_TAUX_COUPLE;

        int[] seuils = ParametresImposition.CEHR_SEUILS;

        double contribution = 0.0;

        // Calcul par tranches
        for (int i = 0; i < taux.length; i++) {
            if (i == taux.length - 1) {
                // dernière tranche
                if (revenuFiscalRef > seuils[i]) {
                    contribution += (revenuFiscalRef - seuils[i]) * taux[i];
                }
            } else {
                int borneInf = seuils[i];
                int borneSup = seuils[i + 1];
                if (revenuFiscalRef <= borneInf) {
                    break;
                }
                double partieImposable = Math.min(revenuFiscalRef, borneSup) - borneInf;
                if (partieImposable < 0) {
                    partieImposable = 0;
                }
                contribution += partieImposable * taux[i];
            }
        }

        return Math.round(contribution);
    }
}

