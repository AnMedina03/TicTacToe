/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import ec.edu.espol.common.*;
import ec.edu.espol.sql.GameDatabase;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Andrés Medina Jácome
 */
public class Sistema{
    
    private Sistema(){}
    
    public static int maxPartida(GameDatabase gd){
        try {
            PreparedStatement stmt = gd.getCon().prepareStatement("SELECT * FROM Partida ORDER BY id DESC");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) return rs.getInt("id");
        } catch (SQLException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static int maxPCampeonato(GameDatabase gd){
        try {
            PreparedStatement stmt = gd.getCon().prepareStatement("SELECT * FROM Campeonato ORDER BY id DESC");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) return rs.getInt("id");
        } catch (SQLException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static Jugador verificarJugador(GameDatabase gd, String usuario,String contraseña) throws ExistenceException{
        try {
            PreparedStatement stmt = gd.getCon().prepareStatement("select * from Jugador");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Jugador p= new Jugador(rs.getString("usuario"),rs.getString("nombre"),rs.getString("apellido"),rs.getString("contraseña"),rs.getInt("estado"),rs.getString("correo"),rs.getString("sexo"),rs.getObject("fecha_Nacimiento").toString());
                if(p.hashCode()== Jugador.hashCode(usuario, contraseña)) return p;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new ExistenceException("Los datos ingresados no coincide con ningún usuario");
    }
    
    public static Jugador crearJugador(Scanner sc){
        String user,nombre,apellido,correo,password,sexo,fechaNacimiento;
        user = Sistema.ingresarCuenta(sc, "Usuario: ", "[a-zA-Z0-9]{1,20}");
        password = Sistema.ingresarCuenta(sc, "Contraseña: ", "[a-zA-Z0-9]{1,16}");
        nombre = Sistema.ingresarCuenta(sc, "Nombre: ", "[a-zA-Z]{1,25}");
        apellido = Sistema.ingresarCuenta(sc, "Apellido: ", "[a-zA-Z]{1,34}");
        correo = Sistema.validarCorreo(sc);
        sexo = Sistema.ingresarGenero(sc);
        fechaNacimiento = Sistema.ingresarFechaNacimiento(sc);
        return new Jugador(user,nombre,apellido,password, 1, correo, sexo,fechaNacimiento);
    }
    
    private static String ingresarCuenta(Scanner sc,String info,String match){
        String user;
        do{
            System.out.print(info);
            user = sc.next();
        }while(!user.matches(match));
        return user;
    }
    
    private static String validarCorreo(Scanner sc){
        String email;
        Matcher mat;
        do{
            System.out.print("Correo: ");
            email = sc.next();
            Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");   
            mat = pat.matcher(email);
        }while(!mat.find()||email.length()>40);
        return email;
    }
    
    private static String ingresarGenero(Scanner sc){
        String sexo;
        do{
            System.out.print("Ingrese solo la letra de su genero (sexo M o F): ");
            sexo = sc.next();
        }while(!sexo.matches("[mfMF]"));
        if(sexo.equals("M")||sexo.equals("m")) return Genero.MASCULINO.toString();
        else return Genero.FEMENINO.toString();
    }
    
    private static String ingresarFechaNacimiento(Scanner sc){
        int year,month,day;
        System.out.println("Ingrese la fecha de su nacimiento en numeros: ");
        do{
            System.out.print("Año: ");
            year = validarNumeros(sc);
        }while(year >= (LocalDateTime.now().getYear()-2));
        do{
            System.out.print("Mes: ");
            month = validarNumeros(sc);
        }while(month > 12 || month < 1);
        do{
            System.out.print("Día: ");
            day = validarNumeros(sc);
        }while(day > verificarMes(month) || day < 1);
        return year+"-"+month+"-"+day;
    }
    
    private static int verificarMes(int month){
        if(month==4||month==6||month==8|| month==9||month==11) return 30;
        else if(month==2) return 28;
        return 31;
    }
    
    public static int validarNumeros(Scanner sc, int min, int max){
        String dimension;
        boolean error;
        do{
            dimension = sc.next();
            try{
            Integer.parseInt(dimension);
            error = true;
            if(Integer.parseInt(dimension)<min||Integer.parseInt(dimension)>max) System.out.print("Ingrese un número dentro del rango asignado ["+min+","+max+"]: ");
            }catch(NumberFormatException ex){
                error = false;
            }
        }while(!error || (Integer.parseInt(dimension)<min||Integer.parseInt(dimension)>max));
        return Integer.parseInt(dimension);
    }
    
    public static int validarNumeros(Scanner sc, int min){
        String dimension;
        boolean error;
        do{
            dimension = sc.next();
            try{
            Integer.parseInt(dimension);
            error = true;
            if(Integer.parseInt(dimension)<min) System.out.print("Ingrese un número mayor o igual a "+min+": ");
            }catch(NumberFormatException ex){
                error = false;
            }
        }while(!error || (Integer.parseInt(dimension)<min));
        return Integer.parseInt(dimension);
    }
    
    public static int validarNumeros(Scanner sc){
        String dimension;
        boolean error;
        do{
            dimension = sc.next();
            try{
            Integer.parseInt(dimension);
            error = true;
            }catch(NumberFormatException ex){
                error = false;
            }
        }while(!error);
        return Integer.parseInt(dimension);
    }
    
}
