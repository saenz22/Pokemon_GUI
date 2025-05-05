import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Este es el punto de entrada de la aplicación. Aquí se inicializa la GUI y se ejecuta la lógica del programa.

        System.out.println("[Main] Iniciando aplicación...");

        // usa SwingUtilities.invokeLater para crear la GUI en el hilo correcto (EDT) por que swing asi es la mejor eleccion para crear la GUI
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

    }
}