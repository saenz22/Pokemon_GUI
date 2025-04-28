import java.util.ArrayList;  
import java.util.Random;     
import java.util.Scanner;    

public class Batalla {
    
    // Objetos globales
    static Scanner scanner = new Scanner(System.in); // Leer entradas por consola
    static Random random = new Random();             // Generar aleatoriedad

    // Método principal para iniciar una batalla entre dos entrenadores
    public static void batallaPorEquipos(Entrenador e1, Entrenador e2) {
        System.out.println("\n ¡Comienza la batalla entre " + e1.getNombre() + " y " + e2.getNombre() + "!");

        // Creamos listas con los Pokémon disponibles (copias del equipo original)
        ArrayList<Pokemon> disponibles1 = new ArrayList<Pokemon>(e1.getEquipo());
        ArrayList<Pokemon> disponibles2 = new ArrayList<Pokemon>(e2.getEquipo());

        // Seleccionamos el primer Pokémon que participará del equipo (menos HP o aleatorio)
        Pokemon activo1 = seleccionarInicial(disponibles1);
        Pokemon activo2 = seleccionarInicial(disponibles2);

        int turno = 1; // Contador de turnos

        // Mientras ambos entrenadores tengan Pokémones disponibles, continúa la batalla
        while (!disponibles1.isEmpty() && !disponibles2.isEmpty()) {
            System.out.println("\n--- TURNO " + turno + " ---");
            System.out.println(e1.getNombre() + " - " + activo1.getNombre() + " (HP: " + activo1.getHp() + ")");
            System.out.println(e2.getNombre() + " - " + activo2.getNombre() + " (HP: " + activo2.getHp() + ")");

            // Definimos el orden de ataque
            Pokemon primero, segundo;
            Entrenador entrenadorPrimero, entrenadorSegundo;

            if (activo1.getHp() < activo2.getHp()) {
                primero = activo1;
                segundo = activo2;
                entrenadorPrimero = e1;
                entrenadorSegundo = e2;
            } else if (activo2.getHp() < activo1.getHp()) {
                primero = activo2;
                segundo = activo1;
                entrenadorPrimero = e2;
                entrenadorSegundo = e1;
            } else {
                // Si tienen el mismo HP, elegimos aleatoriamente quién ataca primero
                if (random.nextBoolean()) {
                    primero = activo1;
                    segundo = activo2;
                    entrenadorPrimero = e1;
                    entrenadorSegundo = e2;
                } else {
                    primero = activo2;
                    segundo = activo1;
                    entrenadorPrimero = e2;
                    entrenadorSegundo = e1;
                }
            }

            // El primer Pokémon ataca
            System.out.println("\n" + entrenadorPrimero.getNombre() + " ataca:");
            primero.atacar(segundo);

            // Si el segundo muere, se elimina de la lista y se elige uno nuevo
            if (!segundo.getVivo()) {
                System.out.println(segundo.getNombre() + " ha sido derrotado.");
                if (entrenadorSegundo == e1) {
                    disponibles1.remove(segundo);
                    if (!disponibles1.isEmpty()) {
                        activo1 = elegirNuevoPokemon(entrenadorSegundo, disponibles1);
                    }
                } else {
                    disponibles2.remove(segundo);
                    if (!disponibles2.isEmpty()) {
                        activo2 = elegirNuevoPokemon(entrenadorSegundo, disponibles2);
                    }
                }
                turno++;
                continue; // Saltamos al siguiente turno
            }

            // El segundo Pokémon contraataca
            System.out.println("\n" + entrenadorSegundo.getNombre() + " contraataca:");
            segundo.atacar(primero);

            // Si el primero muere, se elimina y se elige uno nuevo
            if (!primero.getVivo()) {
                System.out.println(primero.getNombre() + " ha sido derrotado.");
                if (entrenadorPrimero == e1) {
                    disponibles1.remove(primero);
                    if (!disponibles1.isEmpty()) {
                        activo1 = elegirNuevoPokemon(entrenadorPrimero, disponibles1);
                    }
                } else {
                    disponibles2.remove(primero);
                    if (!disponibles2.isEmpty()) {
                        activo2 = elegirNuevoPokemon(entrenadorPrimero, disponibles2);
                    }
                }
            }

            turno++; // Aumenta el turno
        }

        // Mensaje final con el ganador
        if (disponibles1.isEmpty()) {
            System.out.println("\n ¡" + e2.getNombre() + " gana la batalla!");
            e2.celebracion();
        } else {
            System.out.println("\n ¡" + e1.getNombre() + " gana la batalla!");
            e1.celebracion();
        }
    }

    // Método que selecciona el primer Pokémon del equipo
    public static Pokemon seleccionarInicial(ArrayList<Pokemon> equipo) {
        Pokemon menor = equipo.get(0);
        for (Pokemon p : equipo) {
            if (p.getHp() < menor.getHp()) {
                menor = p;
            } else if (p.getHp() == menor.getHp() && random.nextBoolean()) {
                menor = p; // Aleatorio si empatan en HP
            }
        }
        return menor;
    }

    // Método para elegir un nuevo Pokémon si el actual es derrotado
    public static Pokemon elegirNuevoPokemon(Entrenador entrenador, ArrayList<Pokemon> disponibles) {
        System.out.println("\n" + entrenador.getNombre() + ", elige un nuevo Pokémon:");
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.println((i + 1) + ". " + disponibles.get(i).getNombre() + " (HP: " + disponibles.get(i).getHp() + ")");
        }
        int opcion;
        while(true){
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
                if (opcion<=disponibles.size() && opcion>0){
                    disponibles.get(opcion - 1).entrada();
                    return disponibles.get(opcion - 1);
                } else {
                    System.out.println("¡Ey! Elige una opción válida");
                    scanner.nextLine();
                }
            }
            else {
                System.out.println("Por favor, elige un número.");
                scanner.nextLine();
            }
        }
    }
}