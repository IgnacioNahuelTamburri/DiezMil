package Modelo;

import java.io.Serializable;

public class Jugador implements Serializable{

    private final String nombre;

    private int puntos;

    private int puntosAcumulados;

    public Jugador(String nombre){
        this.nombre=nombre;
        this.puntos=0;
        this.puntosAcumulados = 0;
    }

    public void restarAcumulados(int puntos){
        this.puntosAcumulados = this.puntosAcumulados-puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void sumarPuntos(){
        this.puntos += puntosAcumulados;
        this.puntosAcumulados = 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarAcumulados(int puntos){
        this.puntosAcumulados += puntos;
    }

    public void resetearAcumulados(){
        this.puntosAcumulados = 0;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }
}