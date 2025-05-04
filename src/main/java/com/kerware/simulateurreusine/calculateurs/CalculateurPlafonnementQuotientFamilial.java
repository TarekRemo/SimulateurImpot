package com.kerware.simulateurreusine.calculateurs;

import com.kerware.ParametresImposition;

/**
 * EXG_IMPOT_05 : Le gain d'impôt procuré par les enfants est plafonné.
 */
public class CalculateurPlafonnementQuotientFamilial {

    private static final double VALEUR_DEMI_PART = 0.5;

    /**
     * Calcule l'impôt après application du plafonnement en fonction des paramètres d'imposition.
     * @see ParametresImposition.java
     * @param impotDeclarantsSeuls  impôt théorique si on ne tenait pas compte des parts enfants
     * @param impotFoyerComplet impôt réel du foyer (avec les enfants)
     * @param nombrePartsDeclarants nombre de parts du (ou des) déclarants sans les enfants
     * @param nombrePartsFoyer nombre de parts complet (avec les enfants)
     * @return l'impôt après plafonnement (brut, non décoté)
     */
    public static double appliquerPlafonnement(
            double impotDeclarantsSeuls,
            double impotFoyerComplet,
            double nombrePartsDeclarants,
            double nombrePartsFoyer) {

        double gain = impotDeclarantsSeuls - impotFoyerComplet;

        // Nombre de demi-parts supplémentaires apportées par les enfants
        double differenceDeParts = nombrePartsFoyer - nombrePartsDeclarants;
        double nombreDemiPartsSupp = differenceDeParts / VALEUR_DEMI_PART;

        // Plafond total
        double plafond = nombreDemiPartsSupp * ParametresImposition.PLAFOND_BAISSE_PAR_DEMI_PART;

        if (gain > plafond) {
            // On ne peut pas bénéficier d'un gain d'impôt supérieur au plafond
            return impotDeclarantsSeuls - plafond;
        } else {
            return impotFoyerComplet;
        }
    }
}
