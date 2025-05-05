package src.lógica;

import java.util.ArrayList;  
import java.util.Random;

public class Batalla {
    
    // Objetos globales
    static Random random = new Random(); // Generar aleatoriedad
    Entrenador e1, e2;
    ArrayList<Pokemon> disponibles1, disponibles2;
    Pokemon activo1, activo2;

    public Batalla(Entrenador e1, Entrenador e2, Pokemon activo1, Pokemon activo2) {
        this.e1 = e1;
        this.e2 = e2;
        this.activo1 = activo1;
        this.activo2 = activo2;
        this.disponibles1 = e1.getEquipo();
        this.disponibles2 = e2.getEquipo();
    }

    // Método principal para iniciar una batalla entre dos entrenadores
    public static Batalla instanciarBatalla(Entrenador e1, Entrenador e2, Pokemon a1, Pokemon a2) {
        Batalla batalla = new Batalla(e1, e2, a1, a2);
        batalla.ordenBatalla();
        batalla.combate();
        return batalla;
    }

    public void combate() {
        // getAtaqueElegido() --> Getter proveniente de método de WindowBatalla
        Pokemon atacado;
        while (!disponibles1.isEmpty() && !disponibles2.isEmpty()) {
            atacado = activo2;
            activo2 = atacarYComprobarEstado(activo1, /*getAtaqueElegido()*/null, disponibles2, activo2);
            if (activo2 == null) {
                break;
            } else if (atacado == activo2) {
                intercambiarActivos();
                continue;
            } else {
                // Método para mostrar a nuevo Pokemon
                ordenBatalla();
                continue;
            }
        }
        if (!disponibles1.isEmpty()) {
            e1.celebracion();
        } else {
            e2.celebracion();
        }
    }

    public void intercambiarActivos() {
        Pokemon temp1 = activo1;
        Entrenador temp2 = e1;
        this.activo1 = activo2;
        this.activo2 = temp1;
        this.e1 = e2;
        this.e2 = temp2;
    }

    public void ordenBatalla() {
        if (activo2.getVelocidad() > activo1.getVelocidad()) {
            this.intercambiarActivos();
        } else if (activo2.getVelocidad() == activo1.getVelocidad()) {
            if (!random.nextBoolean()) {
                this.intercambiarActivos();
            }
        }
    }

    public Pokemon atacarYComprobarEstado(Pokemon atacante, Ataque ataqueElegido, ArrayList<Pokemon> equipo, Pokemon pokemon) { 
        atacante.atacar(ataqueElegido, pokemon);
        if (!pokemon.getVivo()) {
            // El Pokemon ha sido derrotado
            equipo.remove(pokemon);
            if (!equipo.isEmpty()) {
                // pokemon = Getter proveniente de método de WindowBatalla
                return pokemon;
            } else {
                return null;
            }
        }
        return pokemon;
    }

    // MÉTODOS elegirAtaque() y elegirNuevoPokemon() candidatos para ser del Controlador
}