package com.example.jtrash.View;

import com.example.jtrash.Controller.EventController;
import com.example.jtrash.Main;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.animation.FadeTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class GestoreFinestreView implements Observer {

    private AnchorPane anchorPane;
    private EventController eventController;

    private ImageView cartaPescata;

    private ImageView pila;

    private boolean pausaAttiva = false;
    private int currentIndex = 0;
    private GestoreAudio gestoreAudio;

    /**
     * Costruttore della classe GestoreFinestreView
     */
    public GestoreFinestreView() {
        anchorPane = new AnchorPane();
        gestoreAudio = new GestoreAudio(Arrays.asList("/com/example/jtrash/Deepwoken-Talent-Card-Flip-Sound-Effect.wav",
                "/com/example/jtrash/downtown.wav",
                "/com/example/jtrash/hipjazz.wav",
                "/com/example/jtrash/Video-Game-Beep-Sound-Effect.wav"));
    }

    /**
     * Metodo che permette la creazione dell'AnchorPane menù
     */
    public AnchorPane menù(AnchorPane root) {
        root.getChildren().clear();
        AnchorPane menùPane = new AnchorPane();
        gestoreAudio.setVolume(-17.0f, 2);
        gestoreAudio.loop(2);

        Image sfondoImage = new Image(Main.class.getResourceAsStream("/com/example/jtrash/JTrash-schermata-iniziale.png"),
                root.getWidth(), root.getHeight(), false, true);
        // Crea un ImageView per l'immagine di sfondo
        ImageView sfondoImageView = new ImageView(sfondoImage);
        sfondoImageView.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        sfondoImageView.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        root.getChildren().add(sfondoImageView);


        ImageView titolo = new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/JTrash-titolo.png")));
        titolo.setFitHeight(150);
        titolo.setFitWidth(480);
        titolo.setScaleX(1.8);
        titolo.setScaleY(1.8);
        titolo.setLayoutX(1920/2 - titolo.getFitWidth()/2);
        titolo.setLayoutY(450/2 - titolo.getFitHeight()/2);
        root.getChildren().add(titolo);

        // Imposta la posizione iniziale sopra la finestra
        titolo.setTranslateY(-75);
        titolo.setOpacity(0.0); // Imposta l'opacità iniziale a 0

        // Crea una transizione di traslazione verso il basso con durata 1 secondo
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2.5), titolo);
        translateTransition.setToY(0);

        // Crea una transizione di fade-in con durata 1 secondo
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2.5), titolo);
        fadeTransition.setToValue(1.0); // Imposta il valore di opacità finale a 1

        // Crea una transizione parallela che esegue entrambe le transizioni contemporaneamente
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);

        parallelTransition.setOnFinished(event -> {
            // Questo codice verrà eseguito quando l'animazione è completata
            Button nuovaPartita = new Button("Nuova Partita");

            nuovaPartita.setOnAction(actionEvent -> {
                gestoreAudio.play(3);
                scegliModalità(anchorPane);
                System.out.println(eventController.username());
            });

            nuovaPartita.setPrefHeight(80);
            nuovaPartita.setPrefWidth(240);
            nuovaPartita.setLayoutX(1320/2-nuovaPartita.getPrefWidth()/2);
            nuovaPartita.setLayoutY(1080/2-nuovaPartita.getPrefHeight()/2);
            nuovaPartita.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
            nuovaPartita.setId("nuovaPartita");
            nuovaPartita.setDisable(true);
            root.getChildren().add(nuovaPartita);


            eventController.loggatoPerMenù();

            Button statistiche = new Button("Statistiche");
            statistiche.setOnAction(actionEvent -> {
                gestoreAudio.play(3);
                statisticheProfilo(anchorPane);
            });
            statistiche.setPrefHeight(80);
            statistiche.setPrefWidth(240);
            statistiche.setLayoutX(1920/2-statistiche.getPrefWidth()/2);
            statistiche.setLayoutY(1080/2-statistiche.getPrefHeight()/2);
            statistiche.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
            statistiche.setId("statsProfilo");
            if (eventController.loginFatto() == false) {
                statistiche.setDisable(true);
            }
            root.getChildren().add(statistiche);

            if (eventController.loginFatto() == false) {
                Button creaProfilo = new Button("Profilo");
                creaProfilo.setOnAction(actionEvent -> {
                    gestoreAudio.play(3);
                    finestraDiDialogoAccount();
                });
                creaProfilo.setPrefHeight(80);
                creaProfilo.setPrefWidth(240);
                creaProfilo.setLayoutX(1920 / 2 - creaProfilo.getPrefWidth() / 2);
                creaProfilo.setLayoutY(1080 / 2 - creaProfilo.getPrefHeight() / 2);
                creaProfilo.setFont(Font.font("Power Clear", FontWeight.BOLD, 35));
                creaProfilo.setId("profilo");
                root.getChildren().add(creaProfilo);
            }

            Button esci = new Button("Esci");
            esci.setOnAction(actionEvent -> {
                gestoreAudio.play(3);
                eventController.terminaEsecuzione();
            });
            esci.setPrefHeight(80);
            esci.setPrefWidth(240);
            esci.setLayoutX(2520/2-esci.getPrefWidth()/2);
            esci.setLayoutY(1080/2-esci.getPrefHeight()/2);
            esci.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
            root.getChildren().add(esci);
        });

        // Avvia la transizione parallela
        parallelTransition.play();


        return menùPane;
    }

    /**
     * Metodo che permette la creazione dell'AnchorPane statisticheProfilo
     */
    public void statisticheProfilo(AnchorPane root) {

        Rectangle sfondo = new Rectangle(1920, 1080);
        sfondo.setFill(Color.BLACK);
        sfondo.setOpacity(0.7);
        root.getChildren().add(sfondo);

        // aggiungi anche immagine profilo
        Label username = new Label("Username = " + eventController.username());
        username.setLayoutX(700);
        username.setLayoutY(400);
        username.setTextFill(Color.WHITE);
        username.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(username);

        Label partiteGiocate = new Label("Partite giocate = " + eventController.partiteGiocate());
        partiteGiocate.setLayoutX(700);
        partiteGiocate.setLayoutY(450);
        partiteGiocate.setTextFill(Color.WHITE);
        partiteGiocate.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(partiteGiocate);

        Label partiteVinte = new Label("Partite vinte = " + eventController.partiteVinte());
        partiteVinte.setLayoutX(700);
        partiteVinte.setLayoutY(500);
        partiteVinte.setTextFill(Color.WHITE);
        partiteVinte.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(partiteVinte);

        Label partitePerse = new Label("Partite perse = " + eventController.partitePerse());
        partitePerse.setLayoutX(700);
        partitePerse.setLayoutY(550);
        partitePerse.setTextFill(Color.WHITE);
        partitePerse.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(partitePerse);

        Label tempoDiGioco = new Label("Tempo di gioco = " + convertiMinutiInOre(convertiSecondiInMinuti(eventController.tempoDiGioco())) % 60 + ":" + convertiSecondiInMinuti(eventController.tempoDiGioco()) % 60 + ":" + eventController.tempoDiGioco() % 60);
        tempoDiGioco.setLayoutX(700);
        tempoDiGioco.setLayoutY(600);
        tempoDiGioco.setTextFill(Color.WHITE);
        tempoDiGioco.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(tempoDiGioco);

        Label livello = new Label("Livello = " + eventController.livello());
        livello.setLayoutX(700);
        livello.setLayoutY(650);
        livello.setTextFill(Color.WHITE);
        livello.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(livello);

        ImageView avatar = new ImageView(eventController.pathAvatar());
        avatar.setLayoutX(700);
        avatar.setLayoutY(55);
        avatar.setScaleX(0.3);
        avatar.setScaleY(0.3);
        root.getChildren().add(avatar);

        Button tornaAlMenù = new Button("Torna al Menù");
        tornaAlMenù.setOnAction(actionEvent -> {
            gestoreAudio.play(3);
            root.getChildren().remove(sfondo);
            root.getChildren().remove(tornaAlMenù);
            root.getChildren().remove(username);
            root.getChildren().remove(tempoDiGioco);
            root.getChildren().remove(avatar);
            root.getChildren().remove(partiteGiocate);
            root.getChildren().remove(partitePerse);
            root.getChildren().remove(partiteVinte);
            root.getChildren().remove(livello);
        });

        tornaAlMenù.setPrefHeight(80);
        tornaAlMenù.setPrefWidth(240);
        tornaAlMenù.setLayoutX(1920/2-tornaAlMenù.getPrefWidth()/2);
        tornaAlMenù.setLayoutY(1750/2-tornaAlMenù.getPrefHeight()/2 + tornaAlMenù.getPrefHeight() * 1.5);
        tornaAlMenù.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(tornaAlMenù);
    }

    /**
     * Metodo che permette la creazione dell'AnchorPane scegliModalità
     */
    public AnchorPane scegliModalità(AnchorPane root) {
        root.getChildren().clear();
        AnchorPane modalitàPane = new AnchorPane();

        Image sfondoImage = new Image(Main.class.getResourceAsStream("/com/example/jtrash/JTrash-schermata-iniziale.png"),
                root.getWidth(), root.getHeight(), false, true);
        // Crea un ImageView per l'immagine di sfondo
        ImageView sfondoImageView = new ImageView(sfondoImage);
        sfondoImageView.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        sfondoImageView.setFitWidth(Screen.getPrimary().getBounds().getWidth());
        root.getChildren().add(sfondoImageView);

        Button vs1 = new Button("Contro 1");
        vs1.setOnAction(actionEvent -> {
            gestoreAudio.play(3);
            gestoreAudio.stop(2);
            // Crea una transizione di fade-out per la scena corrente
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), anchorPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Aggiungi un event handler che cambierà la scena dopo che l'animazione è terminata
            fadeOut.setOnFinished(event -> {
                gestoreAudio.setVolume(-17.0f, 1);
                gestoreAudio.play(1);
                finestraDiGioco(anchorPane);  // Cambia la scena
                eventController.avviaPartita(2);

                // Crea una transizione di fade-in per la nuova scena
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), anchorPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        });
        vs1.setPrefHeight(80);
        vs1.setPrefWidth(240);
        vs1.setLayoutX(1920/2-vs1.getPrefWidth()/2);
        vs1.setLayoutY(550/2-vs1.getPrefHeight()/2);
        vs1.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(vs1);

        Button vs2 = new Button("Contro 2");
        vs2.setOnAction(actionEvent -> {
            gestoreAudio.play(3);
            gestoreAudio.stop(2);
            // Crea una transizione di fade-out per la scena corrente
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), anchorPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Aggiungi un event handler che cambierà la scena dopo che l'animazione è terminata
            fadeOut.setOnFinished(event -> {
                gestoreAudio.setVolume(-17.0f, 1);
                gestoreAudio.play(1);
                finestraDiGioco(anchorPane);  // Cambia la scena
                eventController.avviaPartita(3);

                // Crea una transizione di fade-in per la nuova scena
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), anchorPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        });
        vs2.setPrefHeight(80);
        vs2.setPrefWidth(240);
        vs2.setLayoutX(1920/2-vs2.getPrefWidth()/2);
        vs2.setLayoutY(550/2-vs2.getPrefHeight()/2 + vs2.getPrefHeight() * 1.5);
        vs2.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(vs2);

        Button vs3 = new Button("Contro 3");
        vs3.setOnAction(actionEvent -> {
            gestoreAudio.play(3);
            gestoreAudio.stop(2);
            // Crea una transizione di fade-out per la scena corrente
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), anchorPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Aggiungi un event handler che cambierà la scena dopo che l'animazione è terminata
            fadeOut.setOnFinished(event -> {
                gestoreAudio.setVolume(-17.0f, 1);
                gestoreAudio.play(1);
                finestraDiGioco(anchorPane);  // Cambia la scena
                eventController.avviaPartita(4);

                // Crea una transizione di fade-in per la nuova scena
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), anchorPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        });
        vs3.setPrefHeight(80);
        vs3.setPrefWidth(240);
        vs3.setLayoutX(1920/2-vs3.getPrefWidth()/2);
        vs3.setLayoutY(550/2-vs3.getPrefHeight()/2 + vs3.getPrefHeight() * 3);
        vs3.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(vs3);

        Button tornaAlMenù = new Button("Torna al Menù");
        tornaAlMenù.setOnAction(actionEvent -> {
            gestoreAudio.play(3);
            menù(anchorPane);
        });
        tornaAlMenù.setPrefHeight(80);
        tornaAlMenù.setPrefWidth(240);
        tornaAlMenù.setLayoutX(1920/2-tornaAlMenù.getPrefWidth()/2);
        tornaAlMenù.setLayoutY(550/2-tornaAlMenù.getPrefHeight()/2 + tornaAlMenù.getPrefHeight() * 4.5);
        tornaAlMenù.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(tornaAlMenù);

        return modalitàPane;
    }

    /**
     * Metodo che permette la creazione dell'AnchorPane finestraDiGioco
     */
    public AnchorPane finestraDiGioco(AnchorPane root) {
        pausaAttiva = false;
        cartaPescata = null;

        root.getChildren().clear();
        AnchorPane anchorPane = new AnchorPane();
        // Scala l'immagine alle dimensioni dell'AnchorPane
        Image sfondoImage = new Image(Main.class.getResourceAsStream("/com/example/jtrash/JTrash-schermata-di-gioco.png"),
                root.getWidth(), root.getHeight(), false, true);
        // Crea un ImageView per l'immagine di sfondo
        ImageView sfondoImageView = new ImageView(sfondoImage);
        sfondoImageView.setFitHeight(Screen.getPrimary().getBounds().getHeight());
        sfondoImageView.setFitWidth(Screen.getPrimary().getBounds().getWidth());

        root.getChildren().add(sfondoImageView);

        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P && pausaAttiva ==  false) {
                pausa(root);
                pausaAttiva = true;
            }
        });

        Button vittoria = new Button("Vittoria");
        vittoria.setOnMouseClicked(actionEvent -> {
            gestoreAudio.play(3);
            eventController.fermaTurnoComputer();
            vittoria(root);
        });
        vittoria.setLayoutX(30);
        vittoria.setLayoutY(500);
        vittoria.setPrefHeight(30);
        vittoria.setPrefWidth(120);
        vittoria.setFont(Font.font("Power Clear", FontWeight.BOLD,20));
        root.getChildren().add(vittoria);

        Button sconfitta = new Button("Sconfitta");
        sconfitta.setOnMouseClicked(actionEvent -> {
            gestoreAudio.play(3);
            eventController.fermaTurnoComputer();
            sconfitta(root);

        });
        sconfitta.setLayoutX(30);
        sconfitta.setLayoutY(550);
        sconfitta.setPrefHeight(30);
        sconfitta.setPrefWidth(120);
        sconfitta.setFont(Font.font("Power Clear", FontWeight.BOLD,20));
        root.getChildren().add(sconfitta);

        return anchorPane;
    }

    /**
     * Metodo che permette la creazione della schermata di vittoria
     */
    public void vittoria(AnchorPane root) {
        eventController.stopTimer();

        Rectangle sfondo = new Rectangle(1920, 1080);
        sfondo.setFill(Color.DARKGREEN);
        sfondo.setOpacity(0.7);
        root.getChildren().add(sfondo);

        ImageView testoVittoria = new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Vittoria-testo.png")));
        testoVittoria.setFitHeight(150);
        testoVittoria.setFitWidth(600);
        testoVittoria.setScaleX(1.8);
        testoVittoria.setScaleY(1.8);
        testoVittoria.setLayoutX(1920/2 - testoVittoria.getFitWidth()/2);
        testoVittoria.setLayoutY(450/2 - testoVittoria.getFitHeight()/2);
        root.getChildren().add(testoVittoria);

        Button tornaAlMenù = new Button("Torna al Menù");
        tornaAlMenù.setOnAction(actionEvent -> {
            gestoreAudio.play(3);
            gestoreAudio.stop(1);
            // Crea una transizione di fade-out per la scena corrente
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), anchorPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Aggiungi un event handler che cambierà la scena dopo che l'animazione è terminata
            fadeOut.setOnFinished(event -> {
//                eventController.vittoria();
                eventController.aumentaPartiteVinte();
                eventController.saveGame();
                eventController.stopTimer();

                menù(anchorPane);  // Cambia la scena

                // Crea una transizione di fade-in per la nuova scena
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), anchorPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        });
        tornaAlMenù.setPrefHeight(80);
        tornaAlMenù.setPrefWidth(240);
        tornaAlMenù.setLayoutX(1920/2-tornaAlMenù.getPrefWidth()/2);
        tornaAlMenù.setLayoutY(1080/2-tornaAlMenù.getPrefHeight()/2 + tornaAlMenù.getPrefHeight() * 1.5);
        tornaAlMenù.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(tornaAlMenù);
    }

    /**
     * Metodo che permette la creazione della schermata di sconfitta
     */
    public void sconfitta(AnchorPane root) {
        eventController.stopTimer();

        Rectangle sfondo = new Rectangle(1920, 1080);
        sfondo.setFill(Color.TOMATO);
        sfondo.setOpacity(0.7);
        root.getChildren().add(sfondo);

        ImageView testoSconfitta = new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Sconfitta-testo.png")));
        testoSconfitta.setFitHeight(150);
        testoSconfitta.setFitWidth(600);
        testoSconfitta.setScaleX(1.8);
        testoSconfitta.setScaleY(1.8);
        testoSconfitta.setLayoutX(1920/2 - testoSconfitta.getFitWidth()/2);
        testoSconfitta.setLayoutY(450/2 - testoSconfitta.getFitHeight()/2);
        root.getChildren().add(testoSconfitta);

        Button tornaAlMenù = new Button("Torna al Menù");
        tornaAlMenù.setOnAction(actionEvent -> {
            gestoreAudio.play(3);
            gestoreAudio.stop(1);
            // Crea una transizione di fade-out per la scena corrente
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), anchorPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Aggiungi un event handler che cambierà la scena dopo che l'animazione è terminata
            fadeOut.setOnFinished(event -> {
                eventController.aumentaPartitePerse();
                eventController.saveGame();
                eventController.stopTimer();

                menù(anchorPane);  // Cambia la scena

                // Crea una transizione di fade-in per la nuova scena
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), anchorPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        });
        tornaAlMenù.setPrefHeight(80);
        tornaAlMenù.setPrefWidth(240);
        tornaAlMenù.setLayoutX(1920/2-tornaAlMenù.getPrefWidth()/2);
        tornaAlMenù.setLayoutY(1080/2-tornaAlMenù.getPrefHeight()/2 + tornaAlMenù.getPrefHeight() * 1.5);
        tornaAlMenù.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(tornaAlMenù);
    }

    /**
     * Metodo che permette la creazione della schermata di pausa
     */
    public void pausa(AnchorPane root) {
        if (!eventController.puòPausare()){
            return;
        }

        eventController.stopTimer();
        Rectangle sfondo = new Rectangle(1920, 1080);
        sfondo.setFill(Color.BLACK);
        sfondo.setOpacity(0.7);
        root.getChildren().add(sfondo);

        Button tornaAlMenù = new Button("Torna al Menù");
        tornaAlMenù.setOnAction(actionEvent -> {
            gestoreAudio.play(3);
            gestoreAudio.stop(1);
            // Crea una transizione di fade-out per la scena corrente
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), anchorPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Aggiungi un event handler che cambierà la scena dopo che l'animazione è terminata
            fadeOut.setOnFinished(event -> {
                eventController.saveGame();
                eventController.stopTimer();

                menù(anchorPane);  // Cambia la scena

                // Crea una transizione di fade-in per la nuova scena
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), anchorPane);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });

            fadeOut.play();
        });
        tornaAlMenù.setPrefHeight(80);
        tornaAlMenù.setPrefWidth(240);
        tornaAlMenù.setLayoutX(1920/2-tornaAlMenù.getPrefWidth()/2);
        tornaAlMenù.setLayoutY(1080/2-tornaAlMenù.getPrefHeight()/2 + tornaAlMenù.getPrefHeight() * 1.5);
        tornaAlMenù.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(tornaAlMenù);

        Button bottoneRiprendi = new Button("Riprendi");
        bottoneRiprendi.setOnMouseClicked(actionEvent -> {
            gestoreAudio.play(3);
            root.getChildren().remove(sfondo);
            root.getChildren().remove(bottoneRiprendi);
            root.getChildren().remove(tornaAlMenù);
            pausaAttiva = false;
            eventController.startTimer();
        });
        bottoneRiprendi.setPrefHeight(80);
        bottoneRiprendi.setPrefWidth(240);
        bottoneRiprendi.setLayoutX(1920/2-bottoneRiprendi.getPrefWidth()/2);
        bottoneRiprendi.setLayoutY(1080/2-bottoneRiprendi.getPrefHeight()/2);
        bottoneRiprendi.setFont(Font.font("Power Clear", FontWeight.BOLD,35));
        root.getChildren().add(bottoneRiprendi);

    }

    /**
     * Metodo che, data una Map di Stringhe, consente di ottenere una rappresentazione numerica di un valore espresso in lettere
     */
    public String enumToInt(String valoreEnum) {
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
        return enumIntero.get(valoreEnum);
    }

    /**
     * Metodo che permette la creazione visiva del mazzo principale
     */
    public void creaMazzoView(AnchorPane root) {
        ImageView cartaMazzo = new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-back2.png")));

        cartaMazzo.setFitHeight(95);
        cartaMazzo.setFitWidth(60);
        cartaMazzo.prefHeight(95);
        cartaMazzo.prefWidth(60);
        cartaMazzo.setScaleX(1.3);
        cartaMazzo.setScaleY(1.3);
        cartaMazzo.setLayoutY(400);
        cartaMazzo.setLayoutX(1100);
        cartaMazzo.setRotate(-90);

        cartaMazzo.setOnMouseClicked(event -> {
            gestoreAudio.play(0);
            eventController.pesca();
        });

        root.getChildren().add(cartaMazzo);

    }

    /**
     * Metodo che permette la creazione visiva della mano del giocatore
     */
    public void creaManoGiocatoreView(String[] carte) {
        HBox manoGiocatore = new HBox();
        manoGiocatore.setSpacing(13);

        for (String carta : carte){
            manoGiocatore.getChildren().add(creaCartaMano(carta, anchorPane));
        }

        manoGiocatore.prefHeight(95);
        manoGiocatore.prefWidth(750);
        manoGiocatore.setScaleX(1.3);
        manoGiocatore.setScaleY(1.3);
        manoGiocatore.setLayoutX(1220/2 - (manoGiocatore.getPrefWidth()/2));
        manoGiocatore.setLayoutY(1880/2- manoGiocatore.getPrefHeight()/2);
        manoGiocatore.setId("giocatore");

        anchorPane.getChildren().add(manoGiocatore);
    }

    /**
     * Metodo che permette la creazione visiva della mano del computer
     */
    public void creaManoCompView(String[] carte) {
        HBox manoComp = new HBox();
        manoComp.setSpacing(13);

        for (String carta : carte){
            manoComp.getChildren().add(creaCartaMano(carta, anchorPane));
        }

        manoComp.prefHeight(95);
        manoComp.prefWidth(750);
        manoComp.setScaleX(1.3);
        manoComp.setScaleY(1.3);
        manoComp.setLayoutX(1220/2 - (manoComp.getPrefWidth()/2));
        manoComp.setLayoutY(100/2- manoComp.getPrefHeight()/2);

        anchorPane.getChildren().add(manoComp);
    }

    /**
     * Metodo che permette la creazione visiva della mano del computer 2
     */
    public void creaManoComp2View(String[] carte) {
        HBox manoComp2 = new HBox();
        manoComp2.setSpacing(13);

        for (String carta : carte){
            manoComp2.getChildren().add(creaCartaMano(carta, anchorPane));
        }

        manoComp2.prefHeight(95);
        manoComp2.prefWidth(750);
        manoComp2.setScaleX(1.3);
        manoComp2.setScaleY(1.3);
        manoComp2.setLayoutX(86/2 - (manoComp2.getPrefWidth()/2));
        manoComp2.setLayoutY(980/2- manoComp2.getPrefHeight()/2);
        manoComp2.setRotate(-90);

        anchorPane.getChildren().add(manoComp2);
    }

    /**
     * Metodo che permette la creazione visiva della mano del computer 3
     */
    public void creaManoComp3View(String[] carte) {
        HBox manoComp3 = new HBox();
        manoComp3.setSpacing(13);

        for (String carta : carte){
            manoComp3.getChildren().add(creaCartaMano(carta, anchorPane));
        }

        manoComp3.prefHeight(95);
        manoComp3.prefWidth(750);
        manoComp3.setScaleX(1.3);
        manoComp3.setScaleY(1.3);
        manoComp3.setLayoutX(2350/2 - (manoComp3.getPrefWidth()/2) * 1.5);
        manoComp3.setLayoutY(980/2- manoComp3.getPrefHeight()/2);
        manoComp3.setRotate(90);

        anchorPane.getChildren().add(manoComp3);
    }

    /**
     * Metodo che permette la creazione visiva delle singole carte delle mani dei giocatori
     */
    public ImageView creaCartaMano(String carta, AnchorPane root){
        String[] valoriCarta = carta.split(" of ");

        if (valoriCarta[2].equalsIgnoreCase("false")){
            ImageView primaCarta = new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-back2.png")));

            primaCarta.setFitHeight(95);
            primaCarta.setFitWidth(60);
            primaCarta.setLayoutY(100);
            primaCarta.setLayoutX(100);
            primaCarta.setId(valoriCarta[0] + " " + valoriCarta[1]);
            primaCarta.setOnMouseClicked(mouseEvent -> {
                gestoreAudio.play(0);
                if (anchorPane.getChildren().contains(cartaPescata)) {
                    HBox manoGiocatore = (HBox) anchorPane.lookup("#giocatore");
                    scambiaKJ(primaCarta.getId());
                }
            });
            return primaCarta;

        } else{
            ImageView primaCarta = new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + valoriCarta[1].toLowerCase() + "-" + enumToInt(valoriCarta[0]) + ".png")));

            primaCarta.setFitHeight(95);
            primaCarta.setFitWidth(60);
            primaCarta.setLayoutY(100);
            primaCarta.setLayoutX(100);
            primaCarta.setId(valoriCarta[0] + " " + valoriCarta[1]);

            return primaCarta;
        }
    }

    /**
     * Metodo che permette di rimuovere gli elementi iniziali degli Array di Stringhe del Model
     * che vengono notificati alla View. Rimuove infatti la stringa iniziale che distingue un Array dall'altro
     * e restituisce il nuovo array privo dell'elemento
     */
    public String[] rimuoviElemento(String[] array, int indiceDaRimuovere) {
        if (indiceDaRimuovere < 0 || indiceDaRimuovere >= array.length) {
            // Controllo se l'indice è valido
            return array;
        }
        String[] nuovoArray = new String[array.length - 1]; // Creo un nuovo array di dimensione n-1
        // Copio gli elementi dalla posizione 0 fino all'indice da rimuovere
        System.arraycopy(array, 0, nuovoArray, 0, indiceDaRimuovere);
        // Copio gli elementi dalla posizione successiva all'indice da rimuovere
        System.arraycopy(array, indiceDaRimuovere + 1, nuovoArray, indiceDaRimuovere, nuovoArray.length - indiceDaRimuovere);
        return nuovoArray; // Restituisco il nuovo array senza l'elemento rimosso
    }

    /**
     * Metodo che permette la creazione visiva della carta pescata dal mazzo principale
     */
    public void creaCartaMazzo(String segno, String valore, AnchorPane root){

        ImageView primaCarta = new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + segno.toLowerCase() + "-" + enumToInt(valore) + ".png")));
        primaCarta.setFitHeight(95);
        primaCarta.setFitWidth(60);
        primaCarta.prefHeight(95);
        primaCarta.prefWidth(60);
        primaCarta.setScaleX(1.3);
        primaCarta.setScaleY(1.3);
        primaCarta.setLayoutY(400);
        primaCarta.setLayoutX(925);
        root.getChildren().addAll(primaCarta);
        primaCarta.setId(valore + segno);


        cartaPescata = primaCarta;
        cartaPescata.setId("cartaPescata");

        cartaPescata.setOnMouseClicked(event -> {
            gestoreAudio.play(0);
            eventController.scambioCarte();
        });
    }

    /**
     * Metodo che chiama il metodo dell'EventController "scambioKJ"
     */
    public void scambiaKJ(String id) {
        eventController.scambioKJ(id);
    }

    /**
     * Metodo che permette la creazione visiva del mazzo degli scarti, e che permette
     * di pescare da questo al click del mouse
     */
    public void scarta(String segno, String valore) {
        ImageView carta = new ImageView(((String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + segno.toLowerCase() + "-" + enumToInt(valore) + ".png")))));

        pila = new ImageView(carta.getImage());
        pila.setId("scarti");
        pila.setFitHeight(95);
        pila.setFitWidth(60);
        pila.prefHeight(95);
        pila.prefWidth(60);
        pila.setScaleX(1.3);
        pila.setScaleY(1.3);
        pila.setLayoutY(400);
        pila.setLayoutX(750);
        pila.setRotate(-90);

        pila.setOnMouseClicked(event -> {
            gestoreAudio.play(0);
            eventController.pescaDaScarti();
        });

        anchorPane.getChildren().add(pila);
    }

    /**
     * Metodo che permette la creazione visiva della finestra di dialogo per effettuare il login in un profilo
     */
    public List<String> finestraDiDialogoAccount() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.setFullScreen(false);
        List<String> accountInfo = new ArrayList<>();

        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Account");

        dialog.setHeaderText("Inserisci il nome dell'account");

        ButtonType bottoneOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType creaAccountButtonType = new ButtonType("Crea Account", ButtonBar.ButtonData.APPLY);
        ButtonType cancelButtonType = new ButtonType("Cancella", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(bottoneOk, creaAccountButtonType, cancelButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField campoUsername = new TextField();
        PasswordField campoPassword = new PasswordField();

        campoUsername.setPromptText("Nome account");
        campoPassword.setPromptText("Password");

        grid.add(new Label("Nome account:"), 0, 0);
        grid.add(campoUsername, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(campoPassword, 1, 1);

        Node okButton = dialog.getDialogPane().lookupButton(bottoneOk);
        okButton.setDisable(true);

        campoUsername.textProperty().addListener((observable, oldValue, newValue) ->
                okButton.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == bottoneOk) {
                List<String> result = new ArrayList<>();
                result.add(campoUsername.getText().trim());
                result.add(campoPassword.getText());
                return result;
            } else if (dialogButton == creaAccountButtonType) {
                finestraDiCreazioneAccount();
            } else if (dialogButton == cancelButtonType) {
                stage.setFullScreen(true);
            }
            return null;
        });
        Optional<List<String>> result = dialog.showAndWait();

        result.ifPresent(credentials -> {
            eventController.gestioneLoginProfilo(credentials.get(0), credentials.get(1));
            stage.setFullScreen(true);
        });

        return accountInfo;
    }

    /**
     * Metodo che permette l'aggiornamento, e quindi la scelta di un avatar al giocatore durante la creazione del profilo
     */
    public void updateImageView(String path, ImageView immagine) {
        immagine.setImage(new ImageView(path).getImage());
    }

    /**
     * Metodo che permette la creazione visiva della finestra di dialogo per effettuare la creazione di un nuovo profilo
     */
    private void finestraDiCreazioneAccount() {
        List<String> imagePaths = new ArrayList<>();
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        for (int i = 0; i < 11; i++) {
            imagePaths.add((Main.class.getResource("/com/example/jtrash/avatar")) + String.valueOf(i) + ".png");
        }

        Dialog<List<String>> dialogCreazioneAccount = new Dialog<>();
        dialogCreazioneAccount.setTitle("Crea Account");

        ButtonType bottoneOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogCreazioneAccount.getDialogPane().getButtonTypes().addAll(bottoneOk, ButtonType.CANCEL);

        GridPane gridCreaAccount = new GridPane();
        gridCreaAccount.setHgap(10);
        gridCreaAccount.setVgap(10);

        TextField campoUsername = new TextField();
        PasswordField campoPassword = new PasswordField();

        campoUsername.setPromptText("Nuovo Nome account");
        campoPassword.setPromptText("Nuova Password");

        Label labelNuovoNome = new Label("Nuovo Nome account:");
        labelNuovoNome.setStyle("-fx-font-size: 14");

        Label labelNuovaPassword = new Label("Nuova Password:");
        labelNuovaPassword.setStyle("-fx-font-size: 14");

        gridCreaAccount.add(labelNuovoNome, 0, 0);
        gridCreaAccount.add(campoUsername, 1, 0);
        gridCreaAccount.add(labelNuovaPassword, 0, 1);
        gridCreaAccount.add(campoPassword, 1, 1);


        ImageView imageView = new ImageView((Main.class.getResource("/com/example/jtrash/avatar")) + String.valueOf(currentIndex) + ".png");

        imageView.setFitWidth(160);
        imageView.setFitHeight(160);
        gridCreaAccount.add(imageView, 2, 0, 1, 2);

        Button frecciaSx = new Button("<");
        frecciaSx.setOnAction(event -> {
            if (currentIndex != 0) {
                currentIndex = (currentIndex - 1 + imagePaths.size()) % imagePaths.size();
                updateImageView((Main.class.getResource("/com/example/jtrash/avatar")) + String.valueOf(currentIndex) + ".png", imageView);
            }
        });
        gridCreaAccount.add(frecciaSx, 2, 2);

        Button frecciaDx = new Button(">");
        frecciaDx.setOnAction(event -> {
            if (currentIndex != 6) {
                currentIndex = (currentIndex + 1 + imagePaths.size()) % imagePaths.size();
                updateImageView((Main.class.getResource("/com/example/jtrash/avatar")) + String.valueOf(currentIndex) + ".png", imageView);
            }
        });
        gridCreaAccount.add(frecciaDx, 3, 2);

        dialogCreazioneAccount.getDialogPane().setMinWidth(400);
        dialogCreazioneAccount.getDialogPane().setMaxWidth(800);

        Node okButton = dialogCreazioneAccount.getDialogPane().lookupButton(bottoneOk);
        okButton.setDisable(true);

        campoUsername.textProperty().addListener((observable, oldValue, newValue) ->
                okButton.setDisable(newValue.trim().isEmpty()));

        dialogCreazioneAccount.getDialogPane().setContent(gridCreaAccount);

        dialogCreazioneAccount.setResultConverter(dialogButton -> {
            if (dialogButton == bottoneOk) {
                List<String> result = new ArrayList<>();
                result.add(campoUsername.getText().trim());
                result.add(campoPassword.getText());
                return result;
            }
            return null;
        });

        Optional<List<String>> result = dialogCreazioneAccount.showAndWait();

        result.ifPresent(credentials -> {
            eventController.gestioneCreazioneProfilo(credentials.get(0), credentials.get(1), (Main.class.getResource("/com/example/jtrash/avatar") + String.valueOf(currentIndex) + ".png"));
            eventController.loggatoPerMenù();
            stage.setFullScreen(true);
        });
    }

    /**
     * Metodo che permette la creazione visiva del timer
     */
    public void disegnaTimer(Integer secondi) {
        Label provaTimer = (Label) anchorPane.lookup("#Timer");
        if (provaTimer!=null){
            anchorPane.getChildren().remove(provaTimer);
        }
        Label timer = new Label(convertiSecondiInMinuti(secondi) + ":" + secondi % 60);
        timer.setId("Timer");
        timer.setPrefHeight(80);
        timer.setPrefWidth(240);
        timer.setLayoutX(2075/2-timer.getPrefWidth()/2);
        timer.setLayoutY(400/2-timer.getPrefHeight()/2);
        timer.setTextFill(Color.WHITE);
        timer.setFont(Font.font("Arial", FontWeight.BOLD,35));

        anchorPane.getChildren().add(timer);
    }

    /**
     * Metodo che converte i secondi registrati dal Timer in minuti, necessario per la corretta
     * visualizzazione della statistica tempoDiGioco del profilo del giocatore
     */
    public int convertiSecondiInMinuti(Integer secondi) {
        int minuti = secondi / 60;
        return minuti;
    }

    /**
     * Metodo che converte i minuti registrati dal Timer in ore, necessario per la corretta
     * visualizzazione della statistica tempoDiGioco del profilo del giocatore
     */
    public int convertiMinutiInOre(Integer minuti) {
        int ore = minuti / 60;
        return ore;
    }

    /**
     * Metodo di update della View, che considera tutte le notifiche di aggiornamenti del Model
     * e li rappresenta visivamente in modo adeguato
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String[]) {
            String[] varTemp = (String[]) arg;
            if (varTemp[0].equalsIgnoreCase("Mazzo")) {
                creaMazzoView(anchorPane);
            } else if (varTemp[0].equalsIgnoreCase("ManoGiocatore")) {
                varTemp = rimuoviElemento(varTemp, 0);
                creaManoGiocatoreView(varTemp);
            } else if (varTemp[0].equalsIgnoreCase("ManoComputer")) {
                varTemp = rimuoviElemento(varTemp, 0);
                creaManoCompView(varTemp);
            } else if (varTemp[0].equalsIgnoreCase("ManoComputer2")) {
                varTemp = rimuoviElemento(varTemp, 0);
                creaManoComp2View(varTemp);
            } else if (varTemp[0].equalsIgnoreCase("ManoComputer3")) {
                varTemp = rimuoviElemento(varTemp, 0);
                creaManoComp3View(varTemp);
            } else if (varTemp[0].equalsIgnoreCase("Carte")) {
                varTemp = rimuoviElemento(varTemp, 0);
                String[] valori = varTemp[0].split(" of ");
                if (cartaPescata != null) {
                    cartaPescata.setImage(new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card" + "-" + valori[1].toLowerCase() + "-" + enumToInt(valori[0]) + ".png"))).getImage());
                } else {
                    cartaPescata = new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + valori[1].toLowerCase() + "-" + enumToInt(valori[0]) + ".png")));
                }
            } else if (varTemp[0].equalsIgnoreCase("Scarti")) {
                if (varTemp.length == 1) {
                    anchorPane.getChildren().remove(pila);
                } else {
                    varTemp = rimuoviElemento(varTemp, 0);
                    String[] valori = varTemp[0].split(" of ");
                    if (anchorPane.lookup("#scarti") == null) {
                        scarta(valori[1], valori[0]);
                    } else {
                        anchorPane.getChildren().remove(anchorPane.lookup("#scarti"));
                        scarta(valori[1], valori[0]);
                        //scarti.setImage(new ImageView(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + valori[1].toLowerCase() + "-" + enumToInt(valori[0]) + ".png"))).getImage());
                    }
                }
            }
        } else if ((arg instanceof String)) {

        } else if (arg instanceof Integer) {
            Integer varTemp = (Integer) arg;
            disegnaTimer(varTemp);
        }
    }

    /**
     * Getter
     */
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * Setter
     */
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    /**
     * Getter
     */
    public EventController getEventController() {
        return eventController;
    }

    /**
     * Setter
     */
    public void setEventController(EventController eventController) {
        this.eventController = eventController;
    }

    /**
     * Getter
     */
    public ImageView getCartaPescata() {
        return cartaPescata;
    }

    /**
     * Setter
     */
    public void setCartaPescata(ImageView cartaPescata) {
        this.cartaPescata = cartaPescata;
    }

    /**
     * Metodo che restituisce true se non è possibile mettere in pausa
     * poiché il gioco si trova già in pausa, altrimenti false
     */
    public boolean isPausaAttiva() {
        return pausaAttiva;
    }

    /**
     * Setter
     */
    public void setPausaAttiva(boolean pausaAttiva) {
        this.pausaAttiva = pausaAttiva;
    }

    /**
     * Getter
     */
    public GestoreAudio getGestoreAudio() {
        return gestoreAudio;
    }

    /**
     * Setter
     */
    public void setGestoreAudio(GestoreAudio gestoreAudio) {
        this.gestoreAudio = gestoreAudio;
    }
}
