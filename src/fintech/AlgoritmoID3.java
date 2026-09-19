package fintech;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class AlgoritmoID3 {
    // Calcula la entropía H(S)
    public double calcularEntropia(List<Instancia> instancias) {
        if (instancias.isEmpty()) return 0;

        Map<String, Integer> conteoClases = new HashMap<>();
        for (Instancia inst : instancias) {
            String clase = inst.getClaseObjetivo();
            conteoClases.put(clase, conteoClases.getOrDefault(clase, 0) + 1);
        }

        double entropia = 0.0;
        double totalInstancias = instancias.size();

        for (double conteo : conteoClases.values()) {
            double pi = conteo / totalInstancias;
            entropia -= pi * (Math.log(pi) / Math.log(2));
        }
        return entropia;
    }

    // Calcula la Ganancia de Información
    public double calcularGanancia(List<Instancia> instancias, String nombreAtributo) {
        double entropiaTotal = calcularEntropia(instancias);
        double totalInstancias = instancias.size();

        Map<String, List<Instancia>> subconjuntos = new HashMap<>();
        for (Instancia inst : instancias) {
            String valor = inst.getValorAtributo(nombreAtributo);
            subconjuntos.putIfAbsent(valor, new ArrayList<>());
            subconjuntos.get(valor).add(inst);
        }

        double entropiaAtributo = 0.0;
        for (List<Instancia> subconjunto : subconjuntos.values()) {
            double peso = subconjunto.size() / totalInstancias;
            entropiaAtributo += peso * calcularEntropia(subconjunto);
        }
        return entropiaTotal - entropiaAtributo;
    }

    // Construye el árbol recursivamente
    public Nodo construirArbol(List<Instancia> instancias, List<String> atributosDisponibles) {
        if (calcularEntropia(instancias) == 0) {
            return new Nodo(instancias.get(0).getClaseObjetivo(), true);
        }

        if (atributosDisponibles.isEmpty()) {
            return new Nodo(obtenerClaseMayoritaria(instancias), true);
        }

        String mejorAtributo = null;
        double maxGanancia = -1.0;

        for (String atributo : atributosDisponibles) {
            double ganancia = calcularGanancia(instancias, atributo);
            if (ganancia > maxGanancia) {
                maxGanancia = ganancia;
                mejorAtributo = atributo;
            }
        }

        Nodo nodoRaiz = new Nodo(mejorAtributo);
        Set<String> valoresPosibles = new HashSet<>();
        for (Instancia inst : instancias) {
            valoresPosibles.add(inst.getValorAtributo(mejorAtributo));
        }

        for (String valor : valoresPosibles) {
            List<Instancia> subconjuntoInstancias = new ArrayList<>();
            for (Instancia inst : instancias) {
                if (inst.getValorAtributo(mejorAtributo).equals(valor)) {
                    subconjuntoInstancias.add(inst);
                }
            }

            List<String> atributosRestantes = new ArrayList<>(atributosDisponibles);
            atributosRestantes.remove(mejorAtributo);

            Nodo nodoHijo = construirArbol(subconjuntoInstancias, atributosRestantes);
            nodoRaiz.agregarHijo(valor, nodoHijo);
        }
        return nodoRaiz;
    }

    private String obtenerClaseMayoritaria(List<Instancia> instancias) {
        Map<String, Integer> conteo = new HashMap<>();
        for (Instancia inst : instancias) {
            String clase = inst.getClaseObjetivo();
            conteo.put(clase, conteo.getOrDefault(clase, 0) + 1);
        }
        String mayoritaria = "";
        int max = -1;
        for (Map.Entry<String, Integer> entry : conteo.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mayoritaria = entry.getKey();
            }
        }
        return mayoritaria;
    }

    // EVALÚA UN CLIENTE NUEVO (La fase de predicción en el Backend)
    public String evaluarCliente(Nodo nodoActual, Map<String, String> datosCliente) {
        if (nodoActual.esHoja()) {
            return nodoActual.getClasePredicha();
        }

        String atributoPregunta = nodoActual.getNombreAtributo();
        String valorCliente = datosCliente.get(atributoPregunta);
        Nodo nodoHijo = nodoActual.getHijos().get(valorCliente);

        if (nodoHijo == null) {
            return "RECHAZADO (Sin antecedentes similares)";
        }

        return evaluarCliente(nodoHijo, datosCliente);
    }

    public void imprimirArbol(Nodo nodo, String indentacion) {
        if (nodo.esHoja()) {
            System.out.println(indentacion + "\u2794 Predicci\u00f3n: " + nodo.getClasePredicha());
            return;
        }
        System.out.println(indentacion + "[" + nodo.getNombreAtributo() + "]");
        for (Map.Entry<String, Nodo> entrada : nodo.getHijos().entrySet()) {
            System.out.println(indentacion + "  \u21b3 Si el valor es '" + entrada.getKey() + "':");
            imprimirArbol(entrada.getValue(), indentacion + "      ");
        }
    }
}
