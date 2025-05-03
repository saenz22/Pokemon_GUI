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
// los pokemones al ser aleatorios no tienen que estar en el mismo orden y adiconalmente la primera demuestra el tipo aleatorio de un pokemon
//la vida o daño que tiene adicional de la maxima pueda que este con pociones 
public class WindowBatalla extends JFrame implements ActionListener {


    private static final int ANCHO_VENTANA = 800;
    private static final int ALTO_VENTANA = 800;
    private static final int ANCHO_IMAGEN_PKM = 200;
    public JProgressBar getPbVidaPokemon1() {
        return pbVidaPokemon1;
    }

    public void setPbVidaPokemon1(JProgressBar pbVidaPokemon1) {
        this.pbVidaPokemon1 = pbVidaPokemon1;
    }

    public JProgressBar getPbVidaPokemon2() {
        return pbVidaPokemon2;
    }

    public void setPbVidaPokemon2(JProgressBar pbVidaPokemon2) {
        this.pbVidaPokemon2 = pbVidaPokemon2;
    }

    public JPanel getPanelAtaques1() {
        return panelAtaques1;
    }

    public void setPanelAtaques1(JPanel panelAtaques1) {
        this.panelAtaques1 = panelAtaques1;
    }

    public JPanel getPanelAtaques2() {
        return panelAtaques2;
    }

    public void setPanelAtaques2(JPanel panelAtaques2) {
        this.panelAtaques2 = panelAtaques2;
    }

    public JPanel getPanelSeleccionPokemon1() {
        return panelSeleccionPokemon1;
    }

    public void setPanelSeleccionPokemon1(JPanel panelSeleccionPokemon1) {
        this.panelSeleccionPokemon1 = panelSeleccionPokemon1;
    }

    public JPanel getPanelSeleccionPokemon2() {
        return panelSeleccionPokemon2;
    }

    public void setPanelSeleccionPokemon2(JPanel panelSeleccionPokemon2) {
        this.panelSeleccionPokemon2 = panelSeleccionPokemon2;
    }

    public JButton getBtnSeleccionarPokemon1_Op1() {
        return btnSeleccionarPokemon1_Op1;
    }

    public void setBtnSeleccionarPokemon1_Op1(JButton btnSeleccionarPokemon1_Op1) {
        this.btnSeleccionarPokemon1_Op1 = btnSeleccionarPokemon1_Op1;
    }

    public JButton getBtnSeleccionarPokemon1_Op2() {
        return btnSeleccionarPokemon1_Op2;
    }

    public void setBtnSeleccionarPokemon1_Op2(JButton btnSeleccionarPokemon1_Op2) {
        this.btnSeleccionarPokemon1_Op2 = btnSeleccionarPokemon1_Op2;
    }

    public JButton getBtnSeleccionarPokemon2_Op1() {
        return btnSeleccionarPokemon2_Op1;
    }

    public void setBtnSeleccionarPokemon2_Op1(JButton btnSeleccionarPokemon2_Op1) {
        this.btnSeleccionarPokemon2_Op1 = btnSeleccionarPokemon2_Op1;
    }

    public JButton getBtnSeleccionarPokemon2_Op2() {
        return btnSeleccionarPokemon2_Op2;
    }

    public void setBtnSeleccionarPokemon2_Op2(JButton btnSeleccionarPokemon2_Op2) {
        this.btnSeleccionarPokemon2_Op2 = btnSeleccionarPokemon2_Op2;
    }

    public JButton[] getBotonesAtaque1() {
        return botonesAtaque1;
    }

    public void setBotonesAtaque1(JButton[] botonesAtaque1) {
        this.botonesAtaque1 = botonesAtaque1;
    }

    public JButton[] getBotonesAtaque2() {
        return botonesAtaque2;
    }

    public void setBotonesAtaque2(JButton[] botonesAtaque2) {
        this.botonesAtaque2 = botonesAtaque2;
    }

    public Entrenador getEntrenador1() {
        return entrenador1;
    }

    public void setEntrenador1(Entrenador entrenador1) {
        this.entrenador1 = entrenador1;
    }

    public Entrenador getEntrenador2() {
        return entrenador2;
    }

    public void setEntrenador2(Entrenador entrenador2) {
        this.entrenador2 = entrenador2;
    }

    public Pokemon getPokemonActivo1() {
        return pokemonActivo1;
    }

    public void setPokemonActivo1(Pokemon pokemonActivo1) {
        this.pokemonActivo1 = pokemonActivo1;
    }

    public Pokemon getPokemonActivo2() {
        return pokemonActivo2;
    }

    public void setPokemonActivo2(Pokemon pokemonActivo2) {
        this.pokemonActivo2 = pokemonActivo2;
    }

    public ArrayList<Pokemon> getEquipoRestante1() {
        return equipoRestante1;
    }

    public void setEquipoRestante1(ArrayList<Pokemon> equipoRestante1) {
        this.equipoRestante1 = equipoRestante1;
    }

    public ArrayList<Pokemon> getEquipoRestante2() {
        return equipoRestante2;
    }

    public void setEquipoRestante2(ArrayList<Pokemon> equipoRestante2) {
        this.equipoRestante2 = equipoRestante2;
    }

    public boolean isTurnoEntrenador1() {
        return turnoEntrenador1;
    }

    public void setTurnoEntrenador1(boolean turnoEntrenador1) {
        this.turnoEntrenador1 = turnoEntrenador1;
    }

    public boolean isSeleccionPendiente() {
        return seleccionPendiente;
    }

    public void setSeleccionPendiente(boolean seleccionPendiente) {
        this.seleccionPendiente = seleccionPendiente;
    }

    public int getEntrenadorSeleccionando() {
        return entrenadorSeleccionando;
    }

    public void setEntrenadorSeleccionando(int entrenadorSeleccionando) {
        this.entrenadorSeleccionando = entrenadorSeleccionando;
    }
    // se coloca final para que no se pueda cambiar el tamaño de la ventana ya que es constante 
    private static final int ALTO_IMAGEN_PKM = 200;
    private static final int ANCHO_INFO_PKM = 250;
    private static final int ALTO_INFO_PKM_BLOQUE = 35 + 18 + 25 + 10; 
    private static final int ANCHO_BOTON_PANEL = 340;
    private static final int ALTO_BOTON_PANEL = 100;
    private static final int Y_POS_POKEMON_AREA = (int) (ALTO_VENTANA * 0.30);
    private static final int MARGEN_LATERAL = 60;
    private static final Font FONT_MONO_BOLD_20 = new Font("Monospaced", Font.BOLD, 20);// fuente de las anteriores ventanas
    private static final Font FONT_MONO_PLAIN_15 = new Font("Monospaced", Font.PLAIN, 15);
    private static final Color COLOR_TEXTO_INFO = Color.WHITE;
// imagenes de fondo y pokemon (aleatorias las ultimas)
    private static final String RUTA_IMAGENES = "image/";
    private static final String RUTA_FONDO = RUTA_IMAGENES + "Estadio.jpg"; 
    private static final String[] RUTAS_PKM1 = {
            RUTA_IMAGENES + "PE1.png",
            RUTA_IMAGENES + "PE2.png",
            RUTA_IMAGENES + "PE3.png",
            RUTA_IMAGENES + "PP1.png",
            RUTA_IMAGENES + "PP2.png",
            RUTA_IMAGENES + "PP3.png",
            RUTA_IMAGENES + "PF1.png",
            RUTA_IMAGENES + "PF2.png",
            RUTA_IMAGENES + "PF3.png",
            RUTA_IMAGENES + "PA1.png",
            RUTA_IMAGENES + "PA2.png",
            RUTA_IMAGENES + "PA3.png",
            RUTA_IMAGENES + "PT1.png",
            RUTA_IMAGENES + "PT2.png",
            RUTA_IMAGENES + "PT3.png"
    };
    private static final String[] RUTAS_PKM2 = {
            RUTA_IMAGENES + "PP1.png",
            RUTA_IMAGENES + "PP2.png",
            RUTA_IMAGENES + "PP3.png",
            RUTA_IMAGENES + "PA1.png",
            RUTA_IMAGENES + "PA2.png",
            RUTA_IMAGENES + "PA3.png",
            RUTA_IMAGENES + "PE1.png",
            RUTA_IMAGENES + "PE2.png",
            RUTA_IMAGENES + "PE3.png",
            RUTA_IMAGENES + "PF1.png",
            RUTA_IMAGENES + "PF2.png",
            RUTA_IMAGENES + "PF3.png",
            RUTA_IMAGENES + "PT1.png",
            RUTA_IMAGENES + "PT2.png",
            RUTA_IMAGENES + "PT3.png"
    }; 
    private static final Random random = new Random();
    private static final String RUTA_IMG_PKM1 = obtenerrutaaleatoria(RUTAS_PKM1);
    private static final String RUTA_IMG_PKM2 = obtenerrutaaleatoria(RUTAS_PKM2);

    private static String obtenerrutaaleatoria(String[] rutas) {
        int indice = random.nextInt(rutas.length);
        return rutas[indice];
    }

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
 // atributos de la batalla (informacion de los entrenadores y pokemon activos)
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Pokemon pokemonActivo1;
    private Pokemon pokemonActivo2;
    private ArrayList<Pokemon> equipoRestante1;
    private ArrayList<Pokemon> equipoRestante2;
    private boolean turnoEntrenador1 = true;
    private boolean seleccionPendiente = false;
    private int entrenadorSeleccionando = 0;

    public WindowBatalla(Entrenador e1, Entrenador e2) {
        this.entrenador1 = Objects.requireNonNull(e1, "Entrenador 1 no puede ser null");
        this.entrenador2 = Objects.requireNonNull(e2, "Entrenador 2 no puede ser null");

        this.equipoRestante1 = (e1.getEquipo() != null) ? new ArrayList<>(e1.getEquipo()) : new ArrayList<>(); // todo por la logica de la batalla recibida por lo anteriormente
        this.equipoRestante2 = (e2.getEquipo() != null) ? new ArrayList<>(e2.getEquipo()) : new ArrayList<>();

        this.pokemonActivo1 = seleccionarPokemonInicial(entrenador1, equipoRestante1, 1); // constructores de pokemon
        this.pokemonActivo2 = seleccionarPokemonInicial(entrenador2, equipoRestante2, 2);

        if (pokemonActivo1 == null || pokemonActivo2 == null) {
            System.err.println("Selección inicial cancelada o fallida. Saliendo.");
            dispose();
             JOptionPane.showMessageDialog(null, "No se pudo iniciar la batalla.\nAsegúrate de que ambos entrenadores tengan equipos válidos.", "Error de Inicio", JOptionPane.ERROR_MESSAGE);
            return; // en caso de salir de la ventana
        }

        setTitle("Batalla Pokémon: " + entrenador1.getNombre() + " vs " + entrenador2.getNombre());
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        final Image imagenFondo = cargarImagen(RUTA_FONDO);
        panelPrincipal = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(100, 100, 120));
                    g.fillRect(0, 0, getWidth(), getHeight());
                    System.err.println("Advertencia: No se pudo cargar la imagen de fondo: " + RUTA_FONDO);
                }
            }
        };
        panelPrincipal.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
        setContentPane(panelPrincipal);

        inicializarPanelesAtaques();
        inicializarPanelesSeleccion();
        inicializarComponentesEntrenador1();
        inicializarComponentesEntrenador2();
        inicializarInfoTurno();

        panelPrincipal.add(panelInfoPokemon1);
        panelPrincipal.add(lblImagenPokemon1);
        panelPrincipal.add(panelInfoPokemon2);
        panelPrincipal.add(lblImagenPokemon2);
        panelPrincipal.add(panelAtaques1);
        panelPrincipal.add(panelAtaques2);
        panelPrincipal.add(panelSeleccionPokemon1);
        panelPrincipal.add(panelSeleccionPokemon2);
        panelPrincipal.add(lblTurnoInfo);

        actualizarInfoPokemon(1, pokemonActivo1);
        actualizarInfoPokemon(2, pokemonActivo2);

        determinarPrimerTurno();
        configurarTurno();

        setVisible(true);
    }

    // Metodos de Inicialización 

    private void determinarPrimerTurno() {
        if (pokemonActivo1.getVelocidad() > pokemonActivo2.getVelocidad()) {
            turnoEntrenador1 = true;
        } else if (pokemonActivo2.getVelocidad() > pokemonActivo1.getVelocidad()) {
            turnoEntrenador1 = false;
        } else {
            turnoEntrenador1 = Math.random() < 0.5;
        }
        agregarMensaje("¡" + (turnoEntrenador1 ? entrenador1.getNombre() : entrenador2.getNombre()) + " comienza!");
    }


    private Pokemon seleccionarPokemonInicial(Entrenador entrenador, ArrayList<Pokemon> equipo, int numEntrenador) {
        if (equipo == null || equipo.isEmpty()) {
            System.err.println("Error: El equipo del Entrenador " + numEntrenador + " está vacío o es nulo.");
            return null;
        }

        // Se filtrar solo Pokémon válidos y vivos 
    
        ArrayList<Pokemon> opcionesValidas = new ArrayList<>();
        for (Pokemon p : equipo) {
            if (p != null && p.getVivo()) {
                opcionesValidas.add(p);
            } else if (p == null) {
                System.err.println("Advertencia: Pokémon nulo encontrado en el equipo inicial del Entrenador " + numEntrenador);
            }
        }


        if (opcionesValidas.isEmpty()) {
             System.err.println("Error: No hay Pokémon válidos/vivos para seleccionar para el Entrenador " + numEntrenador);
             return null;
        }

        String[] nombresPokemon = new String[opcionesValidas.size()];
        for (int i = 0; i < opcionesValidas.size(); i++) {
            Pokemon p = opcionesValidas.get(i);
            // No necesitamos verificar p != null aquí porque ya filtramos arriba 
             nombresPokemon[i] = p.getNombre() + " (Nv." + p.getNivel() + ")";
        } // compleja esta parte, la de la vida maxima, si esta vivo o no por lo de que tengo que saber si lo hacia de una forma simple no funcionaba. 


        JComboBox<String> comboBox = new JComboBox<>(nombresPokemon);
        // Aplicar fuente al ComboBox si se desea
        comboBox.setFont(FONT_MONO_PLAIN_15);

        int result = JOptionPane.showConfirmDialog(this, comboBox,
                "Entrenador " + entrenador.getNombre() + ", elige tu primer Pokémon:",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int selectedIndex = comboBox.getSelectedIndex();
            // Validar índice contra la lista de opciones válidas
            if (selectedIndex >= 0 && selectedIndex < opcionesValidas.size()) {
                 Pokemon elegido = opcionesValidas.get(selectedIndex);
                 // No necesitamos verificar elegido != null porque ya filtramos
                 System.out.println("Entrenador " + numEntrenador + " ("+entrenador.getNombre()+") elige a: " + elegido.getNombre());
                 return elegido;

            } else {
                System.err.println("Error: Índice de ComboBox inválido: " + selectedIndex);
                return null;
            }
        }
        System.out.println("Selección cancelada por Entrenador " + numEntrenador + " ("+entrenador.getNombre()+")");
        return null;
    }


    private void inicializarPanelesAtaques() {
        int yAtaques = ALTO_VENTANA - ALTO_BOTON_PANEL - 50;
        int xAtaques1 = MARGEN_LATERAL;
        int xAtaques2 = ANCHO_VENTANA - ANCHO_BOTON_PANEL - MARGEN_LATERAL;

        panelAtaques1 = new JPanel(new GridLayout(2, 2, 6, 6));
        panelAtaques1.setBounds(xAtaques1, yAtaques, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelAtaques1.setOpaque(false);
        for (int i = 0; i < 4; i++) {
            botonesAtaque1[i] = crearBotonAtaque("Ataque " + (i + 1)); // Texto temporal de los ataques 1
            final int index = i;
            botonesAtaque1[i].addActionListener(e -> ejecutarAtaque(1, index));
            panelAtaques1.add(botonesAtaque1[i]);
        }
        panelAtaques2 = new JPanel(new GridLayout(2, 2, 6, 6));
        panelAtaques2.setBounds(xAtaques2, yAtaques, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelAtaques2.setOpaque(false);
        for (int i = 0; i < 4; i++) {
            botonesAtaque2[i] = crearBotonAtaque("Ataque " + (i + 1)); // Texto temporal de los ataques 2
            final int index = i;
            botonesAtaque2[i].addActionListener(e -> ejecutarAtaque(2, index));
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
        panelSeleccionPokemon1.setBackground(new Color(0, 0, 50, 200));
        panelSeleccionPokemon1.setVisible(false);

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
        panelSeleccionPokemon2.setBackground(new Color(50, 0, 0, 200));
        panelSeleccionPokemon2.setVisible(false);

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

        panelInfoPokemon1 = new JPanel(null);
        panelInfoPokemon1.setBounds(xBase, yInfo, ANCHO_INFO_PKM, ALTO_INFO_PKM_BLOQUE);
        panelInfoPokemon1.setOpaque(false);

        lblNombrePokemon1 = new JLabel("PKM1", SwingConstants.CENTER);
        lblNombrePokemon1.setFont(FONT_MONO_BOLD_20); 
        lblNombrePokemon1.setForeground(COLOR_TEXTO_INFO);
        lblNombrePokemon1.setBounds(0, 0, ANCHO_INFO_PKM, 30); 
        panelInfoPokemon1.add(lblNombrePokemon1);

        pbVidaPokemon1 = new JProgressBar(0, 100);
        pbVidaPokemon1.setValue(100);
        pbVidaPokemon1.setStringPainted(false);
        pbVidaPokemon1.setForeground(Color.GREEN);
        pbVidaPokemon1.setBackground(Color.DARK_GRAY);
        pbVidaPokemon1.setBorder(new LineBorder(Color.GRAY));
        pbVidaPokemon1.setBounds(10, 35, ANCHO_INFO_PKM - 20, 18); // Posición bajo nombre
        panelInfoPokemon1.add(pbVidaPokemon1);

        lblVidaTextoPokemon1 = new JLabel("HP: 100 / 100", SwingConstants.CENTER);
        lblVidaTextoPokemon1.setFont(FONT_MONO_PLAIN_15);
        lblVidaTextoPokemon1.setForeground(COLOR_TEXTO_INFO);
        lblVidaTextoPokemon1.setBounds(0, 55, ANCHO_INFO_PKM, 25); // Posición bajo barra
        panelInfoPokemon1.add(lblVidaTextoPokemon1);

        lblImagenPokemon1 = new JLabel();
        lblImagenPokemon1.setBounds(xBase + (ANCHO_INFO_PKM / 2) - (ANCHO_IMAGEN_PKM / 2),
                                   yImagen, ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM);
        lblImagenPokemon1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPokemon1.setBorder(BorderFactory.createEmptyBorder());
    }

    private void inicializarComponentesEntrenador2() {
        int xBase = ANCHO_VENTANA - MARGEN_LATERAL - ANCHO_INFO_PKM;
        int yInfo = Y_POS_POKEMON_AREA - ALTO_INFO_PKM_BLOQUE - 10;
        int yImagen = Y_POS_POKEMON_AREA;

        panelInfoPokemon2 = new JPanel(null);
        panelInfoPokemon2.setBounds(xBase, yInfo, ANCHO_INFO_PKM, ALTO_INFO_PKM_BLOQUE);
        panelInfoPokemon2.setOpaque(false);

        lblNombrePokemon2 = new JLabel("PKM2", SwingConstants.CENTER);
        lblNombrePokemon2.setFont(FONT_MONO_BOLD_20); 
        lblNombrePokemon2.setForeground(COLOR_TEXTO_INFO);
        lblNombrePokemon2.setBounds(0, 0, ANCHO_INFO_PKM, 30); // Ajustar altura si es necesario
        panelInfoPokemon2.add(lblNombrePokemon2);

        pbVidaPokemon2 = new JProgressBar(0, 100);
        pbVidaPokemon2.setValue(100);
        pbVidaPokemon2.setStringPainted(false);
        pbVidaPokemon2.setForeground(Color.GREEN);
        pbVidaPokemon2.setBackground(Color.DARK_GRAY);
        pbVidaPokemon2.setBorder(new LineBorder(Color.GRAY));
        pbVidaPokemon2.setBounds(10, 35, ANCHO_INFO_PKM - 20, 18); // Posición bajo nombre
        panelInfoPokemon2.add(pbVidaPokemon2);

        lblVidaTextoPokemon2 = new JLabel("HP: 100 / 100", SwingConstants.CENTER);
        lblVidaTextoPokemon2.setFont(FONT_MONO_PLAIN_15); 
        lblVidaTextoPokemon2.setForeground(COLOR_TEXTO_INFO);
        lblVidaTextoPokemon2.setBounds(0, 55, ANCHO_INFO_PKM, 25); // Posición bajo barra
        panelInfoPokemon2.add(lblVidaTextoPokemon2);

        lblImagenPokemon2 = new JLabel();
        lblImagenPokemon2.setBounds(xBase + (ANCHO_INFO_PKM / 2) - (ANCHO_IMAGEN_PKM / 2),
                                   yImagen, ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM);
        lblImagenPokemon2.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPokemon2.setBorder(BorderFactory.createEmptyBorder());
    }

    private void inicializarInfoTurno() {
        lblTurnoInfo = new JLabel(" ", SwingConstants.CENTER);
        lblTurnoInfo.setFont(FONT_MONO_BOLD_20); 
        lblTurnoInfo.setForeground(Color.YELLOW);
        int yTurno = ALTO_VENTANA - ALTO_BOTON_PANEL - 50 - 35; // Ajustar Y si la fuente es más alta para que no se vea tan pegado
        lblTurnoInfo.setBounds(0, yTurno, ANCHO_VENTANA, 30);
        lblTurnoInfo.setOpaque(false);
    }


    private void actualizarInfoPokemon(int entrenadorIndex, Pokemon nuevoPokemon) {
    
        // Asignar el nuevo pokémon activo al estado
        if (entrenadorIndex == 1) {
             pokemonActivo1 = nuevoPokemon;
        } else {
             pokemonActivo2 = nuevoPokemon;
        }

        String nombre = nuevoPokemon.getNombre() != null ? nuevoPokemon.getNombre() : "[Sin Nombre]";
        int nivel = nuevoPokemon.getNivel();
        int vidaMax = calcularVidaMaximaAproximada(nuevoPokemon); 
        float vidaActualFloat = nuevoPokemon.getHp();
        int vidaActual = (int) vidaActualFloat;

        String rutaImagenEspecifica = (entrenadorIndex == 1) ? RUTA_IMG_PKM1 : RUTA_IMG_PKM2;
        Image imagen = cargarImagen(rutaImagenEspecifica);

        JLabel lblImagenTarget = (entrenadorIndex == 1) ? lblImagenPokemon1 : lblImagenPokemon2;
        JLabel lblNombreTarget = (entrenadorIndex == 1) ? lblNombrePokemon1 : lblNombrePokemon2;// Nombre
        JProgressBar pbVidaTarget = (entrenadorIndex == 1) ? pbVidaPokemon1 : pbVidaPokemon2; 

        lblNombreTarget.setText(nombre + " Nv." + nivel);
        pbVidaTarget.setMaximum(vidaMax); // <-- Actualizar el máximo de la barra
        actualizarVidaPokemonUI(entrenadorIndex, vidaActual, vidaMax); // Actualiza valor y texto
        actualizarBotonesAtaque(entrenadorIndex, nuevoPokemon.getAtaques()); // Actualiza botones de ataque
         Image imagenRedimensionada = imagen.getScaledInstance(ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM, Image.SCALE_SMOOTH);
         lblImagenTarget.setIcon(new ImageIcon(imagenRedimensionada));//Imagen se redimensiona
         lblImagenTarget.setText("");
        
    }
    
    private void actualizarVidaPokemonUI(int entrenadorIndex, int vidaActual, int vidaMaxima) {
        vidaActual = Math.max(0, vidaActual);
        vidaMaxima = Math.max(1, vidaMaxima);
        JProgressBar pb = (entrenadorIndex == 1) ? pbVidaPokemon1 : pbVidaPokemon2;
        JLabel lblTexto = (entrenadorIndex == 1) ? lblVidaTextoPokemon1 : lblVidaTextoPokemon2;

        pb.setMaximum(vidaMaxima); // Asegurar que el máximo esté correcto y en caso de no poción
        pb.setValue(vidaActual);
        lblTexto.setText("HP: " + vidaActual + " / " + vidaMaxima);

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
             for (JButton boton : botones) {
                 if (boton != null) {
                     boton.setText("-"); //cambia de estado el botón para que no se pueda usar
                     boton.setEnabled(false);
                 }
             }
             return;
        }

        for (int i = 0; i < botones.length; i++) {
             JButton botonActual = botones[i];
             if (botonActual == null) continue;

            if (i < ataques.size() && ataques.get(i) != null && ataques.get(i).getNombre() != null) {
                botonActual.setText(ataques.get(i).getNombre());
                botonActual.setEnabled(true);
            } else {
                botonActual.setText("-");
                botonActual.setEnabled(false);
                if (i < ataques.size() && (ataques.get(i) == null || ataques.get(i).getNombre() == null)) {
                     System.err.println("Advertencia: Ataque en índice " + i + " para Entrenador " + entrenadorIndex + " es null o tiene nombre nulo.");
                }
            }
        }
    }

    private void ejecutarAtaque(int indexAtacante, int indexAtaque) {
        if (seleccionPendiente || (indexAtacante == 1 && !turnoEntrenador1) || (indexAtacante == 2 && turnoEntrenador1)) {
            agregarMensaje("¡Espera tu turno!");
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        Pokemon atacante = (indexAtacante == 1) ? pokemonActivo1 : pokemonActivo2;
        Pokemon defensor = (indexAtacante == 1) ? pokemonActivo2 : pokemonActivo1;
        Entrenador entrenadorAtacante = (indexAtacante == 1) ? entrenador1 : entrenador2;
        int indexDefensor = (indexAtacante == 1) ? 2 : 1;

        if (atacante == null || defensor == null) {
             agregarMensaje("Error crítico: Pokémon activo es null.");
             finalizarBatalla(indexAtacante == 1 ? 2 : 1);
             return;
        }

        Ataque ataqueSeleccionado = null;
        ArrayList<Ataque> listaAtaques = atacante.getAtaques(); // Lista de ataques del Pokémon atacante
        if (listaAtaques != null && indexAtaque >= 0 && indexAtaque < listaAtaques.size()) {
             ataqueSeleccionado = listaAtaques.get(indexAtaque);
        }

        if (ataqueSeleccionado == null || ataqueSeleccionado.getNombre() == null) {
            agregarMensaje("Error: Ataque no válido seleccionado.");
            System.err.println("Error: Intento de usar ataque null o inválido en índice " + indexAtaque + " por Entrenador " + indexAtacante);
            return;
        }

        agregarMensaje(entrenadorAtacante.getNombre() + ": ¡" + atacante.getNombre() + ", usa " + ataqueSeleccionado.getNombre() + "!");

        float vidaAntes = defensor.getHp();
        atacante.atacar(ataqueSeleccionado, defensor); 
        float vidaDespues = defensor.getHp();
        int danioInfligido = (int) Math.max(0, vidaAntes - vidaDespues);

        agregarMensaje("¡" + defensor.getNombre() + " recibió " + danioInfligido + " de daño!");

        actualizarVidaPokemonUI(indexDefensor, (int) defensor.getHp(), calcularVidaMaximaAproximada(defensor));

        verificarDebilitado(indexDefensor); 

         if (!seleccionPendiente) { // Solo cambia turno si no hay que elegir pokémon
            cambiarTurno();
        }
    }
    private void verificarDebilitado(int indexPokemonVerificar) {
        Pokemon pokemonVerificar = (indexPokemonVerificar == 1) ? pokemonActivo1 : pokemonActivo2;
        ArrayList<Pokemon> equipoRestante = (indexPokemonVerificar == 1) ? equipoRestante1 : equipoRestante2;
        Entrenador entrenador = (indexPokemonVerificar == 1) ? entrenador1 : entrenador2; 

        if (pokemonVerificar == null) {
             System.err.println("Error en verificarDebilitado: El Pokémon a verificar es null para índice " + indexPokemonVerificar);
             return;
        }

        if (!pokemonVerificar.getVivo()) { //  Mira si está vivo
            
            System.out.println("DEBUG: " + pokemonVerificar.getNombre() + " detectado como NO vivo. vivo=" + pokemonVerificar.getVivo());
            agregarMensaje("¡" + pokemonVerificar.getNombre() + " se ha debilitado!");

            boolean removido = equipoRestante.remove(pokemonVerificar); // Lo quita de la lista de disponibles
            if (!removido) {
                 System.err.println("Advertencia: No se pudo remover a " + pokemonVerificar.getNombre() + " del equipo restante de " + entrenador.getNombre() + ".");
            }

            // Comprueba si quedan otros Pokémon VIVOS en la lista restante
            boolean tieneMasPokemon = false;
            if (equipoRestante != null) {
                 for (Pokemon p : equipoRestante) {
                      if (p != null && p.getVivo()) { //  Busca al menos uno vivo
                          tieneMasPokemon = true;
                          break;
                      }
                 }
            }

            if (!tieneMasPokemon) { // Si no quedan vivos
                finalizarBatalla(indexPokemonVerificar == 1 ? 2 : 1); // Finaliza
            } else { // Si quedan vivos
                // Crea la lista de opciones SOLO con los vivos
                ArrayList<Pokemon> opcionesVivas = new ArrayList<>();
                if (equipoRestante != null) {
                    for (Pokemon p : equipoRestante) {
                        if (p != null && p.getVivo()) { 
                            opcionesVivas.add(p);
                        }
                    }
                }

                // Comprueba si por alguna razón la lista filtrada quedó vacía
                if (opcionesVivas.isEmpty()) {
                     System.err.println("Error: No quedan Pokémon vivos para seleccionar, pero se detectó que sí había. Finalizando.");
                     finalizarBatalla(indexPokemonVerificar == 1 ? 2 : 1);
                } else {
                     System.out.println("DEBUG: Opciones Vivas a mostrar para " + entrenador.getNombre() + ":");
                     for(Pokemon p : opcionesVivas) {
                          System.out.println("  - " + p.getNombre() + " (vivo=" + p.getVivo() + ")");
                     }
                     // Muestra el panel pasando SOLO las opciones vivas
                     seleccionPendiente = true;
                     entrenadorSeleccionando = indexPokemonVerificar;
                     mostrarPanelSeleccion(entrenadorSeleccionando, pokemonVerificar.getNombre(), opcionesVivas); 
                     configurarTurno();
                }
            }
        } else {
             System.out.println("DEBUG: " + pokemonVerificar.getNombre() + " sigue vivo. vivo=" + pokemonVerificar.getVivo());
        }
        // Si el pokemonVerificar.getVivo() era veradero-, no hace nada más aquí.
    }


     private void mostrarPanelSeleccion(int indexEntrenador, String pkmDebilitado, ArrayList<Pokemon> opcionesVivas) {
         if (opcionesVivas == null || opcionesVivas.isEmpty()) {
              System.err.println("Error crítico en mostrarPanelSeleccion: No hay opciones vivas para mostrar.");
              finalizarBatalla(indexEntrenador == 1 ? 2 : 1);
              return;
         }

        JPanel panelSeleccion = (indexEntrenador == 1) ? panelSeleccionPokemon1 : panelSeleccionPokemon2;
        JPanel panelAtaquesOcultar = (indexEntrenador == 1) ? panelAtaques1 : panelAtaques2;
        JLabel lblInfo = (indexEntrenador == 1) ? lblSeleccionInfo1 : lblSeleccionInfo2;
        JButton btnOp1 = (indexEntrenador == 1) ? btnSeleccionarPokemon1_Op1 : btnSeleccionarPokemon2_Op1;
        JButton btnOp2 = (indexEntrenador == 1) ? btnSeleccionarPokemon1_Op2 : btnSeleccionarPokemon2_Op2;

         if (panelSeleccion == null || panelAtaquesOcultar == null || lblInfo == null || btnOp1 == null || btnOp2 == null) {
              System.err.println("Error crítico: Componente GUI nulo en mostrarPanelSeleccion.");
              return;
         }

        lblInfo.setText(pkmDebilitado + " debilitado. Elige:"); // Texto más corto

        for (ActionListener al : btnOp1.getActionListeners()) { btnOp1.removeActionListener(al); }
        for (ActionListener al : btnOp2.getActionListeners()) { btnOp2.removeActionListener(al); }

         Pokemon opcion1 = opcionesVivas.get(0);
         if (opcion1 != null) {
             btnOp1.setText(opcion1.getNombre() + " Nv." + opcion1.getNivel());
             btnOp1.setVisible(true);
             btnOp1.setEnabled(true);
             btnOp1.addActionListener(e -> seleccionarNuevoPokemon(indexEntrenador, opcion1));
         } else {
              System.err.println("Error: Opción 1 de Pokémon para seleccionar es null.");
              btnOp1.setText("[Error]");
              btnOp1.setEnabled(false);
              btnOp1.setVisible(true);
         }

         if (opcionesVivas.size() > 1) {
             Pokemon opcion2 = opcionesVivas.get(1); // opciones vivas ya filtradas
             if (opcion2 != null) {
                 btnOp2.setText(opcion2.getNombre() + " Nv." + opcion2.getNivel());
                 btnOp2.setVisible(true);
                 btnOp2.setEnabled(true);
                 btnOp2.addActionListener(e -> seleccionarNuevoPokemon(indexEntrenador, opcion2));
             } else {
                 System.err.println("Error: Opción 2 de Pokémon para seleccionar es null.");
                 btnOp2.setText("[Error]");
                 btnOp2.setVisible(true);
                 btnOp2.setEnabled(false);
             }
         } else {
             btnOp2.setVisible(false);
             btnOp2.setEnabled(false);
         }

         panelAtaquesOcultar.setVisible(false);
         panelSeleccion.setVisible(true);
         seleccionPendiente = true;
         entrenadorSeleccionando = indexEntrenador;
    }

     private void seleccionarNuevoPokemon(int indexEntrenador, Pokemon nuevoPokemon) {
         if (nuevoPokemon == null) {
             System.err.println("Error crítico: Se intentó seleccionar un Pokémon null.");
             agregarMensaje("Error al seleccionar. Intenta de nuevo.");
             return;
         }

         agregarMensaje("¡" + (indexEntrenador == 1 ? entrenador1.getNombre() : entrenador2.getNombre()) +
                       " envía a " + nuevoPokemon.getNombre() + "!");

         actualizarInfoPokemon(indexEntrenador, nuevoPokemon); // Actualiza la GUI con el nuevo

         JPanel panelSeleccion = (indexEntrenador == 1) ? panelSeleccionPokemon1 : panelSeleccionPokemon2;
         if (panelSeleccion != null) {
            panelSeleccion.setVisible(false); // Oculta el panel de selección
         }

         seleccionPendiente = false; 
         entrenadorSeleccionando = 0;
         configurarTurno();
     }


    private void cambiarTurno() {
        if (!seleccionPendiente) {
            turnoEntrenador1 = !turnoEntrenador1;
            configurarTurno();
        } else {
            System.out.println("Cambio de turno lógico bloqueado: Selección pendiente por Entrenador " + entrenadorSeleccionando);
            // Si hay selección pendiente, no cambia el turno
            configurarTurno();
        }
    }

    private void configurarTurno() {
        boolean puedeAtacar1 = turnoEntrenador1 && !seleccionPendiente;
        boolean puedeAtacar2 = !turnoEntrenador1 && !seleccionPendiente; // Visibilidad paneles ataque: Muestra el del jugador activo o el del oponente si el otro elige
        if (panelAtaques1 != null) panelAtaques1.setVisible(turnoEntrenador1 || (seleccionPendiente && entrenadorSeleccionando == 2));
        if (panelAtaques2 != null) panelAtaques2.setVisible(!turnoEntrenador1 || (seleccionPendiente && entrenadorSeleccionando == 1));
         configurarEstadoBotones(botonesAtaque1, puedeAtacar1, pokemonActivo1);
         configurarEstadoBotones(botonesAtaque2, puedeAtacar2, pokemonActivo2);

        if (panelSeleccionPokemon1 != null) panelSeleccionPokemon1.setVisible(seleccionPendiente && entrenadorSeleccionando == 1);
        if (panelSeleccionPokemon2 != null) panelSeleccionPokemon2.setVisible(seleccionPendiente && entrenadorSeleccionando == 2);

       
        if (lblTurnoInfo != null) {
             if (seleccionPendiente) {
                 String nombreSeleccionador = (entrenadorSeleccionando == 1 && entrenador1 != null) ? entrenador1.getNombre() :
                                             ((entrenadorSeleccionando == 2 && entrenador2 != null) ? entrenador2.getNombre() : "Entrenador " + entrenadorSeleccionando);
                 lblTurnoInfo.setText("¡" + nombreSeleccionador + " elige!");
                 lblTurnoInfo.setForeground(Color.YELLOW);
             } else if (turnoEntrenador1) {
                 lblTurnoInfo.setText("Turno: " + (entrenador1 != null ? entrenador1.getNombre() : "E1"));
                 lblTurnoInfo.setForeground(Color.CYAN);
             } else {
                 lblTurnoInfo.setText("Turno: " + (entrenador2 != null ? entrenador2.getNombre() : "E2"));
                 lblTurnoInfo.setForeground(Color.ORANGE);
             }
        }

         // refresh panel principal
         if (panelPrincipal != null) {
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
         }
    }

    // Método auxiliar para habilitar/deshabilitar botones de ataque
    private void configurarEstadoBotones(JButton[] botones, boolean puedeAtacar, Pokemon pokemonActivo) {
         for (int i = 0; i < botones.length; i++) {
            if (botones[i] != null) {
                 boolean ataqueValido = pokemonActivo != null &&
                                        pokemonActivo.getAtaques() != null &&
                                        i < pokemonActivo.getAtaques().size() &&
                                        pokemonActivo.getAtaques().get(i) != null;
                 botones[i].setEnabled(puedeAtacar && ataqueValido);
            }
        }
    }

    private void finalizarBatalla(int indexGanador) {
        seleccionPendiente = true; // Bloquea más acciones
        entrenadorSeleccionando = 0;

        Entrenador ganador = (indexGanador == 1) ? entrenador1 : entrenador2;
        Entrenador perdedor = (indexGanador == 1) ? entrenador2 : entrenador1;

        String nombreGanador = (ganador != null) ? ganador.getNombre() : "Entrenador " + indexGanador;
        String nombrePerdedor = (perdedor != null) ? perdedor.getNombre() : "El oponente";

        agregarMensaje("¡" + nombrePerdedor + " no tiene más Pokémon!");
        agregarMensaje("¡Ganador: " + nombreGanador + "!");

         // Deshabilitar todos los botones
         deshabilitarTodosLosBotones();

         if (lblTurnoInfo != null) {
            lblTurnoInfo.setText("¡VICTORIA: " + nombreGanador.toUpperCase() + "!");
            lblTurnoInfo.setForeground(Color.GREEN);
         }

         JOptionPane.showMessageDialog(this,
             "¡Fin de la batalla!\nGanador: " + nombreGanador,
             "Fin de la Batalla",
             JOptionPane.INFORMATION_MESSAGE);
    }

    // Método auxiliar para deshabilitar todos los botones al final, parece bonito y mas limpio
    private void deshabilitarTodosLosBotones() {
         for(JButton btn : botonesAtaque1) if (btn != null) btn.setEnabled(false);
         for(JButton btn : botonesAtaque2) if (btn != null) btn.setEnabled(false);
         if (btnSeleccionarPokemon1_Op1 != null) btnSeleccionarPokemon1_Op1.setEnabled(false);
         if (btnSeleccionarPokemon1_Op2 != null) btnSeleccionarPokemon1_Op2.setEnabled(false);
         if (btnSeleccionarPokemon2_Op1 != null) btnSeleccionarPokemon2_Op1.setEnabled(false);
         if (btnSeleccionarPokemon2_Op2 != null) btnSeleccionarPokemon2_Op2.setEnabled(false);

         if (panelSeleccionPokemon1 != null) panelSeleccionPokemon1.setVisible(false);
         if (panelSeleccionPokemon2 != null) panelSeleccionPokemon2.setVisible(false);
    }

    private JButton crearBotonAtaque(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FONT_MONO_PLAIN_15); 
        boton.setFocusPainted(false);
        boton.setBackground(new Color(50, 50, 70));
        boton.setForeground(Color.WHITE);
        boton.setBorder(new LineBorder(Color.GRAY));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (boton.isEnabled()) boton.setBackground(new Color(80, 80, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                 if (boton.isEnabled()) boton.setBackground(new Color(50, 50, 70));
            }
        });
        return boton;
    }

     private JButton crearBotonSeleccion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FONT_MONO_PLAIN_15); 
        boton.setFocusPainted(false);
        boton.setBackground(new Color(70, 70, 90));
        boton.setForeground(Color.WHITE);
        boton.setBorder(new LineBorder(Color.CYAN));
        boton.setPreferredSize(new Dimension(150, 40));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { // Cambia el color al pasar el mouse
                 if (boton.isEnabled()) boton.setBackground(new Color(100, 100, 120));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                  if (boton.isEnabled()) boton.setBackground(new Color(70, 70, 90));
            }
        });
        return boton;
    }

    private void agregarMensaje(String mensaje) {
        System.out.println("[Batalla Pokemon] " + mensaje);
    }

    private Image cargarImagen(String ruta) {
        File archivoImagen = new File(ruta);
        if (!archivoImagen.exists() || !archivoImagen.isFile()) {
             System.err.println("Advertencia: Imagen no encontrada o inválida: " + ruta);
             return null;
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(archivoImagen);
             if (img == null) {
                 System.err.println("Advertencia: Formato no soportado o archivo corrupto: " + ruta);
                 return null;
             }
        } catch (java.io.IOException e) {
            System.err.println("Error de E/S leyendo imagen: " + ruta + " - " + e.getMessage());
            // e.printStackTrace(); // Descomentar para más detalle si hay errores de imagen
            return null;
        } catch (Exception e) {
             System.err.println("Error inesperado cargando imagen: " + ruta + " - " + e.getMessage());
             // e.printStackTrace(); // Descomentar para más detalle
             return null;
        }
        return img;
    }

    private int calcularVidaMaximaAproximada(Pokemon p) {
    
        if (p == null) return 100; // Valor por defecto
        int vidaBase = 50;
        int vidaPorNivel = 5;
        return vidaBase + (p.getNivel() * vidaPorNivel);
    } //esta parte de abajo se peude borrar si ya esta en el archivo Main
    public static void main(String[] args) {

         final Entrenador testE1;
         final Entrenador testE2;
         try {
             testE1 = Entrenador.capturarEntrenador("Ash", "Pikachu", "Bulbasaur", "Squirtle");
             testE2 = Entrenador.capturarEntrenador("Gary", "Eevee", "Charmander", "Rattata");
         } catch (Exception e) {
             System.err.println("Error al crear entrenadores o Pokémon de prueba en main: " + e.getMessage());
             e.printStackTrace();
             JOptionPane.showMessageDialog(null, "Error creando los datos de prueba.\nAsegúrate que los Pokémon existen y revisa la consola.", "Error de Prueba", JOptionPane.ERROR_MESSAGE);
             return;
         }
        SwingUtilities.invokeLater(() -> new WindowBatalla (testE1, testE2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
} 