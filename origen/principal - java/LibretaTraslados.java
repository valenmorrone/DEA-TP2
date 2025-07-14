package aed;

public class LibretaTraslados {

    private Heap<Traslado> trasladosRedito;
    private Heap<Traslado> trasladosTimeStamp;
    private int cantTraslados;
    private int gananciaTotal;
    private int trasladosDespachados;

    public LibretaTraslados(Traslado[] traslados) {
        ReditoComparator compRedito = new ReditoComparator();
        TimeStampComparator compTimeStamp = new TimeStampComparator();
        this.trasladosRedito = new Heap<>(traslados, compRedito); //O(|t|)
        this.trasladosTimeStamp = new Heap<>(traslados, compTimeStamp);//O(|t|)
        this.cantTraslados = traslados.length;
        this.gananciaTotal = 0;
        this.trasladosDespachados = 0;
    } //O(|t|). Creamos dos Heaps, el constructor de la clase Heap tiene complejidad O(|t|). 

    public void agregarTraslado(Traslado traslado) {
        trasladosRedito.agregar(traslado);
        trasladosTimeStamp.agregar(traslado);
        cantTraslados++;
    } //Agregar a un heap es de orden O(log n). Lo agrega al final y hace sift up hasta la raiz.

    public void despacharAntiguo() { 
        Traslado despachado = trasladosTimeStamp.obtenerMayorPrioridad(); //
        trasladosDespachados++;
        gananciaTotal = gananciaTotal + despachado.gananciaNeta();
        trasladosRedito.eliminar(despachado.handleRedito());
        cantTraslados--;
    }//O(log n) porque obtenerMayorPrioridad cuesta O(log n). Tambien lo elimina del heapRedito, que al tener el handle lo podemos hacer en orden O(log n)

    public void despacharRedituable() { 
        Traslado despachado = trasladosRedito.obtenerMayorPrioridad();
        trasladosDespachados++;
        gananciaTotal = gananciaTotal + despachado.gananciaNeta();
        trasladosTimeStamp.eliminar(despachado.handleTimeStamp());
        cantTraslados--;
    } //O(log n) idem que en despachatAntiguo

    public Traslado trasladoMasRedituable() { 
        return trasladosRedito.verMayorPrioridad();
    } //O(1) porque el metodo verMayorPrioridad de la clase Heap cuesta O(1)

    public Traslado trasladoMasAntiguo() { 
        return trasladosTimeStamp.verMayorPrioridad();
    } //O(1) porque el metodo verMayorPrioridad de la clase Heap cuesta O(1)

    public int gananciaPromedio() { 
        return gananciaTotal / trasladosDespachados;
    } //O(1). Es una cuenta.

    public int cantTraslados() { 
        return cantTraslados;
    } //O(1)

}
