import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class WindowBatalla extends JFrame implements ActionListener {

    //<editor-fold defaultstate="collapsed" desc="Constantes y Variables de instancia (sin cambios estructurales)">
    // --- Constantes ---
    private static final int ANCHO_VENTANA = 800;
    private static final int ALTO_VENTANA = 800;
    private static final int ANCHO_IMAGEN_PKM = 200;
    private static final int ALTO_IMAGEN_PKM = 200;
    private static final int ANCHO_INFO_PKM = 250;
    private static final int ALTO_INFO_PKM_BLOQUE = 35 + 18 + 25 + 10;
    private static final int ANCHO_BOTON_PANEL = 340;
    private static final int ALTO_BOTON_PANEL = 100;
    private static final int Y_POS_POKEMON_AREA = (int) (ALTO_VENTANA * 0.30);
    private static final int MARGEN_LATERAL = 60;
    private static final Font FONT_MONO_BOLD_20 = new Font("Monospaced", Font.BOLD, 20);
    private static final Font FONT_MONO_PLAIN_15 = new Font("Monospaced", Font.PLAIN, 15);
    private static final Color COLOR_TEXTO_INFO = Color.WHITE;

    private static final String RUTA_IMAGENES = "image/"; // Asegúrate que esta ruta sea correcta
    private static final String RUTA_FONDO = RUTA_IMAGENES + "Estadio.jpg";
    private static final String[] RUTAS_PKM1 = { RUTA_IMAGENES + "PE1.png", /* ... otras rutas ... */ }; // Completa tus rutas
    private static final String[] RUTAS_PKM2 = { RUTA_IMAGENES + "PP1.png", /* ... otras rutas ... */ }; // Completa tus rutas
    private static final Random random = new Random();
    private static final String RUTA_IMG_PKM1 = obtenerrutaaleatoria(RUTAS_PKM1);
    private static final String RUTA_IMG_PKM2 = obtenerrutaaleatoria(RUTAS_PKM2);


    // --- Componentes UI ---
    private JPanel panelPrincipal;
    private JLabel lblImagenPokemon1, lblNombrePokemon1, lblVidaTextoPokemon1;
    private JProgressBar pbVidaPokemon1;
    private JPanel panelInfoPokemon1;
    private JLabel lblImagenPokemon2, lblNombrePokemon2, lblVidaTextoPokemon2;
    private JProgressBar pbVidaPokemon2;
    private JPanel panelInfoPokemon2;
    private JPanel panelAtaques1, panelAtaques2;
    private JPanel panelSeleccionPokemon1, panelSeleccionPokemon2;
    private JLabel lblSeleccionInfo1, lblSeleccionInfo2;
    private JButton btnSeleccionarPokemon1_Op1, btnSeleccionarPokemon1_Op2;
    private JButton btnSeleccionarPokemon2_Op1, btnSeleccionarPokemon2_Op2;
    private JButton[] botonesAtaque1 = new JButton[4];
    private JButton[] botonesAtaque2 = new JButton[4];
    private JLabel lblTurnoInfo;

    // --- Estado de la Batalla ---
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Pokemon pokemonActivo1; // Se asignará en iniciarBatalla
    private Pokemon pokemonActivo2; // Se asignará en iniciarBatalla
    private ArrayList<Pokemon> equipoRestante1;
    private ArrayList<Pokemon> equipoRestante2;
    private boolean turnoEntrenador1 = true; // Se determinará en iniciarBatalla
    private boolean seleccionPendiente = false;
    private int entrenadorSeleccionando = 0;

    //</editor-fold>

    // --- *** CONSTRUCTOR MODIFICADO *** ---
    // Solo inicializa componentes y guarda datos, no inicia la lógica de batalla.
    public WindowBatalla(Entrenador e1, Entrenador e2) {
        System.out.println("[Debug WB] Constructor WindowBatalla started."); // Debug

        // Guarda los entrenadores
        this.entrenador1 = Objects.requireNonNull(e1, "Entrenador 1 no puede ser null");
        this.entrenador2 = Objects.requireNonNull(e2, "Entrenador 2 no puede ser null");

        // Inicializa listas de equipo (copia para no modificar el original)
        this.equipoRestante1 = (e1.getEquipo() != null) ? new ArrayList<>(e1.getEquipo()) : new ArrayList<>();
        this.equipoRestante2 = (e2.getEquipo() != null) ? new ArrayList<>(e2.getEquipo()) : new ArrayList<>();

        // Configuración básica de la ventana
        setTitle("Batalla Pokémon: " + entrenador1.getNombre() + " vs " + entrenador2.getNombre());
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Importante: DISPOSE, no EXIT
        setLocationRelativeTo(null);
        setResizable(false);

        // Crea el panel principal y fondo
        final Image imagenFondo = cargarImagen(RUTA_FONDO);
        panelPrincipal = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(100, 100, 120)); // Fondo alternativo
                    g.fillRect(0, 0, getWidth(), getHeight());
                    System.err.println("Advertencia: No se pudo cargar la imagen de fondo: " + RUTA_FONDO);
                }
            }
        };
        panelPrincipal.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
        setContentPane(panelPrincipal);

        // Inicializa TODOS los componentes gráficos
        System.out.println("[Debug WB] Initializing UI components..."); // Debug
        inicializarPanelesAtaques();
        inicializarPanelesSeleccion();
        inicializarComponentesEntrenador1();
        inicializarComponentesEntrenador2();
        inicializarInfoTurno();
        System.out.println("[Debug WB] UI components initialized."); // Debug

        // Añade los componentes al panel principal
        System.out.println("[Debug WB] Adding components to panel..."); // Debug
        panelPrincipal.add(panelInfoPokemon1);
        panelPrincipal.add(lblImagenPokemon1);
        panelPrincipal.add(panelInfoPokemon2);
        panelPrincipal.add(lblImagenPokemon2);
        panelPrincipal.add(panelAtaques1);
        panelPrincipal.add(panelAtaques2);
        panelPrincipal.add(panelSeleccionPokemon1);
        panelPrincipal.add(panelSeleccionPokemon2);
        panelPrincipal.add(lblTurnoInfo);
         System.out.println("[Debug WB] Components added."); // Debug

        // NO LLAMAR a setVisible(true) aquí.
        // NO LLAMAR a seleccionarPokemonInicial, determinarPrimerTurno, configurarTurno aquí.

        System.out.println("[Debug WB] Constructor WindowBatalla finished."); // Debug
    }

    // --- *** NUEVO MÉTODO PARA INICIAR LA LÓGICA DE BATALLA *** ---
    public void iniciarBatalla() {
        System.out.println("[Debug WB] iniciarBatalla started.");

        // --- Selección Inicial de Pokémon (Muestra JOptionPanes) ---
        System.out.println("[Debug WB] Starting initial Pokemon selection...");
        this.pokemonActivo1 = seleccionarPokemonInicial(entrenador1, equipoRestante1, 1);
        this.pokemonActivo2 = seleccionarPokemonInicial(entrenador2, equipoRestante2, 2);

        // --- Validar Selección ---
        if (pokemonActivo1 == null || pokemonActivo2 == null) {
            System.err.println("Selección inicial cancelada o fallida. Cerrando ventana de batalla.");
            JOptionPane.showMessageDialog(this, // 'this' es el JFrame de batalla
                    "No se pudo iniciar la batalla.\nAsegúrate de que ambos entrenadores tengan equipos válidos y elijan un Pokémon inicial.",
                    "Error de Inicio", JOptionPane.ERROR_MESSAGE);
            this.dispose(); // Cierra esta ventana si no se pudo iniciar
            return; // Termina el método aquí
        }
        System.out.println("[Debug WB] Pokémon iniciales seleccionados: " + pokemonActivo1.getNombre() + ", " + pokemonActivo2.getNombre());

        // --- Actualizar UI con Pokémon Iniciales ---
        System.out.println("[Debug WB] Updating UI with initial Pokemon...");
        actualizarInfoPokemon(1, pokemonActivo1);
        actualizarInfoPokemon(2, pokemonActivo2);
        System.out.println("[Debug WB] UI updated.");

        // --- Determinar Primer Turno ---
        System.out.println("[Debug WB] Determining first turn...");
        determinarPrimerTurno();
        System.out.println("[Debug WB] First turn determined.");

        // --- Configurar UI para el Primer Turno ---
        System.out.println("[Debug WB] Configuring turn UI...");
        configurarTurno();
        System.out.println("[Debug WB] Turn UI configured.");

        // Refrescar layout (buena práctica después de añadir/configurar)
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
        System.out.println("[Debug WB] Panel revalidated and repainted.");
        System.out.println("[Debug WB] iniciarBatalla finished.");
    }


    //<editor-fold defaultstate="collapsed" desc="Métodos de Inicialización UI (sin cambios estructurales)">
    private void determinarPrimerTurno() {
        if (pokemonActivo1 == null || pokemonActivo2 == null) {
             System.err.println("Error: No se puede determinar el primer turno sin Pokémon activos.");
             turnoEntrenador1 = true; // Asignar un valor por defecto o manejar el error
             return;
        }
        if (pokemonActivo1.getVelocidad() > pokemonActivo2.getVelocidad()) {
            turnoEntrenador1 = true;
        } else if (pokemonActivo2.getVelocidad() > pokemonActivo1.getVelocidad()) {
            turnoEntrenador1 = false;
        } else {
            // Empate en velocidad, decidir aleatoriamente
            turnoEntrenador1 = Math.random() < 0.5;
        }
        // Usar agregarMensaje si está disponible o System.out
        System.out.println("¡" + (turnoEntrenador1 ? entrenador1.getNombre() : entrenador2.getNombre()) + " comienza!");
    }

    private Pokemon seleccionarPokemonInicial(Entrenador entrenador, ArrayList<Pokemon> equipo, int numEntrenador) {
         System.out.println("[Debug WB] Seleccionando Pokémon inicial para Entrenador " + numEntrenador + " ("+entrenador.getNombre()+")");
        if (equipo == null || equipo.isEmpty()) {
            System.err.println("Error: El equipo del Entrenador " + numEntrenador + " ("+entrenador.getNombre()+") está vacío o es nulo.");
            return null;
        }

        // Filtrar solo Pokémon válidos y vivos
        ArrayList<Pokemon> opcionesValidas = new ArrayList<>();
        for (Pokemon p : equipo) {
            if (p != null && p.getVivo()) { // Asume que Pokemon tiene getVivo()
                opcionesValidas.add(p);
            } else {
                System.err.println("Advertencia: Pokémon nulo o no vivo encontrado en equipo inicial de Entrenador " + numEntrenador + ": " + (p != null ? p.getNombre() : "NULL"));
            }
        }

        if (opcionesValidas.isEmpty()) {
            System.err.println("Error: No hay Pokémon válidos/vivos para seleccionar para el Entrenador " + numEntrenador + " ("+entrenador.getNombre()+")");
            return null;
        }

        String[] nombresPokemon = new String[opcionesValidas.size()];
        for (int i = 0; i < opcionesValidas.size(); i++) {
            Pokemon p = opcionesValidas.get(i);
            nombresPokemon[i] = p.getNombre() + " (Nv." + p.getNivel() + ")"; // Asume getters
        }

        // Crear ComboBox dentro del método asegura que use el EDT correctamente con JOptionPane
        JComboBox<String> comboBox = new JComboBox<>(nombresPokemon);
        comboBox.setFont(FONT_MONO_PLAIN_15);

        // Mostrar el diálogo modal (esto pausará la ejecución aquí hasta que el usuario elija)
        int result = JOptionPane.showConfirmDialog(this, // Usar 'this' (el JFrame) como padre
                comboBox,
                "Entrenador " + entrenador.getNombre() + ", elige tu primer Pokémon:",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int selectedIndex = comboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < opcionesValidas.size()) {
                Pokemon elegido = opcionesValidas.get(selectedIndex);
                System.out.println("Entrenador " + numEntrenador + " ("+entrenador.getNombre()+") elige a: " + elegido.getNombre());
                return elegido;
            } else {
                System.err.println("Error: Índice de ComboBox inválido: " + selectedIndex + " para Entrenador " + numEntrenador);
                return null; // O manejar como cancelación
            }
        }

        System.out.println("Selección cancelada por Entrenador " + numEntrenador + " ("+entrenador.getNombre()+")");
        return null; // El usuario presionó Cancelar o cerró el diálogo
    }

    private void inicializarPanelesAtaques() {
        int yAtaques = ALTO_VENTANA - ALTO_BOTON_PANEL - 50;
        int xAtaques1 = MARGEN_LATERAL;
        int xAtaques2 = ANCHO_VENTANA - ANCHO_BOTON_PANEL - MARGEN_LATERAL;

        panelAtaques1 = new JPanel(new GridLayout(2, 2, 6, 6));
        panelAtaques1.setBounds(xAtaques1, yAtaques, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelAtaques1.setOpaque(false);
        for (int i = 0; i < 4; i++) {
            botonesAtaque1[i] = crearBotonAtaque("..."); // Texto inicial
            final int index = i;
            // Añadir ActionListener para ejecutar el ataque
            botonesAtaque1[i].addActionListener(e -> ejecutarAtaque(1, index));
             botonesAtaque1[i].setEnabled(false); // Deshabilitar inicialmente
            panelAtaques1.add(botonesAtaque1[i]);
        }

        panelAtaques2 = new JPanel(new GridLayout(2, 2, 6, 6));
        panelAtaques2.setBounds(xAtaques2, yAtaques, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelAtaques2.setOpaque(false);
        for (int i = 0; i < 4; i++) {
            botonesAtaque2[i] = crearBotonAtaque("..."); // Texto inicial
            final int index = i;
            // Añadir ActionListener para ejecutar el ataque
            botonesAtaque2[i].addActionListener(e -> ejecutarAtaque(2, index));
             botonesAtaque2[i].setEnabled(false); // Deshabilitar inicialmente
            panelAtaques2.add(botonesAtaque2[i]);
        }
    }

     private void inicializarPanelesSeleccion() {
        int ySeleccion = ALTO_VENTANA - ALTO_BOTON_PANEL - 50;
        int xSeleccion1 = MARGEN_LATERAL;
        int xSeleccion2 = ANCHO_VENTANA - ANCHO_BOTON_PANEL - MARGEN_LATERAL;

        panelSeleccionPokemon1 = new JPanel(new BorderLayout(10, 10));
        panelSeleccionPokemon1.setBounds(xSeleccion1, ySeleccion, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelSeleccionPokemon1.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        panelSeleccionPokemon1.setBackground(new Color(0, 0, 50, 200)); // Fondo semi-transparente
        panelSeleccionPokemon1.setVisible(false); // Oculto inicialmente

        lblSeleccionInfo1 = new JLabel("Elige tu próximo Pokémon", SwingConstants.CENTER);
        lblSeleccionInfo1.setFont(FONT_MONO_BOLD_20);
        lblSeleccionInfo1.setForeground(Color.WHITE);
        panelSeleccionPokemon1.add(lblSeleccionInfo1, BorderLayout.NORTH);

        JPanel panelBotonesSeleccion1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotonesSeleccion1.setOpaque(false);
        btnSeleccionarPokemon1_Op1 = crearBotonSeleccion("Opción 1");
        btnSeleccionarPokemon1_Op2 = crearBotonSeleccion("Opción 2");
        panelBotonesSeleccion1.add(btnSeleccionarPokemon1_Op1);
        panelBotonesSeleccion1.add(btnSeleccionarPokemon1_Op2);
        panelSeleccionPokemon1.add(panelBotonesSeleccion1, BorderLayout.CENTER);

        // Panel para el segundo entrenador
        panelSeleccionPokemon2 = new JPanel(new BorderLayout(10, 10));
        panelSeleccionPokemon2.setBounds(xSeleccion2, ySeleccion, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelSeleccionPokemon2.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        panelSeleccionPokemon2.setBackground(new Color(50, 0, 0, 200)); // Fondo semi-transparente
        panelSeleccionPokemon2.setVisible(false); // Oculto inicialmente

        lblSeleccionInfo2 = new JLabel("Elige tu próximo Pokémon", SwingConstants.CENTER);
        lblSeleccionInfo2.setFont(FONT_MONO_BOLD_20);
        lblSeleccionInfo2.setForeground(Color.WHITE);
        panelSeleccionPokemon2.add(lblSeleccionInfo2, BorderLayout.NORTH);

        JPanel panelBotonesSeleccion2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotonesSeleccion2.setOpaque(false);
        btnSeleccionarPokemon2_Op1 = crearBotonSeleccion("Opción 1");
        btnSeleccionarPokemon2_Op2 = crearBotonSeleccion("Opción 2");
        panelBotonesSeleccion2.add(btnSeleccionarPokemon2_Op1);
        panelBotonesSeleccion2.add(btnSeleccionarPokemon2_Op2);
        panelSeleccionPokemon2.add(panelBotonesSeleccion2, BorderLayout.CENTER);
    }


    private void inicializarComponentesEntrenador1() {
        int xBase = MARGEN_LATERAL;
        int yInfo = Y_POS_POKEMON_AREA - ALTO_INFO_PKM_BLOQUE - 10;
        int yImagen = Y_POS_POKEMON_AREA;

        panelInfoPokemon1 = new JPanel(null); // Layout absoluto dentro del panel de info
        panelInfoPokemon1.setBounds(xBase, yInfo, ANCHO_INFO_PKM, ALTO_INFO_PKM_BLOQUE);
        panelInfoPokemon1.setOpaque(false); // Hacer transparente para ver el fondo

        lblNombrePokemon1 = new JLabel("...", SwingConstants.CENTER); // Texto inicial
        lblNombrePokemon1.setFont(FONT_MONO_BOLD_20);
        lblNombrePokemon1.setForeground(COLOR_TEXTO_INFO);
        lblNombrePokemon1.setBounds(0, 0, ANCHO_INFO_PKM, 30); // Posición relativa a panelInfoPokemon1
        panelInfoPokemon1.add(lblNombrePokemon1);

        pbVidaPokemon1 = new JProgressBar(0, 100); // Máximo se ajustará después
        pbVidaPokemon1.setValue(100);
        pbVidaPokemon1.setStringPainted(false); // No mostrar texto en la barra
        pbVidaPokemon1.setForeground(Color.GREEN); // Color inicial
        pbVidaPokemon1.setBackground(Color.DARK_GRAY);
        pbVidaPokemon1.setBorder(new LineBorder(Color.GRAY));
        pbVidaPokemon1.setBounds(10, 35, ANCHO_INFO_PKM - 20, 18); // Posición relativa
        panelInfoPokemon1.add(pbVidaPokemon1);

        lblVidaTextoPokemon1 = new JLabel("HP: ... / ...", SwingConstants.CENTER); // Texto inicial
        lblVidaTextoPokemon1.setFont(FONT_MONO_PLAIN_15);
        lblVidaTextoPokemon1.setForeground(COLOR_TEXTO_INFO);
        lblVidaTextoPokemon1.setBounds(0, 55, ANCHO_INFO_PKM, 25); // Posición relativa
        panelInfoPokemon1.add(lblVidaTextoPokemon1);

        // El JLabel para la imagen se posiciona directamente en el panelPrincipal
        lblImagenPokemon1 = new JLabel();
        lblImagenPokemon1.setBounds(xBase + (ANCHO_INFO_PKM / 2) - (ANCHO_IMAGEN_PKM / 2), // Centrar imagen bajo info
                                   yImagen, ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM);
        lblImagenPokemon1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPokemon1.setBorder(BorderFactory.createEmptyBorder()); // Sin borde
        // La imagen se carga en actualizarInfoPokemon
    }

    private void inicializarComponentesEntrenador2() {
        int xBase = ANCHO_VENTANA - MARGEN_LATERAL - ANCHO_INFO_PKM; // Lado derecho
        int yInfo = Y_POS_POKEMON_AREA - ALTO_INFO_PKM_BLOQUE - 10;
        int yImagen = Y_POS_POKEMON_AREA;

        panelInfoPokemon2 = new JPanel(null);
        panelInfoPokemon2.setBounds(xBase, yInfo, ANCHO_INFO_PKM, ALTO_INFO_PKM_BLOQUE);
        panelInfoPokemon2.setOpaque(false);

        lblNombrePokemon2 = new JLabel("...", SwingConstants.CENTER);
        lblNombrePokemon2.setFont(FONT_MONO_BOLD_20);
        lblNombrePokemon2.setForeground(COLOR_TEXTO_INFO);
        lblNombrePokemon2.setBounds(0, 0, ANCHO_INFO_PKM, 30);
        panelInfoPokemon2.add(lblNombrePokemon2);

        pbVidaPokemon2 = new JProgressBar(0, 100);
        pbVidaPokemon2.setValue(100);
        pbVidaPokemon2.setStringPainted(false);
        pbVidaPokemon2.setForeground(Color.GREEN);
        pbVidaPokemon2.setBackground(Color.DARK_GRAY);
        pbVidaPokemon2.setBorder(new LineBorder(Color.GRAY));
        pbVidaPokemon2.setBounds(10, 35, ANCHO_INFO_PKM - 20, 18);
        panelInfoPokemon2.add(pbVidaPokemon2);

        lblVidaTextoPokemon2 = new JLabel("HP: ... / ...", SwingConstants.CENTER);
        lblVidaTextoPokemon2.setFont(FONT_MONO_PLAIN_15);
        lblVidaTextoPokemon2.setForeground(COLOR_TEXTO_INFO);
        lblVidaTextoPokemon2.setBounds(0, 55, ANCHO_INFO_PKM, 25);
        panelInfoPokemon2.add(lblVidaTextoPokemon2);

        // JLabel para la imagen del Pokémon 2
        lblImagenPokemon2 = new JLabel();
        lblImagenPokemon2.setBounds(xBase + (ANCHO_INFO_PKM / 2) - (ANCHO_IMAGEN_PKM / 2),
                                   yImagen, ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM);
        lblImagenPokemon2.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPokemon2.setBorder(BorderFactory.createEmptyBorder());
    }

     private void inicializarInfoTurno() {
        lblTurnoInfo = new JLabel(" ", SwingConstants.CENTER); // Texto inicial vacío
        lblTurnoInfo.setFont(FONT_MONO_BOLD_20);
        lblTurnoInfo.setForeground(Color.YELLOW); // Color inicial
        // Posicionar encima de los paneles de botones/selección
        int yTurno = ALTO_VENTANA - ALTO_BOTON_PANEL - 50 - 35;
        lblTurnoInfo.setBounds(0, yTurno, ANCHO_VENTANA, 30); // Centrado horizontalmente
        lblTurnoInfo.setOpaque(false); // Transparente
    }

    private static String obtenerrutaaleatoria(String[] rutas) {
        // Manejo de caso donde el array es vacío o nulo
         if (rutas == null || rutas.length == 0) {
             System.err.println("Advertencia: El array de rutas de imagen está vacío o es nulo.");
             // Devolver una ruta por defecto o null, según prefieras
             return RUTA_IMAGENES + "default.png"; // Ejemplo de ruta por defecto
         }
        int indice = random.nextInt(rutas.length);
        return rutas[indice];
    }

     private Image cargarImagen(String ruta) {
        File archivoImagen = new File(ruta);
        // Verificación más robusta
        if (!archivoImagen.exists() || !archivoImagen.isFile() || !archivoImagen.canRead()) {
             System.err.println("Advertencia: Imagen no encontrada, no es un archivo o no se puede leer: " + ruta);
             return null;
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(archivoImagen);
             if (img == null) {
                 // Esto puede pasar si el formato no es soportado o el archivo está corrupto
                 System.err.println("Advertencia: Formato no soportado o archivo de imagen corrupto: " + ruta);
                 return null;
             }
        } catch (javax.imageio.IIOException iioe) {
             System.err.println("Error de formato leyendo imagen (IIOException): " + ruta + " - " + iioe.getMessage());
             return null;
        } catch (java.io.IOException e) {
            System.err.println("Error de E/S leyendo imagen (IOException): " + ruta + " - " + e.getMessage());
            return null;
        } catch (Exception e) {
             // Captura cualquier otra excepción inesperada
             System.err.println("Error inesperado cargando imagen: " + ruta + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
             // e.printStackTrace(); // Descomentar para depuración avanzada si es necesario
             return null;
        }
        return img; // Devuelve la BufferedImage cargada
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos de Actualización UI y Lógica Batalla (sin cambios estructurales)">

    private void actualizarInfoPokemon(int entrenadorIndex, Pokemon nuevoPokemon) {
         if (nuevoPokemon == null) {
             System.err.println("Error crítico: Se intentó actualizar con un Pokémon null para Entrenador " + entrenadorIndex);
             // Podrías poner un estado por defecto o manejar el error de otra forma
             return;
         }

        // Asignar el nuevo pokémon activo al estado
        if (entrenadorIndex == 1) {
             pokemonActivo1 = nuevoPokemon;
        } else {
             pokemonActivo2 = nuevoPokemon;
        }

        // Obtener datos del Pokémon (con manejo de nulls si aplica en tus clases)
        String nombre = nuevoPokemon.getNombre() != null ? nuevoPokemon.getNombre() : "[Sin Nombre]";
        int nivel = nuevoPokemon.getNivel(); // Asume que no es null
        float vidaActualFloat = nuevoPokemon.getHp(); // Asume que no es null
        int vidaActual = Math.max(0, (int) vidaActualFloat); // Asegurar no negativo
        // Calcular vida máxima (si no está directamente en Pokemon)
        int vidaMax = calcularVidaMaximaAproximada(nuevoPokemon); // Ajusta si tienes un getter directo

        // Seleccionar componentes UI correctos
        JLabel lblImagenTarget = (entrenadorIndex == 1) ? lblImagenPokemon1 : lblImagenPokemon2;
        JLabel lblNombreTarget = (entrenadorIndex == 1) ? lblNombrePokemon1 : lblNombrePokemon2;
        // JProgressBar pbVidaTarget = (entrenadorIndex == 1) ? pbVidaPokemon1 : pbVidaPokemon2; // Se actualiza en actualizarVidaPokemonUI

        // Actualizar Nombre
        lblNombreTarget.setText(nombre + " Nv." + nivel);

        // Actualizar Barra de Vida y Texto HP (usando método dedicado)
        actualizarVidaPokemonUI(entrenadorIndex, vidaActual, vidaMax);

        // Actualizar Botones de Ataque
        actualizarBotonesAtaque(entrenadorIndex, nuevoPokemon.getAtaques()); // Asume getAtaques()

        // Actualizar Imagen
        String rutaImagenEspecifica = (entrenadorIndex == 1) ? RUTA_IMG_PKM1 : RUTA_IMG_PKM2; // Rutas aleatorias fijadas al inicio
        Image imagen = cargarImagen(rutaImagenEspecifica);
        if (imagen != null) {
            // Redimensionar suavemente
            Image imagenRedimensionada = imagen.getScaledInstance(ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM, Image.SCALE_SMOOTH);
            lblImagenTarget.setIcon(new ImageIcon(imagenRedimensionada));
            lblImagenTarget.setText(""); // Limpiar texto si lo hubiera
        } else {
             // Poner un placeholder o limpiar si la imagen no carga
            lblImagenTarget.setIcon(null);
            lblImagenTarget.setText("[X]"); // Indicador de imagen no cargada
             System.err.println("No se pudo cargar la imagen para " + nombre + ": " + rutaImagenEspecifica);
        }
    }

     private void actualizarVidaPokemonUI(int entrenadorIndex, int vidaActual, int vidaMaxima) {
        // Asegurar valores mínimos
        vidaActual = Math.max(0, vidaActual);
        vidaMaxima = Math.max(1, vidaMaxima); // Evitar división por cero

        // Seleccionar componentes correctos
        JProgressBar pb = (entrenadorIndex == 1) ? pbVidaPokemon1 : pbVidaPokemon2;
        JLabel lblTexto = (entrenadorIndex == 1) ? lblVidaTextoPokemon1 : lblVidaTextoPokemon2;

         if (pb == null || lblTexto == null) {
              System.err.println("Error: Componente UI de vida es null para Entrenador " + entrenadorIndex);
              return;
         }

        // Actualizar máximo y valor de la barra
        pb.setMaximum(vidaMaxima);
        pb.setValue(vidaActual);

        // Actualizar texto HP
        lblTexto.setText("HP: " + vidaActual + " / " + vidaMaxima);

        // Cambiar color de la barra según el porcentaje de vida
        double porcentajeVida = (double) vidaActual / vidaMaxima;
        if (porcentajeVida > 0.5) {
            pb.setForeground(Color.GREEN);
        } else if (porcentajeVida > 0.2) {
            pb.setForeground(Color.ORANGE);
        } else {
            pb.setForeground(Color.RED);
        }
    }

    private void actualizarBotonesAtaque(int entrenadorIndex, ArrayList<Ataque> ataques) {
        JButton[] botones = (entrenadorIndex == 1) ? botonesAtaque1 : botonesAtaque2;

        if (ataques == null) {
             System.err.println("Advertencia: Lista de ataques es null para Entrenador " + entrenadorIndex);
             // Deshabilitar todos los botones si la lista es nula
             for (JButton boton : botones) {
                 if (boton != null) {
                     boton.setText("-");
                     boton.setEnabled(false);
                 }
             }
             return;
        }

        for (int i = 0; i < botones.length; i++) {
             JButton botonActual = botones[i];
             if (botonActual == null) continue; // Saltar si el botón no existe

             // Verificar si hay un ataque válido en esta posición
            if (i < ataques.size() && ataques.get(i) != null && ataques.get(i).getNombre() != null && !ataques.get(i).getNombre().isEmpty()) {
                Ataque ataque = ataques.get(i);
                // Podrías añadir más info si quieres, como tipo o PP si tu clase Ataque lo tiene
                botonActual.setText(ataque.getNombre());
                // Habilitar/deshabilitar se hará en configurarTurno
                // botonActual.setEnabled(true);
            } else {
                // No hay ataque o es inválido
                botonActual.setText("-");
                 botonActual.setEnabled(false); // Asegurar deshabilitado si no hay ataque
                if (i < ataques.size() && (ataques.get(i) == null || ataques.get(i).getNombre() == null || ataques.get(i).getNombre().isEmpty())) {
                    // Log opcional si un ataque esperado es inválido
                    // System.err.println("Advertencia: Ataque en índice " + i + " para Entrenador " + entrenadorIndex + " es null o inválido.");
                }
            }
        }
         // La habilitación final depende del turno, se hace en configurarTurno
    }

    private void ejecutarAtaque(int indexAtacante, int indexAtaque) {
        // --- Validaciones Previas ---
        // 1. No ejecutar si hay selección pendiente
        if (seleccionPendiente) {
            agregarMensaje("Espera a que se seleccione el próximo Pokémon.");
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        // 2. Verificar si es el turno correcto
        if ((indexAtacante == 1 && !turnoEntrenador1) || (indexAtacante == 2 && turnoEntrenador1)) {
            agregarMensaje("¡Espera tu turno!");
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        // --- Obtener Atacante y Defensor ---
        Pokemon atacante = (indexAtacante == 1) ? pokemonActivo1 : pokemonActivo2;
        Pokemon defensor = (indexAtacante == 1) ? pokemonActivo2 : pokemonActivo1;
        Entrenador entrenadorAtacante = (indexAtacante == 1) ? entrenador1 : entrenador2;
        int indexDefensor = (indexAtacante == 1) ? 2 : 1;

        // 3. Verificar que los Pokémon existen
        if (atacante == null || defensor == null) {
             agregarMensaje("Error crítico: Pokémon activo es null. Finalizando batalla.");
              System.err.println("Error crítico en ejecutarAtaque: Atacante o Defensor es null.");
             finalizarBatalla(indexAtacante == 1 ? 2 : 1); // Gana el oponente
             return;
        }

        // --- Obtener Ataque Seleccionado ---
        Ataque ataqueSeleccionado = null;
        ArrayList<Ataque> listaAtaques = atacante.getAtaques();
        // 4. Verificar que la lista de ataques y el índice son válidos
        if (listaAtaques != null && indexAtaque >= 0 && indexAtaque < listaAtaques.size()) {
             ataqueSeleccionado = listaAtaques.get(indexAtaque);
        }

        // 5. Verificar que el ataque obtenido es válido
        if (ataqueSeleccionado == null || ataqueSeleccionado.getNombre() == null || ataqueSeleccionado.getNombre().isEmpty()) {
            agregarMensaje("Error: Ataque no válido seleccionado.");
            System.err.println("Error en ejecutarAtaque: Intento de usar ataque null o inválido en índice " + indexAtaque + " por Entrenador " + indexAtacante);
            // No cambiar turno, permitir reintentar
            return;
        }

        // --- Ejecutar el Ataque ---
        agregarMensaje(entrenadorAtacante.getNombre() + ": ¡" + atacante.getNombre() + ", usa " + ataqueSeleccionado.getNombre() + "!");

        // Guardar vida antes para calcular daño
        float vidaAntes = defensor.getHp();

        // Lógica de ataque (asume que el método atacar modifica la vida del defensor)
        atacante.atacar(ataqueSeleccionado, defensor); // Modifica defensor.hp

        // Calcular daño real infligido
        float vidaDespues = defensor.getHp();
        int danioInfligido = (int) Math.max(0, vidaAntes - vidaDespues); // Asegurar no negativo

        agregarMensaje("¡" + defensor.getNombre() + " recibió " + danioInfligido + " de daño!");

        // Actualizar la UI del defensor
        actualizarVidaPokemonUI(indexDefensor, (int) defensor.getHp(), calcularVidaMaximaAproximada(defensor));

        // Verificar si el defensor se debilitó
        // ¡Importante! Pasar el índice del POKEMON DEFENSOR a verificarDebilitado
        verificarDebilitado(indexDefensor);

         // Cambiar turno SOLO si no hay una selección pendiente (causada por debilitamiento)
         if (!seleccionPendiente) {
            cambiarTurno();
        } else {
             // Si hay selección pendiente, configurarTurno se llamará después de seleccionar
             // pero actualizamos el estado visual ahora
             configurarTurno();
        }
    }

    private void verificarDebilitado(int indexPokemonVerificar) {
         System.out.println("[Debug WB] Verificando si Pokémon " + indexPokemonVerificar + " se debilitó.");
        Pokemon pokemonVerificar = (indexPokemonVerificar == 1) ? pokemonActivo1 : pokemonActivo2;
        ArrayList<Pokemon> equipoRestante = (indexPokemonVerificar == 1) ? equipoRestante1 : equipoRestante2;
        Entrenador entrenador = (indexPokemonVerificar == 1) ? entrenador1 : entrenador2;

        if (pokemonVerificar == null) {
             System.err.println("Error en verificarDebilitado: El Pokémon a verificar es null para índice " + indexPokemonVerificar);
             // Podría finalizar la batalla si esto ocurre inesperadamente
             finalizarBatalla(indexPokemonVerificar == 1 ? 2 : 1);
             return;
        }

        // Verificar si el Pokémon está vivo (asume método getVivo o similar)
        if (!pokemonVerificar.getVivo()) { // Si HP <= 0, getVivo debería ser false
            System.out.println("[Debug WB] " + pokemonVerificar.getNombre() + " está debilitado (vivo=" + pokemonVerificar.getVivo() + ", hp=" + pokemonVerificar.getHp() + ")");
            agregarMensaje("¡" + pokemonVerificar.getNombre() + " se ha debilitado!");

            // Quitarlo de la lista de Pokémon *disponibles* para selección futura
            // Es importante quitar el objeto correcto
            boolean removido = equipoRestante.remove(pokemonVerificar);
            if (!removido) {
                 // Esto podría pasar si ya fue removido o no estaba en la lista, investigar si ocurre
                 System.err.println("Advertencia: No se pudo remover a " + pokemonVerificar.getNombre() + " del equipo restante de " + entrenador.getNombre() + ".");
            } else {
                 System.out.println("[Debug WB] " + pokemonVerificar.getNombre() + " removido de equipoRestante" + indexPokemonVerificar);
            }


            // Buscar si quedan otros Pokémon VIVOS en el equipo restante
            boolean tieneMasPokemonVivos = false;
            if (equipoRestante != null) {
                 System.out.println("[Debug WB] Verificando equipo restante de " + entrenador.getNombre() + ":");
                 for (Pokemon p : equipoRestante) {
                      if (p != null && p.getVivo()) { // Buscar al menos uno vivo
                          System.out.println("  - " + p.getNombre() + " (vivo=" + p.getVivo() + ")");
                          tieneMasPokemonVivos = true;
                          break; // Encontramos uno, suficiente
                      } else {
                           System.out.println("  - " + (p != null ? p.getNombre() : "NULL") + " (vivo=" + (p != null ? p.getVivo() : "N/A") + ") - Ignorado");
                      }
                 }
            }

            if (!tieneMasPokemonVivos) {
                // Si no quedan Pokémon vivos, finalizar la batalla
                System.out.println("[Debug WB] No quedan Pokémon vivos para " + entrenador.getNombre() + ". Finalizando batalla.");
                finalizarBatalla(indexPokemonVerificar == 1 ? 2 : 1); // Gana el oponente
            } else {
                // Si quedan vivos, forzar selección
                System.out.println("[Debug WB] Quedan Pokémon vivos para " + entrenador.getNombre() + ". Mostrando panel de selección.");
                // Crear la lista de opciones SOLO con los vivos para el panel
                ArrayList<Pokemon> opcionesVivas = new ArrayList<>();
                if (equipoRestante != null) {
                    for (Pokemon p : equipoRestante) {
                        if (p != null && p.getVivo()) {
                            opcionesVivas.add(p);
                        }
                    }
                }

                // Doble chequeo por si acaso
                if (opcionesVivas.isEmpty()) {
                     System.err.println("Error Lógico: Se detectaron Pokémon vivos, pero la lista filtrada está vacía. Finalizando.");
                     finalizarBatalla(indexPokemonVerificar == 1 ? 2 : 1);
                } else {
                     // Marcar que se necesita seleccionar y quién debe hacerlo
                     seleccionPendiente = true;
                     entrenadorSeleccionando = indexPokemonVerificar;
                     // Mostrar el panel correspondiente con las opciones vivas
                     mostrarPanelSeleccion(entrenadorSeleccionando, pokemonVerificar.getNombre(), opcionesVivas);
                     // Actualizar la UI para reflejar el estado de selección
                     configurarTurno();
                }
            }
        } else {
             // Si el Pokémon no se debilitó, no hacer nada más aquí
             System.out.println("[Debug WB] " + pokemonVerificar.getNombre() + " sigue vivo (hp=" + pokemonVerificar.getHp() + ").");
        }
    }


    private void mostrarPanelSeleccion(int indexEntrenador, String pkmDebilitado, ArrayList<Pokemon> opcionesVivas) {
         System.out.println("[Debug WB] Mostrando panel de selección para Entrenador " + indexEntrenador);
         // Validación de entrada
         if (opcionesVivas == null || opcionesVivas.isEmpty()) {
              System.err.println("Error crítico en mostrarPanelSeleccion: No hay opciones vivas para mostrar para Entrenador " + indexEntrenador + ".");
              finalizarBatalla(indexEntrenador == 1 ? 2 : 1); // Gana el oponente
              return;
         }

        // Determinar qué componentes UI usar
        JPanel panelSeleccion = (indexEntrenador == 1) ? panelSeleccionPokemon1 : panelSeleccionPokemon2;
        JPanel panelAtaquesOcultar = (indexEntrenador == 1) ? panelAtaques1 : panelAtaques2;
        JLabel lblInfo = (indexEntrenador == 1) ? lblSeleccionInfo1 : lblSeleccionInfo2;
        JButton btnOp1 = (indexEntrenador == 1) ? btnSeleccionarPokemon1_Op1 : btnSeleccionarPokemon2_Op1;
        JButton btnOp2 = (indexEntrenador == 1) ? btnSeleccionarPokemon1_Op2 : btnSeleccionarPokemon2_Op2;

         // Verificar que los componentes no sean null (importante)
         if (panelSeleccion == null || panelAtaquesOcultar == null || lblInfo == null || btnOp1 == null || btnOp2 == null) {
              System.err.println("Error crítico: Componente GUI nulo en mostrarPanelSeleccion para Entrenador " + indexEntrenador + ".");
              // Podría intentar finalizar la batalla o simplemente loggear el error
              finalizarBatalla(indexEntrenador == 1 ? 2 : 1);
              return;
         }

        // Configurar texto informativo
        lblInfo.setText(pkmDebilitado + " debilitado. Elige:");

        // --- Configurar Botón 1 (Siempre habrá al menos una opción) ---
        // Limpiar listeners anteriores para evitar duplicados
        for (ActionListener al : btnOp1.getActionListeners()) { btnOp1.removeActionListener(al); }

         Pokemon opcion1 = opcionesVivas.get(0); // Primera opción viva garantizada
         if (opcion1 != null) {
             btnOp1.setText(opcion1.getNombre() + " Nv." + opcion1.getNivel());
             btnOp1.setVisible(true);
             btnOp1.setEnabled(true);
             // Añadir listener que llame a seleccionarNuevoPokemon con esta opción
             btnOp1.addActionListener(e -> seleccionarNuevoPokemon(indexEntrenador, opcion1));
             System.out.println("  Opción 1: " + opcion1.getNombre());
         } else {
              // Esto no debería pasar si la validación inicial es correcta
              System.err.println("Error: Opción 1 de Pokémon para seleccionar es null.");
              btnOp1.setText("[Error]");
              btnOp1.setEnabled(false);
              btnOp1.setVisible(true);
         }

        // --- Configurar Botón 2 (Si existe una segunda opción) ---
         // Limpiar listeners anteriores
         for (ActionListener al : btnOp2.getActionListeners()) { btnOp2.removeActionListener(al); }

         if (opcionesVivas.size() > 1) {
             Pokemon opcion2 = opcionesVivas.get(1); // Segunda opción viva
             if (opcion2 != null) {
                 btnOp2.setText(opcion2.getNombre() + " Nv." + opcion2.getNivel());
                 btnOp2.setVisible(true);
                 btnOp2.setEnabled(true);
                 // Añadir listener que llame a seleccionarNuevoPokemon con esta opción
                 btnOp2.addActionListener(e -> seleccionarNuevoPokemon(indexEntrenador, opcion2));
                 System.out.println("  Opción 2: " + opcion2.getNombre());
             } else {
                 System.err.println("Error: Opción 2 de Pokémon para seleccionar es null.");
                 btnOp2.setText("[Error]");
                 btnOp2.setVisible(true); // Hacer visible pero deshabilitado
                 btnOp2.setEnabled(false);
             }
         } else {
             // No hay segunda opción, ocultar y deshabilitar el botón 2
             btnOp2.setText(""); // Limpiar texto
             btnOp2.setVisible(false);
             btnOp2.setEnabled(false);
             System.out.println("  (Solo una opción disponible)");
         }

         // Ocultar panel de ataques y mostrar panel de selección
         panelAtaquesOcultar.setVisible(false);
         panelSeleccion.setVisible(true);

         // Marcar estado de selección
         seleccionPendiente = true;
         entrenadorSeleccionando = indexEntrenador;

         System.out.println("[Debug WB] Panel de selección mostrado.");
    }

    private void seleccionarNuevoPokemon(int indexEntrenador, Pokemon nuevoPokemon) {
         System.out.println("[Debug WB] Entrenador " + indexEntrenador + " seleccionó a " + nuevoPokemon.getNombre());

         if (nuevoPokemon == null) {
             System.err.println("Error crítico: Se intentó seleccionar un Pokémon null.");
             agregarMensaje("Error al seleccionar. Intenta de nuevo o reinicia.");
             // Podríamos intentar mostrar el panel de nuevo, pero es arriesgado
             // O finalizar la batalla
             finalizarBatalla(indexEntrenador == 1 ? 2 : 1);
             return;
         }
         // Validar si el Pokémon seleccionado está realmente vivo (doble chequeo)
         if (!nuevoPokemon.getVivo()) {
              System.err.println("Error Lógico: Se seleccionó un Pokémon (" + nuevoPokemon.getNombre() + ") que no está vivo.");
              agregarMensaje("Error: ¡" + nuevoPokemon.getNombre() + " no puede luchar!");
              // Forzar mostrar el panel de nuevo con las opciones correctas
              // Esto requiere obtener la lista de vivas de nuevo
              ArrayList<Pokemon> equipoRestante = (indexEntrenador == 1) ? equipoRestante1 : equipoRestante2;
              ArrayList<Pokemon> opcionesVivas = new ArrayList<>();
               if (equipoRestante != null) {
                   for (Pokemon p : equipoRestante) {
                       if (p != null && p.getVivo()) {
                           opcionesVivas.add(p);
                       }
                   }
               }
               if (!opcionesVivas.isEmpty()) {
                   mostrarPanelSeleccion(indexEntrenador, "Pokémon inválido", opcionesVivas);
               } else {
                   // Si no quedan otras opciones, finalizar
                   finalizarBatalla(indexEntrenador == 1 ? 2 : 1);
               }
              return;
         }


         // Mensaje al usuario
         agregarMensaje("¡" + (indexEntrenador == 1 ? entrenador1.getNombre() : entrenador2.getNombre()) +
                       " envía a " + nuevoPokemon.getNombre() + "!");

         // Actualizar la UI con el nuevo Pokémon activo
         // Esto también asigna nuevoPokemon a pokemonActivo1 o pokemonActivo2
         actualizarInfoPokemon(indexEntrenador, nuevoPokemon);

         // Ocultar el panel de selección que se usó
         JPanel panelSeleccion = (indexEntrenador == 1) ? panelSeleccionPokemon1 : panelSeleccionPokemon2;
         if (panelSeleccion != null) {
            panelSeleccion.setVisible(false);
         } else {
              System.err.println("Advertencia: Panel de selección a ocultar es null para Entrenador " + indexEntrenador);
         }

         // Resets del estado de selección
         seleccionPendiente = false;
         entrenadorSeleccionando = 0;

         System.out.println("[Debug WB] Selección completada. Configurando turno.");
         // Configurar la UI para el siguiente estado (probablemente el turno del oponente)
         // Nota: No cambiamos el turno aquí explícitamente, configurarTurno lo manejará
         configurarTurno();

         // Es posible que después de seleccionar, el turno cambie al oponente.
         // O si el Pokémon entró y hay algún efecto de entrada (trampas, clima), se aplicaría aquí.

         // Forzar repintado por si acaso
         panelPrincipal.revalidate();
         panelPrincipal.repaint();
    }


    private void cambiarTurno() {
        // Solo cambia el turno si NO hay una selección pendiente
        if (!seleccionPendiente) {
            turnoEntrenador1 = !turnoEntrenador1;
            System.out.println("[Debug WB] Turno cambiado. Ahora es turno de Entrenador " + (turnoEntrenador1 ? "1" : "2"));
            configurarTurno(); // Actualiza la UI para el nuevo turno
        } else {
            // Si hay selección pendiente, no se cambia el turno lógico aún.
            // La UI se actualiza en configurarTurno para mostrar el estado de selección.
            System.out.println("[Debug WB] Cambio de turno bloqueado: Selección pendiente por Entrenador " + entrenadorSeleccionando);
            configurarTurno(); // Asegura que la UI refleje el estado de selección
        }
    }

   private void configurarTurno() {
        System.out.println("[Debug WB] Configurando turno. Es turno de E1: " + turnoEntrenador1 + ", Selección pendiente: " + seleccionPendiente + " por E" + entrenadorSeleccionando);

        // Determinar qué panel de ataque debería estar visible y habilitado
        boolean puedeAtacar1 = turnoEntrenador1 && !seleccionPendiente;
        boolean puedeAtacar2 = !turnoEntrenador1 && !seleccionPendiente;

        // Visibilidad de Paneles de Ataque:
        // Mostrar el panel del jugador activo si nadie está seleccionando.
        // Si alguien está seleccionando (ej: E1 selecciona), ocultamos su panel de ataque
        // y podríamos mantener visible el del oponente (E2) aunque esté deshabilitado.
        if (panelAtaques1 != null) {
            panelAtaques1.setVisible(!seleccionPendiente || entrenadorSeleccionando == 2); // Visible si es turno de E1 o si E2 está seleccionando
        }
        if (panelAtaques2 != null) {
            panelAtaques2.setVisible(!seleccionPendiente || entrenadorSeleccionando == 1); // Visible si es turno de E2 o si E1 está seleccionando
        }

        // Habilitar/Deshabilitar Botones de Ataque:
        // Los botones solo están habilitados si es el turno de ese jugador Y no hay selección pendiente.
        configurarEstadoBotones(botonesAtaque1, puedeAtacar1, pokemonActivo1);
        configurarEstadoBotones(botonesAtaque2, puedeAtacar2, pokemonActivo2);


        // Visibilidad de Paneles de Selección:
        // Solo visible si la selección está pendiente Y es para ese entrenador específico.
        if (panelSeleccionPokemon1 != null) {
            panelSeleccionPokemon1.setVisible(seleccionPendiente && entrenadorSeleccionando == 1);
        }
        if (panelSeleccionPokemon2 != null) {
            panelSeleccionPokemon2.setVisible(seleccionPendiente && entrenadorSeleccionando == 2);
        }

        // Actualizar Texto de Información de Turno/Selección:
        if (lblTurnoInfo != null) {
             if (seleccionPendiente) {
                 // Mostrar quién está seleccionando
                 String nombreSeleccionador = (entrenadorSeleccionando == 1 && entrenador1 != null) ? entrenador1.getNombre() :
                                             ((entrenadorSeleccionando == 2 && entrenador2 != null) ? entrenador2.getNombre() : "Entrenador " + entrenadorSeleccionando);
                 lblTurnoInfo.setText("¡" + nombreSeleccionador + ", elige tu Pokémon!");
                 lblTurnoInfo.setForeground(Color.YELLOW); // Amarillo para selección
             } else {
                 // Mostrar de quién es el turno de atacar
                 if (turnoEntrenador1) {
                     lblTurnoInfo.setText("Turno: " + (entrenador1 != null ? entrenador1.getNombre() : "E1"));
                     lblTurnoInfo.setForeground(Color.CYAN); // Color para E1
                 } else {
                     lblTurnoInfo.setText("Turno: " + (entrenador2 != null ? entrenador2.getNombre() : "E2"));
                     lblTurnoInfo.setForeground(Color.ORANGE); // Color para E2
                 }
             }
        }

         // Refrescar el panel principal para asegurar que todos los cambios de visibilidad/estado se apliquen
         if (panelPrincipal != null) {
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
         }
         System.out.println("[Debug WB] Configuración de turno UI completada.");
    }

    // Método auxiliar para habilitar/deshabilitar botones de ataque
    private void configurarEstadoBotones(JButton[] botones, boolean puedeAtacar, Pokemon pokemonActivo) {
        if (botones == null) return;

         // Verificar si el Pokémon activo existe
         boolean pokemonValido = pokemonActivo != null && pokemonActivo.getAtaques() != null;

         for (int i = 0; i < botones.length; i++) {
            if (botones[i] != null) {
                // El botón debe estar habilitado si:
                // 1. El jugador puede atacar (es su turno y no hay selección)
                // 2. El Pokémon activo existe y tiene ataques
                // 3. Hay un ataque válido en esta posición (índice i)
                 boolean ataqueValidoEnIndice = pokemonValido &&
                                                i < pokemonActivo.getAtaques().size() &&
                                                pokemonActivo.getAtaques().get(i) != null;

                 botones[i].setEnabled(puedeAtacar && ataqueValidoEnIndice);
            }
        }
    }

     private void finalizarBatalla(int indexGanador) {
         System.out.println("[Debug WB] Finalizando batalla. Ganador índice: " + indexGanador);
         // Bloquear más acciones
         seleccionPendiente = true;
         entrenadorSeleccionando = 0; // Nadie selecciona
         turnoEntrenador1 = (indexGanador == 1); // Opcional: poner turno al ganador

         // Determinar nombres
         Entrenador ganador = (indexGanador == 1) ? entrenador1 : entrenador2;
         Entrenador perdedor = (indexGanador == 1) ? entrenador2 : entrenador1;
         String nombreGanador = (ganador != null) ? ganador.getNombre() : "Entrenador " + indexGanador;
         String nombrePerdedor = (perdedor != null) ? perdedor.getNombre() : "El oponente";

         // Mensajes finales
         agregarMensaje("¡" + nombrePerdedor + " no tiene más Pokémon capaces de luchar!");
         agregarMensaje("¡El ganador es " + nombreGanador + "!");

         // Deshabilitar todos los botones interactivos
         deshabilitarTodosLosBotones();

         // Actualizar etiqueta de turno con mensaje de victoria
         if (lblTurnoInfo != null) {
            lblTurnoInfo.setText("¡VICTORIA PARA " + nombreGanador.toUpperCase() + "!");
            lblTurnoInfo.setForeground(Color.GREEN); // Color de victoria
         }

         // Mostrar diálogo de fin de batalla (Modal)
         JOptionPane.showMessageDialog(this, // 'this' es el JFrame de batalla
             "¡Fin de la batalla!\n\nGanador: " + nombreGanador,
             "Fin de la Batalla",
             JOptionPane.INFORMATION_MESSAGE);

         // Podrías cerrar la ventana automáticamente después del diálogo si quieres
         // this.dispose();
    }

    // Método auxiliar para deshabilitar todos los botones al final
    private void deshabilitarTodosLosBotones() {
         System.out.println("[Debug WB] Deshabilitando todos los botones.");
         // Deshabilitar botones de ataque
         for(JButton btn : botonesAtaque1) if (btn != null) btn.setEnabled(false);
         for(JButton btn : botonesAtaque2) if (btn != null) btn.setEnabled(false);

         // Deshabilitar botones de selección (aunque los paneles deberían estar ocultos)
         if (btnSeleccionarPokemon1_Op1 != null) btnSeleccionarPokemon1_Op1.setEnabled(false);
         if (btnSeleccionarPokemon1_Op2 != null) btnSeleccionarPokemon1_Op2.setEnabled(false);
         if (btnSeleccionarPokemon2_Op1 != null) btnSeleccionarPokemon2_Op1.setEnabled(false);
         if (btnSeleccionarPokemon2_Op2 != null) btnSeleccionarPokemon2_Op2.setEnabled(false);

         // Asegurarse que los paneles de selección estén ocultos
         if (panelSeleccionPokemon1 != null) panelSeleccionPokemon1.setVisible(false);
         if (panelSeleccionPokemon2 != null) panelSeleccionPokemon2.setVisible(false);
    }

    private JButton crearBotonAtaque(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FONT_MONO_PLAIN_15);
        boton.setFocusPainted(false); // Quitar borde de foco
        boton.setBackground(new Color(50, 50, 70)); // Color base
        boton.setForeground(Color.WHITE);
        boton.setBorder(new LineBorder(Color.GRAY)); // Borde sutil
        // Efecto Hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (boton.isEnabled()) boton.setBackground(new Color(80, 80, 100)); // Más claro al pasar
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                 if (boton.isEnabled()) boton.setBackground(new Color(50, 50, 70)); // Volver al color base
            }
        });
        return boton;
    }

     private JButton crearBotonSeleccion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FONT_MONO_PLAIN_15);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(70, 70, 90)); // Color base diferente
        boton.setForeground(Color.WHITE);
        boton.setBorder(new LineBorder(Color.CYAN)); // Borde más llamativo
        boton.setPreferredSize(new Dimension(150, 40)); // Tamaño preferido
        // Efecto Hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                 if (boton.isEnabled()) boton.setBackground(new Color(100, 100, 120));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                  if (boton.isEnabled()) boton.setBackground(new Color(70, 70, 90));
            }
        });
        return boton;
    }

    // Método simple para mensajes (puedes redirigir a un JTextArea si quieres)
    private void agregarMensaje(String mensaje) {
        System.out.println("[Batalla] " + mensaje);
        // Aquí podrías añadir el mensaje a un log en la UI si tuvieras uno
    }


    // Calcular vida máxima basado en nivel (ajusta esta fórmula según tu juego)
    private int calcularVidaMaximaAproximada(Pokemon p) {
        if (p == null) return 1; // Evitar división por cero
        // Fórmula de ejemplo, reemplaza con la tuya si es diferente
        int vidaBase = 50; // Ejemplo
        int vidaPorNivel = 5; // Ejemplo
        int vidaMax = vidaBase + (p.getNivel() * vidaPorNivel);
        return Math.max(1, vidaMax); // Asegurar que sea al menos 1
    }

    // --- Método main (MODIFICADO para usar el nuevo flujo de inicialización) ---
    // Útil para probar WindowBatalla directamente
    public static void main(String[] args) {
         System.out.println("[Debug WB] Ejecutando main de WindowBatalla para prueba...");
         // Crear entrenadores de prueba (manejar posibles errores)
         Entrenador testE1 = null;
         Entrenador testE2 = null;
         try {
             // Asegúrate que estos Pokémon y el método capturarEntrenador existan
             testE1 = Entrenador.capturarEntrenador("Ash", "Pikachu", "Bulbasaur", "Squirtle");
             testE2 = Entrenador.capturarEntrenador("Gary", "Eevee", "Charmander", "Rattata");
             System.out.println("[Debug WB] Entrenadores de prueba creados.");
         } catch (Exception e) {
             System.err.println("Error CRÍTICO al crear entrenadores o Pokémon de prueba en main: " + e.getMessage());
             e.printStackTrace();
             JOptionPane.showMessageDialog(null,
                 "Error creando los datos de prueba.\nAsegúrate que los Pokémon existen y revisa la consola.\nError: " + e.getMessage(),
                 "Error de Prueba", JOptionPane.ERROR_MESSAGE);
             return; // Salir si no se pueden crear los entrenadores
         }

         // Necesitamos pasar final para usar dentro de invokeLater
         final Entrenador finalE1 = testE1;
         final Entrenador finalE2 = testE2;

         // Ejecutar en el hilo de eventos de Swing
         SwingUtilities.invokeLater(() -> {
             System.out.println("[Debug WB] Creando WindowBatalla desde main...");
             WindowBatalla ventanaBatalla = new WindowBatalla(finalE1, finalE2);

             System.out.println("[Debug WB] Haciendo visible WindowBatalla desde main...");
             ventanaBatalla.setVisible(true);

             // Llamar a iniciarBatalla DESPUÉS de setVisible(true)
             System.out.println("[Debug WB] Llamando a iniciarBatalla desde main...");
             // No necesitamos invokeLater aquí porque ya estamos dentro de uno
             ventanaBatalla.iniciarBatalla();
              System.out.println("[Debug WB] Flujo de main completado.");
         });
    }


    // Implementación requerida por ActionListener (aunque no la usemos directamente aquí)
    @Override
    public void actionPerformed(ActionEvent e) {
        // Puedes dejarlo vacío o añadir lógica si algún componente usa esta ventana como listener
        System.out.println("ActionEvent recibido por WindowBatalla: " + e.getActionCommand());
        // throw new UnsupportedOperationException("ActionListener no implementado específicamente en WindowBatalla.");
    }

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters (Opcional - si los necesitas)">
    // Puedes añadir getters/setters para componentes o estado si son necesarios desde fuera
    public JProgressBar getPbVidaPokemon1() { return pbVidaPokemon1; }
    public JProgressBar getPbVidaPokemon2() { return pbVidaPokemon2; }
    // ... otros getters/setters ...
    //</editor-fold>

} // Fin de la clase WindowBatalla