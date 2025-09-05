/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.control;

import co.edu.udistrital.model.BancoCandidato;
import co.edu.udistrital.model.Candidato;

/**
 *
 * @author Jhon
 */
public class Main {
    public static void main(String[] args) {
        int N = 2;           // número de candidatos
        int M = 10000;       // tamaño de cada característica (prueba, súbelo a 1M si quieres)
        String distribucion = "aleatoria";

        // Generar candidatos
        ControlCandidato control = new ControlCandidato(1234L);
        BancoCandidato banco = control.generarCandidatos(N, M, distribucion);

        // Recorrer candidatos
        for (Candidato c : banco.getCandidatos()) {
            System.out.println("\n=== Candidato " + c.getId() + " ===");

            // Mostrar primeros 10 valores de la primera característica antes de ordenar
            System.out.print("Característica 1 antes: ");
            for (int i = 0; i < 10; i++) {
                System.out.print(c.getCaracteristicas().get(0).get(i) + " ");
            }
            System.out.println("...");

            // 1. Burbuja en característica 1
            ResultadoOrdenamiento r1 = Organizador.burbuja(c.getCaracteristicas().get(0));
            System.out.println("Burbuja -> Comparaciones: " + r1.getComparaciones() +
                               ", Intercambios: " + r1.getIntercambios() +
                               ", Tiempo: " + r1.getTiempoMs() + " ms");

            // 2. Inserción en característica 2
            ResultadoOrdenamiento r2 = Organizador.insercion(c.getCaracteristicas().get(1));
            System.out.println("Inserción -> Comparaciones: " + r2.getComparaciones() +
                               ", Intercambios: " + r2.getIntercambios() +
                               ", Tiempo: " + r2.getTiempoMs() + " ms");

            // 3. Merge en característica 3
            ResultadoOrdenamiento r3 = Organizador.merge(c.getCaracteristicas().get(2));
            System.out.println("Merge -> Comparaciones: " + r3.getComparaciones() +
                               ", Intercambios: " + r3.getIntercambios() +
                               ", Tiempo: " + r3.getTiempoMs() + " ms");

            // 4. Selección en característica 4
            ResultadoOrdenamiento r4 = Organizador.seleccion(c.getCaracteristicas().get(3));
            System.out.println("Selección -> Comparaciones: " + r4.getComparaciones() +
                               ", Intercambios: " + r4.getIntercambios() +
                               ", Tiempo: " + r4.getTiempoMs() + " ms");

            // 5. Quick en característica 5
            ResultadoOrdenamiento r5 = Organizador.quick(c.getCaracteristicas().get(4));
            System.out.println("Quick -> Comparaciones: " + r5.getComparaciones() +
                               ", Intercambios: " + r5.getIntercambios() +
                               ", Tiempo: " + r5.getTiempoMs() + " ms");

            // Mostrar primeros 10 valores de la primera característica después de ordenar
            System.out.print("Característica 1 después (Burbuja): ");
            for (int i = 0; i < 10; i++) {
                System.out.print(c.getCaracteristicas().get(0).get(i) + " ");
            }
            System.out.println("...");
        }
    }
}
