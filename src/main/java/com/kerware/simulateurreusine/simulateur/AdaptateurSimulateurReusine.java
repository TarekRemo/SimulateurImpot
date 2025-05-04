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

/**
 * Adaptateur entre le simulateur Reusine et l'interface ICalculateurImpot.
 */
public final class AdaptateurSimulateurReusine implements ICalculateurImpot {

    private FoyerFiscal foyer;

    public AdaptateurSimulateurReusine() {
        this.foyer = new FoyerFiscal();
    }

    /** Définit le revenu net du déclarant 1. */
    @Override
    public void setRevenusNetDeclarant1(int rn) {
        foyer.setRevenuNetDeclarant1(rn);
    }

    /** Définit le revenu net du déclarant 2. */
    @Override
    public void setRevenusNetDeclarant2(int rn) {
        foyer.setRevenuNetDeclarant2(rn);
    }

    /** Définit la situation familiale du foyer. */
    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        foyer.setSituationFamiliale(sf);
    }

    /** Définit le nombre d'enfants à charge. */
    @Override
    public void setNbEnfantsACharge(int nbe) {
        foyer.setNombreEnfants(nbe);
    }

    /** Définit le nombre d'enfants en situation de handicap. */
    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        foyer.setNombreEnfantsHandicapes(nbesh);
    }

    /** Indique si le déclarant est parent isolé. */
    @Override
    public void setParentIsole(boolean pi) {
        foyer.setParentIsole(pi);
    }

    /** Lance le calcul de l'impôt. */
    @Override
    public void calculImpotSurRevenuNet() {
        SimulateurReusine.calculerImpot(foyer);
    }

    /** Retourne le revenu net du déclarant 1. */
    @Override
    public int getRevenuNetDeclatant1() {
        return foyer.getRevenuNetDeclarant1();
    }

    /** Retourne le revenu net du déclarant 2. */
    @Override
    public int getRevenuNetDeclatant2() {
        return foyer.getRevenuNetDeclarant2();
    }

    /** Retourne le montant de la contribution exceptionnelle sur les hauts revenus. */
    @Override
    public double getContribExceptionnelle() {
        return CalculateurContributionExceptionnelle.calculerContribution(
                getRevenuFiscalReference(), getPartsDeclarants());
    }

    /** Calcule le revenu fiscal de référence. */
    @Override
    public int getRevenuFiscalReference() {
        double rfr = foyer.getRevenuNetDeclarant1() + foyer.getRevenuNetDeclarant2()
                   - getAbattement();
        return (int) Math.max(0, Math.round(rfr));
    }

    /** Retourne le montant total de l’abattement. */
    @Override
    public int getAbattement() {
        return (int) Math.round(CalculateurAbattement.calculerAbattementTotal(foyer));
    }

    /** Retourne le nombre total de parts fiscales du foyer. */
    @Override
    public double getNbPartsFoyerFiscal() {
        return CalculateurPartsFiscales.calculerPartsFoyer(foyer);
    }

    /** Retourne le nombre de parts liées aux seuls déclarants. */
    public double getPartsDeclarants() {
        return CalculateurPartsFiscales.calculerPartsDeclarants(foyer.getSituationFamiliale());
    }

    /** Calcule l'impôt avant décote avec plafonnement du quotient familial. */
    @Override
    public int getImpotAvantDecote() {
        double impotSeuls = CalculateurBaremeProgressif.calculerImpotBrutFoyer(
                getRevenuFiscalReference(), getPartsDeclarants());

        double impotTotal = CalculateurBaremeProgressif.calculerImpotBrutFoyer(
                getRevenuFiscalReference(), getNbPartsFoyerFiscal());

        double impotPlafonne = CalculateurPlafonnementQuotientFamilial.appliquerPlafonnement(
                impotSeuls, impotTotal, getPartsDeclarants(), getNbPartsFoyerFiscal());

        return (int) Math.round(impotPlafonne);
    }

    /** Calcule la décote. */
    @Override
    public int getDecote() {
        return (int) Math.round(CalculateurDecote.calculerDecote(
                getImpotAvantDecote(), getImpotAvantDecote()));
    }

    /** Calcule l’impôt net à payer. */
    @Override
    public int getImpotSurRevenuNet() {
        return SimulateurReusine.calculerImpot(foyer);
    }

    /** Accès (optionnel) au foyer pour debug ou test. */
    public FoyerFiscal getFoyer() {
        return foyer;
    }
}
