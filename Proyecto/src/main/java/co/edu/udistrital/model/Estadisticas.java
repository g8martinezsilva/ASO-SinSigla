/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model;

import java.util.*;
/**
 *
 * @author Gabriela
 */

public class Estadisticas {
    public static boolean orden(List<Integer> lista) {
        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i-1) > lista.get(i)) return false;
        }
        return true;
    }
    public static long mediana(List<Long> valores) {
        if (valores.isEmpty()) return 0;
        ArrayList<Long> copia = new ArrayList<>(valores);
        Collections.sort(copia);
        int n = copia.size();
        if (n % 2 == 0)
            return (copia.get(n / 2 - 1) + copia.get(n / 2)) / 2;
        else
            return copia.get(n / 2);
    }
    public static long ric(List<Long> valores) {
        if (valores.isEmpty()) return 0;
        ArrayList<Long> copia = new ArrayList<>(valores);
        Collections.sort(copia);
        int n = copia.size();
        long q1 = copia.get(n / 4);
        long q3 = copia.get((3 * n) / 4);
        return q3 - q1;
    }
}
