package cp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import modelling.Variable;

/**
 * Cette classe définie une heuristics qui range aléatoirement des domaines suivant
 * un générateur aléatoire
 */
public class RandomValueHeuristic implements ValueHeuristic{

    private Random rand;


    /**
     * Constructeur de la class Random
     * @param rand générateur aléatoire
     */

     public RandomValueHeuristic(Random rand){
        this.rand = rand;
     }

     
    /**
     * @return un domaine mélangé sous forme de liste des valeurs de la variable
     */
     @Override
     public List<Object> ordering(Variable variable, Set<Object> domaine){
        List<Object> shuffledList = new ArrayList<>(domaine);
        Collections.shuffle(shuffledList, rand);
        return shuffledList;
     }

    
}
