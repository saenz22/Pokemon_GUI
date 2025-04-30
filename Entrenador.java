import java.util.Scanner;
import java.util.ArrayList;
public class Entrenador extends SerVivo {

    // Atributos
    private ArrayList<Pokemon> equipo = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    ArrayList<Integer> repetidos = new ArrayList<>();

    // Constructor
    public Entrenador(String nombre) {
        // Heredando nombre
        super(nombre);
        capturarPokemon();
    }


    // Getter
    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }
    
    //Factory method para capturar un entrenador por consola sin necesidad de instanciar un objeto con new
    public static Entrenador capturarEntrenador() {

        // Inicializando variables locales
        String nombre = "";
        System.out.println("************");
        System.out.println("¡Hola, espero estés listo para comenzar una aventura pokemon!");

        // Para evitar que ingrese nombre vacío
        while (nombre.isEmpty()) {
            System.out.print("Ingrese el nombre del entrenador: ");
            nombre = scanner.nextLine().trim(); // Eliminamos espacios en blanco al inicio y al final

            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Inténtalo de nuevo.");
            }
        }
        System.out.println("************");
        System.out.println("Bienvenido " + nombre + "!");
        System.out.println("¡Prepárate para capturar Pokémons y convertirte en un maestro Pokémon!");
        System.out.println("************");
        System.out.println("¡Comencemos!");
        System.out.println("************");
        return new Entrenador(nombre); // Retorna nuevo entrenador
    }

    // Método para crear el equipo de 3 Pokemones
    public void capturarPokemon() {
    // preguntar si quiere crear equipo manualmente o aleatoriamente
    boolean equipoManual = true;
    System.out.println("¿Quieres capturar un pokemon de forma manual? (s/n)");
    String respuesta = scanner.nextLine().trim().toLowerCase();
    while (true) {
        if (respuesta.equals("s")) {
            break;
        } else if (respuesta.equals("n")) {
            equipoManual = false; // Elegir automáticamente
            break;
        } else {
            System.out.println("Respuesta no válida. Por favor, ingresa 's' o 'n'.");
            respuesta = scanner.nextLine().trim().toLowerCase();
        }
    }
  for (int i = 0; i < 3; i++) {
       equipo.add(Pokemon.instanciarPokemon(equipoManual, repetidos)); // Se instancian automáticamente
      
  }

} 

    // Métodos heredados y sobrescritos
    @Override
    public void entrada(){
        System.out.println("Me llamo " + getNombre() + ", ¡Y seré tu contrincante!");
    }

    @Override
    public void celebracion(){
        System.out.println("Eso es, ¡victoria!");
    }
}