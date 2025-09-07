/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

import java.util.*;

/**
 *
 * @author Jhon
 */

public class Candidato {
    private int id;
    private List<List<Integer>> caracteristicas;

    public Candidato(int id) {
        this.id = id;
        this.caracteristicas = new ArrayList<>();
    }
    
    public int getId() {
        return id; 
    }
    
    public List<List<Integer>> getCaracteristicas() {
        return caracteristicas;
    }

    @Override
    public String toString() {
        return "Candidato{id=" + id + ", caracteristicas=" + caracteristicas + '}';
    }
}