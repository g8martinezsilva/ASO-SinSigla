package co.edu.udistrital.control;

import co.edu.udistrital.model.*;
import static co.edu.udistrital.model.Estadisticas.*;
import co.edu.udistrital.view.VistaResultados;

import javax.swing.*;
import java.util.*;

public class Main {
    private static final String[] algoritmos = {
        "Burbuja", "Insercion", "Merge", "Seleccion", "Quick"
    };
    
    private static final String[] distribuciones = {"aleatoria", "casiordenada", "inversa"};

    private static int N;
    private static int M;
    private static int k;
    private static String distribucion;
    private static final long semilla = 1234L;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            N = datos("Ingrese N (cantidad de candidatos):", 2);
            M = datos("Ingrese M (cantidad de valores por característica):", 10000);
            k = datos("Ingrese k (número de repeticiones):", 2);
            distribucion = distribucion();
            
            VistaResultados vista = new VistaResultados();
            BancoCandidato bancoGenerado = generarDatos(distribucion);
            String json = ordenar(bancoGenerado, distribucion);
            vista.mostrarJson(json);
        });
    }

    private static int datos(String mensaje, int valorDefault) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(null, mensaje, Integer.toString(valorDefault));
            if (entrada == null) System.exit(0);
            try {
                int valor = Integer.parseInt(entrada.trim());
                if (valor > 0) 
                    return valor;
                else JOptionPane.showMessageDialog(null, "Ingrese un número entero positivo.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    private static String distribucion() {
        String input = (String) JOptionPane.showInputDialog(null,
                "Seleccione la distribución:",
                "Distribución",
                JOptionPane.QUESTION_MESSAGE,
                null,
                distribuciones,
                distribuciones[0]);
        if (input == null) 
            System.exit(0);
        return input;
    }

    private static BancoCandidato generarDatos(String distribucion) {
        ControlCandidato control = new ControlCandidato(semilla);
        return control.generarCandidatos(N, M, distribucion);
    }

    private static String ordenar(BancoCandidato banco, String distribucion) {
        long tiempoInicio = System.currentTimeMillis();

        StringBuilder json = new StringBuilder();
        json.append("{\n  \"ejecuciones\": [\n");
        for (int caracteristica = 0; caracteristica < 5; caracteristica++) {
            String algoritmo = algoritmos[caracteristica];
            ArrayList<Long> tiemposMs = new ArrayList<>();
            ArrayList<Long> comps = new ArrayList<>();
            ArrayList<Long> interc = new ArrayList<>();

            json.append("    {\n");
            json.append("      \"caracteristica\": ").append(caracteristica + 1).append(",\n");
            json.append("      \"algoritmo\": \"").append(algoritmo).append("\",\n");
            json.append("      \"distribucion\": \"").append(distribucion).append("\",\n");
            json.append("      \"resultados\": [\n");

            boolean first = true;
            for (int rep = 0; rep < k; rep++) {
                ControlCandidato control = new ControlCandidato(semilla + rep);
                BancoCandidato bancoR = control.generarCandidatos(N, M, distribucion);
                for (int cand = 0; cand < bancoR.getTotalCandidatos(); cand++) {
                    Candidato c = bancoR.getCandidatos().get(cand);
                    List<Integer> datos = new ArrayList<>(c.getCaracteristicas().get(caracteristica));
                    ResultadoOrdenamiento res;
                    switch (algoritmo) {
                        case "Burbuja":   res = Organizador.burbuja(datos);
                            break;
                        case "Insercion": res = Organizador.insercion(datos);
                            break;
                        case "Merge":     res = Organizador.merge(datos);
                            break;
                        case "Seleccion": res = Organizador.seleccion(datos);
                            break;
                        case "Quick":     res = Organizador.quick(datos);
                            break;
                        default:          res = Organizador.burbuja(datos);
                    }
                    boolean ok = orden(datos);
                    double tiempoSeg = res.getTiempoMs() / 1000.0;

                    tiemposMs.add(res.getTiempoMs());
                    comps.add(res.getComparaciones());
                    interc.add(res.getIntercambios());

                    if (!first) json.append(",\n");
                    json.append("        {")
                            .append("\"repeticion\": ").append(rep+1).append(", ")
                            .append("\"candidato\": ").append(cand+1).append(", ")
                            .append("\"comparaciones\": ").append(res.getComparaciones()).append(", ")
                            .append("\"intercambios\": ").append(res.getIntercambios()).append(", ")
                            .append("\"tiempo_s\": ").append(tiempoSeg).append(", ")
                            .append("\"orden_ok\": ").append(ok)
                            .append("}");
                    first = false;
                }
            }
            json.append("\n      ],\n");
            double medianaSeg = mediana(tiemposMs) / 1000.0;
            double ricSeg = ric(tiemposMs) / 1000.0;

            json.append("      \"mediana_comparaciones\": ").append(mediana(comps)).append(",\n");
            json.append("      \"ric_comparaciones\": ").append(ric(comps)).append(",\n");
            json.append("      \"mediana_intercambios\": ").append(mediana(interc)).append(",\n");
            json.append("      \"ric_intercambios\": ").append(ric(interc)).append(",\n");
            json.append("      \"mediana_tiempo_s\": ").append(medianaSeg).append(",\n");
            json.append("      \"ric_tiempo_s\": ").append(ricSeg).append("\n");
            json.append("    }");
            if (caracteristica < 4) json.append(",");
            json.append("\n");
        }
        json.append("  ],\n");
        long tiempoFin = System.currentTimeMillis();
        double totalSeg = (tiempoFin - tiempoInicio) / 1000.0;

        json.append("  \"parametros\": {\n");
        json.append("    \"semilla\": ").append(semilla).append(",\n");
        json.append("    \"N\": ").append(N).append(", \"M\": ").append(M).append(", \"k\": ").append(k).append(",\n");
        json.append("    \"jdk\": \"").append(System.getProperty("java.version")).append("\",\n");
        json.append("    \"so\": \"").append(System.getProperty("os.name")).append("\",\n");
        json.append("    \"hardware\": \"i5, 16 GB RAM\",\n");
        json.append("    \"tiempo_total_s\": ").append(totalSeg).append("\n");
        json.append("  }\n");
        json.append("}");
        
        json.append("  \"mas_elegibles\": [\n");
        for (int caracteristica = 0; caracteristica < 5; caracteristica++) {
            for (int rep = 0; rep < k; rep++) {
                ControlCandidato control = new ControlCandidato(semilla + rep);
                BancoCandidato bancoR = control.generarCandidatos(N, M, distribucion);

                double mayorPromedio = Double.NEGATIVE_INFINITY;
                int idMejorCandidato = -1;

                for (int cand = 0; cand < bancoR.getTotalCandidatos(); cand++) {
                    Candidato c = bancoR.getCandidatos().get(cand);
                    List<Integer> valores = c.getCaracteristicas().get(caracteristica);

                    double suma = 0;
                    for (int val : valores) suma += val;
                    double promedio = suma / valores.size();

                    if (promedio > mayorPromedio) {
                        mayorPromedio = promedio;
                        idMejorCandidato = c.getId();
                    }
                }

                json.append("    { \"caracteristica\": ").append(caracteristica + 1)
                   .append(", \"repeticion\": ").append(rep + 1)
                   .append(", \"candidato\": ").append(idMejorCandidato)
                   .append(", \"promedio\": ").append(mayorPromedio)
                   .append(" }");
                if (!(caracteristica == 4 && rep == k - 1)) json.append(",");
                json.append("\n");
            }
        }
        
        json.append("  ],\n");
        return json.toString();
    }
}