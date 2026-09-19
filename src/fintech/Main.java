
package fintech;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

   
    public static void main(String[] args) {
       // 1. CARGA DE DATOS HISTÓRICOS (Entrenamiento)
        List<Instancia> historicoClientes = new ArrayList<>();
        
        historicoClientes.add(crearCliente("Malo",   "Altos",  "Temporal", "RECHAZADO"));
        historicoClientes.add(crearCliente("Bueno",  "Altos",  "Fijo",     "APROBADO"));
        historicoClientes.add(crearCliente("Bueno",  "Medios", "Fijo",     "APROBADO"));
        historicoClientes.add(crearCliente("Malo",   "Medios", "Temporal", "RECHAZADO"));
        historicoClientes.add(crearCliente("Bueno",  "Bajos",  "Fijo",     "APROBADO"));
        historicoClientes.add(crearCliente("Regular","Medios", "Fijo",     "APROBADO"));
        historicoClientes.add(crearCliente("Malo",   "Bajos",  "Temporal", "RECHAZADO"));
        historicoClientes.add(crearCliente("Regular","Altos",  "Fijo",     "APROBADO"));

        List<String> atributos = new ArrayList<>(Arrays.asList("Historial", "Ingresos", "Contrato"));

        // 2. INICIALIZAR EL MOTOR ID3
        AlgoritmoID3 motorIA = new AlgoritmoID3();
        Nodo arbolEntrenado = motorIA.construirArbol(historicoClientes, atributos);
        
        // Mostrar estructura por consola
        System.out.println("=== SISTEMA FINTECH: \u00c1RBOL GENERADO ===");
        motorIA.imprimirArbol(arbolEntrenado, "");
        System.out.println("=============================================\n");

        // 3. ENTRADA DE UN NUEVO CLIENTE (Simulación desde Formulario Web)
        Map<String, String> clienteNuevo = new HashMap<>();
        clienteNuevo.put("Historial", "Malo");
        clienteNuevo.put("Ingresos", "Medios");
        clienteNuevo.put("Contrato", "Temporal");

        System.out.println("Evaluando solicitud en tiempo real...");
        System.out.println("Datos del postulante: " + clienteNuevo);

        // 4. CONSULTA AL MOTOR DE IA
        String resultado = motorIA.evaluarCliente(arbolEntrenado, clienteNuevo);
        System.out.println("\n\u2794 DECISI\u00d3N DEL BACKEND: " + resultado);
    }
    private static Instancia crearCliente(String historial, String ingresos, String contrato, String decision) {
        Instancia inst = new Instancia(decision);
        inst.agregarAtributo("Historial", historial);
        inst.agregarAtributo("Ingresos", ingresos);
        inst.agregarAtributo("Contrato", contrato);
        return inst;
    
    }
    
}
