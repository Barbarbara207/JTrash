package com.example.jtrash;

import com.example.jtrash.Controller.Controller;
import com.example.jtrash.Model.Model;
import com.example.jtrash.View.GestoreFinestreView;
import com.example.jtrash.View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
//        GestoreFinestreView view = new GestoreFinestreView(primaryStage);
        View view = new View(primaryStage);
        Controller controller = new Controller(model, view);
        model.setEventController(controller.getEventController());
        view.getGestoreFinestreView().setEventController(controller.getEventController());
        controller.osserva();
        view.avvia();
//        model.avvia();
        primaryStage.show();
    }
}
