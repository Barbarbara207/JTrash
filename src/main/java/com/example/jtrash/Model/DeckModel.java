package com.example.jtrash.Model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeckModel {
    private ArrayList<CardModel> mazzo;

    /**
     * Costruttore della classe DeckModel
     */
    public DeckModel(ArrayList<CardModel> carte) {
        this.mazzo = carte;
    }

    /**
     * Metodo che restituisce un Array di Stringhe con valore e segno di ogni carta del mazzo, permettendo alla View di rappresentarla correttamente
     */
    public String[] listaMazzo() {
        ArrayList<String> stringMazzo = new ArrayList<>();
        for (CardModel carta : mazzo) {
            stringMazzo.add(carta.getValore() + " of " + carta.getSegno());
        }
        stringMazzo.add(0, "Mazzo");

        return stringMazzo.stream().toArray(String[] :: new);
    }

    /**
     * Getter
     */
    public ArrayList<CardModel> getMazzo() {
        return mazzo;
    }

    /**
     * Setter
     */
    public void setMazzo(ArrayList<CardModel> mazzo) {
        this.mazzo = mazzo;
    }
}
