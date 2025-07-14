package aed;

import java.util.ArrayList;

public class LibretaCiudades {

    private Ciudad[] ciudades;
    private Heap<Ciudad> heapSuperavit;
    private ArrayList<Integer> ciudadesMayorPerdida;
    private ArrayList<Integer> ciudadesMayorGanancia;

    public LibretaCiudades(int cantCiudades) {
        SuperavitComparator comp = new SuperavitComparator();
        ciudadesMayorGanancia = new ArrayList<>();
        ciudadesMayorPerdida = new ArrayList<>();
        ciudades = new Ciudad[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) {
            Ciudad ciudad = new Ciudad(i);
            ciudades[i] = ciudad;
            ciudadesMayorGanancia.add(i);
            ciudadesMayorPerdida.add(i);
        }
        this.heapSuperavit = new Heap<>(ciudades, comp);

    }//O(|c|). Agregamos una por una las ciudades a la lista "ciudades". 

    public void sumarGanancia(int ciudad, int valor) {
        int mayorGananciaActual = 0;
        if (!ciudadesMayorGanancia.isEmpty()) {
            mayorGananciaActual = ciudades[ciudadesMayorGanancia.get(0)].ganancia();
        }
        ciudades[ciudad].modificarGanancia(ciudades[ciudad].ganancia() + valor); //asignacion O(1)
        actualizarCiudadesMayorGanancia(ciudad, mayorGananciaActual); //O(1)
        actualizarSuperavit(ciudad);
    }//O(log |c|). Por actualizar el heapSuperavit.

    private void actualizarCiudadesMayorGanancia(int ciudad, int mayorGanancia) {
        if (ciudadesMayorGanancia.isEmpty()) {
            ciudadesMayorGanancia.add(ciudad); //O(1)
        } else {
            if (ciudades[ciudad].ganancia() > mayorGanancia) {
                ciudadesMayorGanancia.clear(); //O(1)
                ciudadesMayorGanancia.add(ciudad); //O(1)
            } else if (ciudades[ciudad].ganancia() == mayorGanancia) {
                ciudadesMayorGanancia.add(ciudad); //O(1)
            }
        }
    }//O(1). Solo hacemos asignaciones, comparaciones y en caso de ser necesario agregamos un elemento a un array.

    public void sumarPerdida(int ciudad, int valor) {
        int mayorPerdidaActual = 0;
        if (!ciudadesMayorPerdida.isEmpty()) {
            mayorPerdidaActual = ciudades[ciudadesMayorPerdida.get(0)].perdida();
        }
        ciudades[ciudad].modificarPerdida(ciudades[ciudad].perdida() + valor);
        actualizarCiudadesMayorPerdida(ciudad, mayorPerdidaActual);
        actualizarSuperavit(ciudad);
    }//O(log |c|). Por actualizar el heapSuperavit.

    private void actualizarCiudadesMayorPerdida(int ciudad, int mayorPerdida) {
        if (ciudadesMayorPerdida.isEmpty()) {
            ciudadesMayorPerdida.add(ciudad);
        } else {
            if (ciudades[ciudad].perdida() > mayorPerdida) {
                ciudadesMayorPerdida.clear();//O(1)
                ciudadesMayorPerdida.add(ciudad);
            } else if (ciudades[ciudad].perdida() == mayorPerdida) {
                ciudadesMayorPerdida.add(ciudad);
            }
        }
    }//O(1). Solo hacemos asignaciones, comparaciones y en caso de ser necesario agregamos un elemento a un array.

    private void actualizarSuperavit(int ciudad) {
        heapSuperavit.actualizar(ciudades[ciudad].handleSuperavit());
    }//O(log |c|). HeapSuperavit debe ser actualizado cada vez que hay un despacho. Para eso usamos los metodos siftup y siftdown de la clase Heap.
    // ambos metodos son de orden O(log n). Por ende, actualizar el superavit tambien lo sera, ya que aparte de eso solo usamos asignaciones.

    public int ciudadMayorSuperavit() {
        return heapSuperavit.verMayorPrioridad().id();
    }//O(1). Solo accedemos a un atributo de la clase Ciudades.

    public ArrayList<Integer> listaCiudadesMayorPerdida() {
        return ciudadesMayorPerdida;
    }//O(1)

    public ArrayList<Integer> listaCiudadesMayorGanancia() {
        return ciudadesMayorGanancia;
    }//O(1)

}
