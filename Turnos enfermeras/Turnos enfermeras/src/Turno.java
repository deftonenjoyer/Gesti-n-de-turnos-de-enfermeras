import java.util.ArrayList;
import java.util.List;

public class Turno {
    private Area area;
    private String tipoTurno; //Mañana-Noche//
    private List<Enfermera> enfermerasAsignadas;
    private int enfermerasNecesarias;

    public Turno(Area area, String tipoTurno, int enfermerasNecesarias){
        this.area = area;
        this.tipoTurno = tipoTurno;
        this.enfermerasNecesarias = enfermerasNecesarias;
        this.enfermerasAsignadas = new ArrayList<Enfermera>();
    }

    public Area getArea() {
        return area;
    }
    public void setArea(Area area) {
        this.area = area;
    }


    public String getTipoTurno() {
        return tipoTurno;
    }
    public void setTipoTurno(){
        this.tipoTurno = tipoTurno;
    }


    public List<Enfermera> getEnfermerasAsignadas(){
        return enfermerasAsignadas;
    }
    public void setEnfermerasAsignadas(List<Enfermera> enfermerasAsignadas){
        this.enfermerasAsignadas = enfermerasAsignadas;
    }


    public int getEnfermerasNecesarias(){
        return enfermerasNecesarias;
    }
    public void setEnfermerasNecesarias(){
        this.enfermerasNecesarias = enfermerasNecesarias;
    }

    public boolean cmabiarEnfermera (Enfermera entra, Enfermera sale){
        if(!enfermerasAsignadas.contains(sale)){
            return false; //no está en la lista
        }
        if (enfermerasAsignadas.contains(entra)){
            return false; //ya está en este turno
        }

        //cambia
        enfermerasAsignadas.remove(sale);
        enfermerasAsignadas.add(entra);
        return true;

    }


}
