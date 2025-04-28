public class Main {
    public static void main(String[] args) throws Exception {

        // Se inicializan 2 entrenadores
        Entrenador e1 = Entrenador.capturarEntrenador();
        Entrenador e2 = Entrenador.capturarEntrenador();
        
        // Se inializa la batalla
        Batalla.batallaPorEquipos(e1, e2);
    }
}