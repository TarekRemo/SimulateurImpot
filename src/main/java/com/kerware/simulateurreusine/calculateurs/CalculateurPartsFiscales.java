package com.kerware.simulateurreusine.calculateurs;

import com.kerware.SituationFamiliale;
import com.kerware.simulateurreusine.FoyerFiscal;

/**
 * EXG_IMPOT_03 : Calcule le nombre de parts fiscales du foyer.
 */
public class CalculateurPartsFiscales {

    private static final double PART_CELIBATAIRE = 1.0;
    private static final double PART_MARIE_PACSE = 2.0;
    private static final double PART_ENFANT = 0.5;
    private static final double MAJORATION_PARENT_ISOLE = 0.5;
    private static final double MAJORATION_VEUF = 1.0;
    private static final double MAJORATION_ENFANT_HANDICAPE = 0.5;

    /**
     * Retourne le nombre de parts "de base" correspondant aux déclarants.
     */
    public static double calculerPartsDeclarants(SituationFamiliale situation) {
        switch (situation) {
            case CELIBATAIRE:
            case DIVORCE:
            case VEUF:
                return PART_CELIBATAIRE;
            case MARIE:
            case PACSE:
                return PART_MARIE_PACSE;
            default:
                return PART_CELIBATAIRE;
        }
    }

    /**
     * Calcule le nombre de parts total (déclarants + enfants + éventuelles majorations).
     */
    public static double calculerPartsFoyer(FoyerFiscal foyer) {
        double partsBase = calculerPartsDeclarants(foyer.getSituationFamiliale());
        int nbEnfants = foyer.getNombreEnfants();
        int nbEnfHand = foyer.getNombreEnfantsHandicapes();

        // 0,5 part pour les 2 premiers enfants, 1 part à partir du 3e
        double partsEnfants;
        if (nbEnfants <= 2) {
            partsEnfants = nbEnfants * PART_ENFANT;
        } else {
            partsEnfants = PART_ENFANT * 2 + (nbEnfants - 2);
        }

        double majorationParentIsole = 
            (foyer.isParentIsole() && nbEnfants > 0) ? MAJORATION_PARENT_ISOLE : 0.0;

        double majorationVeuf =
            (foyer.getSituationFamiliale() == SituationFamiliale.VEUF && nbEnfants > 0)
            ? MAJORATION_VEUF : 0.0;

        double majorationEnfHand = nbEnfHand * MAJORATION_ENFANT_HANDICAPE;

        return partsBase + partsEnfants + majorationParentIsole
                + majorationVeuf + majorationEnfHand;
    }
}
