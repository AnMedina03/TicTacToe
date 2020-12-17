/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

/**
 *
 * @author Ajmj2002
 */
public class Tablero {
    private int[][] matriz;
    
    public Tablero (int cuadrada){
        if(cuadrada>0){
        this.matriz = new int[cuadrada][cuadrada];
        }
    }
    
    public Tablero(){
        this.matriz = new int[3][3];
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }
    
    public int getFilas(){
        int filas = this.matriz.length;
        return filas;
    }
    
    public int getColumnas(){
        int columnas = this.matriz[0].length;
        return columnas;
    }
    
    public int get(int fila, int columna){
        return this.matriz[fila][columna];
    }
    
    public void set(int row, int column, int valor){
        if ((0<=row||row<this.getFilas())&&(0<=column||column<this.getColumnas())){
            this.matriz[row][column] = valor;
        }
    }
    
    public boolean diagonal(int sign){
        for(int i=0;i<this.getFilas();i++)
            if(this.matriz[i][i]!=sign) return false;
        return true;
    }
    
    public boolean diagonalInverso(int sign){
        int last = this.getFilas();
        for(int i=0;i<this.getFilas();i++)
            if(this.matriz[i][--last]!=sign) return false;
        return true;
    }
    
    public boolean empate(){
        for(int i=0;i<this.getFilas();i++)
            for(int j=0;j<this.getColumnas();j++)
                if(this.matriz[i][j]==0) return false;
        return true;
    }
    
    @Override
    public String toString(){
        if (this == null) return "[]";
        StringBuilder r = new StringBuilder();
        r.append("   0 1 2\n");
        for (int i=0; i<this.getFilas();i++){
            r.append(i);
            r.append(" [");
            r.append(this.matriz[i][0]);
            for(int j=1;j<this.getColumnas();j++){
                r.append(",");
                r.append(this.matriz[i][j]);
            }
        r.append("]\n");
        }
        return r.toString();
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj==null) return false;
        if(this.getClass()!=obj.getClass()) return false;
        if(this == obj) return true;
        Tablero another = (Tablero) obj;
        if((this.getFilas()==another.getFilas())&&(this.getColumnas()==another.getColumnas())){
            for(int i=0;i<this.getFilas();i++){
                for(int j=0;j<this.getColumnas();j++)
                    if(this.matriz[i][j]!=another.matriz[i][j]) return false;
            }
            return true;
        }
        else
            return false;
    }
}
