import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; // Importar para redimensionar imagen

/**
 * Interfaz gráfica para una batalla Pokémon simple.
 * MODIFICADA (v4): Layout reorganizado según imagen, texto blanco y grande,
 * botones más largos, alternativa de vida como texto.
 * IMÁGENES Y INFO MOVIDAS a ~45% desde abajo.
 */
public class BatallaGUI_v4 extends JFrame { // Cambiado a v4 para diferenciar

    // --- Constantes ---
    private static final int ANCHO_VENTANA = 800;
    private static final int ALTO_VENTANA = 800;
    private static final int ANCHO_IMAGEN_PKM = 200; // Un poco más grandes
    private static final int ALTO_IMAGEN_PKM = 200;
    private static final int ANCHO_INFO_PKM = 250; // Ancho reservado para nombre/nivel/vida
    private static final int ALTO_INFO_PKM_BLOQUE = 30 + 18 + 25 + 10; // Nombre(30) + Barra(18) + TextoVida(25) + Espacios(10)
    private static final int ANCHO_BOTON_PANEL = 340;
    private static final int ALTO_BOTON_PANEL = 100;
    private static final int Y_POS_POKEMON_AREA = (int) (ALTO_VENTANA * 0.30); // 45% desde abajo
    private static final int MARGEN_LATERAL = 60; // Margen desde los lados para Pkm/Info

    private static final Font FONT_INFO_GRANDE = new Font("Arial", Font.BOLD, 18); // Fuente más grande
    private static final Font FONT_VIDA_TEXTO = new Font("Arial", Font.BOLD, 16); // Fuente para HP texto
    private static final Color COLOR_TEXTO_INFO = Color.WHITE; // Color blanco para texto

    // --- Componentes de la GUI ---
    private JPanel panelPrincipal;

    // Elementos Entrenador 1 (Izquierda)
    private JLabel lblImagenPokemon1;
    private JLabel lblNombrePokemon1;
    private JLabel lblNivelPokemon1;
    private JProgressBar pbVidaPokemon1;
    private JLabel lblVidaTextoPokemon1; // Texto HP: x/y
    private JPanel panelInfoPokemon1; // Panel para agrupar info (nombre, nivel, vida)

    // Elementos Entrenador 2 (Derecha)
    private JLabel lblImagenPokemon2;
    private JLabel lblNombrePokemon2;
    private JLabel lblNivelPokemon2;
    private JProgressBar pbVidaPokemon2;
    private JLabel lblVidaTextoPokemon2; // Texto HP: x/y
     private JPanel panelInfoPokemon2; // Panel para agrupar info

    // Paneles de Ataque
    private JPanel panelAtaques1;
    private JPanel panelAtaques2;

    // Paneles de Selección de Pokémon
    private JPanel panelSeleccionPokemon1;
    private JLabel lblSeleccionInfo1;
    private JButton btnSeleccionarPokemon1_Op2;
    private JButton btnSeleccionarPokemon1_Op3;

    // Botones de Ataque Entrenador 1
    private JButton btnAtaque1_1;
    private JButton btnAtaque1_2;
    private JButton btnAtaque1_3;
    private JButton btnAtaque1_4;

    // Botones de Ataque Entrenador 2
    private JButton btnAtaque2_1;
    private JButton btnAtaque2_2;
    private JButton btnAtaque2_3;
    private JButton btnAtaque2_4;

    private JPanel panelSeleccionPokemon2;
    private JLabel lblSeleccionInfo2;
    private JButton btnSeleccionarPokemon2_Op2;
    private JButton btnSeleccionarPokemon2_Op3;

    // Etiqueta para info de turno
    private JLabel lblTurnoInfo;

    // --- Variables de Estado (Simuladas) ---
    private boolean turnoEntrenador1 = true;
    // ... tu lógica de juego ...

    // --- Constructor ---
    public BatallaGUI_v4() { // Cambiado a v4
        setTitle("Batalla Pokémon v4"); // Cambiado a v4
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- Panel Principal con Fondo ---
        try {
            // ¡¡¡CAMBIA ESTA RUTA A TU IMAGEN DE FONDO!!!
            String rutaFondo = "image/Estadio.jpg";
            final Image imagenFondo = ImageIO.read(new File(rutaFondo));
            panelPrincipal = new JPanel(null) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Asegura que la imagen cubra toda la ventana
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            };
            panelPrincipal.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
        } catch (Exception e) {
            System.err.println("Error al cargar imagen de fondo: " + e.getMessage());
            panelPrincipal = new JPanel(null);
            panelPrincipal.setBackground(new Color(100, 100, 120)); // Fondo oscuro si falla
            panelPrincipal.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
        }
        setContentPane(panelPrincipal);

        // --- Inicializar Componentes ---
        // Inicializamos paneles de ataque y selección primero ya que están anclados abajo
        inicializarPanelesAtaques();
        inicializarPanelSeleccion();

        // Ahora inicializamos componentes de Pokémon/Info en sus nuevas posiciones
        inicializarComponentesEntrenador1();
        inicializarComponentesEntrenador2();

        // Inicializar info de turno
        inicializarInfoTurno();


        // --- Añadir Componentes al Panel Principal ---
        // Entrenador 1
        panelPrincipal.add(panelInfoPokemon1); // Añadimos el panel que contiene info
        panelPrincipal.add(lblImagenPokemon1); // La imagen va directamente al panel principal para control total de Z

        // Entrenador 2
        panelPrincipal.add(panelInfoPokemon2); // Añadimos el panel que contiene info
        panelPrincipal.add(lblImagenPokemon2); // La imagen va directamente al panel principal

        // Paneles de Ataque/Selección (abajo)
        panelPrincipal.add(panelAtaques1);
        panelPrincipal.add(panelAtaques2);
        panelPrincipal.add(panelSeleccionPokemon1); // Se añade pero inicialmente invisible
        panelPrincipal.add(panelSeleccionPokemon2); // Se añade pero inicialmente invisible

        // Info de Turno (casi abajo del todo)
        panelPrincipal.add(lblTurnoInfo);


        // --- Configuración Inicial (¡¡¡REEMPLAZAR CON TU LÓGICA!!!) ---
        // Es importante llamar a actualizarInfo ANTES de actualizarVida para que vidaMaxima esté seteada en la barra
        // ¡¡¡CAMBIA ESTAS RUTAS E INFOS A TUS POKEMONES REALES!!!
        actualizarInfoPokemon1("Pikachu", 5, 100, 100, "\"C:\\Users\\josem\\JAVA\\Pokemon\\Pokemon_GUI\\WindowBattle\\image\\hatsApp Image 2025-04-30 at 6.25.32 PM.png\""); // Ejemplo de ruta real
        actualizarInfoPokemon2("Charmander", 5, 100, 100, "\"C:\\Users\\josem\\JAVA\\Pokemon\\Pokemon_GUI\\WindowBattle\\image\\WhatsApp Image 2025-04-3at 6.25.32 PM (1).png\""); // Ejemplo de ruta real

        // Asegura que el valor inicial sea 100% en barra y texto
        actualizarVidaPokemon(1, 100, 100);
        actualizarVidaPokemon(2, 100, 100);

        agregarMensaje("¡Comienza la batalla!");
        configurarTurno(); // Configura el primer turno

        setVisible(true);
    }

    // --- Métodos de Inicialización ---

    private void inicializarPanelesAtaques() {
        // Posiciona los paneles de ataque anclados al fondo
        int yAtaques = ALTO_VENTANA - ALTO_BOTON_PANEL - 50; // 50px desde el borde inferior
        int xAtaques1 = MARGEN_LATERAL; // Margen lateral para el panel izquierdo
        int xAtaques2 = ANCHO_VENTANA - ANCHO_BOTON_PANEL - MARGEN_LATERAL; // Margen lateral para el panel derecho

        // --- Panel y Botones Ataques Entrenador 1 ---
        panelAtaques1 = new JPanel(new GridLayout(2, 2, 6, 6)); // Espaciado ligero
        panelAtaques1.setBounds(xAtaques1, yAtaques, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelAtaques1.setOpaque(false); // Hacer transparente para ver fondo

        /*
         * ArrayList<Ataque> ataques = pokemonElegido.getAtaques();
         * 
         * btnAtaque1_1 = crearBotonAtaque("\"ataques.get(0).getNombre()\"");
         */

        btnAtaque1_1 = crearBotonAtaque("\"Placaje\"");
        btnAtaque1_2 = crearBotonAtaque("\"Ataque Rápido\"");
        btnAtaque1_3 = crearBotonAtaque("\"Rayo\"");
        btnAtaque1_4 = crearBotonAtaque("\"Cola Férrea\"");

        ActionListener listenerAtaque1 = e -> {
            if (!turnoEntrenador1 || panelSeleccionPokemon1.isVisible() || panelSeleccionPokemon2.isVisible()) return; // No atacar si no es tu turno o hay selección pendiente
            JButton botonPulsado = (JButton) e.getSource();
            String nombreAtaque = botonPulsado.getText().replace("\"", ""); // Quitar comillas simuladas
            agregarMensaje("Entrenador 1 usó: " + nombreAtaque);
            // --- LÓGICA ATAQUE E1 ---
            // Simulación daño y cambio turno
            int vidaActualP2 = pbVidaPokemon2.getValue();
            int danioSimulado = 20 + (int)(Math.random()*11); // 20-30
            int nuevaVidaP2 = Math.max(0, vidaActualP2 - danioSimulado);
            agregarMensaje("Causó " + danioSimulado + " de daño a " + lblNombrePokemon2.getText().replace("\"", "") + "!"); // Usar nombre real del label
            actualizarVidaPokemon(2, nuevaVidaP2, pbVidaPokemon2.getMaximum()); // Pasar índice del Pokémon que recibió el daño
            verificarDebilitado(2); // Verificar si el atacado (Pkm 2) se debilitó
            // --- FIN LÓGICA E1 ---
            // El cambio de turno ahora se hace en verificarDebilitado o después de la selección
        };
        btnAtaque1_1.addActionListener(listenerAtaque1);
        btnAtaque1_2.addActionListener(listenerAtaque1);
        btnAtaque1_3.addActionListener(listenerAtaque1);
        btnAtaque1_4.addActionListener(listenerAtaque1);

        panelAtaques1.add(btnAtaque1_1);
        panelAtaques1.add(btnAtaque1_2);
        panelAtaques1.add(btnAtaque1_3);
        panelAtaques1.add(btnAtaque1_4);


        // --- Panel y Botones Ataques Entrenador 2 ---
        panelAtaques2 = new JPanel(new GridLayout(2, 2, 6, 6));
        panelAtaques2.setBounds(xAtaques2, yAtaques, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelAtaques2.setOpaque(false); // Hacer transparente para ver fondo

        btnAtaque2_1 = crearBotonAtaque("\"Ascuas\"");
        btnAtaque2_2 = crearBotonAtaque("\"Gruñido\"");
        btnAtaque2_3 = crearBotonAtaque("\"Lanzallamas\"");
        btnAtaque2_4 = crearBotonAtaque("\"Giro Fuego\"");

        ActionListener listenerAtaque2 = e -> {
             if (turnoEntrenador1 || panelSeleccionPokemon1.isVisible() || panelSeleccionPokemon2.isVisible()) return; // No atacar si no es tu turno o hay selección pendiente
             JButton botonPulsado = (JButton) e.getSource();
             String nombreAtaque = botonPulsado.getText().replace("\"", ""); // Quitar comillas simuladas
             agregarMensaje("Entrenador 2 usó: " + nombreAtaque);
             // --- LÓGICA ATAQUE E2 ---
             // Simulación daño y cambio turno
             int vidaActualP1 = pbVidaPokemon1.getValue();
             int danioSimulado = 20 + (int)(Math.random()*11); // 20-30
             int nuevaVidaP1 = Math.max(0, vidaActualP1 - danioSimulado);
             agregarMensaje("Causó " + danioSimulado + " de daño a " + lblNombrePokemon1.getText().replace("\"", "") + "!"); // Usar nombre real del label
             actualizarVidaPokemon(1, nuevaVidaP1, pbVidaPokemon1.getMaximum()); // Pasar índice del Pokémon que recibió el daño
             verificarDebilitado(1); // Verificar si el atacado (Pkm 1) se debilitó
             // --- FIN LÓGICA E2 ---
             // El cambio de turno ahora se hace en verificarDebilitado o después de la selección
         };
        btnAtaque2_1.addActionListener(listenerAtaque2);
        btnAtaque2_2.addActionListener(listenerAtaque2);
        btnAtaque2_3.addActionListener(listenerAtaque2);
        btnAtaque2_4.addActionListener(listenerAtaque2);

        panelAtaques2.add(btnAtaque2_1);
        panelAtaques2.add(btnAtaque2_2);
        panelAtaques2.add(btnAtaque2_3);
        panelAtaques2.add(btnAtaque2_4);
    }

    private void inicializarComponentesEntrenador1() {
        // Posiciones relativas al área del Pokémon (45% desde abajo)
        int yBaseArea = Y_POS_POKEMON_AREA; // La coordenada Y de referencia (parte superior del área)
    
        // Panel para agrupar Nombre, Nivel y Vida (barra + texto)
        panelInfoPokemon1 = new JPanel(null); // Usamos null layout dentro para posicionar elementos
        panelInfoPokemon1.setOpaque(false); // Hacer transparente
        // Definimos el área del panel de info - alineado a la izquierda
        int xPanelInfo1 = MARGEN_LATERAL; // Margen desde la izquierda
        int yPanelInfo1 = yBaseArea;
        panelInfoPokemon1.setBounds(xPanelInfo1, yPanelInfo1, ANCHO_INFO_PKM, ALTO_INFO_PKM_BLOQUE);
    
        // Componentes dentro del panelInfoPokemon1 (posiciones relativas a panelInfoPokemon1)
        int infoPaddingX = 10; // Pequeño margen dentro del panel de info
        int infoSpacingY = 5; // Espacio vertical entre elementos
    
        lblNombrePokemon1 = new JLabel("\"Nombre Pkm 1\"");
        lblNombrePokemon1.setBounds(infoPaddingX, 0, ANCHO_INFO_PKM - infoPaddingX * 2, 30);
        lblNombrePokemon1.setFont(FONT_INFO_GRANDE);
        lblNombrePokemon1.setForeground(COLOR_TEXTO_INFO);
    
        lblNivelPokemon1 = new JLabel("Nv. \"Lvl\"");
        lblNivelPokemon1.setBounds(infoPaddingX + 150, 0, ANCHO_INFO_PKM - infoPaddingX * 2 - 150, 30); // Ajustar posición X y ancho
        lblNivelPokemon1.setFont(FONT_INFO_GRANDE);
        lblNivelPokemon1.setForeground(COLOR_TEXTO_INFO);
    
        // Barra de vida (justo debajo del nombre/nivel)
        pbVidaPokemon1 = new JProgressBar(0, 100);
        pbVidaPokemon1.setBounds(infoPaddingX, 30 + infoSpacingY, ANCHO_INFO_PKM - infoPaddingX * 2, 18); // Ancho del panel de info
        pbVidaPokemon1.setValue(100);
        pbVidaPokemon1.setStringPainted(false); // No pintar porcentaje DENTRO de la barra
        pbVidaPokemon1.setForeground(Color.GREEN);
        pbVidaPokemon1.setBackground(Color.DARK_GRAY); // Fondo oscuro para la barra
        pbVidaPokemon1.setBorderPainted(false);
    
        // Texto de vida (debajo de la barra)
        lblVidaTextoPokemon1 = new JLabel("HP: 100/100");
        lblVidaTextoPokemon1.setBounds(infoPaddingX, 30 + infoSpacingY + 18 + infoSpacingY, ANCHO_INFO_PKM - infoPaddingX * 2, 25);
        lblVidaTextoPokemon1.setFont(FONT_VIDA_TEXTO);
        lblVidaTextoPokemon1.setForeground(COLOR_TEXTO_INFO);
        lblVidaTextoPokemon1.setHorizontalAlignment(SwingConstants.CENTER); // Centrado bajo la barra
    
        // Añadir componentes al panel de info
        panelInfoPokemon1.add(lblNombrePokemon1);
        panelInfoPokemon1.add(lblNivelPokemon1);
        panelInfoPokemon1.add(pbVidaPokemon1);
        panelInfoPokemon1.add(lblVidaTextoPokemon1);
    
        // Imagen del Pokémon (Posicionada debajo del panel de info y centrada horizontalmente en su área)
        int yImagen1 = yPanelInfo1 + panelInfoPokemon1.getHeight() + 10; // 10px de espacio debajo del panel de info
        // Calcular posición X para centrar la imagen en el área disponible (desde MARGEN_LATERAL hasta antes del centro)
        int xImagen1 = MARGEN_LATERAL + (ANCHO_INFO_PKM / 2) - (ANCHO_IMAGEN_PKM / 2); // Centrada horizontalmente con el panel de info
        lblImagenPokemon1 = new JLabel();
        lblImagenPokemon1.setBounds(xImagen1, yImagen1, ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM);
        lblImagenPokemon1.setOpaque(false); // <--- AÑADIR ESTA LÍNEA
        //lblImagenPokemon1.setBorder(new LineBorder(Color.CYAN)); // Borde temporal para depurar posición
        lblImagenPokemon1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPokemon1.setVerticalAlignment(SwingConstants.CENTER); // Asegurar que la imagen o texto esté centrado
        lblImagenPokemon1.setText("Img Pkm 1"); // Placeholder inicial
    }

    private void inicializarComponentesEntrenador2() {
        // Posiciones relativas al área del Pokémon (45% desde abajo)
       int yBaseArea = Y_POS_POKEMON_AREA; // La coordenada Y de referencia (parte superior del área)
   
       // Panel para agrupar Nombre, Nivel y Vida (barra + texto)
       panelInfoPokemon2 = new JPanel(null); // Usamos null layout dentro para posicionar elementos
       panelInfoPokemon2.setOpaque(false); // Hacer transparente
       // Definimos el área del panel de info - alineado a la derecha
       int xPanelInfo2 = ANCHO_VENTANA - MARGEN_LATERAL - ANCHO_INFO_PKM; // Margen desde la derecha
       int yPanelInfo2 = yBaseArea;
       panelInfoPokemon2.setBounds(xPanelInfo2, yPanelInfo2, ANCHO_INFO_PKM, ALTO_INFO_PKM_BLOQUE);
   
   
       // Componentes dentro del panelInfoPokemon2 (posiciones relativas a panelInfoPokemon2)
       int infoPaddingX = 10; // Pequeño margen dentro del panel de info
       int infoSpacingY = 5; // Espacio vertical entre elementos
   
       lblNombrePokemon2 = new JLabel("\"Nombre Pkm 2\"");
       lblNombrePokemon2.setBounds(infoPaddingX, 0, ANCHO_INFO_PKM - infoPaddingX * 2, 30);
       lblNombrePokemon2.setFont(FONT_INFO_GRANDE);
       lblNombrePokemon2.setForeground(COLOR_TEXTO_INFO);
       lblNombrePokemon2.setHorizontalAlignment(SwingConstants.RIGHT); // Alinear texto a la derecha en el panel de info
   
       lblNivelPokemon2 = new JLabel("Nv. \"Lvl\"");
       lblNivelPokemon2.setBounds(infoPaddingX, 0, 100, 30); // Alinear a la izquierda del panel de info
       lblNivelPokemon2.setFont(FONT_INFO_GRANDE);
       lblNivelPokemon2.setForeground(COLOR_TEXTO_INFO);
   
   
       pbVidaPokemon2 = new JProgressBar(0, 100);
       pbVidaPokemon2.setBounds(infoPaddingX, 30 + infoSpacingY, ANCHO_INFO_PKM - infoPaddingX * 2, 18);
       pbVidaPokemon2.setValue(100);
       pbVidaPokemon2.setStringPainted(false);
       pbVidaPokemon2.setForeground(Color.GREEN);
       pbVidaPokemon2.setBackground(Color.DARK_GRAY);
       pbVidaPokemon2.setBorderPainted(false);
   
   
       lblVidaTextoPokemon2 = new JLabel("HP: 100/100");
       lblVidaTextoPokemon2.setBounds(infoPaddingX, 30 + infoSpacingY + 18 + infoSpacingY, ANCHO_INFO_PKM - infoPaddingX * 2, 25);
       lblVidaTextoPokemon2.setFont(FONT_VIDA_TEXTO);
       lblVidaTextoPokemon2.setForeground(COLOR_TEXTO_INFO);
       lblVidaTextoPokemon2.setHorizontalAlignment(SwingConstants.CENTER); // Centrado bajo la barra
   
   
       // Añadir componentes al panel de info
       panelInfoPokemon2.add(lblNombrePokemon2);
       panelInfoPokemon2.add(lblNivelPokemon2);
       panelInfoPokemon2.add(pbVidaPokemon2);
       panelInfoPokemon2.add(lblVidaTextoPokemon2);
   
   
       // Imagen del Pokémon (Posicionada debajo del panel de info y centrada horizontalmente en su área)
       int yImagen2 = yPanelInfo2 + panelInfoPokemon2.getHeight() + 10; // 10px de espacio debajo del panel de info
        // Calcular posición X para centrar la imagen en el área disponible (desde antes del centro hasta ANCHO_VENTANA - MARGEN_LATERAL)
       int xImagen2 = ANCHO_VENTANA - MARGEN_LATERAL - ANCHO_INFO_PKM + (ANCHO_INFO_PKM / 2) - (ANCHO_IMAGEN_PKM / 2); // Centrada horizontalmente con el panel de info
       lblImagenPokemon2 = new JLabel();
       lblImagenPokemon2.setBounds(xImagen2, yImagen2, ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM);
       lblImagenPokemon2.setOpaque(false); // <--- AÑADIR ESTA LÍNEA
       //lblImagenPokemon2.setBorder(new LineBorder(Color.MAGENTA)); // Borde temporal para depurar posición
       lblImagenPokemon2.setHorizontalAlignment(SwingConstants.CENTER);
       lblImagenPokemon2.setVerticalAlignment(SwingConstants.CENTER); // Asegurar que la imagen o texto esté centrado
       lblImagenPokemon2.setText("Img Pkm 2"); // Placeholder inicial
   }


    // Método auxiliar para crear botones de ataque
    private JButton crearBotonAtaque(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente ligeramente más grande para botones más largos
        boton.setForeground(COLOR_TEXTO_INFO); // Texto blanco
        boton.setContentAreaFilled(false); // Fondo transparente
        boton.setBorderPainted(true);
        boton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Borde fino
        boton.setFocusPainted(false);
        boton.setOpaque(false); // Asegurar transparencia
        return boton;
    }

    // inicializarPanelSeleccion (reutiliza posiciones de paneles de ataque)
    private void inicializarPanelSeleccion() {
        // --- Panel Selección Entrenador 1 ---
        panelSeleccionPokemon1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15)); // Ajustar vgap para centrar mejor contenido
        panelSeleccionPokemon1.setBounds(panelAtaques1.getBounds()); // Usa los límites del panel de ataques
        panelSeleccionPokemon1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Elige Siguiente (Entrenador 1)", 0, 0, FONT_INFO_GRANDE, Color.WHITE)); // Borde y título blancos
        panelSeleccionPokemon1.setVisible(false);
        panelSeleccionPokemon1.setBackground(new Color(50, 50, 70, 230)); // Fondo oscuro semi-transparente

        lblSeleccionInfo1 = new JLabel("\"Pkm 1 Debilitado\" no puede continuar! Elige:");
        lblSeleccionInfo1.setForeground(COLOR_TEXTO_INFO); // Texto blanco
        lblSeleccionInfo1.setFont(FONT_VIDA_TEXTO);

        // Usar botones con estilo similar a los de ataque si se desea, o dejarlos estándar.
        // Dejamos estándar por ahora para diferenciarlos de los de ataque.
        btnSeleccionarPokemon1_Op2 = new JButton("\"Nombre Pkm 2\"");
        btnSeleccionarPokemon1_Op3 = new JButton("\"Nombre Pkm 3\"");
        btnSeleccionarPokemon1_Op2.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSeleccionarPokemon1_Op3.setFont(new Font("Arial", Font.PLAIN, 12));

        ActionListener listenerSeleccion1 = e -> {
            JButton botonPulsado = (JButton) e.getSource();
            String nombrePokemonElegido = botonPulsado.getText().replace("\"", "");
            agregarMensaje("Entrenador 1 envía a: " + nombrePokemonElegido);
            // --- TU LÓGICA CAMBIO PKM 1 ---
            // Aquí deberías actualizar el pokemon actual del Entrenador 1
            // Simulación: Cambiar a un pokemon genérico
             actualizarInfoPokemon1(nombrePokemonElegido, 7, 120, 120, "C:\\Users\\josem\\JAVA\\Pokemon\\Pokemon_GUI\\WindowBattle\\image\\OtroPokemon1.png"); // Reemplazar con lógica real

             // Asegura resetear vida a la vida máxima del nuevo pkm
            actualizarVidaPokemon(1, pbVidaPokemon1.getMaximum(), pbVidaPokemon1.getMaximum());
            // --- FIN LÓGICA ---

            // Oculta el panel de selección, muestra los paneles de ataque
            panelSeleccionPokemon1.setVisible(false);
            panelAtaques1.setVisible(true);
            panelAtaques2.setVisible(true); // Asegura que ambos estén visibles antes de cambiar turno
            cambiarTurno(); // Turno pasa al rival
        };
        btnSeleccionarPokemon1_Op2.addActionListener(listenerSeleccion1);
        btnSeleccionarPokemon1_Op3.addActionListener(listenerSeleccion1);

        panelSeleccionPokemon1.add(lblSeleccionInfo1);
        panelSeleccionPokemon1.add(btnSeleccionarPokemon1_Op2);
        panelSeleccionPokemon1.add(btnSeleccionarPokemon1_Op3);


        // --- Panel Selección Entrenador 2 --- (Análogo)
        panelSeleccionPokemon2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15)); // Ajustar vgap
        panelSeleccionPokemon2.setBounds(panelAtaques2.getBounds());// Usa los límites del panel de ataques
        panelSeleccionPokemon2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Elige Siguiente (Entrenador 2)", 0, 0, FONT_INFO_GRANDE, Color.WHITE));
        panelSeleccionPokemon2.setVisible(false);
        panelSeleccionPokemon2.setBackground(new Color(70, 50, 50, 230)); // Color diferente para diferenciar

        lblSeleccionInfo2 = new JLabel("\"Pkm 2 Debilitado\" no puede continuar! Elige:");
        lblSeleccionInfo2.setForeground(COLOR_TEXTO_INFO);
        lblSeleccionInfo2.setFont(FONT_VIDA_TEXTO);

        btnSeleccionarPokemon2_Op2 = new JButton("\"Nombre Pkm 2 (Rival)\"");
        btnSeleccionarPokemon2_Op3 = new JButton("\"Nombre Pkm 3 (Rival)\"");
        btnSeleccionarPokemon2_Op2.setFont(new Font("Arial", Font.PLAIN, 12));
        btnSeleccionarPokemon2_Op3.setFont(new Font("Arial", Font.PLAIN, 12));

        ActionListener listenerSeleccion2 = e -> {
            JButton botonPulsado = (JButton) e.getSource();
            String nombrePokemonElegido = botonPulsado.getText().replace("\"", "");
            agregarMensaje("Entrenador 2 envía a: " + nombrePokemonElegido);
            // --- TU LÓGICA CAMBIO PKM 2 ---
             // Simulación: Cambiar a un pokemon genérico para el rival
            actualizarInfoPokemon2(nombrePokemonElegido, 8, 130, 130, "C:\\Users\\josem\\JAVA\\Pokemon\\Pokemon_GUI\\WindowBattle\\image\\OtroPokemon2.png"); // Reemplazar con lógica real
            actualizarVidaPokemon(2, pbVidaPokemon2.getMaximum(), pbVidaPokemon2.getMaximum());
            // --- FIN LÓGICA ---

            // Oculta el panel de selección, muestra los paneles de ataque
            panelSeleccionPokemon2.setVisible(false);
             panelAtaques1.setVisible(true);
            panelAtaques2.setVisible(true); // Asegura que ambos estén visibles antes de cambiar turno
            cambiarTurno(); // Turno pasa al rival
        };
        btnSeleccionarPokemon2_Op2.addActionListener(listenerSeleccion2);
        btnSeleccionarPokemon2_Op3.addActionListener(listenerSeleccion2);

        panelSeleccionPokemon2.add(lblSeleccionInfo2);
        panelSeleccionPokemon2.add(btnSeleccionarPokemon2_Op2);
        panelSeleccionPokemon2.add(btnSeleccionarPokemon2_Op3);
    }

    // inicializarInfoTurno (sin cambios mayores, solo ajuste de posición)
    private void inicializarInfoTurno() {
        lblTurnoInfo = new JLabel("Turno de: Entrenador X");
        // Posicionada justo encima de los paneles de ataque
        int yPos = panelAtaques1.getY() - 40; // 40px encima de los paneles de ataque
        lblTurnoInfo.setBounds(ANCHO_VENTANA / 2 - 150, yPos, 300, 30); // Centrado horizontalmente
        lblTurnoInfo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTurnoInfo.setForeground(COLOR_TEXTO_INFO);
        lblTurnoInfo.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // --- Métodos para Actualizar la GUI ---

    // actualizarInfoPokemon1/2: Actualiza nombre, nivel, vida máxima y carga imagen
    public void actualizarInfoPokemon1(String nombre, int nivel, int vidaActual, int vidaMaxima, String rutaImagen) {
        lblNombrePokemon1.setText(nombre);
        lblNivelPokemon1.setText("Nv. " + nivel);
        // Es crucial setear el maximo ANTES de setear el valor si se llama desde aqui
        pbVidaPokemon1.setMaximum(vidaMaxima);
        // La vida actual y texto se actualizan en actualizarVidaPokemon()

        // Carga y escala de imagen
        try {
            String rutaReal = rutaImagen.replace("\"", ""); // Quitar comillas si las envías así
            File imagenFile = new File(rutaReal);
            if (imagenFile.exists()) {
                BufferedImage originalImage = ImageIO.read(imagenFile);
                Image scaledImage = originalImage.getScaledInstance(ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM, Image.SCALE_SMOOTH);
                ImageIcon icono = new ImageIcon(scaledImage);
                lblImagenPokemon1.setIcon(icono);
                lblImagenPokemon1.setText(""); // Quitar texto si se carga imagen
            } else {
                System.err.println("Imagen no encontrada: " + rutaReal);
                lblImagenPokemon1.setIcon(null); // Asegurar que no haya icono viejo
                lblImagenPokemon1.setText("Imagen no encontrada"); // Mostrar texto si falla la carga
            }
        } catch (Exception e) {
            System.err.println("Error al cargar o escalar imagen: " + e.getMessage());
            lblImagenPokemon1.setIcon(null);
            lblImagenPokemon1.setText("Error de Imagen");
        }
         panelPrincipal.revalidate(); // Revalidar y repintar para asegurar que todo se muestre
         panelPrincipal.repaint();
    }

     public void actualizarInfoPokemon2(String nombre, int nivel, int vidaActual, int vidaMaxima, String rutaImagen) {
        lblNombrePokemon2.setText(nombre);
        lblNivelPokemon2.setText("Nv. " + nivel);
        pbVidaPokemon2.setMaximum(vidaMaxima);

         // Carga y escala de imagen
        try {
            String rutaReal = rutaImagen.replace("\"", "");
            File imagenFile = new File(rutaReal);
            if (imagenFile.exists()) {
                BufferedImage originalImage = ImageIO.read(imagenFile);
                Image scaledImage = originalImage.getScaledInstance(ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM, Image.SCALE_SMOOTH);
                ImageIcon icono = new ImageIcon(scaledImage);
                lblImagenPokemon2.setIcon(icono);
                lblImagenPokemon2.setText("");
            } else {
                System.err.println("Imagen no encontrada: " + rutaReal);
                lblImagenPokemon2.setIcon(null);
                lblImagenPokemon2.setText("Imagen no encontrada");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar o escalar imagen: " + e.getMessage());
            lblImagenPokemon2.setIcon(null);
            lblImagenPokemon2.setText("Error de Imagen");
        }
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }


    // actualizarVidaPokemon: Actualiza tanto la barra como la etiqueta de texto
    public void actualizarVidaPokemon(int pokemonIndex, int vidaActual, int vidaMaxima) {
        // Asegura que vidaMaxima no sea 0 para evitar división por cero
        int max = (vidaMaxima <= 0) ? 1 : vidaMaxima;
        int actual = Math.max(0, Math.min(vidaActual, max)); // Asegura 0 <= actual <= max

        if (pokemonIndex == 1) {
            pbVidaPokemon1.setMaximum(max); // Asegurar máximo correcto
            pbVidaPokemon1.setValue(actual);
            lblVidaTextoPokemon1.setText(String.format("HP: %d/%d", actual, max));
            actualizarColorBarraVida(pbVidaPokemon1, actual, max);
        } else if (pokemonIndex == 2) {
            pbVidaPokemon2.setMaximum(max); // Asegurar máximo correcto
            pbVidaPokemon2.setValue(actual);
            lblVidaTextoPokemon2.setText(String.format("HP: %d/%d", actual, max));
            actualizarColorBarraVida(pbVidaPokemon2, actual, max);
        }
         panelPrincipal.revalidate(); // Revalidar y repintar después de actualizar vida
         panelPrincipal.repaint();
    }

    // actualizarColorBarraVida (sin cambios)
    private void actualizarColorBarraVida(JProgressBar barra, int vidaActual, int vidaMaxima) {
        double porcentaje = (vidaMaxima == 0) ? 0 : (double) vidaActual / vidaMaxima;
        if (porcentaje > 0.5) {
            barra.setForeground(Color.GREEN);
        } else if (porcentaje > 0.2) {
            barra.setForeground(new Color(255, 165, 0)); // Naranja más estándar
        } else {
            barra.setForeground(Color.RED);
        }
    }

    // Métodos para actualizar botones, habilitar paneles, agregar mensajes y mostrar selección
    // Sin cambios funcionales mayores, solo se actualiza la visibilidad de paneles en mostrarPanelSeleccion.
    public void actualizarBotonesAtaque1(String[] nombresAtaques) {
        if (nombresAtaques == null || nombresAtaques.length < 4) {
            btnAtaque1_1.setText("N/A"); btnAtaque1_2.setText("N/A");
            btnAtaque1_3.setText("N/A"); btnAtaque1_4.setText("N/A");
             habilitarBotonesAtaquePanel(1, false); // Deshabilitar si no hay ataques
            return;
        }
        btnAtaque1_1.setText(nombresAtaques[0]);
        btnAtaque1_2.setText(nombresAtaques[1]);
        btnAtaque1_3.setText(nombresAtaques[2]);
        btnAtaque1_4.setText(nombresAtaques[3]);
        habilitarBotonesAtaquePanel(1, true); // Habilitar si hay ataques
    }

     public void actualizarBotonesAtaque2(String[] nombresAtaques) {
        if (nombresAtaques == null || nombresAtaques.length < 4) {
            btnAtaque2_1.setText("N/A"); btnAtaque2_2.setText("N/A");
            btnAtaque2_3.setText("N/A"); btnAtaque2_4.setText("N/A");
             habilitarBotonesAtaquePanel(2, false); // Deshabilitar si no hay ataques
            return;
        }
        btnAtaque2_1.setText(nombresAtaques[0]);
        btnAtaque2_2.setText(nombresAtaques[1]);
        btnAtaque2_3.setText(nombresAtaques[2]);
        btnAtaque2_4.setText(nombresAtaques[3]);
        habilitarBotonesAtaquePanel(2, true); // Habilitar si hay ataques
    }

    public void habilitarBotonesAtaquePanel(int panelIndex, boolean habilitar) {
        if (panelIndex == 1) {
            btnAtaque1_1.setEnabled(habilitar); btnAtaque1_2.setEnabled(habilitar);
            btnAtaque1_3.setEnabled(habilitar); btnAtaque1_4.setEnabled(habilitar);
        } else if (panelIndex == 2) {
            btnAtaque2_1.setEnabled(habilitar); btnAtaque2_2.setEnabled(habilitar);
            btnAtaque2_3.setEnabled(habilitar); btnAtaque2_4.setEnabled(habilitar);
        }
    }

    public void agregarMensaje(String mensaje) {
        // Puedes expandir esto para mostrar mensajes en una área de texto en la GUI
        System.out.println("[BatallaGUI] " + mensaje);
    }

    /**
     * Muestra el panel de selección para un entrenador cuyo Pokémon se debilitó.
     * @param entrenadorIndex El índice del entrenador (1 o 2).
     * @param nombrePokemonDebilitado El nombre del Pokémon que se debilitó.
     * @param nombresOpciones Un array con los nombres de los Pokémon disponibles para elegir.
     */
    public void mostrarPanelSeleccion(int entrenadorIndex, String nombrePokemonDebilitado, String[] nombresOpciones) {
        // Asegura que ambos paneles de ataque estén ocultos
        panelAtaques1.setVisible(false);
        panelAtaques2.setVisible(false);

        if (entrenadorIndex == 1) {
            panelSeleccionPokemon2.setVisible(false); // Asegurar que el otro panel de selección esté oculto
            lblSeleccionInfo1.setText(nombrePokemonDebilitado + " debilitado. Elige:");
            // Actualiza botones con las opciones
            btnSeleccionarPokemon1_Op2.setVisible(false); // Ocultar por defecto
            btnSeleccionarPokemon1_Op3.setVisible(false); // Ocultar por defecto

            if (nombresOpciones != null && nombresOpciones.length >= 1) {
                btnSeleccionarPokemon1_Op2.setText(nombresOpciones[0]);
                btnSeleccionarPokemon1_Op2.setVisible(true);
                 btnSeleccionarPokemon1_Op2.setEnabled(true); // Asegurar que esté habilitado
            }
            if (nombresOpciones != null && nombresOpciones.length >= 2) {
                btnSeleccionarPokemon1_Op3.setText(nombresOpciones[1]);
                btnSeleccionarPokemon1_Op3.setVisible(true);
                 btnSeleccionarPokemon1_Op3.setEnabled(true); // Asegurar que esté habilitado
            }
             if (nombresOpciones == null || nombresOpciones.length == 0) {
                 lblSeleccionInfo1.setText(nombrePokemonDebilitado + " debilitado. No hay Pokémon disponibles.");
                  // Aquí podrías añadir lógica para fin de batalla
             }


            panelSeleccionPokemon1.setVisible(true);

        } else if (entrenadorIndex == 2) {
            panelSeleccionPokemon1.setVisible(false); // Asegurar que el otro panel de selección esté oculto
            lblSeleccionInfo2.setText(nombrePokemonDebilitado + " debilitado. Elige:");
             // Actualiza botones con las opciones
             btnSeleccionarPokemon2_Op2.setVisible(false); // Ocultar por defecto
             btnSeleccionarPokemon2_Op3.setVisible(false); // Ocultar por defecto

            if (nombresOpciones != null && nombresOpciones.length >= 1) {
                btnSeleccionarPokemon2_Op2.setText(nombresOpciones[0]);
                btnSeleccionarPokemon2_Op2.setVisible(true);
                 btnSeleccionarPokemon2_Op2.setEnabled(true);
            }
            if (nombresOpciones != null && nombresOpciones.length >= 2) {
                btnSeleccionarPokemon2_Op3.setText(nombresOpciones[1]);
                btnSeleccionarPokemon2_Op3.setVisible(true);
                 btnSeleccionarPokemon2_Op3.setEnabled(true);
            }
            if (nombresOpciones == null || nombresOpciones.length == 0) {
                 lblSeleccionInfo2.setText(nombrePokemonDebilitado + " debilitado. No hay Pokémon disponibles.");
                  // Aquí podrías añadir lógica para fin de batalla
             }

            panelSeleccionPokemon2.setVisible(true);
        }
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    // --- Lógica de Turnos y Control ---

    private void configurarTurno() {
        // Asegura que los paneles de selección estén ocultos al configurar un turno normal
        panelSeleccionPokemon1.setVisible(false);
        panelSeleccionPokemon2.setVisible(false);

        // Asegura que los paneles de ataque estén visibles para el turno
        panelAtaques1.setVisible(true);
        panelAtaques2.setVisible(true);


        if (turnoEntrenador1) {
            lblTurnoInfo.setText("Turno de: Entrenador 1");
            String[] ataquesPkm1 = {"Placaje", "Ataque Rápido", "Rayo", "Cola Férrea"}; // Placeholder - Usa los ataques reales del pkm actual
            actualizarBotonesAtaque1(ataquesPkm1);
            habilitarBotonesAtaquePanel(1, true); // Habilita ataques del jugador actual
            habilitarBotonesAtaquePanel(2, false); // Deshabilita ataques del rival
        } else {
            lblTurnoInfo.setText("Turno de: Entrenador 2");
            String[] ataquesPkm2 = {"Ascuas", "Gruñido", "Lanzallamas", "Giro Fuego"}; // Placeholder - Usa los ataques reales del pkm actual
            actualizarBotonesAtaque2(ataquesPkm2);
            habilitarBotonesAtaquePanel(1, false); // Deshabilita ataques del jugador actual
            habilitarBotonesAtaquePanel(2, true); // Habilita ataques del rival
        }
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    private void cambiarTurno() {
        // Solo cambia de turno si NO se está mostrando un panel de selección.
        // Si un pkm se debilitó, la función verificarDebilitado() llama a mostrarPanelSeleccion
        // y NO llama a cambiarTurno. El cambio de turno ocurre *después* de que el jugador
        // elige un nuevo pokémon desde el panel de selección.
        if (!panelSeleccionPokemon1.isVisible() && !panelSeleccionPokemon2.isVisible()) {
            turnoEntrenador1 = !turnoEntrenador1;
            configurarTurno(); // Configura el GUI para el nuevo turno
        } else {
             // Si un panel de selección está visible, mantenemos los botones de ataque
             // deshabilitados (esto ya lo hace mostrarPanelSeleccion) y esperamos la elección.
        }
    }

    /**
     * Verifica si el pokémon indicado por índice se debilitó (HP <= 0) y actúa en consecuencia.
     * Si se debilitó, oculta paneles de ataque, muestra el panel de selección
     * y pide al jugador correspondiente que elija su siguiente Pokémon.
     * Si no se debilitó, simplemente cambia el turno.
     * @param indexPokemonAtacado 1 si se atacó al Pkm1 (Entrenador 1), 2 si se atacó al Pkm2 (Entrenador 2).
     */
    private void verificarDebilitado(int indexPokemonAtacado) {
        JProgressBar barraVida = (indexPokemonAtacado == 1) ? pbVidaPokemon1 : pbVidaPokemon2;
        // Obtener el nombre del Pokémon que fue atacado
        String nombrePkmDebilitado = (indexPokemonAtacado == 1) ?
                                      lblNombrePokemon1.getText().replace("\"", "") :
                                      lblNombrePokemon2.getText().replace("\"", ""); // Usar nombre real del label

        if (barraVida.getValue() <= 0) {
            // --- Lógica Debilitado ---
            agregarMensaje(nombrePkmDebilitado + " se ha debilitado!");

            // Simulación de opciones disponibles (debes obtener esto de tu lógica de Entrenador,
            // por ejemplo, lista de Pokémon no debilitados en el equipo)
            String[] opcionesSimuladas;
            if (indexPokemonAtacado == 1) {
                // Opciones para el Entrenador 1 (su pkm se debilitó)
                opcionesSimuladas = new String[]{"Bulbasaur", "Squirtle"}; // Ejemplo
            } else {
                 // Opciones para el Entrenador 2 (su pkm se debilitó)
                 opcionesSimuladas = new String[]{"Totodile", "Chikorita"}; // Ejemplo para rival
            }


            // Muestra el panel de selección correspondiente.
            // Esto OCULTA los paneles de ataque y DESHABILITA los botones.
            mostrarPanelSeleccion(indexPokemonAtacado, nombrePkmDebilitado, opcionesSimuladas);

            // NO se llama a cambiarTurno aquí. El turno cambiará *después* de que
            // el jugador elija un nuevo Pokémon desde el panel de selección.

        } else {
            // Si el pokémon atacado no se debilitó, el turno simplemente pasa al otro entrenador.
            cambiarTurno();
        }
    }

    // --- Método Principal ---
    public static void main(String[] args) {
        // Establecer Look and Feel (opcional, puede mejorar apariencia en algunos sistemas)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el LookAndFeel del sistema.");
        }
        SwingUtilities.invokeLater(() -> new BatallaGUI_v4()); // Cambiado a v4
    }
}