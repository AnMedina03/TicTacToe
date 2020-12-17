/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.common;

import java.util.LinkedList;

/**
 *
 * @author Andrés Medina Jácome
 */
public class Campeonato {
    private int id;
    private String fecha;

    public Campeonato(int id) {
        this.id = id;
    }

    public Campeonato(int id, String fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
