package aed;

public class Traslado {

    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;
    private int handleTimeStamp;
    private int handleRedito;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp) { //O(1) pues cada instruccion es una asignacion
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public void modificarHandleTimeStamp(int valor) { //O(1). asignacion
        this.handleTimeStamp = valor;
    }

    public void modificarHandleRedito(int valor) { //O(1). asignacion
        this.handleRedito = valor;
    }

    public int id() { //O(1)
        return id;
    }

    public int origen() { //O(1)
        return origen;
    }

    public int destino() { //O(1)
        return destino;
    }

    public int gananciaNeta() { //O(1)
        return gananciaNeta;
    }

    public int timestamp() { //O(1)
        return timestamp;
    }

    public int handleTimeStamp() { //O(1)
        return handleTimeStamp;
    }

    public int handleRedito() { //O(1)
        return handleRedito;
    }

}
