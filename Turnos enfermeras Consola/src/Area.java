import java.util.ArrayList;
import java.util.List;

public class Area {

    private String nombre;
    private List<Area> listaArea = new ArrayList<>();

    public Area(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}





