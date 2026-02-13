package com.example.jtrash.Model;

import java.util.ArrayList;

public class ScartiModel {
    private ArrayList<CardModel> carteScartate;

    /**
     * Costruttore della classe ScartiModel
     */
    public ScartiModel(ArrayList<CardModel> carteScartate) {
        this.carteScartate = carteScartate;
    }

    /**
     * Metodo che restituisce un Array di Stringhe con valore e segno di ogni carta del mazzo degli scarti, permettendo alla View di rappresentarla correttamente
     */
    public String[] listaScarti() {
        ArrayList<String> stringScarti = new ArrayList<>();
        stringScarti.add(0, "Scarti");
        if (carteScartate.isEmpty() == false) {
            stringScarti.add(carteScartate.get(0).getValore() + " of " + carteScartate.get(0).getSegno());
            return stringScarti.stream().toArray(String[] :: new);
        } else {
            return stringScarti.stream().toArray(String[]::new);
        }
    }

    /**
     * Getter
     */
    public ArrayList<CardModel> getCarteScartate() {
        return carteScartate;
    }

    /**
     * Setter
     */
    public void setCarteScartate(ArrayList<CardModel> carteScartate) {
            this.carteScartate = carteScartate;
        }

    /**
     * Metodo non usato
     */
    public void aggiungiCarta(CardModel carta){
        carteScartate.add(carta);
    }
}
