/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.sql;


import ec.edu.espol.common.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.*;

/**
 *
 * @author Andrés Medina Jácome
 */
public class GameDatabase {
    private static final String usuario = "root";
    private static final String clave = "";
    private static final String url = "jdbc:mysql://localhost:3306/tictactoe";
    private Connection con;
    private PreparedStatement stmt;
    
    public GameDatabase(){
        try {
            con = DriverManager.getConnection(url,usuario,clave);
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getCon() {
        return con;
    }
    
    public boolean guardarDatosJugador(Jugador jugador){
        try {
            stmt = con.prepareStatement("select * from Jugador");
            stmt.executeUpdate("INSERT INTO Jugador VALUES('"+jugador.getUsuario()+"','"+jugador.getNombre()+"','"+jugador.getApellido()+"','"+jugador.getContraseña()+"',"+jugador.getEstado()+",'"+jugador.getCorreo()+"','"+jugador.getSexo()+"','"+jugador.getFechaNacimiento()+"')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean guardarDatosPartida(Partida partida){
        try {
            stmt = con.prepareStatement("select * from Partida");
            stmt.executeUpdate("INSERT INTO Partida (id, fechaInicio, horaInicio) VALUES ("+partida.getId()+", CURDATE(), CURTIME())");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean guardarDatosCampeonato(Campeonato c){
        try {
            stmt = con.prepareStatement("select * from Campeonato");
            stmt.executeUpdate("INSERT INTO Campeonato VALUES ("+c.getId()+", CURDATE())");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean guardarDatosJuego(Partida partida){
        try {
            stmt = con.prepareStatement("select * from Juega");
            stmt.executeUpdate("INSERT INTO Juega VALUES ("+partida.getId()+",'"+partida.getPlayers().get(0).getUsuario()+"'),("+partida.getId()+",'"+partida.getPlayers().get(1).getUsuario()+"')");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean guardarCoordenadas(Jugador jugador, Partida partida, int x, int y){
        try {
            stmt = con.prepareStatement("select * from Ubica");
            stmt.executeUpdate("INSERT INTO Ubica VALUES ("+partida.getId()+",'"+jugador.getUsuario()+"',"+y+","+x+")");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean guardarAsignaciones(Partida partida, int clasificacion){
        try {
            stmt = con.prepareStatement("select * from Asigna");
            System.out.println(partida.getWinner());
            stmt.executeUpdate("INSERT INTO Asigna VALUES ("+partida.getId()+","+partida.getC().getId()+",'"+partida.getWinner().getUsuario()+"',"+clasificacion+")");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public boolean actualizar(Partida partida, Jugador jugador){
        try {
            stmt = con.prepareStatement("select * from Partida");
            stmt.executeUpdate("UPDATE Partida SET ganador = '"+jugador.getUsuario()+"',fechaFin = CURDATE(), horaFin = CURTIME(), estado = "+1+" WHERE id = "+partida.getId());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean actualizar(Partida partida, int estado){
        try {
            stmt = con.prepareStatement("select * from Partida");
            LocalDateTime time = LocalDateTime.now();
            stmt.executeUpdate("UPDATE Partida SET fechaFin = CURDATE(), horaFin = CURTIME(), estado = "+estado+" WHERE id = "+partida.getId());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[!]Problemas al actualizar, la partida no se ha guardado\nRevisar si el servidor está activo");;
            return false;
        }
    }
    
    /*public boolean recuperarDatos(){
        try {
            stmt = con.prepareStatement("select * from Jugador");
            ResultSet rs = stmt.executeQuery();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(GameDatabase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }*/
    
    
    
}
