import java.util.Arrays;

public class Persona {
    String [] datos;

    public Persona(String ... dato){
        this.datos = dato;
    }

    public String[] getDatos() {
        return datos;
    }

    public String getDatoCorreo(){
        return datos[3];
    }

    public String getDatoNombre(){
        return datos[1];
    }
    public String getDatoApellido(){
        return datos[2];
    }

    public void setDatos(String[] datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "datos=" + Arrays.toString(datos) +
                '}' + '\n';
    }
}
