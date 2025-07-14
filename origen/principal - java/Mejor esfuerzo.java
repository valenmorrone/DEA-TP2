package aed;

import java.util.ArrayList;

public class BestEffort {

    private LibretaTraslados lTraslados;
    private LibretaCiudades lCiudades;

    public BestEffort(int cantCiudades, Traslado[] traslados) { 
        this.lTraslados = new LibretaTraslados(traslados);
        this.lCiudades = new LibretaCiudades(cantCiudades);
    }//O(|t| + |c|). Llama a los constructores de dos clases. Que son de orden O(|t|) para libreta de Traslados y O(cantCiudades) para libreta de ciudades.
     // Las compejidades se suman ya que las operaciones son independientes y en secuencia.

    public void registrarTraslados(Traslado[] traslados) {   
        for (int i = 0; i < traslados.length; i++) {
            lTraslados.agregarTraslado(traslados[i]);
        }
    }//O(|t| log t) Por cada traslado del array Traslados el bucle llama al metodo "agregarTraslados", que los agrega a un HEAP. Este metodo tiene complejidad log (t).
    

    public int[] despacharMasRedituables(int n) { 
        int cantDespachos;
        if (n > lTraslados.cantTraslados()) {
            cantDespachos = lTraslados.cantTraslados();
        } else {
            cantDespachos = n;
        }//O(1)
        int[] res = new int[cantDespachos];
        for (int i = 0; i < cantDespachos; i++) { //De este bucle sale la 'n' multiplicando en la compejidad final.
            Traslado trasladoDespachado = lTraslados.trasladoMasRedituable();
            res[i] = trasladoDespachado.id();
            lCiudades.sumarGanancia(trasladoDespachado.origen(), trasladoDespachado.gananciaNeta()); //O(log|c|)
            lCiudades.sumarPerdida(trasladoDespachado.destino(), trasladoDespachado.gananciaNeta()); //O(log|c|)
            lTraslados.despacharRedituable();// O(log |t|)
        }
        return res;
    }//O(n (log |t| + log |c|)). Por cada despacho, este proc debe eliminar el traslado de HeapRedito y de HeapTimeStamp lo cual es de complejidad O(log |t|).
     //Ademas, se actualiza el HeapSuperavit. Lo cual tiene complejidad O(log|c|). 
     // Las compejidades se suman ya que las operaciones son independientes y en secuencia. 
     //Este bucle se repite n veces.

    public int[] despacharMasAntiguos(int n) { 
        int cantDespachos;
        if (n > lTraslados.cantTraslados()) {
            cantDespachos = lTraslados.cantTraslados();
        } else {
            cantDespachos = n;
        } //O(1)
        int[] res = new int[cantDespachos];
        for (int i = 0; i < cantDespachos; i++) {//De este bucle sale la 'n' multiplicando en la compejidad final.
            Traslado trasladoDespachado = lTraslados.trasladoMasAntiguo();
            res[i] = trasladoDespachado.id();
            lCiudades.sumarGanancia(trasladoDespachado.origen(), trasladoDespachado.gananciaNeta());//O (log|c|)
            lCiudades.sumarPerdida(trasladoDespachado.destino(), trasladoDespachado.gananciaNeta());//O (log|c|)
            lTraslados.despacharAntiguo();// O(log |t|)
        }
        return res;
    }//O(n (log |t| + log |c|)). Por cada despacho, este proc debe eliminar el traslado de HeapRedito y de HeapTimeStamp lo cual es de complejidad O(log |t|).
     //Ademas, se actualiza el HeapSuperavit. Lo cual tiene complejidad O(log|c|). 
     // Las compejidades se suman ya que las operaciones son independientes y en secuencia. 
     //Este bucle se repite n veces.

    public int ciudadConMayorSuperavit() { //O(1)
        return lCiudades.ciudadMayorSuperavit();
    } //O(1). El superavit se actualiza cada vez que se hace un despacho. Tenemos las ciudes ordenadas por su superavit en valor decreciente,
    // por lo que acceder y devolver su raiz (es decir la ciudad de mayor superavit) es de Orden 1.

    public ArrayList<Integer> ciudadesConMayorGanancia() { 
        return lCiudades.listaCiudadesMayorGanancia();
    }//O(1). Devuelve atributo de la clase LibretaDeCiudades

    public ArrayList<Integer> ciudadesConMayorPerdida() { //O(1)
        return lCiudades.listaCiudadesMayorPerdida();
    }//O(1). Devuelve atributo de la clase LibretaDeCiudades

    public int gananciaPromedioPorTraslado() { //O(1)
        return lTraslados.gananciaPromedio();
    }//O(1). Devuelve atributo de la clase LibretaDeCiudades

}
