import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Pokemon extends SerVivo {

    // Inicializando atributos
    private TipoAtaquePokemon tipo;
    private ArrayList<Ataque> ataques = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private boolean vivo; // vivo se encanga de inhabilitar al Pokemon cuando hp=0
    private float hp, df;
    private byte velocidad;

    // Getters y Setters
    public TipoAtaquePokemon getTipo() {
        return tipo;
    }
    public void setTipo(TipoAtaquePokemon tipo) {
        this.tipo = tipo;
    }
    public ArrayList<Ataque> getAtaques() {
        return ataques;
    }
    public void setAtaques(ArrayList<Ataque> ataques) {
        this.ataques = ataques;
    }
    public float getHp() {
        return hp;
    }
    public void setHp(float hp) {
        this.hp = hp;
    }
    public float getDefensa() {
        return df;
    }

    public void setDefensa(float df) {
        this.df = df;
    }
    
    public float getVelocidad() {
            return velocidad;
    }

    public void setVelocidad(byte velocidad) {
        this.velocidad = velocidad;
    }

    public boolean getVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    // Constructor
    public Pokemon(String nombre, TipoAtaquePokemon tipo, ArrayList<Ataque> ataques, float hp, float df, byte velocidad) {
        // Trayendo herencia: atributo nombre
        super(nombre);
        this.tipo = tipo;
        this.ataques = ataques;
        this.hp = hp;
        this.df = df;
        this.velocidad = velocidad;
        this.vivo = true;
    }

    // Método para que el usuario elija el tipo de Pokemon
    public static TipoAtaquePokemon elegirTipo(boolean confirmo, ArrayList<Integer> repetidos) {

        // Inicializando variables locales
        int eleccion;
        TipoAtaquePokemon tipoPokemon;

        // ELECCIÓN AUTOMÁTICA
        if(!confirmo) {
            eleccion = aleatorioInt(TipoAtaquePokemon.values().length-1, 0, false, repetidos);
            tipoPokemon = TipoAtaquePokemon.values()[eleccion];
        } 
        // ELECCIÓN MANUAL
        else {
            System.out.println("¡Su tipo!");
            int contador = 0;
            // Mostrando Tipos disponibles
            for(TipoAtaquePokemon clase : TipoAtaquePokemon.values()) {
                contador++;
                System.out.println(contador + ". " + clase);
            }
            while(true) {
                if(scanner.hasNextInt()) {
                    // Para asegurar que es un entero
                    eleccion = scanner.nextInt();
                    scanner.nextLine();
                    if(eleccion>0 && eleccion<=TipoAtaquePokemon.values().length) {
                        // Para asegurar que elección está dentro del rango
                        tipoPokemon = TipoAtaquePokemon.values()[eleccion-1]; // Asignando tipoPokemon
                        break;
                    } else {
                        System.out.println("Por favor, selecciona una opción dentro del rango");
                        scanner.nextLine();
                    }
                } else {
                    System.out.println("¡Elige un número! 😠");
                    scanner.nextLine();
                }
            }
        }
        return tipoPokemon;
    }

    // Método para que el usuario elija los ataques de acuerdo con el tipo de pokemon elegido
    public static ArrayList<Ataque> capturarAtaques(TipoAtaquePokemon tipoPokemon, boolean confirmo, String nombrePokemon) {

        // Inicializando variables locales
        String nombreAtk;
        float poderAtk;
        int eleccion;
        String[] arsenal = tipoPokemon.getAtaques(); // Se asignan los ataques correspondientes
        ArrayList<Ataque> ataques = new ArrayList<>();
        ArrayList<Integer> repetidos = new ArrayList<>();

        // SELECCIÓN MANUAL
        if (confirmo) {
            // Mostrando ataques
            System.out.println("ATAQUES DISPONIBLES (TIPO " + tipoPokemon + ")");
            for(int i = 0; i < arsenal.length; i++) {
                System.out.println((i+1) + "." + arsenal[i]);
            }
            for (int i = 0; i < 4; i++) { 
                // Seleccionando ataque 1 x 1
                System.out.println("Tu ataque # " + (i+1) + " (" + nombrePokemon + ")");
                while(true) {
                    if (scanner.hasNextInt()) { 
                        // Para asegurar que es un entero
                        eleccion = scanner.nextInt();
                        scanner.nextLine();
                        if(repetidos.contains(eleccion)) { 
                            // Para evitar repeticiones
                            while(repetidos.contains(eleccion)) {
                                System.out.println("¡Selecciona un ataque diferente!");
                                eleccion = scanner.nextInt();
                                scanner.nextLine();
                            }
                        }
                        if (eleccion > 0 && eleccion <= arsenal.length) { 
                            // Para asegurar que la elección está dentro del rango
                            repetidos.add(eleccion);
                            nombreAtk = arsenal[(eleccion-1)];
                            poderAtk = aleatorioFloat(100f, 10f); // Se elige poderAtk automáticamente
                            ataques.add(new Ataque(nombreAtk, poderAtk)); // Se agrega ataque
                            break;
                        } else {
                            System.out.println("¡Ey! Elige una opción válida");
                            scanner.nextLine();
                        }
                    } else {
                        System.out.println("Por favor, elige un número.");
                        scanner.nextLine();
                    }
                }
            }
        } 
        // SELECCIÓN AUTOMÁTICA
        else if (!confirmo) {
            for (int i = 0; i < 4; i++) {
                nombreAtk = arsenal[aleatorioInt(arsenal.length-1, 0, true, repetidos)];
                poderAtk = aleatorioFloat(100f, 50f);
                ataques.add(new Ataque(nombreAtk, poderAtk));
            }
        }
        return ataques; // Se retorna ArrayList de ataques
    }

    // Uso del patrón "método de fábrica" para instanciar directamente al Pokemon en la clase
    public static Pokemon instanciarPokemon(boolean confirmo, ArrayList<Integer> repetidos) {
        
        // Inicializando variables locales
        int hpPokemon;
        TipoAtaquePokemon tipoPokemon;
        String nombrePokemon = "";
        ArrayList<Ataque> ataquesPokemon = new ArrayList<>();
    
        hpPokemon = aleatorioInt(300, 50, false, repetidos); // Se elige hpPokemon automáticamente

        System.out.println("¿quien ES ese POKEMON? 🤔🕶️");
        System.out.println("¡Es hora de elegir tu Pokemon!");

        // Para impedir que se registre un nombre vacío
        while (nombrePokemon.isEmpty()) {
            System.out.print("Ingrese el nombre de este pokemon: ");
            nombrePokemon = scanner.nextLine().trim(); // Se eliminan espacios en blanco al inicio y al final

            if (nombrePokemon.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Inténtalo de nuevo.");
            }
        }

        tipoPokemon = Pokemon.elegirTipo(confirmo, repetidos);
        ataquesPokemon = Pokemon.capturarAtaques(tipoPokemon, confirmo, nombrePokemon);
        System.out.println("Las unidades de vida (HP) y la potencia de cada ataque son aleatorios, ¡Buena suerte!");

        return new Pokemon(nombrePokemon, tipoPokemon, ataquesPokemon, hpPokemon); // Se retorna un nuevo Pokemon
    }

    // Método para elegir ataque
    public void atacar(Pokemon enemigo) {

        // Mostrando ataques disponibles
        System.out.println("Tus ataques (" + this.getNombre() + " / " + this.getTipo() + "):");
        for (int i = 0; i < ataques.size(); i++) {
            System.out.println((i+1) + ". " + ataques.get(i).getNombre() + " - daño: " + ataques.get(i).getPoder());
        }
        while(true) {
            if (scanner.hasNextInt()) {
                // Para asegurar que es un entero
                int eleccion = scanner.nextInt();
                scanner.nextLine();
                if (eleccion > 0 && eleccion <= ataques.size()){
                    // Para que la elección esté dentro del rango
                    Ataque ataqueElegido = ataques.get((eleccion-1));
                    System.out.println(this.getNombre() + ", " + "¡" + ataqueElegido.getNombre() + "!");
                    enemigo.daño(ataqueElegido.getPoder(), this); // Se invoca a la función que aplica el daño
                    break;
                } else {
                    System.out.println("¡Ey! Elige una opción válida");
                    scanner.nextLine();
                }
            }
            else {
                System.out.println("Por favor, elige un número.");
                scanner.nextLine();
            }
        }
    }

    // Método para calcular el daño recibido
    public void daño(float atk, Pokemon enemigo){
        TipoAtaquePokemon[] counters;
        counters = this.getTipo().getCounter(); // Obteniendo counters

        if(Arrays.asList(counters).contains(enemigo.getTipo())) {
            System.out.println("¡Ataque súper efectivo! " + enemigo.getNombre() + " es counter de " + this.getNombre());
            atk *= 1.3f; // En caso de que el enemigo sea counter, aumenta poderAtk 30%
        }
        if(atk >= this.hp){
            // Si el atk derrota al Pokemon
            this.hp = 0;
            vivo = !vivo; // Inhabilitar al Pokemon
        } else {
            // Se resta poderAtk a hp del Pokemon
            this.hp -= atk;
            System.out.println(this.getNombre() + " ha recibido " + atk + " daño, hp = " + hp);
        }
    }

    // Método aleatorio para Float
    public static float aleatorioFloat(float max, float min) {
        Random r = new Random(); 
        float resultado = r.nextFloat() * (max - min) + min;
        return resultado;
    }

    // Método aleatorio para enteros
    private static int aleatorioInt(int max, int min, boolean repetirImporta, ArrayList<Integer> repetidos) {
        Random r = new Random(); 
        int resultado = r.nextInt(max - min + 1) + min;

        if (repetirImporta){
            // Para evitar repetición de ataques. En ese caso particular repetirImporta
            while(repetidos.contains(resultado)) {   
                resultado = r.nextInt(max - min + 1) + min;
            }
            repetidos.add(resultado);
        }
        return resultado;
    }

    // Métodos heredados y sobrescritos
    @Override
    public void entrada() {
        System.out.println(getNombre() + ", ¡Yo te elijo!");
    }

    @Override
    public void celebracion() {
        System.out.println("Yupii");
    }
}