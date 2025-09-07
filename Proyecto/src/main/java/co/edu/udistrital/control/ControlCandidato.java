/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.control;

import co.edu.udistrital.model.*;
import java.util.*;

/**
 *
 * @author Jhon
 */

public class ControlCandidato {
    private Random random;

    public ControlCandidato(long semilla) {
        this.random = new Random(semilla);
    }

    public BancoCandidato generarCandidatos(int N, int M, String distribucion) {
        BancoCandidato banco = new BancoCandidato();
        for (int i = 1; i <= N; i++) {
            Candidato c = new Candidato(i);
            for (int j = 0; j < 5; j++) {
                List<Integer> valores = generarValores(M, distribucion);
                c.getCaracteristicas().add(valores);
            }
            banco.agregarCandidato(c);
        }
        return banco;
    }

    private List<Integer> generarValores(int M, String distribucion) {
        List<Integer> valores = new ArrayList<>();
        switch (distribucion.toLowerCase()) {
            case "aleatoria":
                for (int i = 0; i < M; i++) {
                    valores.add(random.nextInt(M) + 1);
                }
                break;
            case "casiordenada":
                for (int i = 1; i <= M; i++) {
                    valores.add(i);
                }
                int perturbaciones = Math.max(1, M / 10);
                for (int i = 0; i < perturbaciones; i++) {
                    int idx1 = random.nextInt(M);
                    int idx2 = random.nextInt(M);
                    Collections.swap(valores, idx1, idx2);
                }
                break;
            case "inversa":
                for (int i = M; i >= 1; i--) {
                    valores.add(i);
                }
                break;
            default:
                throw new IllegalArgumentException("Distribución no válida: " + distribucion);
        }
        return valores;
    }
}
