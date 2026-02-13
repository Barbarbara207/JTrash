package com.example.jtrash.View;

import com.example.jtrash.Controller.EventController;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private Stage stage;

    private AnchorPane anchorPane;

    private GestoreFinestreView gestoreFinestreView;
    private GestoreAudio gestoreAudio;

    /**
     * Costruttore della classe View
     */
    public View(Stage primaryStage) {
        anchorPane = new AnchorPane();
        primaryStage.setWidth(Screen.getPrimary().getBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getBounds().getHeight());
        primaryStage.setFullScreen(true);
        stage = primaryStage;
        gestoreFinestreView = new GestoreFinestreView();
    }

    /**
     * Metodo che crea lo stage, ovvero la finestra vera e propria
     */
    public void avvia(){
        stage.setTitle("JTrash");

        AnchorPane anchorPane = gestoreFinestreView.getAnchorPane();

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        gestoreFinestreView.menù(anchorPane);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F) {
                tornaSchermoIntero();
            }
        });
    }

    /**
     * Metodo che permette di far tornare la finestra a schermo intero
     */
    public void tornaSchermoIntero() {
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }
    }

    /**
     * Override del metodo update di Observer Observable
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * Getter
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Setter
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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
    public GestoreFinestreView getGestoreFinestreView() {
        return gestoreFinestreView;
    }

    /**
     * Setter
     */
    public void setGestoreFinestreView(GestoreFinestreView gestoreFinestreView) {
        this.gestoreFinestreView = gestoreFinestreView;
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
