package com.kerware.simulateurreusine.simulateur;

import com.kerware.SituationFamiliale;
import com.kerware.simulateurreusine.FoyerFiscal;
import com.kerware.simulateurreusine.calculateurs.CalculateurAbattement;
import com.kerware.simulateurreusine.calculateurs.CalculateurBaremeProgressif;
import com.kerware.simulateurreusine.calculateurs.CalculateurContributionExceptionnelle;
import com.kerware.simulateurreusine.calculateurs.CalculateurDecote;
import com.kerware.simulateurreusine.calculateurs.CalculateurPartsFiscales;
import com.kerware.simulateurreusine.calculateurs.CalculateurPlafonnementQuotientFamilial;

/**
 * Classe principale qui orchestre le calcul de l'impôt sur le revenu en utilisant les différentes
 * classes spécialisées.
 */
public class SimulateurReusine {

    /**
     * Calcule l'impôt final pour un foyer donné, selon les paramètres
     * d'une année (2024, 2025, etc.).
     */
    public static int calculerImpot(FoyerFiscal foyer) {
    	
    	verifierValeurs(foyer); 

        // 1) Calcul de l'abattement total (EXG_IMPOT_02)
        double abattementTotal = CalculateurAbattement.calculerAbattementTotal(foyer);

        // 2) Calcul du revenu fiscal de référence
        double revenuFiscalRef = (foyer.getRevenuNetDeclarant1() 
                                  + foyer.getRevenuNetDeclarant2()) 
                                  - abattementTotal;
        if (revenuFiscalRef < 0) {
            revenuFiscalRef = 0;
        }

        // 3) Calcul du nombre de parts déclarants (1 ou 2) 
        //    et du nombre de parts total du foyer (EXG_IMPOT_03)
        double nbPartsDeclarants = CalculateurPartsFiscales
                                       .calculerPartsDeclarants(foyer.getSituationFamiliale());
        double nbPartsFoyer = CalculateurPartsFiscales.calculerPartsFoyer(foyer);

        // 4) Impôt brut si on ne tenait compte QUE des déclarants (sans enfants),
        //    pour déterminer le plafond de baisse (EXG_IMPOT_05).
        double impotDeclarantsAlone = CalculateurBaremeProgressif
                                        .calculerImpotBrutFoyer(revenuFiscalRef, 
                                                                 nbPartsDeclarants);

        // 5) Impôt brut du foyer complet (avec enfants) 
        //    (EXG_IMPOT_04, barème progressif)
        double impotBrutFoyer = CalculateurBaremeProgressif
                                  .calculerImpotBrutFoyer(revenuFiscalRef, 
                                                           nbPartsFoyer);

        // 6) Plafonnement du quotient familial (EXG_IMPOT_05)
        double impotApresPlafond = CalculateurPlafonnementQuotientFamilial
                .appliquerPlafonnement(impotDeclarantsAlone, 
                                       impotBrutFoyer,
                                       nbPartsDeclarants, 
                                       nbPartsFoyer);

        // 7) Calcul de la décote (EXG_IMPOT_06)
        double decote = CalculateurDecote
                .calculerDecote(impotApresPlafond, nbPartsDeclarants);

        double impotApresDecote = impotApresPlafond - decote;

        // 8) Contribution exceptionnelle sur les hauts revenus (EXG_IMPOT_07)
        double cehr = CalculateurContributionExceptionnelle
                .calculerContribution(revenuFiscalRef, nbPartsDeclarants);

        // 9) Impôt final = (impôt après décote) + CEHR
        double impotFinal = impotApresDecote + cehr;

        // EXG_IMPOT_01 : Arrondi à l'euro
        int impotArrondi = (int)Math.round(impotFinal);

        return impotArrondi;
    }

	private static void verifierValeurs(FoyerFiscal foyer) {
        // Contrôles de validité basiques (exemple)
        if (foyer.getRevenuNetDeclarant1() < 0 || foyer.getRevenuNetDeclarant2() < 0) {
            throw new IllegalArgumentException("Le revenu net ne peut pas être négatif.");
        }
        if (foyer.getNombreEnfants() < 0 || foyer.getNombreEnfants() > 7) {
            throw new IllegalArgumentException("Le nombre d'enfants doit être compris entre 0 et 7.");
        }
        if (foyer.getNombreEnfantsHandicapes() < 0) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas être négatif.");
        }
        if (foyer.getNombreEnfantsHandicapes() > foyer.getNombreEnfants()) {
            throw new IllegalArgumentException("Le nombre d'enfants handicapés ne peut pas dépasser le nombre total d'enfants.");
        }
        if (foyer.getSituationFamiliale() == null) {
            throw new IllegalArgumentException("La situation familiale ne peut pas être nulle.");
        }
        if (foyer.isParentIsole() && (foyer.getSituationFamiliale() == SituationFamiliale.MARIE 
        		|| foyer.getSituationFamiliale() == SituationFamiliale.PACSE)) {
            throw new IllegalArgumentException("Un parent isolé ne peut pas être marié ou pacsé.");
        }

        // Contrôle pour CELIBATAIRE / DIVORCE / VEUF : pas de 2ème déclarant
        boolean estSeul = (foyer.getSituationFamiliale() == SituationFamiliale.CELIBATAIRE 
                            || foyer.getSituationFamiliale() == SituationFamiliale.DIVORCE 
                            || foyer.getSituationFamiliale() == SituationFamiliale.VEUF);
        if (estSeul && foyer.getRevenuNetDeclarant2() > 0) {
            throw new IllegalArgumentException("Déclarant 2 ne doit pas avoir de revenus si la situation familiale indique un déclarant unique.");
        }
	}
}

