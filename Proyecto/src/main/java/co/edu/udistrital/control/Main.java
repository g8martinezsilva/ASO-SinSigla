/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.control;

import co.edu.udistrital.model.BancoCandidato;
import co.edu.udistrital.model.Candidato;
import co.edu.udistrital.control.*;
import java.util.*;

/**
 *
 * @author Jhon
 */
public class Main {
    public static void main(String[] args) {
        int N = 2;           // número de candidatos
        int M = 100000;       // tamaño de cada característica
        int k = 5;              // numero de repeticiones para cada experimento
        String [] distribuciones = {"aleatoria", "casiordenada", "inversa"};
        
        long semilla = 1234L;

        for (String distribucion : distribuciones) {
            System.out.println("\n=== PRUEBA para DISTRIBUCION: " + distribucion + " ===");

            for (int caracteristica = 0; caracteristica < 5; caracteristica++) {
                System.out.println("-- Caracteristica #" + (caracteristica + 1) + " --");
                List<Long> tiempos = new ArrayList<>();
                List<Long> comparaciones = new ArrayList<>();
                List<Long> intercambios = new ArrayList<>();

                for (int rep = 0; rep < k; rep++) {
                    ControlCandidato control = new ControlCandidato(semilla + rep);
                    BancoCandidato banco = control.generarCandidatos(N, M, distribucion);

                    for (int cand = 0; cand < N; cand++) {
                        Candidato c = banco.getCandidatos().get(cand);
                        List<Integer> datos = new ArrayList<>(c.getCaracteristicas().get(caracteristica));
                        ResultadoOrdenamiento res;
                        switch (caracteristica) {
                            case 0: res = Organizador.burbuja(datos); break;
                            case 1: res = Organizador.insercion(datos); break;
                            case 2: res = Organizador.merge(datos); break;
                            case 3: res = Organizador.seleccion(datos); break;
                            case 4: res = Organizador.quick(datos); break;
                            default: res = Organizador.burbuja(datos);
                        }
                        tiempos.add(res.getTiempoMs());
                        comparaciones.add(res.getComparaciones());
                        intercambios.add(res.getIntercambios());
                        // Mostrar los valores individuales de esta corrida/candidato
                        System.out.println("  Rep " + (rep + 1) + " - Candidato " + (cand + 1) + 
                                           ": Tiempo (ms)=" + res.getTiempoMs() + 
                                           " Comparaciones=" + res.getComparaciones() +
                                           " Intercambios=" + res.getIntercambios());
                    }
                }
                // Mostrar listas globales (todas las repeticiones y candidatos)
                System.out.println("  [Todos] Tiempos (ms):      " + tiempos);
                System.out.println("  [Todos] Comparaciones:     " + comparaciones);
                System.out.println("  [Todos] Intercambios:      " + intercambios);

                System.out.println("  Tiempo (ms): Mediana=" + mediana(tiempos) +
                                   ", Rango Intercuartilico=" + rangoIntercuartilico(tiempos));
                System.out.println("  Comparaciones: Mediana=" + mediana(comparaciones) +
                                   ", Rango Intercuartilico=" + rangoIntercuartilico(comparaciones));
                System.out.println("  Intercambios: Mediana=" + mediana(intercambios) +
                                   ", Rango Intercuartilico=" + rangoIntercuartilico(intercambios));
            }
        }
    }

    // Función para calcular la mediana de una lista de números
    public static long mediana(List<Long> valores) {
        Collections.sort(valores);
        int n = valores.size();
        if (n % 2 == 0)
            return (valores.get(n/2 - 1) + valores.get(n/2)) / 2;
        else
            return valores.get(n/2);
    }

    // Función para calcular el rango intercuartílico
    public static long rangoIntercuartilico(List<Long> valores) {
        Collections.sort(valores);
        int n = valores.size();
        long q1 = valores.get(n/4);
        long q3 = valores.get((3*n)/4);
        return q3 - q1;
    }
}
