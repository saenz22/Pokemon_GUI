import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Ya no necesitas 'throws Exception' aquí generalmente

        System.out.println("[Main] Iniciando aplicación...");

        // 4. Usa SwingUtilities.invokeLater para crear la GUI en el hilo correcto (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Este código se ejecutará en el Event Dispatch Thread

                System.out.println("[Main EDT] Creando instancia de WindowBuilder...");
                // Crea la ventana. El constructor de WindowBuilder ya la hace visible.
                new WindowBuilder();
                System.out.println("[Main EDT] Instancia de WindowBuilder creada.");
            }
        });

        System.out.println("[Main] Creación de GUI programada en EDT. El método main termina.");

        /* El código comentado que tenías antes ya no es necesario aquí,
           porque la lógica de batalla ahora se inicia desde WindowBuilder.
        // Se inicializan 2 entrenadores
        // Entrenador e1 = Entrenador.capturarEntrenador(nombre, pokemon1, pokemon2, pokemon3);
        // Entrenador e2 = Entrenador.capturarEntrenador(nombre, pokemon1, pokemon2, pokemon3);
        // Se inializa la batalla
        // Batalla.batallaPorEquipos(e1, eleccion1, e2, eleccion2);
        */
    }
}