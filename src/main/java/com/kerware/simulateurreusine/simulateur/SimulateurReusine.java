package com.kerware.simulateurreusine.simulateur;

import com.kerware.SituationFamiliale;
import com.kerware.simulateurreusine.FoyerFiscal;
import com.kerware.simulateurreusine.calculateurs.*;

/**
 * Classe principale qui orchestre le calcul de l'impôt sur le revenu
 * en utilisant les différentes classes spécialisées.
 */
public class SimulateurReusine {

    private static final int NB_ENFANTS_MAX = 7;

    /**
     * Calcule l'impôt final pour un foyer donné.
     */
    public static int calculerImpot(FoyerFiscal foyer) {
        verifierValeurs(foyer);

        double rfr = calculRevenuFiscalReference(foyer);
        double nbPartsDec = CalculateurPartsFiscales
                .calculerPartsDeclarants(foyer.getSituationFamiliale());
        double nbPartsTot = CalculateurPartsFiscales.calculerPartsFoyer(foyer);

        double impotSeuls = CalculateurBaremeProgressif
                .calculerImpotBrutFoyer(rfr, nbPartsDec);
        double impotFoyer = CalculateurBaremeProgressif
                .calculerImpotBrutFoyer(rfr, nbPartsTot);

        double impotPlafonne = CalculateurPlafonnementQuotientFamilial
                .appliquerPlafonnement(impotSeuls, impotFoyer, nbPartsDec, nbPartsTot);

        double decote = CalculateurDecote.calculerDecote(impotPlafonne, nbPartsDec);
        double impotApresDecote = impotPlafonne - decote;

        double cehr = CalculateurContributionExceptionnelle
                .calculerContribution(rfr, nbPartsDec);

        return (int) Math.round(impotApresDecote + cehr);
    }

    private static double calculRevenuFiscalReference(FoyerFiscal foyer) {
        double abattement = CalculateurAbattement.calculerAbattementTotal(foyer);
        double revenu = foyer.getRevenuNetDeclarant1() + foyer.getRevenuNetDeclarant2();
        return Math.max(0, revenu - abattement);
    }

    private static void verifierValeurs(FoyerFiscal foyer) {
        if (foyer.getRevenuNetDeclarant1() < 0 || foyer.getRevenuNetDeclarant2() < 0) {
            throw new IllegalArgumentException("Le revenu net ne peut pas être négatif.");
        }
        if (foyer.getNombreEnfants() < 0 || foyer.getNombreEnfants() > NB_ENFANTS_MAX) {
            throw new IllegalArgumentException("Nombre d'enfants invalide.");
        }
        if (foyer.getNombreEnfantsHandicapes() < 0) {
            throw new IllegalArgumentException("Nombre d'enfants handicapés invalide.");
        }
        if (foyer.getNombreEnfantsHandicapes() > foyer.getNombreEnfants()) {
            throw new IllegalArgumentException(
                "Le nombre d'enfants handicapés ne peut pas dépasser le total d'enfants.");
        }
        if (foyer.getSituationFamiliale() == null) {
            throw new IllegalArgumentException("Situation familiale manquante.");
        }
        if (foyer.isParentIsole() && 
            (foyer.getSituationFamiliale() == SituationFamiliale.MARIE ||
             foyer.getSituationFamiliale() == SituationFamiliale.PACSE)) {
            throw new IllegalArgumentException("Un parent isolé ne peut être marié/pacsé.");
        }

        boolean estSeul = foyer.getSituationFamiliale() == SituationFamiliale.CELIBATAIRE ||
                          foyer.getSituationFamiliale() == SituationFamiliale.DIVORCE ||
                          foyer.getSituationFamiliale() == SituationFamiliale.VEUF;

        if (estSeul && foyer.getRevenuNetDeclarant2() > 0) {
            throw new IllegalArgumentException(
                "Déclarant 2 ne doit pas avoir de revenus si un seul déclarant.");
        }
    }
}
