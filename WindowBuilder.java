import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class WindowBuilder extends JFrame implements ActionListener, KeyListener { // Implementamos ActionListener
    

    public String getNombre1() {
        return nombre1;
    }


    public String getNombre2() {
        return nombre2;
    }

    private Timer timer; // Declaramos el Timer como un campo de la clase
    private int currentPanel = 0;
    private String nombre1 = "";
    private String nombre2 = "";
    private JTextField jugador1Field = new JTextField();
    private JTextField jugador2Field = new JTextField();

    public WindowBuilder() {
        setTitle("Pokémon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(605, 327);
        setResizable(false);
        setLocationRelativeTo(null);
        showFirstPanel(); // Mostrar el primer panel
        setVisible(true); // Hacemos visible la ventana

        // Creamos un temporizador que se activará después de 3 segundos
        timer = new Timer(3000, this); // El temporizador escucha a esta clase (WindowBuilder)
        timer.setRepeats(false);
        timer.start(); // Iniciamos el temporizador
    }

    public static void main(String[] args) {
        new WindowBuilder(); // Crear una instancia de WindowBuilder
    }



    private void showFirstPanel() {
        currentPanel = 1; // Cambiamos el panel actual a 1
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

        this.add(panel); // Usamos 'this' porque es una instancia de JFrame (WindowBuilder)
    }

    private void switchToNextPanel(JPanel panel) {
        getContentPane().removeAll(); // Limpiar la ventana
        add(panel); // Añadir el nuevo panel
        if ( currentPanel == 2 || currentPanel == 3 || currentPanel == 4) {
            panel.setFocusable(true); // Habilitar el foco para el panel
            panel.requestFocusInWindow(); // Solicitar el foco al panel
        }
       
        this.revalidate(); // Revalidar la ventana para que se actualice
      
    }

    private JPanel showSecondPanel() {
        currentPanel = 2; // Cambiamos el panel actual a 2
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null);
        secondPanel.setBackground(new Color(10, 20, 48));

        secondPanel.addKeyListener(this);

        // Crear el área de texto
        String texto = """

                    Estás a punto de sumergirte en un
                    mundo lleno de aventuras de las
                    que vas a ser protagonista.

                    Te cruzarás con rivales y criaturas
                    salvajes que querrán luchar contigo,
                    pero ¡ánimo, tú puedes!
                """;
        
        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(90, 40, 400, 200); // Posicionamos el cuadro de texto
        textArea.setEditable(false); // No editable
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        textArea.setCaretColor(new Color(0, 0, 0, 0)); // Sin cursor visible// Márgenes dentro del área de texto
        textArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 3, true)); // Borde alrededor

        // Añadir el área de texto al panel
        secondPanel.add(textArea);

        // Crear la flecha roja
        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 250, 30, 30); // Nueva posición razonable de la flecha
        flecha.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente de la flecha

        // Añadir la flecha encima del cuadro de texto
        secondPanel.add(flecha);

        // Añadir el secondPanel al JFrame

        return secondPanel; // Usamos 'this' para añadir el segundo panel
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Este método se ejecutará cuando el temporizador haya terminado (3 segundos)
            switchToNextPanel(showSecondPanel()); 
             // Mostrar el segundo panel
        
        // Cambiar al siguiente panel
    }

    private JPanel showThirdPanel() {
        currentPanel = 3; // Cambiamos el panel actual a 3
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(null);
        thirdPanel.setBackground(new Color(10, 20, 48));

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
        jugador1Field.addKeyListener(this); // Añadir el KeyListener al campo de texto

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
        jugador2Field.addKeyListener(this); // Añadir el KeyListener al campo de texto 
        thirdPanel.add(jugador2Field);
        // Crear la flecha roja
        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 230, 30, 30); // Nueva posición razonable de la flecha
        flecha.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente de la flecha
        // Añadir la flecha encima del cuadro de texto
        thirdPanel.add(flecha);
    

        return thirdPanel; // Usamos 'this' para añadir el tercer panel
        // Código para el tercer panel
        
    }

    private JPanel showFourthPanel() {
        currentPanel = 4; // Cambiamos el panel actual a 4
        JPanel fourthPanel = new JPanel();
        fourthPanel.setLayout(null);
        fourthPanel.setBackground(new Color(10, 20, 48));
       

       fourthPanel.addKeyListener(this);

        // Crear el área de texto
     
        String texto1 = """

                        Bienvenidos a Pokémon {jugador1} y 
                        {jugador2} les espera un gran 
                        desafío en su aventura.

                        Sus pokemones serán asignados 
                        aleatoriamente,y tendrán que 
                        enfrentarse para demostrar 
                        quién es el mejor entrenador.
                """;
        String texto = texto1.replace("{jugador1}", nombre1).replace("{jugador2}", nombre2);
        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(90, 40, 400, 200); // Posicionamos el cuadro de texto
        textArea.setEditable(false); // No editable
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        textArea.setCaretColor(new Color(0, 0, 0, 0)); // Sin cursor visible// Márgenes dentro del área de texto
        textArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 3, true)); // Borde alrededor

        // Añadir el área de texto al panel
       fourthPanel.add(textArea);

        // Crear la flecha roja
        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 250, 30, 30); // Nueva posición razonable de la flecha
        flecha.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente de la flecha

        // Añadir la flecha encima del cuadro de texto
       fourthPanel.add(flecha);

        // Añadir elfourthPanel al JFrame


        return fourthPanel; // Usamos 'this' para añadir el cuarto panel
    }
    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (currentPanel) {
                case 2:switchToNextPanel(showThirdPanel());
                    break;
                case 3:
                       
                    nombre1 = jugador1Field.getText(); // Obtener el texto del primer campo
                    nombre2 = jugador2Field.getText(); // Obtener el texto del segundo campo
                    if (nombre1.isEmpty() || nombre2.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Por favor, ingrese ambos nombres.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        switchToNextPanel(showFourthPanel());
                    }
                    break;
                default:
                    break;
            }
          // Cambiar al tercer panel
        }
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }
}
