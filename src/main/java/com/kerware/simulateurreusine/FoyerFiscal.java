package com.kerware.simulateurreusine;

import com.kerware.SituationFamiliale;

/**
 * Permet de représenter un foyer fiscal.
 */
public class FoyerFiscal {

    private int revenuNetDeclarant1;
    private int revenuNetDeclarant2;
    private SituationFamiliale situationFamiliale;
    private int nombreEnfants;
    private int nombreEnfantsHandicapes;
    private boolean parentIsole;
    
    /**
     * Constructeur permettant d'initialiser le foyer avec des valeurs par défauts
     */
    public FoyerFiscal() {
        this.revenuNetDeclarant1 = 0;
        this.revenuNetDeclarant2 = 0;
        this.situationFamiliale = SituationFamiliale.CELIBATAIRE;
        this.nombreEnfants = 0;
        this.nombreEnfantsHandicapes = 0;
        this.parentIsole = false;
    }

	public FoyerFiscal(int revenuNetDecl1,
                       int revenuNetDecl2,
                       SituationFamiliale situationFam,
                       int nbEnfants,
                       int nbEnfantsHandicapes,
                       boolean parentIsole) {

        this.revenuNetDeclarant1 = revenuNetDecl1;
        this.revenuNetDeclarant2 = revenuNetDecl2;
        this.situationFamiliale = situationFam;
        this.nombreEnfants = nbEnfants;
        this.nombreEnfantsHandicapes = nbEnfantsHandicapes;
        this.parentIsole = parentIsole;
    }

    public int getRevenuNetDeclarant1() {
        return revenuNetDeclarant1;
    }

    public int getRevenuNetDeclarant2() {
        return revenuNetDeclarant2;
    }

    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }

    public int getNombreEnfantsHandicapes() {
        return nombreEnfantsHandicapes;
    }

    public boolean isParentIsole() {
        return parentIsole;
    }
    
    public void setRevenuNetDeclarant1(int revenuNetDecl1) {
		this.revenuNetDeclarant1 = revenuNetDecl1;
	}

	public void setRevenuNetDeclarant2(int revenuNetDeclarant2) {
		this.revenuNetDeclarant2 = revenuNetDeclarant2;
	}

	public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
		this.situationFamiliale = situationFamiliale;
	}

	public void setNombreEnfants(int nbEnfants) {
		this.nombreEnfants = nbEnfants;
	}

	public void setNombreEnfantsHandicapes(int nbEnfantsHandicapes) {
		this.nombreEnfantsHandicapes = nbEnfantsHandicapes;
	}

	public void setParentIsole(boolean parentIsole) {
		this.parentIsole = parentIsole;
	}
}

