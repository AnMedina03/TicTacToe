/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.common;

import ec.edu.espol.util.Sistema;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author Andrés Medina Jácome
 */
public class Jugador extends PerfilJugador{
    private String usuario;
    private String contraseña;
    private String correo;
    private int num;
    private int estado;

    public Jugador(String usuario, String nombre, String apellido, String contraseña, int estado, String correo, String sexo, String fechaNacimiento) {
        super(nombre, apellido, fechaNacimiento, sexo);
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.correo = correo;
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public void setNum(Scanner sc) {
        this.num = Sistema.validarNumeros(sc,0,9);
    }

    @Override
    public String toString() {
        return "Jugador{" + "usuario=" + usuario + ", contrase\u00f1a=" + contraseña + ", correo=" + correo + ", estado=" + estado + ", nombre=" + this.getNombre() + ", apellido=" + getApellido() + ", fechaNacimiento=" + getFechaNacimiento() + ", sexo=" + getSexo() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.usuario);
        hash = 19 * hash + Objects.hashCode(this.contraseña);
        return hash;
    }

    public static int hashCode(String usuario,String contraseña) {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(usuario);
        hash = 19 * hash + Objects.hashCode(contraseña);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Jugador other = (Jugador) obj;
        return Objects.equals(this.usuario, other.usuario) && Objects.equals(this.contraseña, other.contraseña);
    }
    
    
    
}
