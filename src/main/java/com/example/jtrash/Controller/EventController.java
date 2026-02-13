package com.example.jtrash.Controller;

import com.example.jtrash.Main;
import com.example.jtrash.Model.CardModel;
import com.example.jtrash.Model.Model;
import com.example.jtrash.Model.ProfiloGiocatore;
import com.example.jtrash.View.View;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.Collections;

public class EventController {

    private Model model;
    private View view;

    /**
     * Costruttore della classe EventController
     */
    public EventController(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Metodo che, restituendo true o false, consente di mettere in pausa il gioco
     */
    public boolean puòPausare(){
        if (model.getGiocatoreAttuale()==0) return true;
        else return false;
    }

    /**
     * Metodo che definisce l'operazione di pesca dal mazzo
     */
    public void pesca() {

        if (model.getTurnoExtra() == 0) {
            // ricomincia ma con una carta in meno a chi vince
            ricomincia();
            System.out.println("nuovo turno");
            return;
        }
        if (model.getCartaPescata() != null) {
            // Carta pescata non è null, quindi non è possibile pescare nuovamente
            return;
        }

        CardModel carta = model.pescaCarta();
        view.getGestoreFinestreView().creaCartaMazzo(carta.getSegno(), carta.getValore(), view.getGestoreFinestreView().getAnchorPane());
    }

    /**
     * Metodo che definisce l'operazione di pesca dal mazzo degli scarti
     */
    public void pescaDaScarti() {
        if (model.getTurnoExtra() == 0) {
            // ricomincia ma con una carta in meno a chi vince
            ricomincia();
            System.out.println("nuovo turno");
            return;
        }
        if (model.getCartaPescata() != null) {
            return;
        }

        model.pescaCartaDaScarti();
        view.getGestoreFinestreView().creaCartaMazzo((model.getCartaPescata().getSegno()), model.getCartaPescata().getValore(), view.getGestoreFinestreView().getAnchorPane());
        model.notifica();
    }

    /**
     * Metodo che definisce l'operazione di pesca del computer
     */
    public void pescaComputer() {
        if (model.getTurnoExtra() == 0) {
//            if (vittorieGiocatoreInPartita() == 9 && model.getManoComputer().isHaVinto()) {
//                view.getGestoreFinestreView().vittoria(view.getGestoreFinestreView().getAnchorPane());
//                return;
//            }
//            // stessa cosa per il computer con else if
            ricomincia();
            // ricomincia ma con una carta in meno a chi vince
            System.out.println("nuovo turno");
            return;
        }
        view.getGestoreFinestreView().getGestoreAudio().play(0);
        CardModel carta = model.pescaCarta();
        view.getGestoreFinestreView().creaCartaMazzo(carta.getSegno(), carta.getValore(), view.getGestoreFinestreView().getAnchorPane());
    }

    /**
     * Metodo che definisce l'operazione di scambio tra la carta sul tavolo e quella nella mano
     */
    public void scambioCarte() {
        if (model.getGiocatoreAttuale() == 0) {
            if (Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) <= 10 - model.getManoGiocatore().getQuanteVittorie()) {
                if ((model.getManoGiocatore().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1)).isScoperta() == false) {
                    model.scambiaCarta();
                    model.notifica();

                    System.out.println("Carta scambiata");
                } else if ((model.getManoGiocatore().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1)).isScoperta() == true) {
                    if (model.getManoGiocatore().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1).getValore().equals("THIRTEEN") || model.getManoGiocatore().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1).getValore().equals("FOURTEEN")) {
                        model.scambiaCarta();
                    } else {
                        view.getGestoreFinestreView().scarta(model.getCartaPescata().getSegno(), model.getCartaPescata().getValore());
                        togliCartaSulTavolo();
                        model.scartaCarta();
                        model.notifica();
                        System.out.println("Carta Scartata");
                        model.avviaTurnoComputer();
                    }
                }
            } else if (view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-joker-14.png"))) || view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + model.getCartaPescata().getSegno().toLowerCase() + "-13.png")))) {
                view.getGestoreFinestreView().getCartaPescata().setDisable(false);
                System.out.println("Carta Speciale!");
                if (model.getManoGiocatore().almenoUnaCartaCoperta() == false) {
                    model.scartaCarta();
                    togliCartaSulTavolo();
                    model.avviaTurnoComputer();
                }
            } else if ((Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) > 10 - model.getManoGiocatore().getQuanteVittorie() && (Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) <= 12))) {
                // scarta la carta pescata
                view.getGestoreFinestreView().scarta(model.getCartaPescata().getSegno(), model.getCartaPescata().getValore());
                togliCartaSulTavolo();
                model.scartaCarta();
                model.notifica();
                System.out.println("Carta scartata");
                model.avviaTurnoComputer();
            }
        }
    }

    /**
     * Metodo che definisce l'operazione di scambio speciale di carte come King e Joker
     */
    public void scambioKJ(String id) {
        if (view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-joker-14.png"))) || view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + model.getCartaPescata().getSegno().toLowerCase() + "-13.png")))) {

            String[] listaId = id.split(" ");
            int indice = model.ottieniIndice(listaId[0], listaId[1]);
            CardModel cartaDaScambiare = model.getManoGiocatore().getCarte().get(indice);
            CardModel cartaTemporanea;
            cartaDaScambiare.setScoperta(true);
            model.getCartaPescata().setScoperta(true);
            cartaTemporanea = cartaDaScambiare;
            model.getManoGiocatore().getCarte().set(indice, model.getCartaPescata());
            model.setCartaPescata(cartaTemporanea);
//            gestoreFinestreView.getCartaPescata().setDisable(false);
            model.notifica();

            view.getGestoreFinestreView().getCartaPescata().setDisable(false);
            System.out.println("carta speciale scambiata");

            if (model.getManoGiocatore().almenoUnaCartaCoperta() == false) {
                model.getManoGiocatore().setHaVinto(true);
                model.scartaCarta();
                togliCartaSulTavolo();
                model.setTurnoExtraIniziato(true);

                model.avviaTurnoComputer();
                System.out.println("turno extra iniziato!");
            }
        }
    }

    /**
     * Metodo che definisce l'operazione di scambio tra la carta sul tavolo e quella nella mano del computer
     */
    public void scambioCarteComputer() {
        switch (model.getGiocatoreAttuale()) {
            case 0:
                // non fare niente, turno del giocatore
                break;

            case 1:
                if (model.getManoComputer().almenoUnaCartaCoperta() == false){
                    view.getGestoreFinestreView().getGestoreAudio().play(0);
                    model.scartaCarta();
                    togliCartaSulTavolo();
                }
                if (Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) <= 10 - model.getManoComputer().getQuanteVittorie()) {
                    if ((model.getManoComputer().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1)).isScoperta() == false) {
                        view.getGestoreFinestreView().getGestoreAudio().play(0);
                        model.scambiaCartaComputer();
                        model.notifica();
                        System.out.println("Carta scambiata");
                    } else if ((model.getManoComputer().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1)).isScoperta() == true) {
                        if (model.getManoComputer().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1).getValore().equals("THIRTEEN") || model.getManoComputer().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1).getValore().equals("FOURTEEN")) {
                            view.getGestoreFinestreView().getGestoreAudio().play(0);
                            model.scambiaCartaComputer();
                        } else {
                            view.getGestoreFinestreView().scarta(model.getCartaPescata().getSegno(), model.getCartaPescata().getValore());
                            view.getGestoreFinestreView().getGestoreAudio().play(0);
                            togliCartaSulTavolo();
                            model.scartaCarta();
                            model.notifica();
                            System.out.println("Carta Scartata");
                        }
                    }
                } else if (view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-joker-14.png"))) || view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + model.getCartaPescata().getSegno().toLowerCase() + "-13.png")))) {
                    view.getGestoreFinestreView().getCartaPescata().setDisable(false);
                    System.out.println("Carta Speciale!");
                    if (model.getManoComputer().almenoUnaCartaCoperta() == false) {
                        view.getGestoreFinestreView().getGestoreAudio().play(0);
                        model.scartaCarta();
                        togliCartaSulTavolo();

                    }
                } else if ((Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) > 10 - model.getManoComputer().getQuanteVittorie() && (Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) <= 12))) {
                    // scarta la carta pescata
                    view.getGestoreFinestreView().getGestoreAudio().play(0);
                    view.getGestoreFinestreView().scarta(model.getCartaPescata().getSegno(), model.getCartaPescata().getValore());
                    model.scartaCarta();
                    togliCartaSulTavolo();
                    model.notifica();
                    System.out.println("Carta scartata");

                }
                break;

            case 2:
                if (model.getManoComputer2().almenoUnaCartaCoperta() == false){
                    view.getGestoreFinestreView().getGestoreAudio().play(0);
                    model.scartaCarta();
                    togliCartaSulTavolo();
                }
                if (Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) <= 10 - model.getManoComputer2().getQuanteVittorie()) {
                    if ((model.getManoComputer2().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1)).isScoperta() == false) {
                        view.getGestoreFinestreView().getGestoreAudio().play(0);
                        model.scambiaCartaComputer();
                        model.notifica();
                        System.out.println("Carta scambiata");
                    } else if ((model.getManoComputer2().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1)).isScoperta() == true) {
                        if (model.getManoComputer2().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1).getValore().equals("THIRTEEN") || model.getManoComputer2().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1).getValore().equals("FOURTEEN")) {
                            view.getGestoreFinestreView().getGestoreAudio().play(0);
                            model.scambiaCartaComputer();
                        } else {
                            view.getGestoreFinestreView().getGestoreAudio().play(0);
                            view.getGestoreFinestreView().scarta(model.getCartaPescata().getSegno(), model.getCartaPescata().getValore());
                            togliCartaSulTavolo();
                            model.scartaCarta();
                            model.notifica();
                            System.out.println("Carta Scartata");
                        }
                    }
                } else if (view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-joker-14.png"))) || view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + model.getCartaPescata().getSegno().toLowerCase() + "-13.png")))) {
                    view.getGestoreFinestreView().getCartaPescata().setDisable(false);
                    System.out.println("Carta Speciale!");
                    if (model.getManoComputer2().almenoUnaCartaCoperta() == false) {
                        view.getGestoreFinestreView().getGestoreAudio().play(0);
                        model.scartaCarta();
                        togliCartaSulTavolo();

                    }
                } else if ((Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) > 10 - model.getManoComputer2().getQuanteVittorie() && (Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) <= 12))) {
                    // scarta la carta pescata
                    view.getGestoreFinestreView().getGestoreAudio().play(0);
                    view.getGestoreFinestreView().scarta(model.getCartaPescata().getSegno(), model.getCartaPescata().getValore());
                    model.scartaCarta();
                    togliCartaSulTavolo();
                    model.notifica();
                    System.out.println("Carta scartata");

                }
                break;

            case 3:
                if (model.getManoComputer3().almenoUnaCartaCoperta() == false){
                    view.getGestoreFinestreView().getGestoreAudio().play(0);
                    model.scartaCarta();
                    togliCartaSulTavolo();
                }
                if (Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) <= 10 - model.getManoComputer3().getQuanteVittorie()) {
                    if ((model.getManoComputer3().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1)).isScoperta() == false) {
                        view.getGestoreFinestreView().getGestoreAudio().play(0);
                        model.scambiaCartaComputer();
                        model.notifica();
                        System.out.println("Carta scambiata");
                    } else if ((model.getManoComputer3().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1)).isScoperta() == true) {
                        if (model.getManoComputer3().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1).getValore().equals("THIRTEEN") || model.getManoComputer3().getCarte().get(Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) - 1).getValore().equals("FOURTEEN")) {
                            view.getGestoreFinestreView().getGestoreAudio().play(0);
                            model.scambiaCartaComputer();
                        } else {
                            view.getGestoreFinestreView().getGestoreAudio().play(0);
                            view.getGestoreFinestreView().scarta(model.getCartaPescata().getSegno(), model.getCartaPescata().getValore());
                            togliCartaSulTavolo();
                            model.scartaCarta();
                            model.notifica();
                            System.out.println("Carta Scartata");
                        }
                    }
                } else if (view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-joker-14.png"))) || view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + model.getCartaPescata().getSegno().toLowerCase() + "-13.png")))) {
                    view.getGestoreFinestreView().getCartaPescata().setDisable(false);
                    System.out.println("Carta Speciale!");
                    if (model.getManoComputer3().almenoUnaCartaCoperta() == false) {
                        view.getGestoreFinestreView().getGestoreAudio().play(0);
                        model.scartaCarta();
                        togliCartaSulTavolo();
                    }
                } else if ((Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) > 10 - model.getManoComputer3().getQuanteVittorie() && (Integer.parseInt(view.getGestoreFinestreView().enumToInt(model.getCartaPescata().getValore())) <= 12))) {
                    // scarta la carta pescata
                    view.getGestoreFinestreView().getGestoreAudio().play(0);
                    view.getGestoreFinestreView().scarta(model.getCartaPescata().getSegno(), model.getCartaPescata().getValore());
                    model.scartaCarta();
                    togliCartaSulTavolo();
                    model.notifica();
                    System.out.println("Carta scartata");
                }
                break;
        }
    }

    /**
     * Metodo che definisce l'operazione di scambio speciale di carte come King e Joker del computer
     */
    public void scambioKJComputer() {
        if (view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-joker-14.png"))) || view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + model.getCartaPescata().getSegno().toLowerCase() + "-13.png")))) {

            // prendo l'indice di tutte le carte coperte della mano del computer e le metto in una lista,
            // poi randomicamente se ne sceglie una
            Integer[] listaIndici = ((model.listaCarteCoperte()));
            Collections.shuffle(Arrays.asList(listaIndici));
            if (model.getManoComputer().almenoUnaCartaCoperta() == false) {
                model.scartaCarta();
                togliCartaSulTavolo();
                if (model.getTurnoExtra()==0){
//                    if (vittorieGiocatoreInPartita() == 9 && model.getManoComputer().isHaVinto()) {
//                        view.getGestoreFinestreView().vittoria(view.getGestoreFinestreView().getAnchorPane());
//                        return;
//                    }
                    model.getManoComputer().setHaVinto(true);
                    ricomincia();
                    model.fermaTurnoComputer();
                }
                return;
            }
            int indice = listaIndici[0];

            CardModel cartaDaScambiare = model.getManoComputer().getCarte().get(indice);
            CardModel cartaTemporanea;
            cartaDaScambiare.setScoperta(true);
            model.getCartaPescata().setScoperta(true);
            cartaTemporanea = cartaDaScambiare;
            model.getManoComputer().getCarte().set(indice, model.getCartaPescata());
            model.setCartaPescata(cartaTemporanea);
            view.getGestoreFinestreView().getGestoreAudio().play(0);
            model.notifica();

            view.getGestoreFinestreView().getCartaPescata().setDisable(false);
            System.out.println("carta speciale scambiata");

            if (model.getManoComputer().almenoUnaCartaCoperta() == false) {
                model.getManoComputer().setHaVinto(true);
                view.getGestoreFinestreView().getGestoreAudio().play(0);
                model.scartaCarta();
                togliCartaSulTavolo();
                model.setTurnoExtraIniziato(true);
                System.out.println("turno extra iniziato!");
            }
        }
    }

    /**
     * Metodo che definisce l'operazione di scambio speciale di carte come King e Joker del computer 2
     */
    public void scambioKJComputer2() {
        if (view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-joker-14.png"))) || view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + model.getCartaPescata().getSegno().toLowerCase() + "-13.png")))) {

            Integer[] listaIndici = ((model.listaCarteCoperte()));
            Collections.shuffle(Arrays.asList(listaIndici));
            if (model.getManoComputer2().almenoUnaCartaCoperta() == false) {
                model.scartaCarta();
                togliCartaSulTavolo();
                if (model.getTurnoExtra()==0){
//                    if (vittorieGiocatoreInPartita() == 9 && model.getManoComputer().isHaVinto()) {
//                        view.getGestoreFinestreView().vittoria(view.getGestoreFinestreView().getAnchorPane());
//                        return;
//                    }
                    model.getManoComputer2().setHaVinto(true);
                    ricomincia();
                    model.fermaTurnoComputer();
                }
                return;
            }
            int indice = listaIndici[0];

            CardModel cartaDaScambiare = model.getManoComputer2().getCarte().get(indice);
            CardModel cartaTemporanea;
            cartaDaScambiare.setScoperta(true);
            model.getCartaPescata().setScoperta(true);
            view.getGestoreFinestreView().getGestoreAudio().play(0);
            cartaTemporanea = cartaDaScambiare;
            model.getManoComputer2().getCarte().set(indice, model.getCartaPescata());
            model.setCartaPescata(cartaTemporanea);
            view.getGestoreFinestreView().getGestoreAudio().play(0);
            model.notifica();

            view.getGestoreFinestreView().getCartaPescata().setDisable(false);
            System.out.println("carta speciale scambiata");

            if (model.getManoComputer2().almenoUnaCartaCoperta() == false) {
                model.getManoComputer2().setHaVinto(true);
                view.getGestoreFinestreView().getGestoreAudio().play(0);
                model.scartaCarta();
                togliCartaSulTavolo();
                model.setTurnoExtraIniziato(true);
                System.out.println("turno extra iniziato!");
            }
        }
    }

    /**
     * Metodo che definisce l'operazione di scambio speciale di carte come King e Joker del computer 3
     */
    public void scambioKJComputer3() {
        if (view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-joker-14.png"))) || view.getGestoreFinestreView().getCartaPescata().getImage().getUrl().equals(String.valueOf(Main.class.getResource("/com/example/jtrash/Playing Cards/card-" + model.getCartaPescata().getSegno().toLowerCase() + "-13.png")))) {

            // prendo l'indice di tutte le carte coperte della mano del computer e le metto in una lista,
            // poi randomicamente se ne sceglie una
            Integer[] listaIndici = ((model.listaCarteCoperte()));
            Collections.shuffle(Arrays.asList(listaIndici));
            if (model.getManoComputer3().almenoUnaCartaCoperta() == false) {
                model.scartaCarta();
                togliCartaSulTavolo();
                if (model.getTurnoExtra()==0){
//                    if (vittorieGiocatoreInPartita() == 9 && model.getManoComputer().isHaVinto()) {
//                        view.getGestoreFinestreView().vittoria(view.getGestoreFinestreView().getAnchorPane());
//                        return;
//                    }
                    model.getManoComputer3().setHaVinto(true);
                    ricomincia();
                    model.fermaTurnoComputer();
                }
                return;
            }
            int indice = listaIndici[0];

            CardModel cartaDaScambiare = model.getManoComputer3().getCarte().get(indice);
            CardModel cartaTemporanea;
            cartaDaScambiare.setScoperta(true);
            model.getCartaPescata().setScoperta(true);
            cartaTemporanea = cartaDaScambiare;
            model.getManoComputer3().getCarte().set(indice, model.getCartaPescata());
            model.setCartaPescata(cartaTemporanea);
            view.getGestoreFinestreView().getGestoreAudio().play(0);
            model.notifica();

            view.getGestoreFinestreView().getCartaPescata().setDisable(false);
            System.out.println("carta speciale scambiata");

            if (model.getManoComputer3().almenoUnaCartaCoperta() == false) {
                model.getManoComputer3().setHaVinto(true);
                view.getGestoreFinestreView().getGestoreAudio().play(0);
                model.scartaCarta();
                togliCartaSulTavolo();
                model.setTurnoExtraIniziato(true);

                System.out.println("turno extra iniziato!");
            }
        }
    }

    /**
     * Metodo che rimuove la carta sul tavolo pescata
     */
    public void togliCartaSulTavolo(){
        view.getGestoreFinestreView().getAnchorPane().getChildren().remove(view.getGestoreFinestreView().getCartaPescata());
    }

    /**
     * Metodo che chiama il metodo del Model per fermare il turno dei computer
     */
    public void fermaTurnoComputer() {
        model.fermaTurnoComputer();
    }

    /**
     * Metodo che, in base al numero di giocatori selezionato, avvia la partita
     */
    public void avviaPartita(int giocatori) {
        model.avvia(giocatori, false);
        resetTimer();
        if (model.getProfiloGiocatore()!=null){
            System.out.println(model.getProfiloGiocatore().getPassword());
            System.out.println(model.getProfiloGiocatore().getVittorie());
        }
    }

    /**
     * Metodo che salva le statistiche del giocatore quando la partita termina
     */
    public void saveGame(){
        model.aggiornaTempoGiocatore();
        model.getProfiloGiocatore().incrementaPartiteGiocate();
        model.getProfiloGiocatore().setLivello(model.getProfiloGiocatore().getVittorie() / 2);
        model.saveGame(model.getProfiloGiocatore().getUsername());
    }

    /**
     * Metodo che, dopo la fine di un turno extra e in base al numero di giocatori in partita,
     * salva le statistiche dei giocatori in partita, crea nuovi Model e Controller e
     * pulisce la View, permettendo così di iniziare un nuovo turno
     */
    public void ricomincia() {
        int numeroDiGiocatori;
        int vittorieGiocatore;
        int vittorieComputer;
        int vittorieComputer2;
        int vittorieComputer3;
        int timerPartita;
        boolean haVintoGiocatore;
        boolean haVintoComp;
        boolean haVintoComp2;
        boolean haVintoComp3;
        ProfiloGiocatore profiloGiocatore;
        Controller controller;

        switch (model.getNumeroDiGiocatori()) {
            case 2 :
                // setta n.giocatori
                numeroDiGiocatori = model.getNumeroDiGiocatori();
                vittorieGiocatore = model.getManoGiocatore().getQuanteVittorie();
                vittorieComputer = model.getManoComputer().getQuanteVittorie();
                timerPartita = model.getSecondiTimer();
                model.getTimer().stop();
                haVintoGiocatore = model.getManoGiocatore().isHaVinto();
                haVintoComp = model.getManoComputer().isHaVinto();
                profiloGiocatore = model.getProfiloGiocatore();

                // crea nuovo model
                model = new Model();

                // nuovo controller
                controller = new Controller(model, view);

                view.getGestoreFinestreView().setEventController(controller.getEventController());

                controller.osserva();

                // pulisco view
                view.getGestoreFinestreView().getAnchorPane().getChildren().clear();

                //setta numero di giocatori
                model.setNumeroDiGiocatori(numeroDiGiocatori);
                if (haVintoGiocatore) model.getManoGiocatore().setQuanteVittorie(vittorieGiocatore + 1); //Vai a modificare tutti i casi in cui un giocatore vince e sostituisci setVittorie con setHaVinto(true), poi qui dentro ricomincia controlli come in questa riga se il giocatore in questione abbia vinto, e, solo allora, gli aumenti le vittorie
                else model.getManoGiocatore().setQuanteVittorie(vittorieGiocatore);

                if (haVintoComp) model.getManoComputer().setQuanteVittorie(vittorieComputer + 1);
                else model.getManoComputer().setQuanteVittorie(vittorieComputer);

                model.setSecondiTimer(timerPartita);
                model.setProfiloGiocatore(profiloGiocatore);

                System.out.println(haVintoGiocatore);
                System.out.println(haVintoComp);
                System.out.println(model.getManoGiocatore().getQuanteVittorie());
                System.out.println(model.getManoComputer().getQuanteVittorie());

                model.setEventController(controller.getEventController());

                // mi rimetto in finestraDiGioco
                view.getGestoreFinestreView().finestraDiGioco(view.getGestoreFinestreView().getAnchorPane());

                // avvia dal nuovo model
                model.avvia(numeroDiGiocatori,true);

                break;

            case 3 :
                // setta n.giocatori
                numeroDiGiocatori = model.getNumeroDiGiocatori();
                vittorieGiocatore = model.getManoGiocatore().getQuanteVittorie();
                vittorieComputer = model.getManoComputer().getQuanteVittorie();
                vittorieComputer2 = model.getManoComputer2().getQuanteVittorie();
                timerPartita = model.getSecondiTimer();
                model.getTimer().stop();
                haVintoGiocatore = model.getManoGiocatore().isHaVinto();
                haVintoComp = model.getManoComputer().isHaVinto();
                haVintoComp2 = model.getManoComputer2().isHaVinto();
                profiloGiocatore = model.getProfiloGiocatore();

                // crea nuovo model
                model = new Model();

                // nuovo controller
                controller = new Controller(model, view);

                view.getGestoreFinestreView().setEventController(controller.getEventController());

                controller.osserva();

                // pulisco view
                view.getGestoreFinestreView().getAnchorPane().getChildren().clear();

                //setta numero di giocatori
                model.setNumeroDiGiocatori(numeroDiGiocatori);
                if (haVintoGiocatore) model.getManoGiocatore().setQuanteVittorie(vittorieGiocatore + 1);
                else model.getManoGiocatore().setQuanteVittorie(vittorieGiocatore);

                if (haVintoComp) model.getManoComputer().setQuanteVittorie(vittorieComputer + 1);
                else model.getManoComputer().setQuanteVittorie(vittorieComputer);

                if (haVintoComp2) model.getManoComputer2().setQuanteVittorie(vittorieComputer2 + 1);
                else model.getManoComputer2().setQuanteVittorie(vittorieComputer2);

                model.setSecondiTimer(timerPartita);
                model.setProfiloGiocatore(profiloGiocatore);
                model.setEventController(controller.getEventController());

                // mi rimetto in finestraDiGioco
                view.getGestoreFinestreView().finestraDiGioco(view.getGestoreFinestreView().getAnchorPane());

                // avvia dal nuovo model
                model.avvia(numeroDiGiocatori,true);

                break;

            case 4:
                numeroDiGiocatori = model.getNumeroDiGiocatori();
                vittorieGiocatore = model.getManoGiocatore().getQuanteVittorie();
                vittorieComputer = model.getManoComputer().getQuanteVittorie();
                vittorieComputer2 = model.getManoComputer2().getQuanteVittorie();
                vittorieComputer3 = model.getManoComputer3().getQuanteVittorie();
                timerPartita = model.getSecondiTimer();
                model.getTimer().stop();
                haVintoGiocatore = model.getManoGiocatore().isHaVinto();
                haVintoComp = model.getManoComputer().isHaVinto();
                haVintoComp2 = model.getManoComputer2().isHaVinto();
                haVintoComp3 = model.getManoComputer3().isHaVinto();
                profiloGiocatore = model.getProfiloGiocatore();

                // crea nuovo model
                model = new Model();

                // nuovo controller
                controller = new Controller(model, view);

                view.getGestoreFinestreView().setEventController(controller.getEventController());

                controller.osserva();

                // pulisco view
                view.getGestoreFinestreView().getAnchorPane().getChildren().clear();

                //setta numero di giocatori
                model.setNumeroDiGiocatori(numeroDiGiocatori);
                if (haVintoGiocatore) model.getManoGiocatore().setQuanteVittorie(vittorieGiocatore + 1);
                else model.getManoGiocatore().setQuanteVittorie(vittorieGiocatore);

                if (haVintoComp) model.getManoComputer().setQuanteVittorie(vittorieComputer + 1);
                else model.getManoComputer().setQuanteVittorie(vittorieComputer);

                if (haVintoComp2) model.getManoComputer2().setQuanteVittorie(vittorieComputer2 + 1);
                else model.getManoComputer2().setQuanteVittorie(vittorieComputer2);

                if (haVintoComp3) model.getManoComputer3().setQuanteVittorie(vittorieComputer3 + 1);
                else model.getManoComputer3().setQuanteVittorie(vittorieComputer3);

                model.setSecondiTimer(timerPartita);
                model.setProfiloGiocatore(profiloGiocatore);
                model.setEventController(controller.getEventController());

                // mi rimetto in finestraDiGioco
                view.getGestoreFinestreView().finestraDiGioco(view.getGestoreFinestreView().getAnchorPane());

                // avvia dal nuovo model
                model.avvia(numeroDiGiocatori,true);

                break;
        }
    }

    /**
     * Metodo che chiama il metodo play della classe Gestore Audio della View
     */
    public void riproduciSuono() {
        view.getGestoreFinestreView().getGestoreAudio().play(0);
    }

    /**
     * Metodo che aumenta la statistica delle partite vinte nel profilo del giocatore
     */
    public void aumentaPartiteVinte(){
        model.getProfiloGiocatore().incrementaVittorie();
        System.out.println(model.getProfiloGiocatore().getVittorie());
    }

    /**
     * Metodo che aumenta la statistica delle partite perse nel profilo del giocatore
     */
    public void aumentaPartitePerse(){
        model.getProfiloGiocatore().incrementaSconfitte();
    }

    /**
     * Metodo che restituisce true se si ha fatto l'accesso al profilo, altrimenti false
     */
    public boolean loginFatto() {
        if (model.getProfiloGiocatore() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo che permette di iniziare una nuova partita, in base a se è stato effettuato o meno un login nel profilo
     */
    public void loggatoPerMenù(){
        if (loginFatto() == true){

            view.getGestoreFinestreView().getAnchorPane().lookup("#nuovaPartita").setDisable(false);
            view.getGestoreFinestreView().getAnchorPane().getChildren().remove(view.getGestoreFinestreView().getAnchorPane().lookup("#profilo"));
            }

    }

    /**
     * Metodo che gestisce la creazione di un nuovo profilo, controllando se sia già esistente
     */
    public void gestioneCreazioneProfilo(String username, String password, String avatar) {
        if (model.getGestoreProfili().profiloEsiste(username) == true) {
            System.out.println("Il profilo è già esistente");
        } else {
            model.setProfiloGiocatore(new ProfiloGiocatore(username, password, avatar));
            model.getGestoreProfili().aggiungiProfilo(model.getProfiloGiocatore());
            model.saveGame(model.getProfiloGiocatore().getUsername());
            view.getGestoreFinestreView().getAnchorPane().lookup("#nuovaPartita").setDisable(false);
            view.getGestoreFinestreView().getAnchorPane().lookup("#statsProfilo").setDisable(false);
            view.getGestoreFinestreView().getAnchorPane().getChildren().remove(view.getGestoreFinestreView().getAnchorPane().lookup("#profilo"));

            System.out.println("Il profilo è stato creato");
        }
    }

    /**
     * Metodo che gestisce il login nel profilo, controllando se si stia accedendo ad un profilo esistente o meno
     */
    public void gestioneLoginProfilo(String username, String password) {
        model.getGestoreProfili().caricaProfiliDaFile(username);
        System.out.println(model.getGestoreProfili().getProfili());
        if (!model.getGestoreProfili().profiloEsiste(username) == true) {
//            if (model.getGestoreProfili().getProfili().get(username).getPassword().equals(password)) {
            System.out.println("Il profilo non esiste, crealo");
        } else {

            model.getGestoreProfili().caricaProfiliDaFile(username);
            model.setProfiloGiocatore(model.getGestoreProfili().getProfilo(username));
            view.getGestoreFinestreView().getAnchorPane().lookup("#nuovaPartita").setDisable(false);
            view.getGestoreFinestreView().getAnchorPane().lookup("#statsProfilo").setDisable(false);
            view.getGestoreFinestreView().getAnchorPane().getChildren().remove(view.getGestoreFinestreView().getAnchorPane().lookup("#profilo"));

            System.out.println("Il profilo hai fatto il login");

        }
    }

    /**
     * Metodo che chiama il metodo del timer "play" del Model
     */
    public void startTimer() {
        model.getTimer().play();
    }

    /**
     * Metodo che chiama il metodo del timer "stop" del Model
     */
    public void stopTimer() {
        model.getTimer().stop();
    }

    /**
     * Metodo che chiama il metodo del timer "reset" del Model
     */
    public void resetTimer() {
        model.setSecondiTimer(0);
    }

    /**
     * Metodo che restituisce l'username del profilo
     */
    public String username() {
        return model.getProfiloGiocatore().getUsername();
    }

    /**
     * Metodo che restituisce la statistica delle partite giocate del profilo
     */
    public int partiteGiocate() {
        return model.getProfiloGiocatore().getPartiteGiocate();
    }

    /**
     * Metodo che restituisce la statistica delle partite vinte del profilo
     */
    public int partiteVinte() {
        return model.getProfiloGiocatore().getVittorie();
    }

    /**
     * Metodo che restituisce la statistica delle partite perse del profilo
     */
    public int partitePerse() {
        return (model.getProfiloGiocatore().getSconfitte());
    }

    /**
     * Metodo che restituisce la statistica del tempo di gioco del profilo
     */
    public int tempoDiGioco() {
        return model.getProfiloGiocatore().getTempoDiGioco();
    }

    /**
     * Metodo che restituisce il path dell'avatar scelto durante la creazione del profilo
     */
    public String pathAvatar() {
        return model.getProfiloGiocatore().getAvatar();
    }

    /**
     * Metodo che restituisce la statistica del livello del profilo
     */
    public int livello() {
        return model.getProfiloGiocatore().getLivello();
    }

    /**
     * Metodo che termina l'esecuzione del gioco
     */
    public void terminaEsecuzione() {
        Platform.exit();
    }

}
