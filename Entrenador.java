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
        // Solicitar nombre al usuario
        capturarPokemon(nombre);
    }


    // Getter
    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }
    
    //Factory method para capturar un entrenador por consola sin necesidad de instanciar un objeto con new
    public static Entrenador capturarEntrenador(String nombre) {

        // Inicializando variables locales
        System.out.println("************");
        System.out.println("¡Hola, espero estés listo para comenzar una aventura pokemon!");
        System.out.println("************");
        System.out.println("Bienvenido " + nombre + "!");
        System.out.println("¡Prepárate para capturar Pokémons y convertirte en un maestro Pokémon!");
        System.out.println("************");
        System.out.println("¡Comencemos!");
        System.out.println("************");
        return new Entrenador(nombre); // Retorna nuevo entrenador
    }

    // Método para crear el equipo de 3 Pokemones
    public void capturarPokemon(String nombre) {
        for (int i = 0; i < 3; i++) {
            equipo.add(Pokemon.instanciarPokemon(nombre)); // Se instancian automáticamente
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