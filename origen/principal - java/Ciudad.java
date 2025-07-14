package aed;

public class Ciudad {

    private int id;
    private int ganancia;
    private int perdida;
    private int handleSuperavit;

    public Ciudad(int id) { //O(1) pues las intrucciones son todas asignaciones
        this.id = id;
        this.ganancia = 0;
        this.perdida = 0;
    }

    public void modificarHandleSuperavit(int valor) { //O(1) asignacion
        this.handleSuperavit = valor;
    }

    public void modificarGanancia(int valor) { //O(1) asignacion
        this.ganancia = valor;
    }

    public void modificarPerdida(int valor) { //O(1) asignacion
        this.perdida = valor;
    }

    public int id() { //O(1)
        return id;
    }

    public int ganancia() { //O(1)
        return ganancia;
    }

    public int perdida() { //O(1)
        return perdida;
    }

    public int handleSuperavit() { //O(1)
        return handleSuperavit;
    }
}
