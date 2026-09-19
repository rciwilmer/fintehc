package fintech;
import java.util.HashMap;
import java.util.Map;

public class Nodo {
   private String nombreAtributo; 
    private String clasePredicha;   
    private boolean esHoja;
    private Map<String, Nodo> hijos;

    public Nodo(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
        this.esHoja = false;
        this.hijos = new HashMap<>();
    }

    public Nodo(String clasePredicha, boolean esHoja) {
        this.clasePredicha = clasePredicha;
        this.esHoja = esHoja;
    }

    public void agregarHijo(String valorAtributo, Nodo nodoHijo) {
        this.hijos.put(valorAtributo, nodoHijo);
    }

    public String getNombreAtributo() { return nombreAtributo; }
    public String getClasePredicha() { return clasePredicha; }
    public boolean esHoja() { return esHoja; }
    public Map<String, Nodo> getHijos() { return hijos; }
}

