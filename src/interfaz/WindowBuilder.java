import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.SwingUtilities; // <-- *** IMPORTACIÓN AÑADIDA ***

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Asume que Entrenador y WindowBatalla están accesibles (mismo paquete o importados)
// import nombre_paquete.Entrenador; // Descomenta y ajusta si es necesario
// import nombre_paquete.WindowBatalla; // Descomenta y ajusta si es necesario


public class WindowBuilder extends JFrame implements ActionListener, KeyListener {

    //<editor-fold defaultstate="collapsed" desc="Getters (No modificados)">
    public String getNombre1() {
        return nombre1;
    }
    public String getNombre2() {
        return nombre2;
    }
    public String getPokemon1() {
        return pokemon1;
    }
    public String getPokemon2() {
        return pokemon2;
    }
    public String getPokemon3() {
        return pokemon3;
    }
    public String getPokemon4() {
        return pokemon4;
    }
    public String getPokemon5() {
        return pokemon5;
    }
    public String getPokemon6() {
        return pokemon6;
    }
    //</editor-fold>

    private Timer timer;
    private int currentPanel = 0;

    private String nombre1 = "";
    private String nombre2 = "";
    private String pokemon1 = "";
    private String pokemon2 = "";
    private String pokemon3 = "";
    private String pokemon4 = "";
    private String pokemon5 = "";
    private String pokemon6 = "";

    private JTextField jugador1Field = new JTextField();
    private JTextField jugador2Field = new JTextField();
    private JTextField poke1Field = new JTextField();
    private JTextField poke2Field = new JTextField();
    private JTextField poke3Field = new JTextField();
    private JTextField poke4Field = new JTextField();
    private JTextField poke5Field = new JTextField();
    private JTextField poke6Field = new JTextField();

    public WindowBuilder() {
        setTitle("Pokémon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(605, 327);
        setResizable(false);
        setLocationRelativeTo(null);
        showFirstPanel();
        setVisible(true);

        timer = new Timer(3000, this);
        timer.setRepeats(false);
        timer.start();
    }

    //<editor-fold defaultstate="collapsed" desc="showFirstPanel (No modificado)">
    private void showFirstPanel() {
        currentPanel = 1;
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#1e1e1e"));

        Font font = new Font("Monospaced", Font.PLAIN, 15);
        Font boldFont = new Font("Monospaced", Font.BOLD, 20);

        JLabel element1 = new JLabel("©2025");
        element1.setBounds(200, 90, 60, 23);
        element1.setFont(font);
        element1.setForeground(Color.decode("#D9D9D9"));
        panel.add(element1);

        JLabel element2 = new JLabel("Pokémon");
        element2.setBounds(300, 80, 100, 30);
        element2.setFont(boldFont);
        element2.setForeground(Color.decode("#D9D9D9"));
        panel.add(element2);

        JLabel element4 = new JLabel("©1995-2025");
        element4.setBounds(200, 120, 106, 17);
        element4.setFont(font);
        element4.setForeground(Color.decode("#D9D9D9"));
        panel.add(element4);

        JLabel element5 = new JLabel("Nintendo");
        element5.setBounds(300, 110, 106, 30);
        element5.setFont(boldFont);
        element5.setForeground(Color.decode("#D9D9D9"));
        panel.add(element5);

        JLabel element6 = new JLabel("©1995-2025");
        element6.setBounds(200, 150, 106, 17);
        element6.setFont(font);
        element6.setForeground(Color.decode("#D9D9D9"));
        panel.add(element6);

        JLabel element7 = new JLabel("Univallunos Inc");
        element7.setBounds(300, 140, 185, 30);
        element7.setFont(boldFont);
        element7.setForeground(Color.decode("#D9D9D9"));
        panel.add(element7);

        JLabel element9 = new JLabel("©1995-2025");
        element9.setBounds(200, 180, 106, 17);
        element9.setFont(font);
        element9.setForeground(Color.decode("#D9D9D9"));
        panel.add(element9);

        JLabel element10 = new JLabel("GAME FREAK inc");
        element10.setBounds(300, 170, 191, 31);
        element10.setFont(boldFont);
        element10.setForeground(Color.decode("#D9D9D9"));
        panel.add(element10);

        this.add(panel);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="switchToNextPanel (No modificado)">
    private void switchToNextPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel);
        if (currentPanel != 1) { // No enfocar el primer panel automaticamente
             panel.setFocusable(true);
             panel.requestFocusInWindow(); // Solicitar foco para KeyListener en el panel
        }
        revalidate();
        repaint(); // Asegurar repintado
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="showSecondPanel (No modificado)">
     private JPanel showSecondPanel() {
        currentPanel = 2;
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null);
        secondPanel.setBackground(new Color(10, 20, 48));

        // *** Añadir KeyListener al panel ***
        secondPanel.addKeyListener(this);

        String texto = """

                    Estás a punto de sumergirte en un
                    mundo lleno de aventuras de las
                    que vas a ser protagonista.

                    Te cruzarás con rivales y criaturas
                    salvajes que querrán luchar contigo,
                    pero ¡ánimo, tú puedes!
                """;

        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(90, 40, 400, 200);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        textArea.setCaretColor(new Color(0, 0, 0, 0));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 3, true));
        // *** Hacer el JTextArea no enfocable para que el panel reciba teclas ***
        textArea.setFocusable(false);
        secondPanel.add(textArea);

        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 250, 30, 30);
        flecha.setFont(new Font("Arial", Font.BOLD, 20));
        secondPanel.add(flecha);

        return secondPanel;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="actionPerformed (No modificado)">
     @Override
    public void actionPerformed(ActionEvent e) {
        // Cuando el timer inicial termina, muestra el segundo panel
        switchToNextPanel(showSecondPanel());
        // Asegurar que el panel tenga el foco para el KeyListener
        getContentPane().getComponent(0).requestFocusInWindow();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="showThirdPanel (Listener añadido a campos)">
    private JPanel showThirdPanel() {
        currentPanel = 3;
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(null);
        thirdPanel.setBackground(new Color(10, 20, 48));

        // *** Añadir KeyListener al panel ***
        thirdPanel.addKeyListener(this);

        JLabel label1 = new JLabel("Ingrese el nombre del entrenador 1:");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label1.setBounds(150, 60, 360, 25);
        thirdPanel.add(label1);

        jugador1Field.setBounds(210, 100, 200, 25);
        jugador1Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        jugador1Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        jugador1Field.setBackground(Color.WHITE);
        jugador1Field.setForeground(Color.BLACK);
        // *** Añadir KeyListener al campo de texto ***
        jugador1Field.addKeyListener(this);
        thirdPanel.add(jugador1Field);

        JLabel label2 = new JLabel("Ingrese el nombre del entrenador 2:");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label2.setBounds(150, 140, 360, 25);
        thirdPanel.add(label2);

        jugador2Field.setBounds(210, 180, 200, 25);
        jugador2Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        jugador2Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        jugador2Field.setBackground(Color.WHITE);
        jugador2Field.setForeground(Color.BLACK);
        // *** Añadir KeyListener al campo de texto ***
        jugador2Field.addKeyListener(this);
        thirdPanel.add(jugador2Field);

        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 230, 30, 30);
        flecha.setFont(new Font("Arial", Font.BOLD, 20));
        thirdPanel.add(flecha);

        // Solicitar foco explícitamente al primer campo al mostrar
        // Se hará después en switchToNextPanel si se necesita
        // SwingUtilities.invokeLater(() -> jugador1Field.requestFocusInWindow());

        return thirdPanel;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="showFourthPanel (Listener añadido a panel)">
    private JPanel showFourthPanel() {
        currentPanel = 4;
        JPanel fourthPanel = new JPanel();
        fourthPanel.setLayout(null);
        fourthPanel.setBackground(new Color(10, 20, 48));

        // *** Añadir KeyListener al panel ***
        fourthPanel.addKeyListener(this);

        String texto1 = """

                        Bienvenidos a Pokémon {jugador1} y
                        {jugador2} les espera un gran
                        desafío en su aventura.

                        Sus pokemones serán asignados
                        aleatoriamente,y tendrán que
                        enfrentarse para demostrar
                        quién es el mejor entrenador.
                """;
        // Usar los nombres ya guardados
        String texto = texto1.replace("{jugador1}", nombre1).replace("{jugador2}", nombre2);

        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(90, 40, 400, 200);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        textArea.setCaretColor(new Color(0, 0, 0, 0));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 3, true));
        // *** Hacer el JTextArea no enfocable ***
        textArea.setFocusable(false);
        fourthPanel.add(textArea);

        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 250, 30, 30);
        flecha.setFont(new Font("Arial", Font.BOLD, 20));
        fourthPanel.add(flecha);

        return fourthPanel;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="showFifthPanel (Listener añadido a campos)">
    private JPanel showFifthPanel() {
        currentPanel = 5;
        JPanel pokemonPanel = new JPanel();
        pokemonPanel.setLayout(null);
        pokemonPanel.setBackground(new Color(10, 20, 48));

        // *** Añadir KeyListener al panel ***
        pokemonPanel.addKeyListener(this);

        String solicitarpokemon = "¡Selecciona tus pokemones " + nombre1 + "!";
        JLabel titulo = new JLabel(solicitarpokemon);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 18));
        titulo.setBounds(120, 20, 500, 30);
        pokemonPanel.add(titulo);

        JLabel label1 = new JLabel("Pokemon 1");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label1.setBounds(200, 60, 200, 25);
        pokemonPanel.add(label1);

        poke1Field.setBounds(200, 90, 200, 25);
        poke1Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        poke1Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        poke1Field.setBackground(Color.WHITE);
        poke1Field.setForeground(Color.BLACK);
        // *** Añadir KeyListener al campo de texto ***
        poke1Field.addKeyListener(this);
        pokemonPanel.add(poke1Field);

        JLabel label2 = new JLabel("Pokemon 2");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label2.setBounds(200, 120, 200, 25);
        pokemonPanel.add(label2);

        poke2Field.setBounds(200, 150, 200, 25);
        poke2Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        poke2Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        poke2Field.setBackground(Color.WHITE);
        poke2Field.setForeground(Color.BLACK);
        // *** Añadir KeyListener al campo de texto ***
        poke2Field.addKeyListener(this);
        pokemonPanel.add(poke2Field);

        JLabel label3 = new JLabel("Pokemon 3");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label3.setBounds(200, 180, 200, 25);
        pokemonPanel.add(label3);

        poke3Field.setBounds(200, 210, 200, 25);
        poke3Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        poke3Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        poke3Field.setBackground(Color.WHITE);
        poke3Field.setForeground(Color.BLACK);
         // *** Añadir KeyListener al campo de texto ***
        poke3Field.addKeyListener(this);
        pokemonPanel.add(poke3Field);

        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(430, 250, 30, 30);
        flecha.setFont(new Font("Arial", Font.BOLD, 20));
        pokemonPanel.add(flecha);

        // Solicitar foco explícitamente al primer campo
        // SwingUtilities.invokeLater(() -> poke1Field.requestFocusInWindow());

        return pokemonPanel;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="showSixthPanel (MODIFICADO - Listeners añadidos)">
    private JPanel showSixthPanel() {
        currentPanel = 6;
        JPanel pokemonPanel = new JPanel();
        pokemonPanel.setLayout(null);
        pokemonPanel.setBackground(new Color(10, 20, 48));

        // *** Añadir KeyListener al panel ***
        pokemonPanel.addKeyListener(this);

        String solicitarpokemon = "¡Selecciona tus pokemones " + nombre2 + "!";
        JLabel titulo = new JLabel(solicitarpokemon);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 18));
        titulo.setBounds(120, 20, 500, 30);
        pokemonPanel.add(titulo);

        JLabel label1 = new JLabel("Pokemon 1");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label1.setBounds(200, 60, 200, 25);
        pokemonPanel.add(label1);

        poke4Field.setBounds(200, 90, 200, 25);
        poke4Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        poke4Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        poke4Field.setBackground(Color.WHITE);
        poke4Field.setForeground(Color.BLACK);
        // *** AÑADIDO KeyListener ***
        poke4Field.addKeyListener(this);
        pokemonPanel.add(poke4Field);

        JLabel label2 = new JLabel("Pokemon 2");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label2.setBounds(200, 120, 200, 25);
        pokemonPanel.add(label2);

        poke5Field.setBounds(200, 150, 200, 25);
        poke5Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        poke5Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        poke5Field.setBackground(Color.WHITE);
        poke5Field.setForeground(Color.BLACK);
        // *** AÑADIDO KeyListener ***
        poke5Field.addKeyListener(this);
        pokemonPanel.add(poke5Field);

        JLabel label3 = new JLabel("Pokemon 3");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label3.setBounds(200, 180, 200, 25);
        pokemonPanel.add(label3);

        poke6Field.setBounds(200, 210, 200, 25);
        poke6Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        poke6Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        poke6Field.setBackground(Color.WHITE);
        poke6Field.setForeground(Color.BLACK);
        // *** AÑADIDO KeyListener ***
        poke6Field.addKeyListener(this);
        pokemonPanel.add(poke6Field);

        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(430, 250, 30, 30);
        flecha.setFont(new Font("Arial", Font.BOLD, 20));
        pokemonPanel.add(flecha);

        // Solicitar foco explícitamente al primer campo
        // SwingUtilities.invokeLater(() -> poke4Field.requestFocusInWindow());

        return pokemonPanel;
    }
    //</editor-fold>

    // --- *** MÉTODO keyPressed COMPLETAMENTE REEMPLAZADO *** ---
    @Override
    public void keyPressed(KeyEvent e) {
        // Mensaje de depuración general
        System.out.println("[Debug WB] Key pressed on panel: " + currentPanel + ", Key code: " + e.getKeyCode() + ", Source: " + e.getSource().getClass().getSimpleName());

        // Solo actuar si la tecla es Enter
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("[Debug WB] Enter detected on panel " + currentPanel); // Confirmar detección de Enter

            switch (currentPanel) {
                case 2: // Después del panel de bienvenida
                    System.out.println("[Debug WB] Handling Enter for Panel 2");
                    switchToNextPanel(showThirdPanel());
                    // Intentar enfocar el primer campo después de cambiar el panel
                    SwingUtilities.invokeLater(() -> jugador1Field.requestFocusInWindow());
                    break;

                case 3: // Después de ingresar nombres
                     System.out.println("[Debug WB] Handling Enter for Panel 3");
                    nombre1 = jugador1Field.getText().trim();
                    nombre2 = jugador2Field.getText().trim();
                    if (nombre1.isEmpty() || nombre2.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor, ingrese ambos nombres.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        System.out.println("[Debug WB] Nombres ingresados: " + nombre1 + ", " + nombre2);
                        switchToNextPanel(showFourthPanel());
                        // El panel 4 no tiene campos, el foco va al panel
                         SwingUtilities.invokeLater(() -> getContentPane().getComponent(0).requestFocusInWindow());
                    }
                    break;

                case 4: // Después del panel informativo
                     System.out.println("[Debug WB] Handling Enter for Panel 4");
                    switchToNextPanel(showFifthPanel());
                    // Intentar enfocar el primer campo de Pokémon
                    SwingUtilities.invokeLater(() -> poke1Field.requestFocusInWindow());
                    break;

                case 5: // Después de ingresar Pokémon Entrenador 1
                     System.out.println("[Debug WB] Handling Enter for Panel 5");
                    pokemon1 = poke1Field.getText().trim();
                    pokemon2 = poke2Field.getText().trim();
                    pokemon3 = poke3Field.getText().trim();
                    if (pokemon1.isEmpty() || pokemon2.isEmpty() || pokemon3.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor, ingrese los tres nombres de Pokémon para " + nombre1 + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        System.out.println("[Debug WB] Entrenador 1 Pokémon: " + pokemon1 + ", " + pokemon2 + ", " + pokemon3);
                        switchToNextPanel(showSixthPanel());
                         // Intentar enfocar el primer campo de Pokémon para Entrenador 2
                        SwingUtilities.invokeLater(() -> poke4Field.requestFocusInWindow());
                    }
                    break;

                case 6: // Después de ingresar Pokémon Entrenador 2 (Lógica Principal)
                    System.out.println("[Debug WB] Handling Enter for Panel 6.");
                    pokemon4 = poke4Field.getText().trim();
                    pokemon5 = poke5Field.getText().trim();
                    pokemon6 = poke6Field.getText().trim();

                    if (pokemon4.isEmpty() || pokemon5.isEmpty() || pokemon6.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor, ingrese los tres nombres de Pokémon para " + nombre2 + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        System.out.println("[Debug WB] Entrenador 2 Pokémon entered: " + pokemon4 + ", " + pokemon5 + ", " + pokemon6);
                        try {
                            System.out.println("[Debug WB] Attempting to create Entrenador objects...");
                            // Asegúrate que Entrenador y capturarEntrenador existan y funcionen
                            // Puede que necesites manejar excepciones específicas de capturarEntrenador si las define
                            Entrenador entrenador1 = Entrenador.capturarEntrenador(nombre1, pokemon1, pokemon2, pokemon3);
                            Entrenador entrenador2 = Entrenador.capturarEntrenador(nombre2, pokemon4, pokemon5, pokemon6);
                            System.out.println("[Debug WB] Entrenador objects created successfully.");

                            System.out.println("[Debug WB] Attempting to create WindowBatalla...");
                            // Crea la instancia de la ventana
                            final WindowBatalla batalla = new WindowBatalla(entrenador1, entrenador2);
                            System.out.println("[Debug WB] WindowBatalla object created.");

                            // Hazla visible PRIMERO
                            batalla.setVisible(true);
                            System.out.println("[Debug WB] WindowBatalla setVisible(true) called.");

                            // Usa invokeLater para la inicialización pesada (selección de Pokémon)
                            SwingUtilities.invokeLater(() -> {
                                System.out.println("[Debug WB] Inside invokeLater, calling iniciarBatalla...");
                                batalla.iniciarBatalla(); // Llama al nuevo método de inicialización
                                System.out.println("[Debug WB] iniciarBatalla finished.");
                            });

                            // Cierra la ventana actual
                            this.dispose();
                            System.out.println("[Debug WB] WindowBuilder disposed.");

                        } catch (Exception ex) {
                            // Captura cualquier error durante la creación o inicio
                            System.err.println("ERROR CRÍTICO al crear entrenadores o iniciar la batalla: " + ex.getMessage());
                            ex.printStackTrace(); // MUY IMPORTANTE: Mira la consola para el detalle del error
                            JOptionPane.showMessageDialog(this,
                                    "Error MUY GRAVE al iniciar la batalla: " + ex.getMessage() +
                                    "\n\nREVISA LA CONSOLA para ver el detalle completo del error.\nVerifica los nombres de los Pokémon.",
                                    "Error de Inicio", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break; // Fin del case 6

                default:
                    System.out.println("[Debug WB] Enter presionado en panel inesperado: " + currentPanel);
                    break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se necesita implementar para esta lógica
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No se necesita implementar para esta lógica
    }

    // Método main (si quieres ejecutar esta ventana directamente para probar)
    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> new WindowBuilder());
    }
}