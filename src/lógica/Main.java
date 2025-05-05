package src.lógica;
import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import src.interfaz.WindowBatalla;
import src.interfaz.WindowBuilder;
public class Main {

    public static void main(String[] args) throws Exception {

        WindowBuilder intro = new WindowBuilder(); // Se muestra la ventana introductoria

        Entrenador e1 = Entrenador.capturarEntrenador(intro.getNombre1(),intro.getPokemon1(), intro.getPokemon2(), intro.getPokemon3());
        Entrenador e2 = Entrenador.capturarEntrenador(intro.getNombre2(), intro.getPokemon4(), intro.getPokemon5(), intro.getPokemon6());

        intro.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Cuando la ventana se haya cerrado, abrimos la nueva ventana
                WindowBatalla batalla = new WindowBatalla(e1, e2);
                batalla.setVisible(true);
            }
        });
    }
}