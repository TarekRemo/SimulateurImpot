package com.kerware.simulateurreusine;

import com.kerware.SituationFamiliale;

/**
 * Permet de représenter un foyer fiscal.
 */
public final class FoyerFiscal {

    private int revenuNetDeclarant1;
    private int revenuNetDeclarant2;
    private SituationFamiliale situationFamiliale;
    private int nombreEnfants;
    private int nombreEnfantsHandicapes;
    private boolean parentIsole;

    /**
     * Constructeur permettant d'initialiser le foyer avec des valeurs par défauts.
     */
    public FoyerFiscal() {
        this.revenuNetDeclarant1 = 0;
        this.revenuNetDeclarant2 = 0;
        this.situationFamiliale = SituationFamiliale.CELIBATAIRE;
        this.nombreEnfants = 0;
        this.nombreEnfantsHandicapes = 0;
        this.parentIsole = false;
    }

    /**
     * @return le revenu net du déclarant 1
     */
    public int getRevenuNetDeclarant1() {
        return revenuNetDeclarant1;
    }

    /**
     * Définit le revenu net du déclarant 1.
     * @param revenu le revenu à définir
     */
    public void setRevenuNetDeclarant1(int revenu) {
        this.revenuNetDeclarant1 = revenu;
    }

    /**
     * @return le revenu net du déclarant 2
     */
    public int getRevenuNetDeclarant2() {
        return revenuNetDeclarant2;
    }

    /**
     * Définit le revenu net du déclarant 2.
     * @param revenu le revenu à définir
     */
    public void setRevenuNetDeclarant2(int revenu) {
        this.revenuNetDeclarant2 = revenu;
    }

    /**
     * @return la situation familiale
     */
    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    /**
     * Définit la situation familiale du foyer.
     * @param situation la situation familiale à définir
     */
    public void setSituationFamiliale(SituationFamiliale situation) {
        this.situationFamiliale = situation;
    }

    /**
     * @return le nombre d'enfants à charge
     */
    public int getNombreEnfants() {
        return nombreEnfants;
    }

    /**
     * Définit le nombre d'enfants à charge.
     * @param nbEnfants le nombre d'enfants
     */
    public void setNombreEnfants(int nbEnfants) {
        this.nombreEnfants = nbEnfants;
    }

    /**
     * @return le nombre d'enfants handicapés
     */
    public int getNombreEnfantsHandicapes() {
        return nombreEnfantsHandicapes;
    }

    /**
     * Définit le nombre d'enfants handicapés à charge.
     * @param nb le nombre d'enfants handicapés
     */
    public void setNombreEnfantsHandicapes(int nb) {
        this.nombreEnfantsHandicapes = nb;
    }

    /**
     * @return true si le parent est isolé, false sinon
     */
    public boolean isParentIsole() {
        return parentIsole;
    }

    /**
     * Définit si le foyer est celui d’un parent isolé.
     * @param isole true si le parent est isolé
     */
    public void setParentIsole(boolean isole) {
        this.parentIsole = isole;
    }

    /**
     * Représentation textuelle du foyer fiscal.
     * @return une chaîne représentant les attributs du foyer
     */
    @Override
    public String toString() {
        return "FoyerFiscal{" +
                "revenuNetDeclarant1=" + revenuNetDeclarant1 +
                ", revenuNetDeclarant2=" + revenuNetDeclarant2 +
                ", situationFamiliale=" + situationFamiliale +
                ", nombreEnfants=" + nombreEnfants +
                ", nombreEnfantsHandicapes=" + nombreEnfantsHandicapes +
                ", parentIsole=" + parentIsole +
                '}';
    }
}
