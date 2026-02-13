package com.example.jtrash.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardModel {
    private String valore;
    private String segno;
    private boolean scoperta;


    /**
     * Costruttore della classe CardModel
     */
    public CardModel(String valore, String segno, boolean scoperta) {
        this.valore = valore;
        this.segno = segno;
        this.scoperta = false;
    }

    /**
     * Metodo che restituisce un Array di Stringhe con valore e segno di ogni carta, permettendo alla View di rappresentarla correttamente
     */
    public String[] listaCarta() {
        ArrayList<String> stringCarta = new ArrayList<>();
        stringCarta.add(0, "Carte");
        stringCarta.add(valore + " of " + segno);
        return stringCarta.stream().toArray(String[] :: new);
    }

    /**
     * Getter
     */
    public String getValore() {
        return valore;
    }

    /**
     * Setter
     */
    public void setValore(String valore) {
        this.valore = valore;
    }

    /**
     * Getter
     */
    public String getSegno() {
        return segno;
    }

    /**
     * Setter
     */
    public void setSegno(String segno) {
        this.segno = segno;
    }

    /**
     * Metodo che restituisce true se la carta è scoperta, altrimenti false
     */
    public boolean isScoperta() {
        return scoperta;
    }

    /**
     * Metodo che, data una Map di Stringhe, consente di ottenere una rappresentazione numerica di un valore espresso in lettere
     */
    public int enumToInt(String valoreEnum) {
        Map<String, String> enumIntero = new HashMap<>();
        enumIntero.put("ACE", "1");
        enumIntero.put("TWO", "2");
        enumIntero.put("THREE", "3");
        enumIntero.put("FOUR", "4");
        enumIntero.put("FIVE", "5");
        enumIntero.put("SIX", "6");
        enumIntero.put("SEVEN", "7");
        enumIntero.put("EIGHT", "8");
        enumIntero.put("NINE", "9");
        enumIntero.put("TEN", "10");
        enumIntero.put("ELEVEN", "11");
        enumIntero.put("TWELVE", "12");
        enumIntero.put("THIRTEEN", "13");
        enumIntero.put("FOURTEEN", "14");

        return Integer.parseInt(enumIntero.get(valoreEnum));
    }

    /**
     * Setter
     */
    public void setScoperta(boolean scoperta) {
        this.scoperta = scoperta;
    }
}
