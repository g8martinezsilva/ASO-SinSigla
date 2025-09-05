/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jhon
 */


public class BancoCandidato {

    private List<Candidato> candidatos;

    public BancoCandidato() {
        this.candidatos = new ArrayList<>();
    }

    public void agregarCandidato(Candidato c) {
        candidatos.add(c);
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public int getTotalCandidatos() {
        return candidatos.size();
    }
}
