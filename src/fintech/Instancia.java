package fintech;

import java.util.HashMap;
import java.util.Map;

public class Instancia {

    private Map<String, String> atributos;
    private String claseObjetivo;

    public Instancia(String claseObjetivo) {
        this.atributos = new HashMap<>();
        this.claseObjetivo = claseObjetivo;
    }

    public void agregarAtributo(String nombre, String valor) {
        this.atributos.put(nombre, valor);
    }

    public String getValorAtributo(String nombre) {
        return this.atributos.get(nombre);
    }

    public String getClaseObjetivo() {
        return claseObjetivo;
    }
}
