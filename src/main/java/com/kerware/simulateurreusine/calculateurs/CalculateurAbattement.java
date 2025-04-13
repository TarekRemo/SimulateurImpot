package com.kerware.simulateurreusine.calculateurs;

import com.kerware.ParametresImposition;
import com.kerware.SituationFamiliale;
import com.kerware.simulateurreusine.FoyerFiscal;

/**
 * Permet de calculer l'abbatement
 */
public class CalculateurAbattement {
	
	/**
	 * EXG_IMPOT_02 : Calcule de l'abattement 
	 */
    public static double calculerAbattementTotal(FoyerFiscal foyer) {
        // Abattement pour déclarant 1
        double abattement1 = foyer.getRevenuNetDeclarant1() * ParametresImposition.TAUX_ABATTEMENT;
        abattement1 = Math.min(abattement1, ParametresImposition.ABATTEMENT_MAX);
        abattement1 = Math.max(abattement1, ParametresImposition.ABATTEMENT_MIN);

        // Abattement pour déclarant 2 (si la situation s'y prête)
        double abattement2 = 0;
        if (foyer.getSituationFamiliale() == SituationFamiliale.MARIE 
             || foyer.getSituationFamiliale() == SituationFamiliale.PACSE) {
            
            abattement2 = foyer.getRevenuNetDeclarant2() * ParametresImposition.TAUX_ABATTEMENT;
            abattement2 = Math.min(abattement2, ParametresImposition.ABATTEMENT_MAX);
            abattement2 = Math.max(abattement2, ParametresImposition.ABATTEMENT_MIN);
        }

        return abattement1 + abattement2;
    }
}

