package com.example.jtrash.Model;

import java.util.ArrayList;

public class CompHand2Model {
    private ArrayList<CardModel> carte;
    private int quanteVittorie;

    private boolean haVinto;

    /**
     * Costruttore della classe CompHand2Model
     */
    public CompHand2Model(ArrayList<CardModel> carte, int quanteVittorie) {
        this.carte = carte;
        this.quanteVittorie = quanteVittorie;
        haVinto = false;
    }

    /**
     * Metodo che restituisce un Array di Stringhe con valore, segno e lo stato
     * (ovvero il valore booleano corrispondente a se è scoperta o meno) di ogni carta
     * della mano del computer 2, permettendo alla View di rappresentarla correttamente
     */
    public String[] listaManoComp2() {
        ArrayList<String> stringManoComp2 = new ArrayList<>();
        for (CardModel carta : carte) {
            stringManoComp2.add(carta.getValore() + " of " + carta.getSegno() + " of " + carta.isScoperta());
        }
        stringManoComp2.add(0, "ManoComputer2");

        return stringManoComp2.stream().toArray(String[] :: new);
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
     * Metodo che restituisce true se il computer 2 è in stato di vittoria, altrimenti false
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

