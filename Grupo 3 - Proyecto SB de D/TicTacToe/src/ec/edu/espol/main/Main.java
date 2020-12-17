/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.main;

import ec.edu.espol.common.*;
import ec.edu.espol.sql.*;
import ec.edu.espol.util.*;
import java.util.*;

/**
 *
 * @author Andrés Medina Jácome
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GameDatabase gd = new GameDatabase();
        System.out.println("\t\t\t\t\t\t\t\tTIC TAC TOE v2.2.2020");
        System.out.println("\t\t\t\t\t\t\t\t╔════════════════╗\n\t\t\t\t\t\t\t\t║Menú principal║\n\t\t\t\t\t\t\t\t╚════════════════╝");
        int opcion = 0;
        boolean error = false;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("==========================================");
            boolean decision = true;
            do{
                do{
                    try{
                        System.out.print("------------------------------------------\n1. Juego simple\n2. Campeonato\n3. Registrar nuevo jugador\n4. Salir\n***************************\nSu opción (número) -> ");
                        String opc = sc.next();
                        opcion = Integer.parseInt(opc);
                        error = !(opcion >= 1 && opcion <=4);
                    }catch(NumberFormatException ex){
                        error = true;
                    }finally{
                        if(error) System.out.println("Ingrese una de las opciones mostradas");
                    }
                }while(error);
                switch(opcion){
                    case 1:
                    {
                        System.out.println("Para jugar ambos jugadores deben iniciar sesión\nEn caso de no recordar su cuenta, escribir EXIT");
                        ArrayList<Jugador> jugadores = new ArrayList<>(2);
                        try {
                            iniciarSesion(sc, jugadores,gd,"JUGADOR 1\nUsuario: ");
                            iniciarSesion(sc, jugadores,gd,"JUGADOR 2\nUsuario: ");
                            if(jugadores.get(0).getNum()==jugadores.get(1).getNum()) jugadores.get(1).setNum(jugadores.get(0).getNum()+1);
                        } catch (GameException ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                        int id = Sistema.maxPartida(gd)+1;
                        Partida game = new Partida(id);
                        crearPartida(jugadores, sc, gd, game);
                        if(game.getWinner() != null ) System.out.println("+5 Puntos");
                        break;
                    }
                    case 2:
                    {
                        System.out.println("\t\t\tCampeonato\nIndicar el número de parejas para el campeonato");
                        System.out.println("[Considerar] Se recuerda que cada pareja juegan dos participantes");
                        int parejas = Sistema.validarNumeros(sc, 2);
                        System.out.println("Para jugar ambos jugadores deben iniciar sesión\nEn caso de no recordar su cuenta, escribir EXIT");
                        int partidasTotal = (int) Math.pow(2, parejas);
                        System.out.println(partidasTotal);
                        LinkedList<Jugador> jugadores = new LinkedList<>();
                        try {
                            for(int i = 1; i<=partidasTotal;i++)
                                iniciarSesion(sc, jugadores,gd,"JUGADOR "+(i)+"\nUsuario: ");
                            Collections.shuffle(jugadores);
                            Campeonato c = new Campeonato(Sistema.maxPCampeonato(gd)+1);
                            gd.guardarDatosCampeonato(c);
                            Jugador winner = asignarPartida(jugadores,sc,gd,partidasTotal,c);
                            System.out.println("VICTORIA PARA EL JUGADOR "+winner.getUsuario()+"\n+10 Puntos");
                        } catch (GameException ex) {
                            System.out.println(ex.getMessage());
                            break;
                        }
                        break;
                    }
                    case 3:
                    {
                        Jugador player = Sistema.crearJugador(sc);
                        if(gd.guardarDatosJugador(player)) System.out.println("Registro existoso");
                        else System.out.println("Problemas al registrar");
                        break;
                    }
                    default:
                    {
                        decision = false;
                        break;
                    }
                }
            }while(decision);
        }
    }
    
    /**
     *
     * @internal
     * Este método verifica si la cuenta ingresada se encuentra en la base de datos
     * Permitiendo también que se asigne un símbolo para jugar la partida contra otro participante
     *
     */
    private static void iniciarSesion(Scanner sc, List<Jugador> jugadores, GameDatabase gd, String message) throws GameException{
        boolean error;
        do{
            System.out.print(message);
            String user = sc.next();
            System.out.print("Contraseña: ");
            String password = sc.next();
            try {
                Jugador player = Sistema.verificarJugador(gd, user, password);
                if(jugadores.contains(player)){
                    System.out.println("Este jugador acaba de ser iniciado");
                    error = true;
                }
                else{
                    jugadores.add(player);
                    error = false;
                }
                System.out.println("¿Qué numero desea usar? [1 - 9] ");
                player.setNum(sc);
            } catch (ExistenceException ex) {
                System.out.println(ex.getMessage());
                System.out.print("Desea volver a intentarlo? (Escribir cualquier palabra diferente a EXIT para continuar) = ");
                user = sc.next();
                error = !user.matches("EXIT");
            }
            if(user.matches("EXIT")) throw new GameException("Juego cancelado");
        }while(error);
    }
    
    
    /**
     *
     * @internal
     * Este método permite asignar a una partida respectiva a cada participante del campeonato
     * Estos datos se guardan en la base de datos tictactoe
     *
     */
    private static Jugador asignarPartida(LinkedList<Jugador> jugadores, Scanner sc, GameDatabase gd, int partidasTotal, Campeonato c) throws GameException{
        Queue<Jugador> cola = jugadores;
        Partida game = new Partida(Sistema.maxPartida(gd)+1);
        LinkedList<Jugador> next = new LinkedList<>();
        while(!cola.isEmpty()){
            game.getPlayers().add(cola.poll());
            if(game.getPlayers().size()%2==0){
                if(game.getPlayers().get(0).getNum()==game.getPlayers().get(1).getNum()) game.getPlayers().get(1).setNum(game.getPlayers().get(0).getNum()+1);
                crearPartida(game.getPlayers(), sc, gd, game);
                game.setC(c);
                gd.guardarAsignaciones(game, partidasTotal);
                victoriaEmpate(game, gd, partidasTotal, c, sc);
                next.add(game.getWinner());
                if(!cola.isEmpty()) game = new Partida(Sistema.maxPartida(gd)+1);
            }
        }
        if(next.size()==1) return next.get(0);
        return asignarPartida(next,sc,gd,partidasTotal-(partidasTotal/2),c);
    }
    
    
    private static void crearPartida(ArrayList<Jugador> jugadores, Scanner sc, GameDatabase gd, Partida game){
        game.setPlayers(jugadores);
        gd.guardarDatosPartida(game);
        gd.guardarDatosJuego(game);
        Tablero tbl = new Tablero();
        System.out.println("*************************\n*      Comiencen!!!     *\n*************************\n"+tbl);
        jugar(jugadores,tbl,sc, gd, game);
    }
    
    /**
     * @internal
     * Este método es la interacción entre ambos jugadores durante la partida
     * Se guardan las coordenadas y al ganador, en caso de que haya, en sus respectivas tablas
     *
     */
    private static void jugar(ArrayList<Jugador> jugadores, Tablero tbl, Scanner sc, GameDatabase gd, Partida partida) {
        int jugador = 0;
        boolean victoria = false;
        do{
            Jugador playing = jugadores.get(jugador);
            System.out.println("//Turno Jugador "+(jugador+1)+"("+playing.getUsuario()+")//");
            boolean coordenadas;
            System.out.println("-1. Abandonar\n1. Jugar");
            if(Sistema.validarNumeros(sc, -1, 0) == -1){
                gd.actualizar(partida,2);
                if(jugador == 0) partida.setWinner(jugadores.get(1));
                else partida.setWinner(jugadores.get(0));
                victoria = true;
            }else{
                do{
                    System.out.print("El número de la fila (horizontal) mostrada en pantalla: ");
                    int x = Sistema.validarNumeros(sc, 0, 2);
                    System.out.print("El número de la columna (vertical) mostrada en pantalla: ");
                    int y = Sistema.validarNumeros(sc, 0, 2);
                    System.out.println(tbl.empate());
                    if(tbl.get(x, y)==0){
                        tbl.set(x,y, playing.getNum());
                        coordenadas = gd.guardarCoordenadas(playing, partida, x, y);
                    }else coordenadas = tbl.empate();
                }while(!coordenadas);
                if(jugador==0) jugador++;
                else jugador--;
                System.out.println(tbl);
                if(rules(tbl,playing.getNum())){
                    System.out.println("TRES EN RAYA!!!\nVICTORIA PARA EL JUGADOR "+playing.getUsuario());
                    partida.setWinner(playing);
                    victoria = gd.actualizar(partida, playing);
                }else if(tbl.empate()){
                    System.out.println("EMPATE... NO HAY PUNTOS PARA NINGUNO");
                    victoria = gd.actualizar(partida,1);
                }
            }
        }while(!victoria);
    }
    
    /**
     * @internal
     * Este método permite volver a jugar una partida en caso de que haya empate
     *
     */
    private static void victoriaEmpate(Partida game, GameDatabase gd, int partidasTotal, Campeonato c, Scanner sc){
        while(game.getWinner()==null){
            Partida empate = new Partida(Sistema.maxPartida(gd)+1);
            empate.setPlayers(game.getPlayers());
            empate.setC(c);
            crearPartida(empate.getPlayers(), sc, gd, empate);
            game.setWinner(empate.getWinner());
            gd.guardarAsignaciones(empate, partidasTotal);
        }
    }
    
    private static boolean rules(Tablero t, int sign){
        return victoryRules(t,sign,0,1,2) || victoryRules(t,sign,1,0,2) || victoryRules(t,sign,2,0,1) || t.diagonal(sign) || t.diagonalInverso(sign);
    }
    
    private static boolean victoryRules(Tablero t, int sign, int f1, int f2,int f3){
        return t.get(f1, f1)==sign && ((t.get(f1, f2)==sign && t.get(f1, f3)==sign)||(t.get(f2, f1)==sign && t.get(f3, f1)==sign));
    }
}
