package com.example.jtrash.Model;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class GestoreProfili {
    private static String DIRECTORY_PROFILI = "C:\\Users\\barba\\OneDrive\\Documents\\uni\\metodologie programmazione\\JTrash\\src\\main\\java\\com\\example\\jtrash\\Model\\profili";
    private Map<String, ProfiloGiocatore> profili;

    /**
     * Costruttore della classe GestoreProfili
     */
    public GestoreProfili() {
        profili = new HashMap<>();
    }

    /**
     * Getter
     */
    public ProfiloGiocatore getProfilo(String username) {
        return profili.get(username);
    }

    /**
     * Metodo che permette l'aggiunta di un profilo alla Map di profili
     */
    public void aggiungiProfilo(ProfiloGiocatore profilo) {
        profili.put(profilo.getUsername(), profilo);
        salvaProfili();
    }

    /**
     * Metodo che permette di caricare il profilo dalla Map di profili
     */
    public void caricaProfiliDaFile(String nomeProfilo) {
        File directory = new File(DIRECTORY_PROFILI);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith(nomeProfilo)) {
                    // Leggi il profilo da file e aggiungilo alla mappa dei profili
                    ProfiloGiocatore profiloGiocatore = leggiProfiloDaFile(file.getAbsolutePath());
                    System.out.println("Percorso del file: " + file.getAbsolutePath());

                    if (profiloGiocatore != null) {
                        profili.put(profiloGiocatore.getUsername(), profiloGiocatore);
                    }
                }
            }
        }
    }

    /**
     * Metodo non usato
     */
    private void caricaProfiliDaDirectory() {
        try {
            Files.walkFileTree(Path.of(DIRECTORY_PROFILI), EnumSet.noneOf(FileVisitOption.class), 1, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (file.getFileName().toString().toLowerCase().endsWith(".txt")) {
                        ProfiloGiocatore profiloGiocatore = leggiProfiloDaFile(String.valueOf(file));
                        if (profiloGiocatore != null) {
                            profili.put(profiloGiocatore.getUsername(), profiloGiocatore);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che legge il file di un profilo utilizzando la deserializzazione
     */
    private ProfiloGiocatore leggiProfiloDaFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (ProfiloGiocatore) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo che permette di salvare il profilo del giocatore
     */
    private void salvaProfili() {
        for (ProfiloGiocatore profilo : profili.values()) {
            String filePath = String.valueOf(Path.of(DIRECTORY_PROFILI));
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(profilo);
                System.out.println("Profilo utente salvato correttamente: " + profilo.getUsername());
            } catch (IOException e) {
                System.err.println("Errore durante il salvataggio di un profilo utente: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo che restituisce true se un profilo è già esistente, altrimenti false
     */
    public boolean profiloEsiste(String username) {
        return profili.containsKey(username);
    }

    /**
     * Getter
     */
    public static String getDirectoryProfili() {
        return DIRECTORY_PROFILI;
    }

    /**
     * Getter
     */
    public Map<String, ProfiloGiocatore> getProfili() {
        return profili;
    }

    /**
     * Setter
     */
    public void setProfili(Map<String, ProfiloGiocatore> profili) {
        this.profili = profili;
    }
}

