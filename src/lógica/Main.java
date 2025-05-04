public class Main {
    public static void main(String[] args) throws Exception {

        WindowBuilder intro = new WindowBuilder(); // Se muestra la ventana introductoria
        
        // Se inicializan 2 entrenadores
        Entrenador e1 = Entrenador.capturarEntrenador(intro.getNombre1(),intro.getPokemon1(), intro.getPokemon2(), intro.getPokemon3());
        Entrenador e2 = Entrenador.capturarEntrenador(intro.getNombre2(), intro.getPokemon4(), intro.getPokemon5(), intro.getPokemon6());
        
        /* 
        // Se inializa la batalla
        Batalla.batallaPorEquipos(e1, eleccion1, e2, eleccion2);
        */
    }
}