import java.util.ArrayList;  
import java.util.Random;

/*
 *  EN RESUMEN:
 * Interacción que tiene con el usuario:
 *  
 * 1. capturarEntrenador(nombre, primero, segundo, tercero) ---> Solicita nombre de entrenador y los 3 nombres de los pokemones
 * 2. batallaPorEquipos(e1, elegido1, e2, elegido2) ---> Solicita pokemon elegido por cada uno para iniciar
 * 3. elegirAtaque(atacante) ---> Recibe el nuevo ataque del usuario
 * 4. elegirNuevoPokemon(entrenador, disponibles) ---> Recibe el nuevo pokemon del usuario
 * 
 */

public class Batalla implements interaccionUsuario {
    
    // Objetos globales
    static Random random = new Random(); // Generar aleatoriedad

    // Método principal para iniciar una batalla entre dos entrenadores
    public static void batallaPorEquipos(Entrenador e1, Pokemon elegido1, Entrenador e2, Pokemon elegido2) {

        // Creamos listas con los Pokémon disponibles (copias del equipo original)
        ArrayList<Pokemon> disponibles1 = new ArrayList<Pokemon>(e1.getEquipo());
        ArrayList<Pokemon> disponibles2 = new ArrayList<Pokemon>(e2.getEquipo());

        // Seleccionamos el primer Pokémon que participará del equipo (menos HP o aleatorio)
        Pokemon activo1 = elegido1;
        Pokemon activo2 = elegido2;

        // Mientras ambos entrenadores tengan Pokémones disponibles, continúa la batalla
        while (!disponibles1.isEmpty() && !disponibles2.isEmpty()) {

            // Definimos el orden de ataque
            Pokemon primero, segundo;
            Entrenador entrenadorPrimero, entrenadorSegundo;

            if (activo1.getVelocidad() > activo2.getVelocidad()) {
                primero = activo1;
                segundo = activo2;
                entrenadorPrimero = e1;
                entrenadorSegundo = e2;
            } else if (activo2.getVelocidad() > activo1.getVelocidad()) {
                primero = activo2;
                segundo = activo1;
                entrenadorPrimero = e2;
                entrenadorSegundo = e1;
            } else {
                // Si tienen la misma velocidad, elegimos aleatoriamente quién ataca primero
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
            //primero.atacar(elegirAtaque(atacante), segundo);

            // Si el segundo muere, se elimina de la lista y se elige uno nuevo
            if (!segundo.getVivo()) {
                // El Pokemon ha sido derrotado
                if (entrenadorSegundo == e1) {
                    disponibles1.remove(segundo);
                    if (!disponibles1.isEmpty()) {
                        //activo1 = elegirNuevoPokemon(entrenadorSegundo, disponibles1);
                    }
                } else {
                    disponibles2.remove(segundo);
                    if (!disponibles2.isEmpty()) {
                        //activo2 = elegirNuevoPokemon(entrenadorSegundo, disponibles2);
                    }
                }
                continue; // Saltamos al siguiente turno
            }

            // El segundo Pokémon contraataca
            //segundo.atacar(elegirAtaque(atacante), primero);

            // Si el primero muere, se elimina y se elige uno nuevo
            if (!primero.getVivo()) {
                // El Pokemon ha sido derrotado
                if (entrenadorPrimero == e1) {
                    disponibles1.remove(primero);
                    if (!disponibles1.isEmpty()) {
                        //activo1 = elegirNuevoPokemon(entrenadorPrimero, disponibles1);
                    }
                } else {
                    disponibles2.remove(primero);
                    if (!disponibles2.isEmpty()) {
                        //activo2 = elegirNuevoPokemon(entrenadorPrimero, disponibles2);
                    }
                }
            }
            continue;
        }

        // Mensaje final con el ganador
        if (disponibles1.isEmpty()) {
            e2.celebracion();
        } else {
            e1.celebracion();
        }
    }

    // MÉTODOS elegirAtaque() y elegirNuevoPokemon() candidatos para ser del Controlador
}