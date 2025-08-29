import java.util.List;

public class Enfermera {
    private int IdEnfermera;
    private String nombreEnfermera;
    private List<Turno> turnoAsignado;
    private List<String> disponibilidad;

    public Enfermera(int idEnfermera, String nombreEnfermera) {
        this.IdEnfermera = idEnfermera;
        this.nombreEnfermera = nombreEnfermera;
    }
    public int getIdEnfermera() {
        return IdEnfermera;
    }
    public void setIdEnfermera(int idEnfermera) {
        IdEnfermera = idEnfermera;
    }
    public String getNombreEnfermera() {
        return nombreEnfermera;
    }
    public void setNombreEnfermera(String nombreEnfermera) {
        this.nombreEnfermera = nombreEnfermera;
    }

    public void asignarTurno(Turno turno) {
        turnoAsignado.add(turno);
    }

    public void asignarTurno(List<Turno> turnos) {
        turnoAsignado.addAll(turnos);
    }

}


