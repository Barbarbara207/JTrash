package com.example.jtrash.Controller;

import com.example.jtrash.Model.Model;
import com.example.jtrash.View.GestoreFinestreView;
import com.example.jtrash.View.View;

public class Controller {

    private Model model;
    private View view;
    private EventController eventController;

    /**
     * Costruttore della classe Controller
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.eventController = new EventController(model, view);
    }

    /**
     * Metodo che consente alla View "osservare" il Model e ricevere notifiche quando il Model cambia
     */
    public void osserva() {
            model.addObserver(view.getGestoreFinestreView());
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
}
