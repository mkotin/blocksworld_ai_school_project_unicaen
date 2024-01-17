package cp;

import modelling.Variable;
import modelling.Constraint;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * La classe BacktrackSolver hérite de la classe AbstractSolver
 */


public class BacktrackSolver extends AbstractSolver {
    
    /**
     * creer une instance de la classe
     * @param variables
     * @param constraints
     */
   
    public BacktrackSolver(Set<Variable> variables,Set<Constraint> constraints){
        super(variables, constraints);
    }

    /**
     * la methode solve utilise l'algorithme backtracking executé à la main en classe
     */

    @Override
    public Map<Variable, Object> solve() {
        return this.backTrack(new HashMap<>(), new LinkedList<Variable>(this.variables));

    }

    public Map<Variable,Object> backTrack(Map<Variable,Object> instantiationPartielle, LinkedList<Variable> variableNonInstancified){
        if(variableNonInstancified.isEmpty()){
            return instantiationPartielle;
        }

        Variable x = variableNonInstancified.poll();
        for(Object v : x.getDomain()){
            Map<Variable,Object> newPartial = new HashMap<>(instantiationPartielle);
            newPartial.put(x,v);
            if(this.isConsistent(newPartial)){
                Map<Variable,Object> newGoal = backTrack(newPartial, variableNonInstancified);
                if(newGoal != null){
                    return newGoal;
                }
            }
        }
        variableNonInstancified.add(x);
        return null;
    }

}
