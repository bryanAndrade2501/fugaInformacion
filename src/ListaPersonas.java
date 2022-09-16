import java.util.ArrayList;

public class ListaPersonas {
    ArrayList<Persona> personas = new ArrayList<>();

    public void addPersona(Persona per){
        personas.add(per);
    }

    @Override
    public String toString() {
        return "ListaPersonas{" +
                "personas=" + personas +
                '}';
    }

    public int tamanio(){
        return personas.size();
    }

    public ArrayList<Persona> getPersonas() {
        return personas;
    }
}
