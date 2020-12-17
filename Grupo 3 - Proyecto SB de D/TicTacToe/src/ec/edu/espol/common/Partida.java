/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.common;

import java.util.ArrayList;

/**
 *
 * @author Andrés Medina Jácome
 */
public class Partida {
    private int id;
    private Jugador winner;
    private ArrayList<Jugador> players;
    private Campeonato c;
    private int estado;

    public Partida(int id, Jugador winner, int estado) {
        this.id = id;
        this.winner = winner;
        this.estado = estado;
        this.players = new ArrayList<>(2);
    }

    public Partida(int id) {
        this.id = id;
        this.players = new ArrayList<>(2);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Jugador getWinner() {
        return winner;
    }

    public void setWinner(Jugador winner) {
        if(winner != null) this.winner = winner;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ArrayList<Jugador> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Jugador> players) {
        this.players = players;
    }

    public Campeonato getC() {
        return c;
    }

    public void setC(Campeonato c) {
        this.c = c;
    }
    
}
