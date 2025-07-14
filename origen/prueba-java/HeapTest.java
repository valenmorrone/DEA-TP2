package aed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapTest {
    Heap<Traslado> heapRedito;
    Heap<Traslado> heapTimeStamp;

    ReditoComparator comparadorRedito;
    TimeStampComparator comparadorTimeStamp;

    Traslado[] traslados;

    @BeforeEach
    void init() {
        traslados = new Traslado[7];
        traslados[0] = new Traslado(1,2,3,2000,2);
        traslados[1] = new Traslado(2,0,1,110,15);
        traslados[2] = new Traslado(3,3,2,200,12);
        traslados[3] = new Traslado(4,4,5,5000,50);
        traslados[4] = new Traslado(5,5,6,300,20);
        traslados[5] = new Traslado(6,1,3,5000,31);
        traslados[6] = new Traslado(7,4,3,200,22);
    
        comparadorRedito = new ReditoComparator();
        comparadorTimeStamp = new TimeStampComparator();

        heapRedito = new Heap(traslados,comparadorRedito);
        heapTimeStamp = new Heap(traslados,comparadorTimeStamp);
    }
    @Test
    void testHeapify(){
        //cantidad de elementos
        assertEquals(7, heapRedito.cantElems());
        assertEquals(7, heapTimeStamp.cantElems());

        //Heap redito:
        Traslado t1 = heapRedito.obtenerMayorPrioridad();

        assertEquals(5000, t1.gananciaNeta());
        assertEquals(4, t1.id()); //probamos que ordene bien por id en caso de empate

        assertEquals(5000, heapRedito.obtenerMayorPrioridad().gananciaNeta());
        assertEquals(2000, heapRedito.obtenerMayorPrioridad().gananciaNeta());
        assertEquals(300, heapRedito.obtenerMayorPrioridad().gananciaNeta());
        assertEquals(200, heapRedito.obtenerMayorPrioridad().gananciaNeta());
        assertEquals(200, heapRedito.obtenerMayorPrioridad().gananciaNeta());
        assertEquals(110, heapRedito.obtenerMayorPrioridad().gananciaNeta());

        //HeapTimeStamp:
        assertEquals(2, heapTimeStamp.obtenerMayorPrioridad().timestamp());
        assertEquals(12, heapTimeStamp.obtenerMayorPrioridad().timestamp());
        assertEquals(15, heapTimeStamp.obtenerMayorPrioridad().timestamp());
        assertEquals(20, heapTimeStamp.obtenerMayorPrioridad().timestamp());
        assertEquals(22, heapTimeStamp.obtenerMayorPrioridad().timestamp());
        assertEquals(31, heapTimeStamp.obtenerMayorPrioridad().timestamp());
        assertEquals(50, heapTimeStamp.obtenerMayorPrioridad().timestamp());
    }

    @Test
    void testAgregar(){

        Traslado t8 = new Traslado(8,1,4,6000,34);
        Traslado t9 = new Traslado(9,4,5,400,1);

        heapRedito.agregar(t8);
        heapRedito.agregar(t9);

        heapTimeStamp.agregar(t8);
        heapTimeStamp.agregar(t9);
        
        assertEquals(t8, heapRedito.verMayorPrioridad());
        assertEquals(t9, heapTimeStamp.verMayorPrioridad());   
    }

    @Test
    //Seguimos el algoritmo de heapify a mano para saber que valores esperar.
    void TestHandles(){

        //Vemos que la raiz este en la posicion 0
        assertEquals(0,heapRedito.verMayorPrioridad().handleRedito());
        assertEquals(0,heapTimeStamp.verMayorPrioridad().handleTimeStamp());
        

        //Vemos que la raiz del heap este bien ubicada en el otro. 
        assertEquals(1,heapTimeStamp.verMayorPrioridad().handleRedito());
        assertEquals(3,heapRedito.verMayorPrioridad().handleTimeStamp());

        //Obtenemos la raiz del heap Redito y la eliminamos del de TimeStamp
        Traslado raizRedito = heapRedito.obtenerMayorPrioridad();
        heapTimeStamp.eliminar(raizRedito.handleTimeStamp());

        //Obtenemos la raiz del heap TimeStamp y la eliminamos del de Redito
        Traslado raizTimeStamp = heapTimeStamp.obtenerMayorPrioridad();
        heapRedito.eliminar(raizTimeStamp.handleRedito());

        //ahora vemos que la raiz de cada heap este bien ubicada en el otro
        assertEquals(2,heapTimeStamp.verMayorPrioridad().handleRedito());
        assertEquals(2,heapRedito.verMayorPrioridad().handleTimeStamp());
    }


    @Test
    void TestMixto(){
        //eliminamos la raiz de cada heap
        Traslado raizRedito = heapRedito.obtenerMayorPrioridad();
        heapTimeStamp.eliminar(raizRedito.handleTimeStamp());
        Traslado raizTimeStamp = heapTimeStamp.obtenerMayorPrioridad();
        heapRedito.eliminar(raizTimeStamp.handleRedito());
        
        //eliminamos la raiz de cada heap
        Traslado raizRedito2 = heapRedito.obtenerMayorPrioridad();
        heapTimeStamp.eliminar(raizRedito2.handleTimeStamp());
        Traslado raizTimeStamp2 = heapTimeStamp.obtenerMayorPrioridad();
        heapRedito.eliminar(raizTimeStamp2.handleRedito());

        //chequeamos la cantidad de elementos
        assertEquals(3,heapRedito.cantElems()); 
        assertEquals(3,heapTimeStamp.cantElems());

        //chequeamos los handles
        assertEquals(2,heapRedito.verMayorPrioridad().handleTimeStamp());
        assertEquals(2,heapTimeStamp.verMayorPrioridad().handleRedito());

        Traslado t8 = new Traslado(8,1,4,6000,34);
        Traslado t9 = new Traslado(9,4,5,400,1);
        Traslado t10 = new Traslado(10,5,0,60,52);

        heapRedito.agregar(t8);
        heapRedito.agregar(t9);
        heapRedito.agregar(t10);
        
        heapTimeStamp.agregar(t8);
        heapTimeStamp.agregar(t9);
        heapTimeStamp.agregar(t10);
        
        assertEquals(6,heapRedito.cantElems()); 
        assertEquals(6,heapTimeStamp.cantElems());

        assertEquals(3,heapRedito.verMayorPrioridad().handleTimeStamp());
        assertEquals(1,heapTimeStamp.verMayorPrioridad().handleRedito());


    }
    void assertEquals(Traslado esperado, Traslado actual){
        if (esperado != actual){
            throw new AssertionError("Esperaba" + esperado + "pero obtuve" + actual);
        }
    }

    void assertEquals(int esperado, int actual){
        if (esperado != actual){
            throw new AssertionError("Esperaba " + esperado + " pero obtuve " + actual);
        }
    }





}