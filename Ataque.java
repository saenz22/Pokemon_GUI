public class Ataque {

    // Atributos
    private String nombre;
    private float poder;

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public float getPoder() {
        return poder;
    }
    public void setPoder(float poder) {
        this.poder = poder;
    }

    // Constructor
    public Ataque(String nombre, float poder){
        this.nombre = nombre;
        this.poder = poder;
    }
}