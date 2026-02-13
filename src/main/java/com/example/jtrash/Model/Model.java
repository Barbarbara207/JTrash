package com.example.jtrash.Model;


import com.example.jtrash.Controller.EventController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Model extends Observable  {
    private DeckModel deck;

    private PlayerHandModel manoGiocatore;
    private CompHandModel manoComputer;
    private CompHand2Model manoComputer2;
    private CompHand3Model manoComputer3;

    private EventController eventController;
    private CardModel cartaPescata;
    private ScartiModel scarti;
    private ScheduledExecutorService executorService;
    private Boolean turnoExtraIniziato;
    private int turnoExtra;
    private int numeroDiGiocatori;
    private int giocatoreAttuale;
    private ProfiloGiocatore profiloGiocatore;
    private GestoreProfili gestoreProfili;
    private Timeline timer;
    private int secondiTimer;

    /**
     * Enum che contiene i semi delle carte
     */
    public enum CardSuit {
        Hearts, Diamonds, Clubs, Spades
    }

    /**
     * Enum che contiene i valori delle carte
     */
    public enum CardValue {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN
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
     * Costruttore della classe Model
     */
    public Model() {
        deck = new DeckModel(new ArrayList<>());
        manoGiocatore = new PlayerHandModel(new ArrayList<>(), 0);
        manoComputer = new CompHandModel(new ArrayList<>(), 0);
        manoComputer2 = new CompHand2Model(new ArrayList<>(),0);
        manoComputer3 = new CompHand3Model(new ArrayList<>(),0);
        cartaPescata = null;
        scarti = new ScartiModel(new ArrayList<>());
        profiloGiocatore = null;
        gestoreProfili = new GestoreProfili();
    }

    /**
     * Metodo che, dato il numero di giocatori e, in base al fatto se si stia
     * cominciando una nuova partita o continuandone una dopo il turno extra,
     * permette di avviare la partita o il nuovo turno
     */
    public void avvia(int giocatori, boolean continuaPartita){
        System.out.println("Vittorie giocatore= " + manoGiocatore.getQuanteVittorie());
        System.out.println("Vittorie computer= " + manoComputer.getQuanteVittorie());
        switch (giocatori) {
            case 2:
                if (continuaPartita == true) {
                    createDeck();
                    shuffleDeck();
                    manoGiocatore();
                    manoComputer();
                    scartaPrimaCarta();
                    shuffleDeck();
                    turnoExtraIniziato = false;
                    numeroDiGiocatori = giocatori;
                    turnoExtra = giocatori - 1;
                } else if (continuaPartita==false) {
                    createDeck();
                    shuffleDeck();
                    manoGiocatore.setQuanteVittorie(0);
                    manoComputer.setQuanteVittorie(0);
                    manoComputer2=null;
                    manoComputer3=null;
                    manoGiocatore();
                    manoComputer();
                    scartaPrimaCarta();
                    shuffleDeck();
                    turnoExtraIniziato = false;
                    numeroDiGiocatori = giocatori;
                    turnoExtra = giocatori - 1;

                }
                break;

            case 3:
                if (continuaPartita == true) {
                    createDoubleDeck();
                    shuffleDeck();
                    manoGiocatore();
                    manoComputer();
                    manoComputer2();
                    scartaPrimaCarta();
                    shuffleDeck();
                    turnoExtraIniziato = false;
                    numeroDiGiocatori = giocatori;
                    turnoExtra = giocatori - 1;
                } else if (continuaPartita==false) {
                    createDoubleDeck();
                    shuffleDeck();
                    manoGiocatore.setQuanteVittorie(0);
                    manoComputer.setQuanteVittorie(0);
                    manoComputer2=new CompHand2Model(new ArrayList<>(), 0);
                    manoComputer3=null;
                    manoComputer2.setQuanteVittorie(0);
                    manoGiocatore();
                    manoComputer();
                    manoComputer2();
                    scartaPrimaCarta();
                    shuffleDeck();
                    turnoExtraIniziato = false;
                    numeroDiGiocatori = giocatori;
                    turnoExtra = giocatori - 1;
                }
                break;

            case 4:
                if (continuaPartita == true) {
                    createDoubleDeck();
                    shuffleDeck();
                    manoGiocatore();
                    manoComputer();
                    manoComputer2();
                    manoComputer3();
                    scartaPrimaCarta();
                    shuffleDeck();
                    turnoExtraIniziato = false;
                    numeroDiGiocatori = giocatori;
                    turnoExtra = giocatori - 1;
                } else if (continuaPartita==false) {
                    createDoubleDeck();
                    shuffleDeck();
                    manoGiocatore.setQuanteVittorie(0);
                    manoComputer.setQuanteVittorie(0);
                    manoComputer2=new CompHand2Model(new ArrayList<>(), 0);
                    manoComputer3=new CompHand3Model(new ArrayList<>(), 0);
                    manoComputer2.setQuanteVittorie(0);
                    manoComputer3.setQuanteVittorie(0);
                    manoGiocatore();
                    manoComputer();
                    manoComputer2();
                    manoComputer3();
                    scartaPrimaCarta();
                    shuffleDeck();
                    turnoExtraIniziato = false;
                    numeroDiGiocatori = giocatori;
                    turnoExtra = giocatori - 1;
                }
        }
        if (timer == null) {
            creaTimer();
        }
        timer.play();
        cartaPescata = null;
    }

    /**
     * Metodo che crea il mazzo da gioco per una partita contro 1 giocatore, aggiunge i due Joker e notifica la View
     */
    public void createDeck() {

        deck.getMazzo().removeAll(deck.getMazzo());

        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value: CardValue.values()) {
                deck.getMazzo().add(new CardModel(value.toString(), suit.toString(), false));
            }
        }
        deck.getMazzo().add(new CardModel("FOURTEEN", "Joker", false));
        deck.getMazzo().add(new CardModel("FOURTEEN", "Joker", false));

        setChanged();
        notifyObservers(deck.listaMazzo());
    }

    /**
     * Metodo che crea due mazzi da gioco per le partite contro 2/3 giocatori, aggiunge i quattro Joker e notifica la View
     */
    public void createDoubleDeck() {

        deck.getMazzo().removeAll(deck.getMazzo());

        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value: CardValue.values()) {
                deck.getMazzo().add(new CardModel(value.toString(), suit.toString(), false));
            }
        }
        deck.getMazzo().add(new CardModel("FOURTEEN", "Joker", false));
        deck.getMazzo().add(new CardModel("FOURTEEN", "Joker", false));

        setChanged();
        notifyObservers(deck.listaMazzo());

        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value: CardValue.values()) {
                deck.getMazzo().add(new CardModel(value.toString(), suit.toString(), false));
            }
        }
        deck.getMazzo().add(new CardModel("FOURTEEN", "Joker", false));
        deck.getMazzo().add(new CardModel("FOURTEEN", "Joker", false));

        setChanged();
        notifyObservers(deck.listaMazzo());
    }

    /**
     * Metodo che mescola il mazzo da gioco
     */
    public void shuffleDeck() {
        Collections.shuffle(deck.getMazzo());

        if (deck.getMazzo().get(0).getValore().equals("THIRTEEN") || deck.getMazzo().get(0).getValore().equals("FOURTEEN")) {
            shuffleDeck();
        }

        setChanged();
        notifyObservers(deck.listaMazzo());
    }

    /**
     * Metodo che permette l'operazione di pesca di una carta dal mazzo principale, notificando la View
     */
    public CardModel pescaCarta() {
        // controllo se il mazzo è vuoto, finché non è vuoto pesca la prima carta
        if (deck.getMazzo().isEmpty()) {
            return null;
        } else {
            notifica();
            cartaPescata = deck.getMazzo().remove(0);
            System.out.println(deck.getMazzo().size());
            return cartaPescata;
        }

    }

    /**
     * Metodo che permette l'operazione di pesca di una carta dal mazzo degli scarti, notificando la View
     */
    public void pescaCartaDaScarti() {
        if (scarti.getCarteScartate().isEmpty()) {
        } else {
            cartaPescata = scarti.getCarteScartate().remove(0);
            notifica();
        }
    }

    /**
     * Metodo che crea la mano del giocatore e notifica la View
     */
    public void manoGiocatore() {

        manoGiocatore = new PlayerHandModel(new ArrayList<>(), manoGiocatore.getQuanteVittorie());

        for (int i = 0; i < 10 - (manoGiocatore.getQuanteVittorie()); i ++ ) {
            manoGiocatore.getCarte().add(deck.getMazzo().remove(0));
        }
        setChanged();
        notifyObservers(manoGiocatore.listaManoGiocatore());
    }

    /**
     * Metodo che crea la mano del computer e notifica la View
     */
    public void manoComputer() {

        manoComputer = new CompHandModel(new ArrayList<>(), manoComputer.getQuanteVittorie());

        for (int i = 0; i < 10 - (manoComputer.getQuanteVittorie()); i ++ ) {
            manoComputer.getCarte().add(deck.getMazzo().remove(i));
        }
        setChanged();
        notifyObservers(getManoComputer().listaManoComp());
    }

    /**
     * Metodo che crea la mano del computer 2 e notifica la View
     */
    public void manoComputer2() {

        manoComputer2 = new CompHand2Model(new ArrayList<>(), manoComputer2.getQuanteVittorie());

        for (int i = 0; i < 10 - (manoComputer2.getQuanteVittorie()); i ++ ) {
            manoComputer2.getCarte().add(deck.getMazzo().remove(i));
        }
        setChanged();
        notifyObservers(getManoComputer2().listaManoComp2());
    }

    /**
     * Metodo che crea la mano del computer 3 e notifica la View
     */
    public void manoComputer3() {

        manoComputer3 = new CompHand3Model(new ArrayList<>(), manoComputer3.getQuanteVittorie());

        for (int i = 0; i < 10 - (manoComputer3.getQuanteVittorie()); i ++ ) {
            manoComputer3.getCarte().add(deck.getMazzo().remove(i));
        }
        setChanged();
        notifyObservers(getManoComputer3().listaManoComp3());
    }

    /**
     * Metodo che permette l'operazione di scambio della carta sul tavolo con una carta della mano
     */
    public void scambiaCarta() {
        CardModel cartaDaScambiare = manoGiocatore.getCarte().get(Integer.parseInt(enumToInt(getCartaPescata().getValore()))-1);
        CardModel cartaTemporanea;
        // scambio effettivamente la carta e notifico gli observer, nel controller chiamo questo metodo
        cartaDaScambiare.setScoperta(true);
        cartaPescata.setScoperta(true);
        cartaTemporanea = cartaDaScambiare;
        manoGiocatore.getCarte().set(Integer.parseInt(enumToInt(getCartaPescata().getValore()))-1, cartaPescata);
        cartaPescata = cartaTemporanea;
        notifica();


        if (!manoGiocatore.almenoUnaCartaCoperta()) {
            manoGiocatore.setHaVinto(true);
            scartaCarta();
            eventController.togliCartaSulTavolo();
            turnoExtraIniziato = true;

            avviaTurnoComputer();
            System.out.println("turno extra iniziato!");
        }
    }

    /**
     * Metodo che permette l'operazione di scambio della carta sul tavolo con una carta della mano dei computer
     */
    public void scambiaCartaComputer() {
        CardModel cartaDaScambiare;
        CardModel cartaTemporanea;
        switch (giocatoreAttuale) {
            case 0 :
                // turno giocatore, non si fa niente qui
                break;
            case 1 :
                cartaDaScambiare = manoComputer.getCarte().get(Integer.parseInt(enumToInt(getCartaPescata().getValore()))-1);
                // scambio effettivamente la carta e notifico gli observer, nel controller chiamo questo metodo
                cartaDaScambiare.setScoperta(true);
                cartaPescata.setScoperta(true);
                cartaTemporanea = cartaDaScambiare;
                manoComputer.getCarte().set(Integer.parseInt(enumToInt(getCartaPescata().getValore()))-1, cartaPescata);
                cartaPescata = cartaTemporanea;
                notifica();
                if (manoComputer.almenoUnaCartaCoperta() == false) {
                    manoComputer.setHaVinto(true);
                    scartaCarta();
                    turnoExtraIniziato = true;
                    eventController.togliCartaSulTavolo();
//                    if (eventController.vittorieGiocatoreInPartita() == 9 && manoComputer.isHaVinto()) {
//                        eventController.chiamaSchermataVittoria();
//                        return;
//                    }

                    System.out.println("turno extra iniziato!");
                }
                break;
            case 2 :
                cartaDaScambiare = manoComputer2.getCarte().get(Integer.parseInt(enumToInt(getCartaPescata().getValore()))-1);
                // scambio effettivamente la carta e notifico gli observer, nel controller chiamo questo metodo
                cartaDaScambiare.setScoperta(true);
                cartaPescata.setScoperta(true);
                cartaTemporanea = cartaDaScambiare;
                manoComputer2.getCarte().set(Integer.parseInt(enumToInt(getCartaPescata().getValore()))-1, cartaPescata);
                cartaPescata = cartaTemporanea;
                notifica();
                if (manoComputer2.almenoUnaCartaCoperta() == false) {
                    manoComputer2.setHaVinto(true);
                    scartaCarta();
                    turnoExtraIniziato = true;
                    eventController.togliCartaSulTavolo();
//                    if (eventController.vittorieGiocatoreInPartita() == 9 && manoComputer.isHaVinto()) {
//                        eventController.chiamaSchermataVittoria();
//                        return;
//                    }

                    System.out.println("turno extra iniziato!");
                }
                break;
            case 3 :
                cartaDaScambiare = manoComputer3.getCarte().get(Integer.parseInt(enumToInt(getCartaPescata().getValore()))-1);
                // scambio effettivamente la carta e notifico gli observer, nel controller chiamo questo metodo
                cartaDaScambiare.setScoperta(true);
                cartaPescata.setScoperta(true);
                cartaTemporanea = cartaDaScambiare;
                manoComputer3.getCarte().set(Integer.parseInt(enumToInt(getCartaPescata().getValore()))-1, cartaPescata);
                cartaPescata = cartaTemporanea;
                notifica();
                if (manoComputer3.almenoUnaCartaCoperta() == false) {
                    manoComputer3.setHaVinto(true);
                    scartaCarta();
                    turnoExtraIniziato = true;
                    eventController.togliCartaSulTavolo();
//                    if (eventController.vittorieGiocatoreInPartita() == 9 && manoComputer.isHaVinto()) {
//                        eventController.chiamaSchermataVittoria();
//                        return;
//                    }

                    System.out.println("turno extra iniziato!");
                }
                break;
        }

    }

    /**
     * Metodo che restituisce l'indice di una carta che ha un determinato valore e segno,
     * necessario per poter effettuare lo scambio speciale di carte come King o Joker
     */
    public int ottieniIndice(String valore, String segno) {

        for (CardModel carta : manoGiocatore.getCarte()) {
            if (carta.getValore().equals(valore) && carta.getSegno().equals(segno)) {
                return  manoGiocatore.getCarte().indexOf(carta);
            } else {

            }
        }
        return -1;
    }

    /**
     * Metodo che restituisce un Array di interi, che contiene gli indici delle carte
     * coperte presenti nelle mani dei computer, necessario per poter effettuare lo
     * scambio speciale di carte come King o Joker da parte dei computer
     */
    public Integer[] listaCarteCoperte() {
        ArrayList<Integer> listaCarteCoperte = new ArrayList<>();

        switch (giocatoreAttuale) {
            case 0 :
                // turno giocatore
                break;

            case 1 :
                for (CardModel carta : manoComputer.getCarte()) {
                    if (carta.isScoperta() == false) {
                        int indice = manoComputer.getCarte().indexOf(carta);
                        listaCarteCoperte.add(indice);
                    }
                }
                return listaCarteCoperte.stream().toArray(Integer[] :: new);

            case 2 :
                for (CardModel carta : manoComputer2.getCarte()) {
                    if (carta.isScoperta() == false) {
                        int indice = manoComputer2.getCarte().indexOf(carta);
                        listaCarteCoperte.add(indice);
                    }
                }
                return listaCarteCoperte.stream().toArray(Integer[] :: new);

            case 3 :
                for (CardModel carta : manoComputer3.getCarte()) {
                    if (carta.isScoperta() == false) {
                        int indice = manoComputer3.getCarte().indexOf(carta);
                        listaCarteCoperte.add(indice);
                    }
                }
                return listaCarteCoperte.stream().toArray(Integer[] :: new);
        }
        return null;
    }

    /**
     * Metodo che scarta la prima carta del mazzo all'inizio di ogni partita,
     * andando a creare di fatto il mazzo degli scarti. Notifica poi la View
     */
    public void scartaPrimaCarta() {
        scarti.getCarteScartate().removeAll(scarti.getCarteScartate());
        scarti.getCarteScartate().add(deck.getMazzo().remove(0));


        notifica();
    }

    /**
     * Metodo che permette di effettuare l'operazione di scarto di una carta sul tavolo,
     * se non si ha possibilità di scambiarla
     */
    public void scartaCarta() {
        // scarto effettivamente la carta e notifico gli observer, nel controller chiamo questo metodo
        scarti.getCarteScartate().add(0, cartaPescata);
        cartaPescata = null;
        if (cartaPescata != null) {
            //se la cartaPescata non è null, non posso pescare
        }

        if (turnoExtraIniziato == true) {
            System.out.println("turno extra =  " + turnoExtra);
            turnoExtra --;
            System.out.println("turno extra =  " + turnoExtra);
        }

        if (turnoExtraIniziato == true && turnoExtra == 0) {
            eventController.ricomincia();
            System.out.println("nuovo turno");
        }

        if (giocatoreAttuale==numeroDiGiocatori-1) {
            giocatoreAttuale=0;
            // il computer non deve fare niente, è il turno del giocatore
        } else {
            System.out.println(giocatoreAttuale);
            giocatoreAttuale++;
            System.out.println(giocatoreAttuale);
        }

        setChanged();
        notifyObservers(scarti.listaScarti());
    }

    /**
     * Metodo che permette di avviare i turni dei computer
     */
    public void avviaTurnoComputer() {
        switch (giocatoreAttuale) {
            case 0:
                // turno giocatore, non fare niente
                break;
            case 1 :
                if (turnoExtra != 0) {
                    executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(this::turnoComputer, 0, 1500, TimeUnit.MILLISECONDS);
                }
                break;
            case 2:
                if (turnoExtra != 0) {
                    executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(this::turnoComputer2, 0, 1500, TimeUnit.MILLISECONDS);
                }
                break;
            case 3:
                if (turnoExtra != 0) {
                    executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(this::turnoComputer3, 0, 1500, TimeUnit.MILLISECONDS);
                }
                break;
        }
    }

    /**
     * Metodo che permette di fermare il turno dei computer
     */
    public void fermaTurnoComputer() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    /**
     * Metodo che delinea tutte le operazioni possibili per ogni turno del computer
     */
    public void turnoComputer() {
        Platform.runLater(() -> {
            if (cartaPescata == null) {
                if (Integer.parseInt(enumToInt(scarti.getCarteScartate().get(0).getValore())) <= 10 - (manoComputer.getQuanteVittorie())) {
                    if (manoComputer.getCarte().get(Integer.parseInt(enumToInt(String.valueOf(scarti.getCarteScartate().get(0).getValore())))-1).isScoperta() == true) {
                        eventController.pescaComputer();
                    }
                    else if ((manoComputer.getCarte().get(Integer.parseInt(enumToInt(scarti.getCarteScartate().get(0).getValore())) - 1).isScoperta() == false) || ((enumToInt(scarti.getCarteScartate().get(0).getValore())).equals("THIRTEEN")) || (enumToInt(scarti.getCarteScartate().get(0).getValore())).equals("FOURTEEN")) {
                        eventController.pescaDaScarti();
                        System.out.println("Il computer ha pescato dagli scarti");
                        }
                    } else {
                    eventController.pescaComputer();
                    System.out.println("Il computer ha pescato");
                }
            } else {
                switch (cartaPescata.getValore()) {
                    case "FOURTEEN":
                        eventController.scambioKJComputer();
                        System.out.println("Il computer ha scambiato un jolly");
                        break;
                    case "THIRTEEN":
                        eventController.scambioKJComputer();
                        System.out.println("Il computer ha scambiato un re");
                        break;
                    default:
                        eventController.scambioCarteComputer();
                        System.out.println("Il computer ha scambiato");
                        if (cartaPescata == null) {
                            fermaTurnoComputer();
                            avviaTurnoComputer();// Fermare il turno automatico quando la carta è stata scartata
                            System.out.println("Il computer ha scartato");
                        }
                }
            }
        });
    }

    /**
     * Metodo che delinea tutte le operazioni possibili per ogni turno del computer 2
     */
    public void turnoComputer2() {
        Platform.runLater(() -> {
            if (cartaPescata == null) {
                if (Integer.parseInt(enumToInt(scarti.getCarteScartate().get(0).getValore())) <= 10 - (manoComputer2.getQuanteVittorie())) {
                    if (manoComputer2.getCarte().get(Integer.parseInt(enumToInt(String.valueOf(scarti.getCarteScartate().get(0).getValore())))-1).isScoperta() == true) {
                        eventController.pescaComputer();
                    }
                    else if ((manoComputer2.getCarte().get(Integer.parseInt(enumToInt(scarti.getCarteScartate().get(0).getValore())) - 1).isScoperta() == false) || ((enumToInt(scarti.getCarteScartate().get(0).getValore())).equals("THIRTEEN")) || (enumToInt(scarti.getCarteScartate().get(0).getValore())).equals("FOURTEEN")) {

                        eventController.pescaDaScarti();
                        System.out.println("Il computer 2 ha pescato dagli scarti");
                    }
                } else {
                    eventController.pescaComputer();
                    System.out.println("Il computer 2 ha pescato");
                }
            } else {
                switch (cartaPescata.getValore()) {
                    case "FOURTEEN":
                        eventController.scambioKJComputer2();
                        System.out.println("Il computer 2 ha scambiato un jolly");
                        break;
                    case "THIRTEEN":
                        eventController.scambioKJComputer2();
                        System.out.println("Il computer 2 ha scambiato un re");
                        break;
                    default:
                        eventController.scambioCarteComputer();
                        System.out.println("Il computer 2 ha scambiato");
                        if (cartaPescata == null) {
                            fermaTurnoComputer();
                            avviaTurnoComputer();// Ferma il turno automatico quando la carta è stata scartata
                            System.out.println("Il computer 2 ha scartato");
                        }
                }
            }
        });
    }

    /**
     * Metodo che delinea tutte le operazioni possibili per ogni turno del computer 3
     */
    public void turnoComputer3() {
        Platform.runLater(() -> {
            if (cartaPescata == null) {
                if (Integer.parseInt(enumToInt(scarti.getCarteScartate().get(0).getValore())) <= 10 - (manoComputer3.getQuanteVittorie())) {
                    if (manoComputer3.getCarte().get(Integer.parseInt(enumToInt(String.valueOf(scarti.getCarteScartate().get(0).getValore())))-1).isScoperta() == true) {
                        eventController.pescaComputer();
                    }
                    else if ((manoComputer3.getCarte().get(Integer.parseInt(enumToInt(scarti.getCarteScartate().get(0).getValore())) - 1).isScoperta() == false) || ((enumToInt(scarti.getCarteScartate().get(0).getValore())).equals("THIRTEEN")) || (enumToInt(scarti.getCarteScartate().get(0).getValore())).equals("FOURTEEN")) {
                        eventController.riproduciSuono();
                        eventController.pescaDaScarti();
                        System.out.println("Il computer 3 ha pescato dagli scarti");
                    }
                } else {
                    eventController.pescaComputer();
                    System.out.println("Il computer 3 ha pescato");
                }
            } else {
                switch (cartaPescata.getValore()) {
                    case "FOURTEEN":
                        eventController.scambioKJComputer3();
                        System.out.println("Il computer 3 ha scambiato un jolly");
                        break;
                    case "THIRTEEN":
                        eventController.scambioKJComputer3();
                        System.out.println("Il computer 3 ha scambiato un re");
                        break;
                    default:
                        eventController.scambioCarteComputer();
                        System.out.println("Il computer 3 ha scambiato");
                        if (cartaPescata == null) {
                            fermaTurnoComputer();
                            avviaTurnoComputer();// Ferma il turno automatico quando la carta è stata scartata
                            System.out.println("Il computer 3 ha scartato");
                        }
                }
            }
        });
    }

    /**
     * Metodo che crea il Timer
     */
    public void creaTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            secondiTimer++;
            setChanged();
            notifyObservers(secondiTimer);
        }));
        timer.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Metodo che permette di salvare nel profilo le statistiche di una partita nel momento in cui termina
     */
    public void saveGame(String fileName) {
        GestoreProfili gestoreProfili = new GestoreProfili();
        gestoreProfili.caricaProfiliDaFile(fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Path.of(gestoreProfili.getDirectoryProfili() + "/" + fileName).toFile()))) {
            oos.writeObject(profiloGiocatore);
        } catch (IOException e) {
            e.printStackTrace();}
    }

    /**
     * Metodo che aggiorna la statistica del tempo di gioco del profilo di un giocatore
     */
    public void aggiornaTempoGiocatore() {
        profiloGiocatore.setTempoDiGioco(profiloGiocatore.getTempoDiGioco() + secondiTimer);
    }

    /**
     * Metodo che notifica alla View tutti i possibili aggiornamenti del Model
     */
    public void notifica() {
        // mazzo
        setChanged();
        notifyObservers(deck.listaMazzo());
        // pesca
        setChanged();
        notifyObservers("Carta");
        // mescola
        setChanged();
        notifyObservers(deck.listaMazzo());
        // mano giocatore
        setChanged();
        notifyObservers(manoGiocatore.listaManoGiocatore());
        // mano computer
        setChanged();
        notifyObservers(manoComputer.listaManoComp());
        // mano computer 2
        if (manoComputer2!=null){
            setChanged();
            notifyObservers(manoComputer2.listaManoComp2());
        }
        // mano computer 3
        if (manoComputer3!=null){
            setChanged();
            notifyObservers(manoComputer3.listaManoComp3());
        }
        // scambia
        if (cartaPescata != null) {
            setChanged();
            notifyObservers(cartaPescata.listaCarta());
        }
        setChanged();
        notifyObservers(manoGiocatore.listaManoGiocatore());
        // scarta prima carta
        setChanged();
        notifyObservers(scarti.listaScarti());
        // scarta
        setChanged();
        notifyObservers(scarti.listaScarti());
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
    public CardModel getCartaPescata() {
        return cartaPescata;
    }

    /**
     * Setter
     */
    public void setCartaPescata(CardModel cartaPescata) {
        this.cartaPescata = cartaPescata;
    }

    /**
     * Getter
     */
    public PlayerHandModel getManoGiocatore() {
        return manoGiocatore;
    }

    /**
     * Setter
     */
    public void setManoGiocatore(PlayerHandModel manoGiocatore) {
        this.manoGiocatore = manoGiocatore;
    }

    /**
     * Getter
     */
    public CompHandModel getManoComputer() {
        return manoComputer;
    }

    /**
     * Setter
     */
    public void setManoComputer(CompHandModel manoComputer) {
        this.manoComputer = manoComputer;
    }

    /**
     * Getter
     */
    public CompHand2Model getManoComputer2() {
        return manoComputer2;
    }

    /**
     * Setter
     */
    public void setManoComputer2(CompHand2Model manoComputer2) {
        this.manoComputer2 = manoComputer2;
    }

    /**
     * Getter
     */
    public CompHand3Model getManoComputer3() {
        return manoComputer3;
    }

    /**
     * Setter
     */
    public void setManoComputer3(CompHand3Model manoComputer3) {
        this.manoComputer3 = manoComputer3;
    }

    /**
     * Getter
     */
    public Boolean getTurnoExtraIniziato() {
        return turnoExtraIniziato;
    }

    /**
     * Setter
     */
    public void setTurnoExtraIniziato(Boolean turnoExtraIniziato) {
        this.turnoExtraIniziato = turnoExtraIniziato;
    }

    /**
     * Getter
     */
    public int getTurnoExtra() {
        return turnoExtra;
    }

    /**
     * Setter
     */
    public void setTurnoExtra(int turnoExtra) {
        this.turnoExtra = turnoExtra;
    }

    /**
     * Getter
     */
    public int getNumeroDiGiocatori() {
        return numeroDiGiocatori;
    }

    /**
     * Setter
     */
    public void setNumeroDiGiocatori(int numeroDiGiocatori) {
        this.numeroDiGiocatori = numeroDiGiocatori;
    }

    /**
     * Getter
     */
    public int getGiocatoreAttuale() {
        return giocatoreAttuale;
    }

    /**
     * Setter
     */
    public void setGiocatoreAttuale(int giocatoreAttuale) {
        this.giocatoreAttuale = giocatoreAttuale;
    }

    /**
     * Getter
     */
    public ProfiloGiocatore getProfiloGiocatore() {
        return profiloGiocatore;
    }

    /**
     * Setter
     */
    public void setProfiloGiocatore(ProfiloGiocatore profiloGiocatore) {
        this.profiloGiocatore = profiloGiocatore;
    }

    /**
     * Getter
     */
    public GestoreProfili getGestoreProfili() {
        return gestoreProfili;
    }

    /**
     * Setter
     */
    public void setGestoreProfili(GestoreProfili gestoreProfili) {
        this.gestoreProfili = gestoreProfili;
    }

    /**
     * Getter
     */
    public Timeline getTimer() {
        return timer;
    }

    /**
     * Setter
     */
    public void setTimer(Timeline timer) {
        this.timer = timer;
    }

    /**
     * Getter
     */
    public int getSecondiTimer() {
        return secondiTimer;
    }

    /**
     * Setter
     */
    public void setSecondiTimer(int secondiTimer) {
        this.secondiTimer = secondiTimer;
    }

}
