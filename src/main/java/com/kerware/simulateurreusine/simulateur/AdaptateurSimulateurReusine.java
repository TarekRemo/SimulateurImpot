package com.kerware.simulateurreusine.simulateur;

import com.kerware.ICalculateurImpot;
import com.kerware.SituationFamiliale;
import com.kerware.simulateurreusine.FoyerFiscal;
import com.kerware.simulateurreusine.calculateurs.CalculateurAbattement;
import com.kerware.simulateurreusine.calculateurs.CalculateurBaremeProgressif;
import com.kerware.simulateurreusine.calculateurs.CalculateurContributionExceptionnelle;
import com.kerware.simulateurreusine.calculateurs.CalculateurDecote;
import com.kerware.simulateurreusine.calculateurs.CalculateurPartsFiscales;
import com.kerware.simulateurreusine.calculateurs.CalculateurPlafonnementQuotientFamilial;

public class AdaptateurSimulateurReusine implements ICalculateurImpot{
	
	FoyerFiscal foyer; 
	
	public AdaptateurSimulateurReusine() {
		foyer = new FoyerFiscal();
	}
	
	@Override
	public void setRevenusNetDeclarant1(int rn) {
		foyer.setRevenuNetDeclarant1(rn);
	}

	@Override
	public void setRevenusNetDeclarant2(int rn) {
		foyer.setRevenuNetDeclarant2(rn);
	}

	@Override
	public void setSituationFamiliale(SituationFamiliale sf) {
		foyer.setSituationFamiliale(sf);
	}

	@Override
	public void setNbEnfantsACharge(int nbe) {
		foyer.setNombreEnfants(nbe);
	}

	@Override
	public void setNbEnfantsSituationHandicap(int nbesh) {
		foyer.setNombreEnfantsHandicapes(nbesh);
	}

	@Override
	public void setParentIsole(boolean pi) {
		foyer.setParentIsole(pi);
	}

	@Override
	public void calculImpotSurRevenuNet() {
		SimulateurReusine.calculerImpot(foyer); 
	}

	@Override
	public int getRevenuNetDeclatant1() {
		return foyer.getRevenuNetDeclarant1(); 
	}

	@Override
	public int getRevenuNetDeclatant2() {
		return foyer.getRevenuNetDeclarant2(); 
	}

	@Override
	public double getContribExceptionnelle() {
        return CalculateurContributionExceptionnelle
                .calculerContribution(getRevenuFiscalReference(), getPartsDeclarants());
	}

	@Override
	public int getRevenuFiscalReference() {
        
        double revenuFiscalRef = (foyer.getRevenuNetDeclarant1() 
                                  + foyer.getRevenuNetDeclarant2()) 
                                  - getAbattement();
        if (revenuFiscalRef < 0) {
            revenuFiscalRef = 0;
        }
        return (int)Math.round(revenuFiscalRef); 
	}

	@Override
	public int getAbattement() {
		return (int)Math.round(CalculateurAbattement.calculerAbattementTotal(foyer));
	}

	@Override
	public double getNbPartsFoyerFiscal() {
		return CalculateurPartsFiscales.calculerPartsFoyer(foyer);
	}
	
	public double getPartsDeclarants() {
        return CalculateurPartsFiscales
                .calculerPartsDeclarants(foyer.getSituationFamiliale());
	}

	@Override
	public int getImpotAvantDecote() {
        double impotDeclarantsSeuls = CalculateurBaremeProgressif
                .calculerImpotBrutFoyer(getRevenuFiscalReference(), 
                							getPartsDeclarants());
		
		double impotBrutFoyer = CalculateurBaremeProgressif
		          .calculerImpotBrutFoyer(getRevenuFiscalReference(), 
		        		  					getNbPartsFoyerFiscal());
		
		// 6) Plafonnement du quotient familial (EXG_IMPOT_05)
		double impotApresPlafond = CalculateurPlafonnementQuotientFamilial
		.appliquerPlafonnement(impotDeclarantsSeuls, 
		               impotBrutFoyer,
		               getPartsDeclarants(), 
		               getNbPartsFoyerFiscal());
		
		return (int)Math.round(impotApresPlafond);
	}

	@Override
	public int getDecote() {
        return (int)Math.round(
        		CalculateurDecote.calculerDecote(getImpotAvantDecote(), getImpotAvantDecote())
        		);
	}

	@Override
	public int getImpotSurRevenuNet() {
		return SimulateurReusine.calculerImpot(foyer); 
	}

}
