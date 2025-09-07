/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.control;

/**
 *
 * @author Jhon
 */
public class ResultadoOrdenamiento {
    private long comparaciones;
    private long intercambios;
    private long tiempoMs;

    public ResultadoOrdenamiento(long comparaciones, long intercambios, long milisegundos) {
        this.comparaciones = comparaciones;
        this.intercambios = intercambios;
        this.tiempoMs = milisegundos;
    }
    
    public long getComparaciones() {
        return comparaciones;
    }
    
    public long getIntercambios() {
        return intercambios;
    }
    
    public long getTiempoMs() {
        return tiempoMs;
    }
}