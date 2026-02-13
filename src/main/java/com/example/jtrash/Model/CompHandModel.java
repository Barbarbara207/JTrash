package com.example.jtrash.Model;

import java.util.ArrayList;

public class CompHandModel {
    private ArrayList<CardModel> carte;
    private int quanteVittorie;

    private boolean haVinto;

    /**
     * Costruttore della classe CompHandModel
     */
    public CompHandModel(ArrayList<CardModel> carte, int quanteVittorie) {
        this.carte = carte;
        this.quanteVittorie = quanteVittorie;
        haVinto = false;
    }

    /**
     * Metodo che restituisce un Array di Stringhe con valore, segno e lo stato
     * (ovvero il valore booleano corrispondente a se è scoperta o meno) di ogni carta
     * della mano del computer, permettendo alla View di rappresentarla correttamente
     */
    public String[] listaManoComp() {
        ArrayList<String> stringManoComp = new ArrayList<>();
        for (CardModel carta : carte) {
            stringManoComp.add(carta.getValore() + " of " + carta.getSegno() + " of " + carta.isScoperta());
        }
        stringManoComp.add(0, "ManoComputer");

        return stringManoComp.stream().toArray(String[] :: new);
    }

    /**
     * Metodo che restituisce true se è presente nella mano almeno una carta coperta, altrimenti false
     */
    public boolean almenoUnaCartaCoperta() {
        for (CardModel carta : carte) {
            if (!carta.isScoperta()) {
                return true;  // Trovata almeno una carta coperta
            }
        }
        return false;  // Nessuna carta coperta trovata
    }

    /**
     * Getter
     */
    public ArrayList<CardModel> getCarte() {
        return carte;
    }

    /**
     * Setter
     */
    public void setCarte(ArrayList<CardModel> carte) {
        this.carte = carte;
    }

    /**
     * Getter
     */
    public int getQuanteVittorie() {
        return quanteVittorie;
    }

    /**
     * Setter
     */
    public void setQuanteVittorie(int quanteVittorie) {
        this.quanteVittorie = quanteVittorie;
    }

    /**
     * Metodo che restituisce true se il computer 3 è in stato di vittoria, altrimenti false
     */
    public boolean isHaVinto() {
        return haVinto;
    }

    /**
     * Setter
     */
    public void setHaVinto(boolean haVinto) {
        this.haVinto = haVinto;
    }
}
