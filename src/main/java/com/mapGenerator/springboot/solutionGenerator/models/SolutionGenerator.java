package com.mapGenerator.springboot.solutionGenerator.models;

import java.util.ArrayList;

public class SolutionGenerator {
    private ArrayList<String> defaultBlocks; // Instrucciones disponibles para generar la solucion
    private ArrayList<ArrayList<String>> solutions; // soluciones diferentes, cada solucion es una lista de instrucciones

    public ArrayList<String> getDefaultBlocks() {
        return defaultBlocks;
    }

    // Las instrucciones disponibles estaran definidas por la unidad a la que pertenezcan.
    // El peor caso es aquella unidad que tengan todas las instrucciones del plan de estudios disponibles

    // Tiene que cumplir las reglas de validación

    // (?) Combinaciones con repeticion - n sobre k,
    // siendo n defaultBlocks.length y 1(ese uno es avanzar/retrodecer) <= k
    // Siempre, casi siempre va a haber menos bloques disponibles de los que vamos a utilizar
    // Entonces, a lo mejor podemos hacer set de combinaciones
    // Ejemplo de wikipedia: https://es.wikipedia.org/wiki/Combinaciones_con_repetición
    // X={a, b, c, d}
    // aaa	aab	aac	aad	abb	abc	abd	acc	acd	add
    // bbb	bbc	bbd	bcc	bcd	bdd	ccc	ccd	cdd	ddd
    // Esas son las combinaciones disponibles, entonces ahora hago otra vez combinaciones pero de los resultados

    // Árbol con dos tipos de probabilidades a calcular:
    // Llamemos a 'a' uno de los sucesos necesarios y a 'b' el otro suceso necesario
    // Se hace la combinatoria de los otros tres elementos que no sean necesarios
    // Combinatoria de posición: Primero una combinatoria simple de bcd
    // y luego con los resultados pivotar la posición con a

    // Echar un ojo a Principio del palomar

    // Casos base
    // Qué pasa cuando solo hay un tipo de elemento (?)
    // Se muiltiplica n veces y se seleccionan subconjuntos de distintos tamaños para generar los mapas para esa unidad

    // defaultBlocks: instrucciones por defecto en la unidad
    // tempIndex: indices de la combinacion que se esta formando
    // r: tamaño de la combinación que se va a generar
    // start & end:
    public static void partialSolutionGenerator(ArrayList<ArrayList<String>> partialSolutions,
                                                ArrayList<String> defaultBlocks,
                                                int tempIndex[], int index, int r, int start, int end) {

        // https://www.geeksforgeeks.org/combinations-with-repetitions/

        // Cuando el indice se convierte en el limite, es que se puede guardar la solucion
        if (index == r) {
            ArrayList<String> partialSolution = new ArrayList<>();
            for (int i : tempIndex) {
                partialSolution.add(defaultBlocks.get(i));
            }
            partialSolutions.add(partialSolution);
        }
        // Elegir todos los elementos posibles de uno en uno. Como se puede repetir no se tiene en cuenta si ha sido
        // elegido ya. Se procede a la recurrencia.
        for (int i = 0; i <= end; i++) {
            tempIndex[index] = i;
            partialSolutionGenerator(partialSolutions, defaultBlocks, tempIndex, index + 1, r, i, end);
        }
    }

    // defaultBlocks: instrucciones por defecto en la unidad
    // r: tamaño de la combinación que se va a generar
    public static void solutionGenerator(ArrayList<String> defaultBlocks, int r) {
        // (?) r deberia pasar a la solucion parcial con su propio valor o se divide en partes para creaar subsoluciones

        // IMPORTANTE: que pasa si defaultBlocks.size() es mayor que r
        // 

        ArrayList<ArrayList<String>> partialSolutions = new ArrayList<>();
        int tempIndex[] = new int[r + 1];
        partialSolutionGenerator(partialSolutions, defaultBlocks, tempIndex, 0, r, 0, defaultBlocks.size() - 1);
        // Ahora tengo las soluciones parciales
        // (?) Separar las que son candidatas por si mismas del resto
        // Para que sea candidata debe cumplir los requisitos
        // Reglas que se me van ocurriendo:
        // Que haya siempre un avance o un retroceder
        // Si hay tres giros seguidos, cambiarlo por el giro contrario
        // No puede haber un avanzar y un retroceder seguidos, da igual el orden
    }
}