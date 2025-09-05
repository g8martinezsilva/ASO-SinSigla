/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

import java.util.Random;

/**
 *
 * @author Jhon
 */

import java.util.ArrayList;

import java.util.List;

public class Candidato {

    private int id;
    // Cada característica es una lista de valores enteros
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
        return "Candidato{"
                + "id=" + id
                + ", caracteristicas=" + caracteristicas
                + '}';
    }
}
