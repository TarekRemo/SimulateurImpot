package com.kerware.simulateurreusine.calculateurs;

import com.kerware.SituationFamiliale;
import com.kerware.simulateurreusine.FoyerFiscal;

/**
 * EXG_IMPOT_03 : Calcule le nombre de parts fiscales du foyer.
 */
public class CalculateurPartsFiscales {

    /**
     * Retourne le nombre de parts "de base" correspondant aux déclarants.
     */
    public static double calculerPartsDeclarants(SituationFamiliale situation) {
        switch (situation) {
            case CELIBATAIRE:
            	return 1.0;
            case DIVORCE:
            	return 1.0;
            case VEUF:
                return 1.0;
            case MARIE:
                return 2.0;
            case PACSE:
                return 2.0;
        }
        return 1.0; 
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
            partsEnfants = nbEnfants * 0.5;
        } else {
            // 2 premiers enfants => 2 * 0.5 = 1 part
            // à partir du 3ème => (nbEnfants - 2) parts
            partsEnfants = 1.0 + (nbEnfants - 2);
        }

        // Majoration si parent isolé et au moins 1 enfant
        double majorationParentIsole = 0.0;
        if (foyer.isParentIsole() && nbEnfants > 0) {
            majorationParentIsole = 0.5; 
        }

        // Si veuf(ve) avec enfants à charge, on conserve la part du conjoint décédé
        double majorationVeuf = 0.0;
        if (foyer.getSituationFamiliale() == SituationFamiliale.VEUF && nbEnfants > 0) {
            majorationVeuf = 1.0;
        }

        // 0.5 part supplémentaire par enfant handicapé
        double majorationEnfHand = nbEnfHand * 0.5;

        return partsBase + partsEnfants + majorationParentIsole + majorationVeuf + majorationEnfHand;
    }
}

