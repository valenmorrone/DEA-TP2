package aed;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testsPropios {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;
    BestEffort sistema;
    LibretaCiudades libretaCiudades;
    LibretaTraslados libretaTraslados;

    @BeforeEach
    void init(){
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
            new Traslado(1, 0, 1, 100, 10),
            new Traslado(2, 0, 1, 400, 20),
            new Traslado(3, 3, 4, 500, 50),
            new Traslado(4, 4, 3, 500, 11),
            new Traslado(5, 1, 0, 1000, 40),
            new Traslado(6, 1, 0, 1000, 41),
            new Traslado(7, 6, 3, 2000, 42)
        };
        sistema = new BestEffort(cantCiudades, listaTraslados);
        libretaCiudades = new LibretaCiudades(cantCiudades);
        libretaTraslados = new LibretaTraslados(listaTraslados);
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " + e1 + " en el arreglo " + s2.toString());
        }
    }

    // Test para BestEffort

    @Test
    //testeamos el caso de que nos pidan despachar mas despacho de los que tenemos.
    void testDespacharTodosPorMasAntiguos() {
        int[] despachados = sistema.despacharMasAntiguos(10);
        assertArrayEquals(new int[] {1, 4, 2, 5, 6, 7, 3}, despachados, "Los traslados más antiguos no se despacharon en el orden esperado.");
    }

    @Test
    void testGananciaPromedioPorDespacho() {
        sistema.despacharMasRedituables(3);
        assertEquals(1333, sistema.gananciaPromedioPorTraslado(), "La ganancia promedio por traslado no es la esperada.");
    }

    // Tests para LibretaCiudades, si modificamos una ganancia el heap superavit debe modificarse.
    @Test
    void testSumarGananciaActualizaHeapSuperavit() {
        libretaCiudades.sumarGanancia(2, 1000);
        assertEquals(2, libretaCiudades.ciudadMayorSuperavit(), "La ciudad con mayor superávit no fue actualizada correctamente.");
    }
    //chequeamos que al sumar ganancias la lista de CiudadesMayorGanancia se actualice correctamente.
    @Test
    void testListaCiudadesMayorGanancia() {
        libretaCiudades.sumarGanancia(0, 500);
        libretaCiudades.sumarGanancia(1, 500);
        ArrayList<Integer> esperado = new ArrayList<>(Arrays.asList(0, 1));
        assertSetEquals(esperado, libretaCiudades.listaCiudadesMayorGanancia());
    }

    // Tests para LibretaTraslados


    @Test
    void testDespacharAntiguoActualizaCantidad() {
        int cantAntes = libretaTraslados.cantTraslados();
        libretaTraslados.despacharAntiguo();
        assertEquals(cantAntes - 1, libretaTraslados.cantTraslados(), "La cantidad de traslados no se decrementó correctamente al despachar el más antiguo.");
    }

    @Test
    void testDespacharRedituableActualizaCantidad() {
        int cantAntes = libretaTraslados.cantTraslados();
        libretaTraslados.despacharRedituable();
        assertEquals(cantAntes - 1, libretaTraslados.cantTraslados(), "La cantidad de traslados no se decrementó correctamente al despachar el más redituable.");
    }

    //testeamos que se actualice bien la ganancia promedio
    @Test
    void testDespachoYActualizacionGananciaPromedio() {
        sistema.despacharMasRedituables(2);
        assertEquals(1500, sistema.gananciaPromedioPorTraslado(), "La ganancia promedio no se calculó correctamente.");
        sistema.despacharMasRedituables(1);
        assertEquals(1333, sistema.gananciaPromedioPorTraslado(), "La ganancia promedio no se actualizó correctamente después del despacho.");
    }
   
    @Test
    //al tener la misma ganancia neta y tener a las mismas ciudades (la 0 y la 1) 
    //nos parecio un caso interesante para ver si CiudadesConMayorPredida/Ganancia al igual que superavit se actualizaba bien.
    void testDespacharTrasladosMismaGananciaNeta(){
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 300 ,10),
            new Traslado(2, 1, 0, 300, 11)
        };
        BestEffort sistema = new BestEffort(2, traslados);
        sistema.despacharMasRedituables(2);
        ArrayList<Integer> ciudadesMayorGanancia = sistema.ciudadesConMayorGanancia();
        assertTrue(ciudadesMayorGanancia.contains(0) && ciudadesMayorGanancia.contains(1),
            "Error: Las ciudades con mayor ganancia deberían ser [0, 1]");
            ArrayList<Integer> ciudadesMayorPerdida = sistema.ciudadesConMayorPerdida();
            assertTrue(ciudadesMayorPerdida.contains(0) && ciudadesMayorPerdida.contains(1),
                "Error: Las ciudades con mayor pérdida deberían ser [0, 1]");
    
            // Verificar ciudad con mayor superávit
            int ciudadMayorSuperavit = sistema.ciudadConMayorSuperavit();
            assertEquals(0, ciudadMayorSuperavit, "Error: se espera ciudad 0 pues se desempata por timestamp");
        }
    @Test
    public void testDespacharMasRedituables() {
        // Caso : Usar la función despacharMasRedituables y verificar qué traslado se despacha
        Traslado[] traslados = {
            new Traslado(1, 0, 1, 300, 10),
            new Traslado(2, 1, 0, 300, 11)
        };
        BestEffort sistema = new BestEffort(2, traslados);

        // Despachar el traslado más redituable, debe despachar el 1 porque tiene menor id.
        int[] despachados = sistema.despacharMasRedituables(1);
        assertEquals(1, despachados[0], "Error: Se esperaba que el traslado con id 1 fuera el primero en ser despachado");
    }
    @Test
    // Caso de prueba para verificar que todas las ciudades aparecen en mayor ganancia y pérdida sin despachos
    public void testCiudadesSinDespachos() {
            // Preparar datos de traslados
            Traslado[] traslados = {
                new Traslado(1, 0, 1, 500, 1),
                new Traslado(2, 2, 3, 300, 2),
                new Traslado(3, 1, 2, 400, 3),
                new Traslado(4, 3, 0, 200, 4)
            };
    
            BestEffort sistema = new BestEffort(4, traslados);
    
            // Verificar que sin despachos todas las ciudades se devuelven en las listas de ganancia y pérdida
            assert sistema.ciudadConMayorSuperavit() == 0 : "Error: Debió devolver 0 para superávit sin despachos";
            
            ArrayList<Integer> ciudadesGanancia = sistema.ciudadesConMayorGanancia();
            ArrayList<Integer> ciudadesPerdida = sistema.ciudadesConMayorPerdida();
            
            assert ciudadesGanancia.size() == 4 : "Error: La lista de mayor ganancia debió contener todas las ciudades sin despachos";
            assert ciudadesPerdida.size() == 4 : "Error: La lista de mayor pérdida debió contener todas las ciudades sin despachos";
            
            for (int i = 0; i < 4; i++) {
                assert ciudadesGanancia.contains(i) : "Error: Ciudad " + i + " no está en la lista de mayor ganancia";
                assert ciudadesPerdida.contains(i) : "Error: Ciudad " + i + " no está en la lista de mayor pérdida";
            }
    
            System.out.println("testCiudadesSinDespachos pasó exitosamente.");
        }
    
       
    @Test
    // Caso de prueba con despachos para verificar valores de superávit, ganancia y pérdida
    public void testCiudadesDespachando() {
            // Preparar datos de traslados
            Traslado[] traslados = {
                new Traslado(1, 0, 1, 500, 1),
                new Traslado(2, 2, 3, 300, 2),
                new Traslado(3, 1, 2, 400, 3),
                new Traslado(4, 3, 0, 200, 4)
            };
    
            BestEffort sistema = new BestEffort(4, traslados);
    
            // Realizar despachos y verificar resultados
            sistema.despacharMasRedituables(2);
    
            // Verificar superávit, ganancia y pérdida después de algunos despachos
            assert sistema.ciudadConMayorSuperavit() == 0 : "Error: Ciudad con mayor superávit incorrecta";
            assert sistema.ciudadesConMayorGanancia().contains(0) : "Error: La ciudad con mayor ganancia no es correcta";
            assert sistema.ciudadesConMayorPerdida().contains(1) : "Error: La ciudad con mayor pérdida no es correcta";
    
            System.out.println("testCiudadesDespachando pasó exitosamente.");
        }
    // Agregar traslados a LibretaTraslados y despachar en orden de rentabilidad
    @Test
    public  void testAgregarYDespacharTraslados() {
                Traslado[] traslados = {
                    new Traslado(1, 0, 1, 300, 1),
                    new Traslado(2, 1, 2, 500, 2),
                    new Traslado(3, 2, 0, 100, 3)
                };
                LibretaTraslados libreta = new LibretaTraslados(traslados);
        
                // Verifica que el traslado más rentable es el de ID 2
                Traslado trasladoMasRedituable = libreta.trasladoMasRedituable();
                assert trasladoMasRedituable.id() == 2 : "Error: El traslado más rentable debería ser el de ID 2";
        
                // Despachar el traslado más rentable y verificar que el siguiente sea el de ID 1
                libreta.despacharRedituable();
                trasladoMasRedituable = libreta.trasladoMasRedituable();
                assert trasladoMasRedituable.id() == 1 : "Error: Después de despachar, el traslado más rentable debería ser el de ID 1";
        
                System.out.println("testAgregarYDespacharTraslados pasó exitosamente.");
            }
        
    // Verificar que LibretaCiudades devuelve correctamente la ciudad con mayor superávit
    @Test
    public  void testCiudadMayorSuperavit() {
                LibretaCiudades libreta = new LibretaCiudades(3);
        
                // Modificar superávit de las ciudades
                libreta.sumarGanancia(0, 300);
                libreta.sumarGanancia(1, 100);
                libreta.sumarPerdida(0, 50);  // superávit de ciudad 0 = 250
                libreta.sumarPerdida(2, 200); // superávit de ciudad 2 = -200
        
                int ciudadMayorSuperavit = libreta.ciudadMayorSuperavit();
                assert ciudadMayorSuperavit == 0 : "Error: La ciudad con mayor superávit debería ser la ciudad 0";
        
                System.out.println("testCiudadMayorSuperavit pasó exitosamente.");
            }
            @Test
            public void testDespacharDeMas() {
                Traslado[] traslados = {
                    new Traslado(1, 0, 1, 300, 10),
                    new Traslado(2, 1, 0, 300, 11)
                };
                BestEffort sistema = new BestEffort(2, traslados);
        
                // Despachar el traslado más redituable. Tengo dos traslados y me pide que despache 5 entonces despacha esos 2 y no se cuelga.
                int[] despachados = sistema.despacharMasRedituables(5);
                assertEquals(1, despachados[0], "Error: Se esperaba que el traslado con id 1 fuera el primero en ser despachado y que no se cuelgue el programa");
        }
        }
        
        
    
    



