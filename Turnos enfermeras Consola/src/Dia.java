import java.util.ArrayList;
import java.util.List;

public class Dia {
    private String diaSemana;
    private List<Turno> turnos;

    public Dia(String diaSemana){
        this.diaSemana = diaSemana;
        this.turnos = new ArrayList<>();
    }

    public String getDiaSemana() {
        return diaSemana;
    }
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
}
