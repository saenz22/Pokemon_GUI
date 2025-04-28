public enum TipoAtaquePokemon {
    
    //Constantes del enum: cada tipo de ataque tiene un nombre y una lista de ataques asociados
    // Los nombres de los ataques son inventados por el mejor creador de ataques por lo que son los mejores ataques creados
    FUEGO(new String[]{"Infiernum", "inceniracion derretidora", "Ultrafuego", "Calor infrajuliano", "Explosion infernal", "Lavabosa", "fueguisimo", "Latigo lava", "Bola de fuego", "Llamarada"}, null),
    AGUA(new String[]{"Tsunami", "Olas mangnificas", "Hydroespada", "Ventisca helada", "Gotas abismales", "Rafagahidro", "Superchapuson", "Cascadadon", "Bombaguaso", "Chorrito de agua"}, null),
    PLANTA(new String[]{"Humoextravenenoso", "Lazos venenoso", "Espiral de espinas", "Semillerar", "Rodatronco", "Raices opresivas", "Enredadera", "Cañon frutas", "lluvia de hojas", "Hojazo"}, null),
    ELECTRICO(new String[]{"Rayolaser", "Electrorapinito", "Ferroinstataque", "Impacto sobrelectrizante", "Megatormenta electrica", "Corriente de rayos", "Descarga", "Electrimaximo", "Corrientazo", "Chispas"}, null),
    TIERRA(new String[]{"Montaña", "Sumergimiento placoso", "Bloque diamante", "Lodo Hyperarenoso", "Lanzamontes", "Enmurallar", "Apreton de arcilla", "Tierra sucias", "Rocal", "Polvo"},null);

// Atributos de cada tipo de ataque: 'ataques' es una lista de nombres de ataques que pertenecen a ese tipo.
    private String[] ataques;
    private TipoAtaquePokemon[] counter;

// Constructor del enum: recibe los ataques y los counters
    private TipoAtaquePokemon(String[] ataques, TipoAtaquePokemon[] counter) {
        this.ataques = ataques;
        this.counter = counter;
    }
// Método público para obtener los ataques de un tipo
    public String[] getAtaques() {
        return ataques;
    }
// Método  para obtener los tipos que contrarrestan a este tipo
    public TipoAtaquePokemon[] getCounter() {
        return counter;
    }
     // Bloque estático: se ejecuta una vez al cargar la clase
    // Aquí es donde realmente se asignan los tipos que contrarrestan a cada tipo de pokemon
    static { 
        FUEGO.counter = new TipoAtaquePokemon[]{AGUA};
        AGUA.counter = new TipoAtaquePokemon[]{PLANTA};
        PLANTA.counter = new TipoAtaquePokemon[]{FUEGO};
        ELECTRICO.counter = new TipoAtaquePokemon[]{TIERRA, PLANTA};
        TIERRA.counter = new TipoAtaquePokemon[]{ELECTRICO, AGUA};
    }
}