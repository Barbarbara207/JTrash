package com.example.jtrash.Model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ProfiloGiocatore implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String avatar;
    private Map<String, Integer> statistiche;

    /**
     * Costruttore della classe ProfiloGiocatore
     */
    public ProfiloGiocatore(String username, String password, String avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.statistiche = new HashMap<>();
        inizializzaStatistiche();
    }

    /**
     * Metodo che inizializza in una Map di Stringhe e Interi le varie
     * statistiche che caratterizzano il profilo di un giocatore
     */
    private void inizializzaStatistiche() {
        statistiche.put("partiteGiocate", 0);
        statistiche.put("vittorie", 0);
        statistiche.put("sconfitte", 0);
        statistiche.put("tempoDiGioco", 0);
        statistiche.put("livello", 0);
    }

    /**
     * Metodo che incrementa di 1 la statistica delle partite giocate del profilo del giocatore
     */
    public void incrementaPartiteGiocate() {
        statistiche.put("partiteGiocate", getPartiteGiocate() + 1);
    }

    /**
     * Metodo che incrementa di 1 la statistica delle partite vinte del profilo del giocatore
     */
    public void incrementaVittorie() {
        statistiche.put("vittorie", getVittorie() + 1);
    }

    /**
     * Metodo che incrementa di 1 la statistica delle partite perse del profilo del giocatore
     */
    public void incrementaSconfitte() {
        statistiche.put("sconfitte", getSconfitte() + 1);
    }

    /**
     * Metodo non utilizzato
     */
    public void incrementaLivello() {
        statistiche.put("livello", getLivello() + 1);
    }

    /**
     * Metodo non utilizzato
     */
    public void salvaStatistiche() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(username + "_statistiche.txt"))) {
            oos.writeObject(this);
            System.out.println("Statistiche salvate correttamente.");
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio delle statistiche: " + e.getMessage());
        }
    }

    /**
     * Metodo non utilizzato
     */
    public static ProfiloGiocatore caricaStatistiche(String nomeGiocatore) {
        ProfiloGiocatore giocatoreStatistiche = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeGiocatore + "_statistiche.txt"))) {
            giocatoreStatistiche = (ProfiloGiocatore) ois.readObject();
            System.out.println("Statistiche caricate correttamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante il caricamento delle statistiche: " + e.getMessage());
        }
        return giocatoreStatistiche;
    }

    /**
     * Setter
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter
     */
    public int getPartiteGiocate() {
        return statistiche.get("partiteGiocate");
    }

    /**
     * Getter
     */
    public int getVittorie() {
        return statistiche.get("vittorie");
    }

    /**
     * Setter
     */
    public void setVittorie(int vittorie) {
        statistiche.put("vittorie", vittorie);
    }

    /**
     * Getter
     */
    public int getSconfitte() {
        return statistiche.get("sconfitte");
    }

    /**
     * Getter
     */
    public int getTempoDiGioco() {
        return statistiche.get("tempoDiGioco");
    }

    /**
     * Setter
     */
    public void setTempoDiGioco(int tempo) {
        int tempoDiGioco = getTempoDiGioco()+tempo;
        statistiche.remove("tempoDiGioco");
        statistiche.put("tempoDiGioco", tempoDiGioco);
    }

    /**
     * Getter
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Setter
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Getter
     */
    public int getLivello() {
        return statistiche.get("livello");
    }

    /**
     * Setter
     */
    public void setLivello(int livello) {
        statistiche.put("livello", livello);
    }
}

