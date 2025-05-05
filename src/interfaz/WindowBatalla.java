import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class WindowBatalla extends JFrame implements ActionListener {

    // --- Constantes de UI (Sin cambios) ---
    private static final int ANCHO_VENTANA = 800;
    private static final int ALTO_VENTANA = 800;
    private static final int ANCHO_IMAGEN_PKM = 200;
    private static final int ALTO_IMAGEN_PKM = 200;
    private static final int ANCHO_INFO_PKM = 250;
    // Altura calculada para el bloque de info (Nombre + Barra + Texto HP + Espaciado)
    private static final int ALTO_INFO_PKM_BLOQUE = 30 + 18 + 25 + 10; // Ajustado para precisión
    private static final int ANCHO_BOTON_PANEL = 340;
    private static final int ALTO_BOTON_PANEL = 100;
    private static final int Y_POS_POKEMON_AREA = (int) (ALTO_VENTANA * 0.30); // Posición Y base para imágenes e info
    private static final int MARGEN_LATERAL = 60;
    private static final Font FONT_MONO_BOLD_20 = new Font("Monospaced", Font.BOLD, 20);
    private static final Font FONT_MONO_PLAIN_15 = new Font("Monospaced", Font.PLAIN, 15);
    private static final Color COLOR_TEXTO_INFO = Color.WHITE;

    private static final String RUTA_IMAGENES = "image/";
    private static final String RUTA_FONDO = RUTA_IMAGENES + "Estadio.jpg";
    // Rutas de imágenes de ejemplo (Mantener o ajustar según tus archivos)
    private static final String[] RUTAS_PKM1 = {  RUTA_IMAGENES + "PE1.png", RUTA_IMAGENES + "PP1.png", RUTA_IMAGENES + "PF1.png", RUTA_IMAGENES + "PA1.png", RUTA_IMAGENES + "PT1.png" };
    private static final String[] RUTAS_PKM2 = {  RUTA_IMAGENES + "PE2.png", RUTA_IMAGENES + "PP2.png", RUTA_IMAGENES + "PF2.png", RUTA_IMAGENES + "PA2.png", RUTA_IMAGENES + "PT2.png" };
    private static final Random random = new Random(); // Random para selección inicial de imagen y empates de velocidad
    // Se seleccionan rutas aleatorias UNA VEZ al inicio para cada slot de imagen
    private static final String RUTA_IMG_PKM1 = obtenerrutaaleatoria(RUTAS_PKM1);
    private static final String RUTA_IMG_PKM2 = obtenerrutaaleatoria(RUTAS_PKM2);

    // --- Componentes UI (Sin cambios) ---
    private JPanel panelPrincipal;
    private JLabel lblImagenPokemon1, lblNombrePokemon1, lblVidaTextoPokemon1;
    private JProgressBar pbVidaPokemon1;
    private JPanel panelInfoPokemon1; // Panel para agrupar info de Pkm1
    private JLabel lblImagenPokemon2, lblNombrePokemon2, lblVidaTextoPokemon2;
    private JProgressBar pbVidaPokemon2;
    private JPanel panelInfoPokemon2; // Panel para agrupar info de Pkm2
    private JPanel panelAtaques1, panelAtaques2; // Paneles que contienen los botones de ataque
    private JPanel panelSeleccionPokemon1, panelSeleccionPokemon2; // Paneles para elegir nuevo Pokémon
    private JLabel lblSeleccionInfo1, lblSeleccionInfo2; // Texto en paneles de selección
    private JButton btnSeleccionarPokemon1_Op1, btnSeleccionarPokemon1_Op2; // Botones de selección Pkm1
    private JButton btnSeleccionarPokemon2_Op1, btnSeleccionarPokemon2_Op2; // Botones de selección Pkm2
    private JButton[] botonesAtaque1 = new JButton[4]; // Botones de ataque Pkm1
    private JButton[] botonesAtaque2 = new JButton[4]; // Botones de ataque Pkm2
    private JLabel lblTurnoInfo; // Label para mostrar de quién es el turno o quién selecciona

    // --- Estado de la Batalla (Lógica integrada aquí) ---
    private Entrenador entrenador1;
    private Entrenador entrenador2;
    private Pokemon pokemonActivo1; // Pokémon actualmente luchando por Entrenador 1
    private Pokemon pokemonActivo2; // Pokémon actualmente luchando por Entrenador 2
    private ArrayList<Pokemon> equipoRestante1; // Pokémon disponibles para Entrenador 1 (se modifica)
    private ArrayList<Pokemon> equipoRestante2; // Pokémon disponibles para Entrenador 2 (se modifica)
    private boolean turnoEntrenador1 = true; // Indica si es el turno del Entrenador 1
    private boolean seleccionPendiente = false; // Indica si se debe seleccionar un nuevo Pokémon
    private int entrenadorSeleccionando = 0; // Quién debe seleccionar (1 o 2), 0 si nadie
    private boolean batallaFinalizada = false; // Indica si la batalla ya terminó

    // --- Constructor (Sin cambios lógicos significativos) ---
    public WindowBatalla(Entrenador e1, Entrenador e2) {
        System.out.println("[Debug WB] Constructor WindowBatalla started.");

        // Guarda los entrenadores (con validación null)
        this.entrenador1 = Objects.requireNonNull(e1, "Entrenador 1 no puede ser null");
        this.entrenador2 = Objects.requireNonNull(e2, "Entrenador 2 no puede ser null");

        // Inicializa listas de equipo RESTANTE haciendo una COPIA del original
        // Esto es crucial para no modificar el equipo original del Entrenador fuera de la batalla
        this.equipoRestante1 = (e1.getEquipo() != null) ? new ArrayList<>(e1.getEquipo()) : new ArrayList<>();
        this.equipoRestante2 = (e2.getEquipo() != null) ? new ArrayList<>(e2.getEquipo()) : new ArrayList<>();

        // Configuración básica de la ventana
        setTitle("Batalla Pokémon: " + entrenador1.getNombre() + " vs " + entrenador2.getNombre());
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(false);

        // --- Creación de la Interfaz Gráfica (Sin cambios) ---
        // Carga imagen de fondo (maneja posible error)
        final Image imagenFondo = cargarImagen(RUTA_FONDO);
        panelPrincipal = new JPanel(null) { // Layout absoluto para posicionar manualmente
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(new Color(100, 100, 120)); // Fondo de respaldo si falla la imagen
                    g.fillRect(0, 0, getWidth(), getHeight());
                    System.err.println("Advertencia: No se pudo cargar la imagen de fondo: " + RUTA_FONDO);
                }
            }
        };
        panelPrincipal.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_VENTANA));
        setContentPane(panelPrincipal);

        // Inicializa TODOS los componentes gráficos (paneles, labels, botones, etc.)
        System.out.println("[Debug WB] Initializing UI components...");
        inicializarPanelesAtaques(); // Crea paneles y botones de ataque
        inicializarPanelesSeleccion(); // Crea paneles y botones de selección (ocultos inicio)
        inicializarComponentesEntrenador1(); // Crea labels, progressbar, etc. para Pkm1
        inicializarComponentesEntrenador2(); // Crea labels, progressbar, etc. para Pkm2
        inicializarInfoTurno(); // Crea el label de info de turno
        System.out.println("[Debug WB] UI components initialized.");

        // Añade los componentes al panel principal
        System.out.println("[Debug WB] Adding components to panel...");
        panelPrincipal.add(panelInfoPokemon1); // Panel con Nombre/Vida Pkm1
        panelPrincipal.add(lblImagenPokemon1); // Imagen Pkm1
        panelPrincipal.add(panelInfoPokemon2); // Panel con Nombre/Vida Pkm2
        panelPrincipal.add(lblImagenPokemon2); // Imagen Pkm2
        panelPrincipal.add(panelAtaques1); // Botones ataque Pkm1
        panelPrincipal.add(panelAtaques2); // Botones ataque Pkm2
        panelPrincipal.add(panelSeleccionPokemon1); // Panel selección Pkm1 (oculto)
        panelPrincipal.add(panelSeleccionPokemon2); // Panel selección Pkm2 (oculto)
        panelPrincipal.add(lblTurnoInfo); // Label de turno
        System.out.println("[Debug WB] Components added.");

        // NO llamamos a iniciarBatalla aquí. Se llamará externamente después de crear la ventana.
        System.out.println("[Debug WB] Constructor WindowBatalla finished.");
    }

    // --- Lógica Principal de la Batalla ---

    /**
     * Inicia la lógica de la batalla DESPUÉS de que la ventana es visible.
     * Selecciona los Pokémon iniciales, determina el primer turno y actualiza la UI.
     */
    public void iniciarBatalla() {
        System.out.println("[Debug WB] iniciarBatalla started.");
        if (batallaFinalizada) { // No iniciar si ya terminó
             System.out.println("[Debug WB] Batalla ya finalizada, no se inicia de nuevo.");
             return;
        }


        System.out.println("[Debug WB] Starting initial Pokemon selection...");
        // Selección interactiva de los primeros Pokémon
        this.pokemonActivo1 = seleccionarPokemonInicial(entrenador1, equipoRestante1, 1);
        this.pokemonActivo2 = seleccionarPokemonInicial(entrenador2, equipoRestante2, 2);

        // Validación crucial: si algún jugador no pudo elegir, la batalla no puede comenzar.
        if (pokemonActivo1 == null || pokemonActivo2 == null) {
            System.err.println("Error crítico: Selección inicial cancelada o fallida. No se puede iniciar la batalla.");
            JOptionPane.showMessageDialog(this,
                    "No se pudo iniciar la batalla.\nAsegúrate de que ambos entrenadores tengan equipos válidos\ny elijan un Pokémon inicial.",
                    "Error de Inicio", JOptionPane.ERROR_MESSAGE);
            batallaFinalizada = true; // Marcar como finalizada para evitar acciones
            deshabilitarTodosLosBotones(); // Deshabilitar controles
            this.dispose(); // Cierra la ventana de batalla
            return; // Termina el método aquí
        }
        System.out.println("[Debug WB] Pokémon iniciales seleccionados: " + pokemonActivo1.getNombre() + ", " + pokemonActivo2.getNombre());

        // Actualizar la interfaz gráfica con la información de los Pokémon seleccionados
        System.out.println("[Debug WB] Updating UI with initial Pokemon...");
        actualizarInfoPokemon(1, pokemonActivo1);
        actualizarInfoPokemon(2, pokemonActivo2);
        System.out.println("[Debug WB] UI updated.");

        // Determinar quién ataca primero basado en la velocidad
        System.out.println("[Debug WB] Determining first turn...");
        determinarPrimerTurno(); // Establece el valor de 'turnoEntrenador1'
        System.out.println("[Debug WB] First turn determined. Es turno de E1: " + turnoEntrenador1);

        // Configurar la interfaz para el primer turno (habilitar/deshabilitar botones, mostrar info)
        System.out.println("[Debug WB] Configuring turn UI...");
        configurarTurno();
        System.out.println("[Debug WB] Turn UI configured.");

        // Refrescar layout (buena práctica después de añadir/configurar componentes)
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
        System.out.println("[Debug WB] Panel revalidated and repainted.");
        System.out.println("[Debug WB] iniciarBatalla finished.");
    }

    /**
     * Determina qué Pokémon ataca primero comparando sus velocidades.
     * En caso de empate, se decide aleatoriamente. Actualiza 'turnoEntrenador1'.
     * ESTA LÓGICA REEMPLAZA A Batalla.ordenBatalla() para el inicio.
     */
    private void determinarPrimerTurno() {
        // Validación: asegurar que ambos Pokémon activos existen
        if (pokemonActivo1 == null || pokemonActivo2 == null) {
             System.err.println("Error crítico: No se puede determinar el primer turno sin Pokémon activos.");
             turnoEntrenador1 = true; // Asignar un valor por defecto o manejar el error de otra forma
             return;
        }

        // Comparar velocidades
        float velocidad1 = pokemonActivo1.getVelocidad(); // Asume getter existe
        float velocidad2 = pokemonActivo2.getVelocidad(); // Asume getter existe

        if (velocidad1 > velocidad2) {
            turnoEntrenador1 = true;
        } else if (velocidad2 > velocidad1) {
            turnoEntrenador1 = false;
        } else {
            // Empate en velocidad, decidir aleatoriamente
            turnoEntrenador1 = random.nextBoolean(); // 50% de probabilidad
            agregarMensaje("¡Empate en velocidad! Se decide aleatoriamente.");
        }

        // Mensaje informativo (opcional pero útil)
        agregarMensaje("¡" + (turnoEntrenador1 ? entrenador1.getNombre() : entrenador2.getNombre()) + " (" + (turnoEntrenador1 ? pokemonActivo1.getNombre() : pokemonActivo2.getNombre()) + ") comienza!");
    }

    /**
     * Ejecuta la acción de atacar cuando se presiona un botón de ataque.
     * Contiene la lógica central de un turno de ataque.
     * REEMPLAZA PARTES CLAVE DE Batalla.combate() y Batalla.atacarYComprobarEstado().
     * @param indexAtacante 1 si ataca el Entrenador 1, 2 si ataca el Entrenador 2.
     * @param indexAtaque El índice (0-3) del ataque seleccionado en el array de botones/ataques.
     */
    private void ejecutarAtaque(int indexAtacante, int indexAtaque) {
        System.out.println("[Debug WB] ejecutarAtaque llamado por E" + indexAtacante + ", Ataque index: " + indexAtaque);

        // --- Validaciones Previas ---
        // 1. No hacer nada si la batalla ya terminó
        if (batallaFinalizada) {
            System.out.println("[Debug WB] Ataque ignorado: Batalla finalizada.");
            return;
        }
        // 2. No ejecutar si hay una selección de Pokémon pendiente
        if (seleccionPendiente) {
            agregarMensaje("Espera a que " + (entrenadorSeleccionando == 1 ? entrenador1.getNombre() : entrenador2.getNombre()) + " seleccione su próximo Pokémon.");
            Toolkit.getDefaultToolkit().beep(); // Sonido de error leve
            return;
        }
        // 3. Verificar si es el turno correcto del atacante
        if ((indexAtacante == 1 && !turnoEntrenador1) || (indexAtacante == 2 && turnoEntrenador1)) {
            agregarMensaje("¡Espera tu turno!");
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        // --- Obtener Atacante, Defensor y Entrenadores ---
        Pokemon atacante = (indexAtacante == 1) ? pokemonActivo1 : pokemonActivo2;
        Pokemon defensor = (indexAtacante == 1) ? pokemonActivo2 : pokemonActivo1;
        Entrenador entrenadorAtacante = (indexAtacante == 1) ? entrenador1 : entrenador2;
        // Entrenador entrenadorDefensor = (indexAtacante == 1) ? entrenador2 : entrenador1; // No usado directamente aquí
        int indexDefensor = (indexAtacante == 1) ? 2 : 1;

        // 4. Verificar que los Pokémon activos existen (salvaguarda)
        if (atacante == null || defensor == null) {
             agregarMensaje("Error crítico: Pokémon activo es null. Finalizando batalla.");
              System.err.println("Error crítico en ejecutarAtaque: Atacante o Defensor es null. Atacante E" + indexAtacante + ", Defensor E" + indexDefensor);
             finalizarBatalla(indexDefensor); // Gana el oponente del que falta
             return;
        }

        // --- Obtener Ataque Seleccionado ---
        Ataque ataqueSeleccionado = null;
        ArrayList<Ataque> listaAtaques = atacante.getAtaques(); // Asume getter existe

        // 5. Verificar que la lista de ataques y el índice son válidos
        if (listaAtaques != null && indexAtaque >= 0 && indexAtaque < listaAtaques.size()) {
             ataqueSeleccionado = listaAtaques.get(indexAtaque);
             // Validar que el ataque obtenido no sea null o inválido
             if (ataqueSeleccionado == null || ataqueSeleccionado.getNombre() == null || ataqueSeleccionado.getNombre().isEmpty()) {
                 System.err.println("Error: Ataque obtenido en índice " + indexAtaque + " es null o inválido para " + atacante.getNombre());
                 ataqueSeleccionado = null; // Asegurar que es null si es inválido
             }
        } else {
            System.err.println("Error: Índice de ataque (" + indexAtaque + ") fuera de rango o lista de ataques null para " + atacante.getNombre());
        }

        // 6. Verificar que el ataque es válido antes de usarlo
        if (ataqueSeleccionado == null) {
            agregarMensaje("Error: Ataque no válido seleccionado.");
            System.err.println("Error en ejecutarAtaque: Intento de usar ataque null o inválido en índice " + indexAtaque + " por Entrenador " + indexAtacante);
            // No cambiar turno, permitir reintentar al mismo jugador si fue un error de datos
            // (Aunque si el botón estaba habilitado, no debería pasar)
            return;
        }

        // --- Ejecución del Ataque ---
        agregarMensaje(entrenadorAtacante.getNombre() + ": ¡" + atacante.getNombre() + ", usa " + ataqueSeleccionado.getNombre() + "!");

        // Guardar vida antes del ataque para calcular daño visualmente (opcional)
        float vidaAntes = defensor.getHp();

        // Llama al método del Pokémon que calcula y aplica el daño
        // ESTA ES LA LLAMADA CENTRAL que estaba en Batalla.atacarYComprobarEstado
        atacante.atacar(ataqueSeleccionado, defensor); // Modifica directamente el HP y estado 'vivo' del defensor

        // Calcular daño real infligido para mostrar mensaje
        float vidaDespues = defensor.getHp();
        int danioInfligido = (int) Math.max(0, vidaAntes - vidaDespues); // Asegurar no negativo

        // Mensaje de daño (puede ser más elaborado con efectividad, críticos, etc.)
        agregarMensaje("¡" + defensor.getNombre() + " recibió " + danioInfligido + " de daño!");

        // Actualizar la interfaz gráfica del defensor (barra de vida, texto HP)
        actualizarVidaPokemonUI(indexDefensor, (int) defensor.getHp(), calcularVidaMaximaAproximada(defensor));

        // Verificar si el Pokémon defensor se debilitó después del ataque
        // ¡Importante! Pasar el índice del POKEMON DEFENSOR
        verificarDebilitado(indexDefensor); // Este método puede cambiar 'seleccionPendiente'

        // Cambiar turno SOLO si la batalla no terminó Y no hay una selección pendiente
        if (!batallaFinalizada && !seleccionPendiente) {
            cambiarTurno();
        } else if (seleccionPendiente) {
             // Si hay selección pendiente, configurarTurno se llamará después de seleccionar,
             // pero actualizamos el estado visual AHORA para reflejar que se espera selección.
             System.out.println("[Debug WB] Selección pendiente detectada después del ataque. Configurando UI para selección.");
             configurarTurno();
        } else {
             // Si la batalla finalizó, no hacer nada más aquí.
             System.out.println("[Debug WB] Batalla finalizada detectada después del ataque. No se cambia turno.");
        }
         System.out.println("[Debug WB] Fin de ejecutarAtaque para E" + indexAtacante);
    }

    /**
     * Comprueba si el Pokémon del índice especificado se ha debilitado (HP <= 0).
     * Si se debilita, lo quita del equipo restante.
     * Si no quedan más Pokémon en ese equipo, finaliza la batalla.
     * Si quedan Pokémon, inicia el proceso de selección del siguiente.
     * REEMPLAZA LA LÓGICA DE COMPROBACIÓN DE ESTADO de Batalla.atacarYComprobarEstado().
     * @param indexPokemonVerificar 1 para el Pokémon del Entrenador 1, 2 para el del Entrenador 2.
     */
    private void verificarDebilitado(int indexPokemonVerificar) {
         System.out.println("[Debug WB] Verificando si Pokémon " + indexPokemonVerificar + " se debilitó.");
        Pokemon pokemonVerificar = (indexPokemonVerificar == 1) ? pokemonActivo1 : pokemonActivo2;
        ArrayList<Pokemon> equipoRestante = (indexPokemonVerificar == 1) ? equipoRestante1 : equipoRestante2;
        Entrenador entrenador = (indexPokemonVerificar == 1) ? entrenador1 : entrenador2;

        // Validación: ¿Existe el Pokémon a verificar?
        if (pokemonVerificar == null) {
             System.err.println("Error crítico en verificarDebilitado: El Pokémon a verificar es null para índice " + indexPokemonVerificar);
             // Si el Pokémon activo es null aquí, algo falló gravemente antes.
             // Finalizar dando la victoria al oponente.
             finalizarBatalla(indexPokemonVerificar == 1 ? 2 : 1);
             return;
        }

        // Comprobar si el Pokémon está vivo (basado en su estado interno, ej: getVivo() o hp <= 0)
        // Asumimos que pokemon.atacar() actualiza el estado 'vivo' correctamente.
        if (!pokemonVerificar.getVivo()) {
            System.out.println("[Debug WB] " + pokemonVerificar.getNombre() + " está debilitado (vivo=" + pokemonVerificar.getVivo() + ", hp=" + pokemonVerificar.getHp() + ")");
            agregarMensaje("¡" + pokemonVerificar.getNombre() + " se ha debilitado!");

            // ----- Gestión del equipo restante -----
            // Quitarlo de la lista de Pokémon *disponibles* para selección futura.
            // ¡Importante usar el objeto correcto!
            boolean removido = equipoRestante.remove(pokemonVerificar);
            if (!removido) {
                 // Esto podría pasar si ya fue removido o no estaba en la lista (error lógico?). Investigar si ocurre.
                 System.err.println("Advertencia: No se pudo remover a " + pokemonVerificar.getNombre() + " del equipo restante de " + entrenador.getNombre() + ". ¿Ya estaba fuera?");
            } else {
                 System.out.println("[Debug WB] " + pokemonVerificar.getNombre() + " removido de equipoRestante" + indexPokemonVerificar + ". Quedan: " + equipoRestante.size());
            }

            // ----- Comprobar si quedan más Pokémon VIVOS -----
            boolean tieneMasPokemonVivos = false;
            ArrayList<Pokemon> opcionesVivas = new ArrayList<>(); // Lista para guardar los vivos
            if (equipoRestante != null) {
                 System.out.println("[Debug WB] Verificando Pokémon vivos en equipo restante de " + entrenador.getNombre() + ":");
                 for (Pokemon p : equipoRestante) {
                      if (p != null && p.getVivo()) { // ¡Asegurarse de que contamos solo los VIVOS!
                          System.out.println("  - " + p.getNombre() + " (vivo=" + p.getVivo() + ") - VÁLIDO");
                          tieneMasPokemonVivos = true;
                          opcionesVivas.add(p); // Añadir a la lista de opciones para selección
                          // No hacemos break, recogemos todas las opciones vivas.
                      } else {
                           System.out.println("  - " + (p != null ? p.getNombre() : "NULL") + " (vivo=" + (p != null ? p.getVivo() : "N/A") + ") - IGNORADO (No vivo o null)");
                      }
                 }
            }

            // ----- Decidir qué hacer: Finalizar o Seleccionar -----
            if (!tieneMasPokemonVivos) {
                // Si NO quedan Pokémon vivos, finalizar la batalla.
                System.out.println("[Debug WB] No quedan Pokémon vivos para " + entrenador.getNombre() + ". Finalizando batalla.");
                finalizarBatalla(indexPokemonVerificar == 1 ? 2 : 1); // Gana el oponente (índice contrario)
            } else {
                // Si quedan Pokémon vivos, forzar al entrenador a seleccionar el siguiente.
                System.out.println("[Debug WB] Quedan " + opcionesVivas.size() + " Pokémon vivos para " + entrenador.getNombre() + ". Mostrando panel de selección.");

                // Marcar que se necesita seleccionar y quién debe hacerlo
                seleccionPendiente = true;
                entrenadorSeleccionando = indexPokemonVerificar;

                // Mostrar el panel de selección correspondiente CON LAS OPCIONES VIVAS
                mostrarPanelSeleccion(entrenadorSeleccionando, pokemonVerificar.getNombre(), opcionesVivas);

                // Actualizar la UI para reflejar el estado de selección (paneles, botones, texto)
                configurarTurno();
            }
        } else {
             // Si el Pokémon no se debilitó, no hacer nada más aquí.
             System.out.println("[Debug WB] " + pokemonVerificar.getNombre() + " sigue vivo (hp=" + pokemonVerificar.getHp() + "). No se requiere acción.");
        }
        System.out.println("[Debug WB] Fin de verificarDebilitado para Pkm" + indexPokemonVerificar);
    }

    /**
     * Muestra el panel adecuado para que el entrenador elija su próximo Pokémon.
     * Configura los botones con los nombres de los Pokémon disponibles y vivos.
     * REEMPLAZA LA NECESIDAD DE UN MÉTODO Batalla.elegirNuevoPokemon().
     * @param indexEntrenador El entrenador (1 o 2) que debe seleccionar.
     * @param pkmDebilitado El nombre del Pokémon que acaba de debilitarse (para mensaje).
     * @param opcionesVivas La lista de Pokémon VIVOS entre los que puede elegir.
     */
    private void mostrarPanelSeleccion(int indexEntrenador, String pkmDebilitado, ArrayList<Pokemon> opcionesVivas) {
         System.out.println("[Debug WB] Mostrando panel de selección para Entrenador " + indexEntrenador);
         // Validación de entrada: Si no hay opciones vivas, no debería llamarse, pero por si acaso.
         if (opcionesVivas == null || opcionesVivas.isEmpty()) {
              System.err.println("Error crítico en mostrarPanelSeleccion: No hay opciones vivas para mostrar a Entrenador " + indexEntrenador + ".");
              agregarMensaje("¡Error! No hay Pokémon disponibles para enviar.");
              finalizarBatalla(indexEntrenador == 1 ? 2 : 1); // Gana el oponente
              return;
         }

        // Determinar qué componentes UI usar según el entrenador
        JPanel panelSeleccion = (indexEntrenador == 1) ? panelSeleccionPokemon1 : panelSeleccionPokemon2;
        JPanel panelAtaquesOcultar = (indexEntrenador == 1) ? panelAtaques1 : panelAtaques2;
        JLabel lblInfo = (indexEntrenador == 1) ? lblSeleccionInfo1 : lblSeleccionInfo2;
        JButton btnOp1 = (indexEntrenador == 1) ? btnSeleccionarPokemon1_Op1 : btnSeleccionarPokemon2_Op1;
        JButton btnOp2 = (indexEntrenador == 1) ? btnSeleccionarPokemon1_Op2 : btnSeleccionarPokemon2_Op2;

        // Configurar texto informativo del panel
        lblInfo.setText(pkmDebilitado + " debilitado. Elige:");

        // --- Configurar Botón 1 (Siempre habrá al menos una opción si llegamos aquí) ---
        // Limpiar listeners anteriores para evitar acciones duplicadas si se muestra varias veces
        for (ActionListener al : btnOp1.getActionListeners()) { btnOp1.removeActionListener(al); }

         Pokemon opcion1 = opcionesVivas.get(0); // Primera opción viva garantizada
         // Validar que la opción no sea null (aunque la lógica previa debería asegurarlo)
         if (opcion1 != null) {
             btnOp1.setText(opcion1.getNombre() + " Nv." + opcion1.getNivel()); // Asume getters
             btnOp1.setVisible(true);
             btnOp1.setEnabled(true);
             // Añadir ActionListener que llame a seleccionarNuevoPokemon con ESTA opción
             // Usamos una expresión lambda para pasar el Pokémon específico
             btnOp1.addActionListener(e -> seleccionarNuevoPokemon(indexEntrenador, opcion1));
             System.out.println("  Panel Selección E" + indexEntrenador + " - Opción 1: " + opcion1.getNombre());
         } else {
              // Esto no debería ocurrir si la lista 'opcionesVivas' se filtró correctamente.
              System.err.println("Error Lógico: Opción 1 de Pokémon para seleccionar es null para E" + indexEntrenador);
              btnOp1.setText("[Error Pkm1]");
              btnOp1.setEnabled(false);
              btnOp1.setVisible(true); // Hacer visible pero deshabilitado para indicar el error
         }

        // --- Configurar Botón 2 (Solo si existe una segunda opción VIVA) ---
         // Limpiar listeners anteriores también para el botón 2
         for (ActionListener al : btnOp2.getActionListeners()) { btnOp2.removeActionListener(al); }

         if (opcionesVivas.size() > 1) { // Si hay más de un Pokémon vivo disponible
             Pokemon opcion2 = opcionesVivas.get(1); // Segunda opción viva
             if (opcion2 != null) {
                 btnOp2.setText(opcion2.getNombre() + " Nv." + opcion2.getNivel());
                 btnOp2.setVisible(true);
                 btnOp2.setEnabled(true);
                 // Añadir ActionListener para la segunda opción
                 btnOp2.addActionListener(e -> seleccionarNuevoPokemon(indexEntrenador, opcion2));
                  System.out.println("  Panel Selección E" + indexEntrenador + " - Opción 2: " + opcion2.getNombre());
             } else {
                 // Error lógico si la segunda opción es null
                 System.err.println("Error Lógico: Opción 2 de Pokémon para seleccionar es null para E" + indexEntrenador);
                 btnOp2.setText("[Error Pkm2]");
                 btnOp2.setVisible(true);
                 btnOp2.setEnabled(false);
             }
         } else {
             // No hay segunda opción viva, ocultar y deshabilitar el botón 2
             btnOp2.setText(""); // Limpiar texto por si acaso
             btnOp2.setVisible(false);
             btnOp2.setEnabled(false);
             System.out.println("  Panel Selección E" + indexEntrenador + " - (Solo una opción disponible)");
         }

         // Asegurarse de que el panel de ataques del jugador que selecciona esté oculto
         if (panelAtaquesOcultar != null) {
             panelAtaquesOcultar.setVisible(false);
         }
         // Mostrar el panel de selección correspondiente
         if (panelSeleccion != null) {
             panelSeleccion.setVisible(true);
         }

         // Marcar estado global de selección (redundante, pero confirma)
         seleccionPendiente = true;
         entrenadorSeleccionando = indexEntrenador;

         System.out.println("[Debug WB] Panel de selección mostrado y configurado para E" + indexEntrenador);
    }

    /**
     * Se ejecuta cuando un entrenador hace clic en un botón del panel de selección.
     * Actualiza el Pokémon activo de ese entrenador, oculta el panel de selección,
     * resetea el estado de selección y configura la interfaz para continuar la batalla.
     * @param indexEntrenador El entrenador (1 o 2) que realizó la selección.
     * @param nuevoPokemon El Pokémon específico que fue seleccionado.
     */
    private void seleccionarNuevoPokemon(int indexEntrenador, Pokemon nuevoPokemon) {
         System.out.println("[Debug WB] Entrenador " + indexEntrenador + " seleccionó a " + (nuevoPokemon != null ? nuevoPokemon.getNombre() : "NULL"));

         // Validación: ¿El Pokémon seleccionado es válido y está vivo?
         if (nuevoPokemon == null) {
              System.err.println("Error Crítico: Se intentó seleccionar un Pokémon null por E" + indexEntrenador);
              agregarMensaje("Error interno al seleccionar Pokémon. Intenta de nuevo.");
              // Podríamos intentar mostrar el panel de nuevo si hay opciones, o finalizar
              // Por seguridad, finalizamos si esto ocurre.
              finalizarBatalla(indexEntrenador == 1 ? 2 : 1);
              return;
         }
         if (!nuevoPokemon.getVivo()) {
              System.err.println("Error Lógico: Se seleccionó un Pokémon (" + nuevoPokemon.getNombre() + ") que no está vivo por E" + indexEntrenador);
              agregarMensaje("Error: ¡" + nuevoPokemon.getNombre() + " no puede luchar ahora!");
              // Forzar mostrar el panel de nuevo con las opciones correctas podría causar bucles.
              // Es mejor validar ANTES de mostrar las opciones. Si llegamos aquí, es un bug.
              // Volver a mostrar el panel con las opciones filtradas de nuevo es una opción:
              ArrayList<Pokemon> equipoRestante = (indexEntrenador == 1) ? equipoRestante1 : equipoRestante2;
              ArrayList<Pokemon> opcionesVivas = new ArrayList<>();
               if (equipoRestante != null) {
                   for (Pokemon p : equipoRestante) { if (p != null && p.getVivo()) { opcionesVivas.add(p); } }
               }
               if (!opcionesVivas.isEmpty()) {
                   mostrarPanelSeleccion(indexEntrenador, "¡Elige uno VIVO!", opcionesVivas);
               } else { // Si no quedan otras opciones vivas (improbable si llegó aquí)
                   finalizarBatalla(indexEntrenador == 1 ? 2 : 1);
               }
              return; // No continuar con la selección inválida
         }

         // ----- Selección Válida -----
         // Mensaje al usuario sobre el cambio
         agregarMensaje("¡" + (indexEntrenador == 1 ? entrenador1.getNombre() : entrenador2.getNombre()) +
                       " envía a " + nuevoPokemon.getNombre() + "!");

         // Actualizar el Pokémon activo del entrenador correspondiente
         if (indexEntrenador == 1) {
             pokemonActivo1 = nuevoPokemon;
         } else {
             pokemonActivo2 = nuevoPokemon;
         }

         // Actualizar la interfaz gráfica con la información del nuevo Pokémon activo
         // Esto incluye nombre, vida, barra, imagen y botones de ataque.
         actualizarInfoPokemon(indexEntrenador, nuevoPokemon);

         // Ocultar el panel de selección que se usó
         JPanel panelSeleccion = (indexEntrenador == 1) ? panelSeleccionPokemon1 : panelSeleccionPokemon2;
         if (panelSeleccion != null) {
            panelSeleccion.setVisible(false);
         } else {
              System.err.println("Advertencia: Panel de selección a ocultar es null para Entrenador " + indexEntrenador);
         }

         // Resets del estado de selección: ya no hay selección pendiente
         seleccionPendiente = false;
         entrenadorSeleccionando = 0;

         System.out.println("[Debug WB] Selección completada por E" + indexEntrenador + ". Pokémon activo ahora: " + nuevoPokemon.getNombre());

         // Configurar la UI para el siguiente estado de la batalla.
         // NO cambiamos el turno aquí. 'configurarTurno' lo hará si es necesario
         // basado en el valor actual de 'turnoEntrenador1' y que 'seleccionPendiente' es false.
         // El turno debería continuar normalmente (probablemente sea turno del Oponente ahora).
         configurarTurno();

         // Forzar repintado por si acaso algún cambio visual no se reflejó
         panelPrincipal.revalidate();
         panelPrincipal.repaint();
         System.out.println("[Debug WB] Fin de seleccionarNuevoPokemon para E" + indexEntrenador);
    }

    /**
     * Cambia el turno lógico entre los entrenadores.
     * Solo se ejecuta si no hay una selección pendiente.
     * Actualiza la UI llamando a configurarTurno().
     * REEMPLAZA LA NECESIDAD DE Batalla.intercambiarActivos() para el flujo normal.
     */
    private void cambiarTurno() {
        // Solo cambia el turno si NO hay una selección pendiente Y la batalla no ha terminado
        if (!seleccionPendiente && !batallaFinalizada) {
            turnoEntrenador1 = !turnoEntrenador1; // Invierte el booleano
            System.out.println("[Debug WB] Turno cambiado. Ahora es turno de Entrenador " + (turnoEntrenador1 ? "1 ("+entrenador1.getNombre()+")" : "2 ("+entrenador2.getNombre()+")"));
            configurarTurno(); // Actualiza la UI para el nuevo turno
        } else {
            // Log si se intenta cambiar turno cuando no se debe
            if (seleccionPendiente) {
                System.out.println("[Debug WB] Cambio de turno bloqueado: Selección pendiente por Entrenador " + entrenadorSeleccionando);
            }
            if (batallaFinalizada) {
                 System.out.println("[Debug WB] Cambio de turno bloqueado: Batalla finalizada.");
            }
            // Asegurarse de que la UI refleje el estado actual aunque no cambie el turno
            configurarTurno();
        }
    }

    /**
     * Configura la visibilidad y estado (habilitado/deshabilitado) de los paneles
     * y botones según de quién sea el turno y si hay una selección pendiente.
     * Es el método central para actualizar el estado de la interfaz entre acciones.
     */
    private void configurarTurno() {
        // No hacer nada si la batalla ya terminó
        if (batallaFinalizada) {
            System.out.println("[Debug WB] configurarTurno omitido: Batalla finalizada.");
            deshabilitarTodosLosBotones(); // Asegurar que todo sigue deshabilitado
            return;
        }

        System.out.println("[Debug WB] Configurando turno UI. Es turno de E1: " + turnoEntrenador1 + ", Selección pendiente: " + seleccionPendiente + " por E" + entrenadorSeleccionando);

        // Determinar si cada jugador puede atacar (es su turno Y no hay selección)
        boolean puedeAtacar1 = turnoEntrenador1 && !seleccionPendiente;
        boolean puedeAtacar2 = !turnoEntrenador1 && !seleccionPendiente;

        // Visibilidad de Paneles de Ataque:
        // - Normalmente, solo el panel del jugador activo es visible.
        // - Si alguien está seleccionando (ej: E1 selecciona), SU panel de ataque se oculta. El del oponente puede quedar visible pero deshabilitado.
        if (panelAtaques1 != null) {
            // Visible si es turno de E1 O si E2 está seleccionando (así E1 ve sus ataques aunque no pueda usarlos)
            // Oculto si E1 está seleccionando.
            panelAtaques1.setVisible(!seleccionPendiente || entrenadorSeleccionando != 1);
        }
        if (panelAtaques2 != null) {
            // Visible si es turno de E2 O si E1 está seleccionando.
            // Oculto si E2 está seleccionando.
            panelAtaques2.setVisible(!seleccionPendiente || entrenadorSeleccionando != 2);
        }

        // Habilitar/Deshabilitar Botones de Ataque:
        // Solo habilitados si es el turno de ESE jugador Y no hay selección pendiente Y el Pokémon tiene ataques válidos.
        configurarEstadoBotonesAtaque(botonesAtaque1, puedeAtacar1, pokemonActivo1);
        configurarEstadoBotonesAtaque(botonesAtaque2, puedeAtacar2, pokemonActivo2);


        // Visibilidad de Paneles de Selección:
        // Solo visible si la selección está pendiente Y es para ESE entrenador específico.
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
                 lblTurnoInfo.setForeground(Color.YELLOW); // Amarillo para indicar acción requerida (selección)
             } else {
                 // Mostrar de quién es el turno de atacar
                 if (turnoEntrenador1) {
                     lblTurnoInfo.setText("Turno: " + (entrenador1 != null ? entrenador1.getNombre() : "E1"));
                     lblTurnoInfo.setForeground(Color.CYAN); // Azul claro para E1
                 } else {
                     lblTurnoInfo.setText("Turno: " + (entrenador2 != null ? entrenador2.getNombre() : "E2"));
                     lblTurnoInfo.setForeground(Color.ORANGE); // Naranja para E2
                 }
             }
        }

         // Refrescar el panel principal para asegurar que todos los cambios de visibilidad/estado se apliquen visualmente
         if (panelPrincipal != null) {
            panelPrincipal.revalidate(); // Recalcular layout
            panelPrincipal.repaint(); // Volver a dibujar
         }
         System.out.println("[Debug WB] Configuración de turno UI completada.");
    }

    /**
     * Método auxiliar para habilitar o deshabilitar un conjunto de botones de ataque.
     * @param botones Array de JButtons a configurar.
     * @param habilitar True si deben habilitarse (si cumplen condiciones), False si deben deshabilitarse.
     * @param pokemonActivo El Pokémon cuyos ataques corresponden a estos botones.
     */
    private void configurarEstadoBotonesAtaque(JButton[] botones, boolean habilitar, Pokemon pokemonActivo) {
        if (botones == null) return;

         // Verificar si el Pokémon activo existe y tiene una lista de ataques válida
         boolean pokemonValidoConAtaques = pokemonActivo != null && pokemonActivo.getAtaques() != null;

         for (int i = 0; i < botones.length; i++) {
            JButton botonActual = botones[i];
            if (botonActual != null) {
                // Condición para que un botón específico esté habilitado:
                // 1. El parámetro 'habilitar' debe ser true (es el turno del jugador y no hay selección).
                // 2. El Pokémon activo debe ser válido y tener ataques.
                // 3. Debe existir un ataque válido en la posición 'i' de la lista de ataques del Pokémon.
                 boolean ataqueValidoEnIndice = pokemonValidoConAtaques &&
                                                i < pokemonActivo.getAtaques().size() && // Índice dentro de límites
                                                pokemonActivo.getAtaques().get(i) != null; // Ataque en esa posición no es null

                 botonActual.setEnabled(habilitar && ataqueValidoEnIndice);

                 // Opcional: Cambiar apariencia si está deshabilitado
                 // if (!botonActual.isEnabled()) {
                 //     botonActual.setBackground(Color.DARK_GRAY); // Ejemplo
                 // } else {
                 //      botonActual.setBackground(new Color(50, 50, 70)); // Color base habilitado
                 // }
            }
        }
    }

    /**
     * Finaliza la batalla, muestra un mensaje con el ganador y deshabilita controles.
     * @param indexGanador 1 si ganó el Entrenador 1, 2 si ganó el Entrenador 2.
     */
     private void finalizarBatalla(int indexGanador) {
         // Evitar múltiples finalizaciones
         if (batallaFinalizada) {
             System.out.println("[Debug WB] Intento de finalizar batalla que ya terminó. Ignorado.");
             return;
         }
         batallaFinalizada = true; // Marcar la batalla como terminada
         System.out.println("[Debug WB] Finalizando batalla. Ganador índice: " + indexGanador);

         // Bloquear más acciones inmediatamente
         seleccionPendiente = false; // Ya no hay selección
         entrenadorSeleccionando = 0;

         // Determinar nombres (con cuidado por si fueran null)
         Entrenador ganador = (indexGanador == 1) ? entrenador1 : entrenador2;
         Entrenador perdedor = (indexGanador == 1) ? entrenador2 : entrenador1;
         String nombreGanador = (ganador != null && ganador.getNombre() != null) ? ganador.getNombre() : "Entrenador " + indexGanador;
         String nombrePerdedor = (perdedor != null && perdedor.getNombre() != null) ? perdedor.getNombre() : "El oponente";

         // Mensajes finales en la consola/log
         agregarMensaje("-------------------------------------");
         agregarMensaje("¡" + nombrePerdedor + " no tiene más Pokémon capaces de luchar!");
         agregarMensaje("¡El ganador es " + nombreGanador + "!");
         agregarMensaje("-------------------------------------");

         // ----- Llamar celebración del ganador (Lógica de Batalla.java) -----
         if (ganador != null) {
             try {
                 // Asumiendo que Entrenador tiene un método celebracion()
                 // Si no existe, comentar o quitar esta línea.
                 ganador.celebracion();
             } catch (Exception e) {
                 // Captura por si el método no existe o falla, para no romper la finalización.
                 System.err.println("Advertencia: No se pudo ejecutar la celebración del ganador. " + e.getMessage());
             }
         }
         // -------------------------------------------------------------------

         // Deshabilitar todos los botones interactivos para prevenir más acciones
         deshabilitarTodosLosBotones();

         // Actualizar etiqueta de turno con mensaje de victoria claro
         if (lblTurnoInfo != null) {
            lblTurnoInfo.setText("¡VICTORIA PARA " + nombreGanador.toUpperCase() + "!");
            lblTurnoInfo.setForeground(Color.GREEN); // Verde brillante para victoria
         }

         // Mostrar diálogo modal de fin de batalla (bloquea interacción hasta cerrarlo)
         JOptionPane.showMessageDialog(this, // 'this' es el JFrame de batalla
             "¡Fin de la batalla!\n\nGanador: " + nombreGanador,
             "Fin de la Batalla",
             JOptionPane.INFORMATION_MESSAGE); // Icono de información

         // Opcional: Cerrar la ventana automáticamente después del diálogo
         // this.dispose();
    }

    /**
     * Método auxiliar para deshabilitar todos los botones de acción al final de la batalla.
     */
    private void deshabilitarTodosLosBotones() {
         System.out.println("[Debug WB] Deshabilitando todos los botones interactivos.");
         // Deshabilitar botones de ataque
         if (botonesAtaque1 != null) {
             for(JButton btn : botonesAtaque1) if (btn != null) btn.setEnabled(false);
         }
         if (botonesAtaque2 != null) {
             for(JButton btn : botonesAtaque2) if (btn != null) btn.setEnabled(false);
         }

         // Deshabilitar botones de selección (aunque los paneles deberían estar ocultos)
         if (btnSeleccionarPokemon1_Op1 != null) btnSeleccionarPokemon1_Op1.setEnabled(false);
         if (btnSeleccionarPokemon1_Op2 != null) btnSeleccionarPokemon1_Op2.setEnabled(false);
         if (btnSeleccionarPokemon2_Op1 != null) btnSeleccionarPokemon2_Op1.setEnabled(false);
         if (btnSeleccionarPokemon2_Op2 != null) btnSeleccionarPokemon2_Op2.setEnabled(false);

         // Asegurarse que los paneles de selección estén ocultos (por si acaso)
         if (panelSeleccionPokemon1 != null) panelSeleccionPokemon1.setVisible(false);
         if (panelSeleccionPokemon2 != null) panelSeleccionPokemon2.setVisible(false);

         // Asegurarse que los paneles de ataque estén visibles pero deshabilitados (opcional estético)
         // O simplemente dejarlos como estén (visibles o no según el último estado)
         // if (panelAtaques1 != null) panelAtaques1.setVisible(true);
         // if (panelAtaques2 != null) panelAtaques2.setVisible(true);
    }


    // --- Métodos de Inicialización y Actualización de UI (Sin cambios lógicos) ---

    /**
     * Muestra un diálogo para que el entrenador elija su primer Pokémon.
     * @return El Pokémon seleccionado, o null si cancela o no hay opciones.
     */
    private Pokemon seleccionarPokemonInicial(Entrenador entrenador, ArrayList<Pokemon> equipo, int numEntrenador) {
         System.out.println("[Debug WB] Seleccionando Pokémon inicial para Entrenador " + numEntrenador + " ("+entrenador.getNombre()+")");
        if (equipo == null || equipo.isEmpty()) {
            System.err.println("Error Crítico: El equipo del Entrenador " + numEntrenador + " ("+entrenador.getNombre()+") está vacío o es nulo al inicio.");
            return null; // No se puede seleccionar
        }

        // Filtrar solo Pokémon VÁLIDOS y VIVOS del equipo inicial
        ArrayList<Pokemon> opcionesValidas = new ArrayList<>();
        for (Pokemon p : equipo) {
            if (p != null && p.getVivo()) { // Crucial chequear si está vivo
                opcionesValidas.add(p);
            } else {
                System.err.println("Advertencia: Pokémon nulo o no vivo encontrado en equipo inicial de Entrenador " + numEntrenador + ": " + (p != null ? p.getNombre() : "NULL"));
            }
        }

        // Si después de filtrar no queda ninguno válido/vivo
        if (opcionesValidas.isEmpty()) {
            System.err.println("Error Crítico: No hay Pokémon válidos/vivos para seleccionar inicialmente para el Entrenador " + numEntrenador + " ("+entrenador.getNombre()+")");
            return null; // No se puede seleccionar
        }

        // Preparar opciones para el ComboBox
        String[] nombresPokemon = new String[opcionesValidas.size()];
        for (int i = 0; i < opcionesValidas.size(); i++) {
            Pokemon p = opcionesValidas.get(i);
            // Mostrar nombre y nivel para ayudar a elegir
            nombresPokemon[i] = p.getNombre() + " (Nv." + p.getNivel() + ")"; // Asume getters
        }

        // Crear ComboBox DENTRO del método asegura que use el EDT correctamente con JOptionPane
        JComboBox<String> comboBox = new JComboBox<>(nombresPokemon);
        comboBox.setFont(FONT_MONO_PLAIN_15); // Usar fuente monoespaciada

        // Mostrar el diálogo modal (pausa la ejecución hasta que el usuario elija)
        int result = JOptionPane.showConfirmDialog(
                this, // Padre del diálogo (esta ventana)
                comboBox, // Componente a mostrar (el ComboBox)
                "Entrenador " + entrenador.getNombre() + ", elige tu primer Pokémon:", // Título del diálogo
                JOptionPane.OK_CANCEL_OPTION, // Botones OK y Cancelar
                JOptionPane.QUESTION_MESSAGE // Icono de pregunta
        );

        // Procesar resultado del diálogo
        if (result == JOptionPane.OK_OPTION) {
            int selectedIndex = comboBox.getSelectedIndex();
            // Validar índice seleccionado (por si acaso)
            if (selectedIndex >= 0 && selectedIndex < opcionesValidas.size()) {
                Pokemon elegido = opcionesValidas.get(selectedIndex);
                System.out.println("Entrenador " + numEntrenador + " ("+entrenador.getNombre()+") elige a: " + elegido.getNombre());
                return elegido; // Devuelve el Pokémon seleccionado
            } else {
                // Error inesperado si el índice es inválido
                System.err.println("Error: Índice de ComboBox inválido: " + selectedIndex + " para Entrenador " + numEntrenador);
                return null; // Tratar como cancelación
            }
        }

        // Si llega aquí, el usuario presionó Cancelar o cerró el diálogo
        System.out.println("Selección inicial cancelada por Entrenador " + numEntrenador + " ("+entrenador.getNombre()+")");
        return null; // Indica que no se seleccionó Pokémon
    }

    private void inicializarPanelesAtaques() {
        int yAtaques = ALTO_VENTANA - ALTO_BOTON_PANEL - 50; // Posición Y común
        int xAtaques1 = MARGEN_LATERAL;
        int xAtaques2 = ANCHO_VENTANA - ANCHO_BOTON_PANEL - MARGEN_LATERAL;

        panelAtaques1 = new JPanel(new GridLayout(2, 2, 6, 6)); // Grid 2x2 para 4 botones
        panelAtaques1.setBounds(xAtaques1, yAtaques, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelAtaques1.setOpaque(false); // Transparente para ver fondo
        for (int i = 0; i < 4; i++) {
            botonesAtaque1[i] = crearBotonAtaque("..."); // Texto inicial
            final int index = i; // Necesario para usar en lambda
            // Añadir ActionListener para EJECUTAR el ataque correspondiente
            botonesAtaque1[i].addActionListener(e -> ejecutarAtaque(1, index));
            botonesAtaque1[i].setEnabled(false); // Deshabilitar inicialmente hasta que sea su turno
            panelAtaques1.add(botonesAtaque1[i]);
        }

        panelAtaques2 = new JPanel(new GridLayout(2, 2, 6, 6));
        panelAtaques2.setBounds(xAtaques2, yAtaques, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelAtaques2.setOpaque(false);
        for (int i = 0; i < 4; i++) {
            botonesAtaque2[i] = crearBotonAtaque("...");
            final int index = i;
            botonesAtaque2[i].addActionListener(e -> ejecutarAtaque(2, index));
            botonesAtaque2[i].setEnabled(false);
            panelAtaques2.add(botonesAtaque2[i]);
        }
    }

    private void inicializarPanelesSeleccion() {
        int ySeleccion = ALTO_VENTANA - ALTO_BOTON_PANEL - 50; // Misma Y que ataques
        int xSeleccion1 = MARGEN_LATERAL;
        int xSeleccion2 = ANCHO_VENTANA - ANCHO_BOTON_PANEL - MARGEN_LATERAL;

        // Panel para Entrenador 1
        panelSeleccionPokemon1 = new JPanel(new BorderLayout(10, 10)); // Layout simple
        panelSeleccionPokemon1.setBounds(xSeleccion1, ySeleccion, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelSeleccionPokemon1.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2)); // Borde llamativo
        panelSeleccionPokemon1.setBackground(new Color(0, 0, 50, 200)); // Fondo azul oscuro semi-transparente
        panelSeleccionPokemon1.setVisible(false); // Oculto inicialmente

        lblSeleccionInfo1 = new JLabel("Elige tu próximo Pokémon", SwingConstants.CENTER);
        lblSeleccionInfo1.setFont(FONT_MONO_BOLD_20);
        lblSeleccionInfo1.setForeground(Color.WHITE);
        panelSeleccionPokemon1.add(lblSeleccionInfo1, BorderLayout.NORTH); // Texto arriba

        JPanel panelBotonesSeleccion1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Para centrar botones
        panelBotonesSeleccion1.setOpaque(false);
        btnSeleccionarPokemon1_Op1 = crearBotonSeleccion("Opción 1"); // Se configurará texto/acción después
        btnSeleccionarPokemon1_Op2 = crearBotonSeleccion("Opción 2");
        // Los ActionListeners se añaden dinámicamente en mostrarPanelSeleccion
        panelBotonesSeleccion1.add(btnSeleccionarPokemon1_Op1);
        panelBotonesSeleccion1.add(btnSeleccionarPokemon1_Op2);
        panelSeleccionPokemon1.add(panelBotonesSeleccion1, BorderLayout.CENTER); // Botones en el centro

        // Panel para Entrenador 2 (análogo)
        panelSeleccionPokemon2 = new JPanel(new BorderLayout(10, 10));
        panelSeleccionPokemon2.setBounds(xSeleccion2, ySeleccion, ANCHO_BOTON_PANEL, ALTO_BOTON_PANEL);
        panelSeleccionPokemon2.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        panelSeleccionPokemon2.setBackground(new Color(50, 0, 0, 200)); // Fondo rojo oscuro semi-transparente
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
        int xBase = MARGEN_LATERAL; // Lado izquierdo
        // Calcular Y para que el bloque de info quede *encima* de la imagen
        int yInfo = Y_POS_POKEMON_AREA - ALTO_INFO_PKM_BLOQUE - 10; // 10px de espacio entre info e imagen
        int yImagen = Y_POS_POKEMON_AREA;

        // Panel para agrupar Nombre, Barra HP, Texto HP (usa layout absoluto interno)
        panelInfoPokemon1 = new JPanel(null);
        panelInfoPokemon1.setBounds(xBase, yInfo, ANCHO_INFO_PKM, ALTO_INFO_PKM_BLOQUE);
        panelInfoPokemon1.setOpaque(false); // Hacer transparente para ver el fondo del estadio

        // Label para el Nombre y Nivel
        lblNombrePokemon1 = new JLabel("...", SwingConstants.CENTER); // Texto inicial
        lblNombrePokemon1.setFont(FONT_MONO_BOLD_20);
        lblNombrePokemon1.setForeground(COLOR_TEXTO_INFO);
        lblNombrePokemon1.setBounds(0, 0, ANCHO_INFO_PKM, 30); // Posición relativa a panelInfoPokemon1
        panelInfoPokemon1.add(lblNombrePokemon1);

        // Barra de Progreso para la Vida
        pbVidaPokemon1 = new JProgressBar(0, 100); // Máximo se ajustará después
        pbVidaPokemon1.setValue(100); // Iniciar llena
        pbVidaPokemon1.setStringPainted(false); // No mostrar porcentaje en la barra
        pbVidaPokemon1.setForeground(Color.GREEN); // Color inicial verde
        pbVidaPokemon1.setBackground(Color.DARK_GRAY); // Fondo oscuro para contraste
        pbVidaPokemon1.setBorder(new LineBorder(Color.GRAY)); // Borde sutil
        pbVidaPokemon1.setBounds(10, 35, ANCHO_INFO_PKM - 20, 18); // Posición relativa (debajo nombre)
        panelInfoPokemon1.add(pbVidaPokemon1);

        // Label para mostrar HP numérico (ej: "HP: 85 / 120")
        lblVidaTextoPokemon1 = new JLabel("HP: ... / ...", SwingConstants.CENTER); // Texto inicial
        lblVidaTextoPokemon1.setFont(FONT_MONO_PLAIN_15);
        lblVidaTextoPokemon1.setForeground(COLOR_TEXTO_INFO);
        lblVidaTextoPokemon1.setBounds(0, 35 + 18 + 2, ANCHO_INFO_PKM, 25); // Posición relativa (debajo barra)
        panelInfoPokemon1.add(lblVidaTextoPokemon1);

        // El JLabel para la imagen del Pokémon se posiciona directamente en el panelPrincipal
        lblImagenPokemon1 = new JLabel();
        // Centrar imagen horizontalmente respecto al área de info
        int xImagen = xBase + (ANCHO_INFO_PKM / 2) - (ANCHO_IMAGEN_PKM / 2);
        lblImagenPokemon1.setBounds(xImagen, yImagen, ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM);
        lblImagenPokemon1.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPokemon1.setBorder(BorderFactory.createEmptyBorder()); // Sin borde
        // La imagen se carga dinámicamente en actualizarInfoPokemon
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
        lblVidaTextoPokemon2.setBounds(0, 35 + 18 + 2, ANCHO_INFO_PKM, 25);
        panelInfoPokemon2.add(lblVidaTextoPokemon2);

        // JLabel para la imagen del Pokémon 2
        int xImagen = xBase + (ANCHO_INFO_PKM / 2) - (ANCHO_IMAGEN_PKM / 2);
        lblImagenPokemon2 = new JLabel();
        lblImagenPokemon2.setBounds(xImagen, yImagen, ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM);
        lblImagenPokemon2.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPokemon2.setBorder(BorderFactory.createEmptyBorder());
        // La imagen se carga dinámicamente
    }

     private void inicializarInfoTurno() {
        lblTurnoInfo = new JLabel(" ", SwingConstants.CENTER); // Texto inicial vacío
        lblTurnoInfo.setFont(FONT_MONO_BOLD_20);
        lblTurnoInfo.setForeground(Color.YELLOW); // Color inicial llamativo
        // Posicionar encima de los paneles de botones/selección
        int yTurno = ALTO_VENTANA - ALTO_BOTON_PANEL - 50 - 35; // 35px encima
        lblTurnoInfo.setBounds(0, yTurno, ANCHO_VENTANA, 30); // Centrado horizontalmente
        lblTurnoInfo.setOpaque(false); // Transparente
    }

    /**
     * Actualiza toda la información visual de un Pokémon en la interfaz.
     * Carga nombre, nivel, vida (barra y texto), ataques e imagen.
     * @param entrenadorIndex 1 o 2, indica qué lado de la interfaz actualizar.
     * @param nuevoPokemon El Pokémon cuya información se mostrará.
     */
    private void actualizarInfoPokemon(int entrenadorIndex, Pokemon nuevoPokemon) {
         // Validación crítica: No continuar si el Pokémon es null
         if (nuevoPokemon == null) {
             System.err.println("Error crítico: Se intentó actualizar UI con un Pokémon null para Entrenador " + entrenadorIndex);
             // ¿Qué hacer? Podríamos limpiar la UI o mostrar un estado de error.
             // Limpiar podría ser lo más seguro si esto ocurre inesperadamente.
             limpiarInfoPokemonUI(entrenadorIndex);
             return;
         }
         System.out.println("[Debug WB] Actualizando UI para Pkm" + entrenadorIndex + ": " + nuevoPokemon.getNombre());

        // Asignar el nuevo pokémon activo al estado interno (redundante si ya se hizo, pero seguro)
        if (entrenadorIndex == 1) {
             pokemonActivo1 = nuevoPokemon;
        } else {
             pokemonActivo2 = nuevoPokemon;
        }

        // Obtener datos del Pokémon (con manejo defensivo de nulls si es necesario)
        String nombre = nuevoPokemon.getNombre() != null ? nuevoPokemon.getNombre() : "[Sin Nombre]";
        int nivel = nuevoPokemon.getNivel(); // Asume getter existe y devuelve primitivo
        float vidaActualFloat = nuevoPokemon.getHp(); // Asume getter existe
        int vidaActual = Math.max(0, (int) vidaActualFloat); // Asegurar no negativo y convertir a int
        // Calcular vida máxima. Asumimos que no la tenemos directamente.
        int vidaMax = calcularVidaMaximaAproximada(nuevoPokemon); // Usa método auxiliar

        // Seleccionar componentes UI correctos según entrenadorIndex
        JLabel lblImagenTarget = (entrenadorIndex == 1) ? lblImagenPokemon1 : lblImagenPokemon2;
        JLabel lblNombreTarget = (entrenadorIndex == 1) ? lblNombrePokemon1 : lblNombrePokemon2;
        // La barra de vida y texto se actualizan por separado en actualizarVidaPokemonUI

        // Actualizar Nombre y Nivel en el Label
        lblNombreTarget.setText(nombre + " Nv." + nivel);

        // Actualizar Barra de Vida y Texto HP (usando método dedicado)
        actualizarVidaPokemonUI(entrenadorIndex, vidaActual, vidaMax);

        // Actualizar Botones de Ataque con los ataques del nuevo Pokémon
        actualizarBotonesAtaque(entrenadorIndex, nuevoPokemon.getAtaques()); // Asume getAtaques() existe

        // Actualizar Imagen del Pokémon
        // Usamos las rutas fijadas al inicio para consistencia visual durante la batalla
        String rutaImagenEspecifica = (entrenadorIndex == 1) ? RUTA_IMG_PKM1 : RUTA_IMG_PKM2;
        Image imagen = cargarImagen(rutaImagenEspecifica);
        if (imagen != null) {
            // Redimensionar suavemente para que encaje en el JLabel
            Image imagenRedimensionada = imagen.getScaledInstance(ANCHO_IMAGEN_PKM, ALTO_IMAGEN_PKM, Image.SCALE_SMOOTH);
            lblImagenTarget.setIcon(new ImageIcon(imagenRedimensionada));
            lblImagenTarget.setText(""); // Limpiar texto si hubiera placeholder
        } else {
             // Si la imagen no carga, poner un placeholder o limpiar
            lblImagenTarget.setIcon(null);
            lblImagenTarget.setText("[IMG]"); // Indicador de imagen no cargada
            lblImagenTarget.setForeground(Color.RED);
             System.err.println("Error: No se pudo cargar la imagen para " + nombre + " desde: " + rutaImagenEspecifica);
        }
        System.out.println("[Debug WB] UI actualizada para Pkm" + entrenadorIndex);
    }

    /**
     * Actualiza específicamente la barra de vida y el texto de HP para un Pokémon.
     * @param entrenadorIndex 1 o 2.
     * @param vidaActual La vida actual del Pokémon.
     * @param vidaMaxima La vida máxima del Pokémon.
     */
     private void actualizarVidaPokemonUI(int entrenadorIndex, int vidaActual, int vidaMaxima) {
        // Asegurar valores mínimos para evitar errores visuales o matemáticos
        vidaActual = Math.max(0, vidaActual); // Vida no puede ser negativa
        vidaMaxima = Math.max(1, vidaMaxima); // Máximo debe ser al menos 1 (evitar división por cero)

        // Seleccionar componentes correctos
        JProgressBar pb = (entrenadorIndex == 1) ? pbVidaPokemon1 : pbVidaPokemon2;
        JLabel lblTexto = (entrenadorIndex == 1) ? lblVidaTextoPokemon1 : lblVidaTextoPokemon2;

        // Validar que los componentes existan
         if (pb == null || lblTexto == null) {
              System.err.println("Error crítico: Componente UI de vida (ProgressBar o Label) es null para Entrenador " + entrenadorIndex);
              return;
         }

        // Actualizar máximo y valor de la barra de progreso
        pb.setMaximum(vidaMaxima);
        pb.setValue(vidaActual);

        // Actualizar texto del Label HP
        lblTexto.setText("HP: " + vidaActual + " / " + vidaMaxima);

        // Cambiar color de la barra según el porcentaje de vida (efecto visual)
        double porcentajeVida = (double) vidaActual / vidaMaxima;
        if (porcentajeVida > 0.5) {
            pb.setForeground(Color.GREEN); // Verde si > 50%
        } else if (porcentajeVida > 0.2) {
            pb.setForeground(Color.ORANGE); // Naranja si > 20% y <= 50%
        } else {
            pb.setForeground(Color.RED); // Rojo si <= 20%
        }
    }

    /**
     * Actualiza el texto de los botones de ataque de un entrenador con los nombres
     * de los ataques del Pokémon activo.
     * @param entrenadorIndex 1 o 2.
     * @param ataques La lista de ataques del Pokémon activo.
     */
    private void actualizarBotonesAtaque(int entrenadorIndex, ArrayList<Ataque> ataques) {
        JButton[] botones = (entrenadorIndex == 1) ? botonesAtaque1 : botonesAtaque2;

        // Validación: ¿Qué hacer si la lista de ataques es nula?
        if (ataques == null) {
             System.err.println("Advertencia: Lista de ataques es null para Entrenador " + entrenadorIndex + ". Deshabilitando botones.");
             // Deshabilitar y limpiar todos los botones si la lista es nula
             for (JButton boton : botones) {
                 if (boton != null) {
                     boton.setText("-");
                     boton.setEnabled(false); // Asegurar deshabilitado
                 }
             }
             return; // No continuar
        }

        // Recorrer los botones (máximo 4)
        for (int i = 0; i < botones.length; i++) {
             JButton botonActual = botones[i];
             if (botonActual == null) continue; // Saltar si el botón no existe (no debería pasar)

             // Verificar si hay un ataque válido en esta posición de la lista
            if (i < ataques.size() // Índice dentro de los límites de la lista
                && ataques.get(i) != null // El ataque en esa posición no es null
                && ataques.get(i).getNombre() != null // El nombre del ataque no es null
                && !ataques.get(i).getNombre().isEmpty()) { // El nombre no está vacío

                Ataque ataque = ataques.get(i);
                // Establecer el texto del botón con el nombre del ataque
                // Podrías añadir más info si quieres, como tipo o PP si tu clase Ataque lo tiene
                // Ejemplo: botonActual.setText("<html>" + ataque.getNombre() + "<br><font size='-2'>PP: " + ataque.getPp() + "</font></html>");
                botonActual.setText(ataque.getNombre());

                // El estado habilitado/deshabilitado se gestionará centralmente en configurarTurno()
                // botonActual.setEnabled(true); // No habilitar aquí directamente
            } else {
                // Si no hay ataque válido en esta posición (lista más corta, ataque null o inválido)
                botonActual.setText("-"); // Poner un placeholder
                botonActual.setEnabled(false); // Asegurar que esté deshabilitado si no hay ataque válido
            }
        }
    }

    /**
     * Limpia la información de un Pokémon en la UI (nombre, vida, imagen).
     * Útil si ocurre un error crítico o al inicio.
     */
    private void limpiarInfoPokemonUI(int entrenadorIndex) {
        JLabel lblNombre = (entrenadorIndex == 1) ? lblNombrePokemon1 : lblNombrePokemon2;
        JProgressBar pbVida = (entrenadorIndex == 1) ? pbVidaPokemon1 : pbVidaPokemon2;
        JLabel lblVidaTexto = (entrenadorIndex == 1) ? lblVidaTextoPokemon1 : lblVidaTextoPokemon2;
        JLabel lblImagen = (entrenadorIndex == 1) ? lblImagenPokemon1 : lblImagenPokemon2;
        JButton[] botones = (entrenadorIndex == 1) ? botonesAtaque1 : botonesAtaque2;

        if (lblNombre != null) lblNombre.setText("...");
        if (pbVida != null) {
            pbVida.setValue(0);
            pbVida.setMaximum(100); // Resetear máximo por si acaso
            pbVida.setForeground(Color.GRAY);
        }
        if (lblVidaTexto != null) lblVidaTexto.setText("HP: - / -");
        if (lblImagen != null) {
            lblImagen.setIcon(null);
            lblImagen.setText("[ERROR]");
             lblImagen.setForeground(Color.RED);
        }
        if (botones != null) {
            for (JButton btn : botones) {
                if (btn != null) {
                    btn.setText("-");
                    btn.setEnabled(false);
                }
            }
        }
         System.err.println("UI limpiada por error para Entrenador " + entrenadorIndex);
    }


    // --- Métodos Auxiliares (Sin cambios) ---

    /** Obtiene una ruta aleatoria de un array de Strings. */
    private static String obtenerrutaaleatoria(String[] rutas) {
        if (rutas == null || rutas.length == 0) {
            System.err.println("Advertencia: Array de rutas vacío o null para obtener ruta aleatoria.");
            return ""; // Devolver vacío o una ruta por defecto
        }
        int indice = random.nextInt(rutas.length);
        return rutas[indice];
    }

    /** Carga una imagen desde una ruta, manejando posibles errores. */
     private Image cargarImagen(String ruta) {
        if (ruta == null || ruta.isEmpty()) {
             System.err.println("Error: Ruta de imagen nula o vacía.");
             return null;
        }
        File archivoImagen = new File(ruta);
        // Verificación más robusta de la existencia y legibilidad del archivo
        if (!archivoImagen.exists() || !archivoImagen.isFile() || !archivoImagen.canRead()) {
             System.err.println("Advertencia: Imagen no encontrada, no es un archivo o no se puede leer: " + ruta);
             return null; // Devolver null si no se puede leer
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(archivoImagen); // Intenta leer la imagen
        } catch (IOException e) {
            // Imprime un error si falla la lectura (corrupción, formato no soportado)
             System.err.println("Error al cargar la imagen desde: " + ruta + " - " + e.getMessage());
             // No relanzar la excepción para no detener la aplicación por una imagen faltante
        }
        return img; // Devuelve la imagen cargada o null si hubo error
    }

    /** Crea y configura un botón estándar para ataques. */
    private JButton crearBotonAtaque(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FONT_MONO_PLAIN_15);
        boton.setFocusPainted(false); // Quitar borde azul al seleccionar
        boton.setBackground(new Color(50, 50, 70)); // Color base gris azulado oscuro
        boton.setForeground(Color.WHITE); // Texto blanco
        boton.setBorder(new LineBorder(Color.GRAY)); // Borde gris sutil
        // Efecto Hover simple (cambio de color al pasar el mouse)
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Solo cambiar color si el botón está habilitado
                if (boton.isEnabled()) boton.setBackground(new Color(80, 80, 100)); // Más claro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                 // Volver al color base (incluso si se deshabilita mientras el mouse está encima)
                 boton.setBackground(new Color(50, 50, 70));
            }
        });
        return boton;
    }

    /** Crea y configura un botón estándar para selección de Pokémon. */
     private JButton crearBotonSeleccion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FONT_MONO_PLAIN_15);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(70, 70, 90)); // Color base ligeramente diferente a ataque
        boton.setForeground(Color.WHITE);
        boton.setBorder(new LineBorder(Color.CYAN)); // Borde cian más llamativo
        boton.setPreferredSize(new Dimension(150, 40)); // Tamaño preferido para que quepan 2
        // Efecto Hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                 if (boton.isEnabled()) boton.setBackground(new Color(100, 100, 120)); // Más claro
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                  boton.setBackground(new Color(70, 70, 90)); // Volver al base
            }
        });
        // Los listeners se añaden dinámicamente en mostrarPanelSeleccion
        return boton;
    }

    /** Método simple para imprimir mensajes de batalla en la consola. */
    private void agregarMensaje(String mensaje) {
        System.out.println("[Batalla] " + mensaje);
        // Futuro: Añadir a un JTextArea en la UI si se desea un log visual.
        // Ejemplo: textAreaLog.append(mensaje + "\n");
        //          textAreaLog.setCaretPosition(textAreaLog.getDocument().getLength()); // Auto-scroll
    }

    /**
     * Calcula una vida máxima aproximada basada en nivel y stats (si estuvieran disponibles).
     * AJUSTA ESTA FÓRMULA según cómo calcules la vida máxima en tu juego.
     * Si tienes un getter para HP máximo en Pokémon, úsalo directamente.
     */
    private int calcularVidaMaximaAproximada(Pokemon p) {
        if (p == null) return 1; // Evitar división por cero o errores si p es null
        // Fórmula MUY BÁSICA de ejemplo si no tienes HP Máx guardado:
        int vidaBase = 50; // Un valor base cualquiera
        int vidaPorNivel = 5; // Un incremento por nivel cualquiera
        // Podrías usar stats base si los tuvieras: ej. (p.getStatBaseHP() * 2 + IV + EV/4) * p.getNivel() / 100 + p.getNivel() + 10
        int vidaMaxCalculada = vidaBase + (p.getNivel() * vidaPorNivel);

        // IMPORTANTE: Si tu clase Pokemon ya tiene un atributo y getter para la vida máxima
        // (ej: p.getHpMaximo()), DEBERÍAS usar eso en lugar de este cálculo.
        // return p.getHpMaximo();

        return Math.max(1, vidaMaxCalculada); // Asegurar que sea al menos 1
    }

    // --- Implementación ActionListener (Requerido por `implements ActionListener`) ---
    @Override
    public void actionPerformed(ActionEvent e) {
        // Este método se llamaría si la PROPIA ventana WindowBatalla fuera añadida
        // como listener a algún componente (lo cual no hacemos aquí, añadimos
        // lambdas directamente a los botones).
        // Se puede dejar vacío o usar para lógica centralizada si se prefiere.
        System.out.println("ActionEvent genérico recibido por WindowBatalla: " + e.getActionCommand() + " Fuente: " + e.getSource().getClass().getName());
    }

    // --- Getters y Setters (Opcionales, si necesitas acceder desde fuera) ---
    // Ejemplo:
    public JProgressBar getPbVidaPokemon1() { return pbVidaPokemon1; }
    public JProgressBar getPbVidaPokemon2() { return pbVidaPokemon2; }
    public Pokemon getPokemonActivo1() { return pokemonActivo1; }
    public Pokemon getPokemonActivo2() { return pokemonActivo2; }
    // Añadir más getters/setters según sea necesario

}