package com.example.jtrash.View;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GestoreAudio {

    // Un campo privato che memorizza una lista di clip audio
    private List<Clip> clips;

    /**
     * Costruttore della classe GestoreAudio
     */
    public GestoreAudio(List<String> fileNames) {
        // nuova lista vuota di clip
        clips = new ArrayList<>();
        for (String fileName : fileNames) {
            try {
                // Apri il file audio come una risorsa audio
                InputStream audioInputStream = getClass().getResourceAsStream(fileName);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(audioInputStream);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedInputStream);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clips.add(clip);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Metodo che permette di avviare la riproduzione di una traccia presente nella lista
     */
    public void play(int index) {
        Clip clip = clips.get(index);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }

    }

    /**
     * Metodo che permette di fermare la riproduzione di una traccia presente nella lista
     */
    public void stop(int index) {
        Clip clip = clips.get(index);
        if (clip != null) {
            clip.stop();
        }
    }



    /**
     * Metodo che permette di avviare la riproduzione in loop di una traccia presente nella lista
     */
    public void loop(int index) {
        if (index >= 0 && index < clips.size()) {
            Clip clip = clips.get(index);
            if (clip != null) {
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    /**
     * Metodo che permette di regolare il volume di una traccia presente nella lista
     */
    public void setVolume(float volume, int index) {
        Clip clip = clips.get(index);
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);
        }
    }



}



