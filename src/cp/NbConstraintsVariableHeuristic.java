package cp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;
import modelling.Variable;

public class NbConstraintsVariableHeuristic implements VariableHeuristic{
   protected Set<Constraint> contraintes;
   protected boolean isPreferable; 

    public NbConstraintsVariableHeuristic(Set<Constraint> contraintes,boolean isPreferable){
        this.contraintes = contraintes;    
        this.isPreferable = isPreferable;
    }

    @Override
    public Variable best (Set<Variable> variables,Map<Variable, Set<Object>> domaines){
        Map<Variable, Integer> bestVariable = new HashMap<>();
        for(Variable variable : variables){
            int compteur = 0;
            for(Constraint constraint: this.contraintes) {
                if(constraint.getScope().contains(variable)) {
                    compteur++;
                }
            }
            bestVariable.put(variable, compteur);
        }
        if(this.isPreferable == true){
            Map.Entry<Variable,Integer> maxkey = Collections.max(bestVariable.entrySet(),Map.Entry.comparingByValue());
            return maxkey.getKey();
        }else{
            Map.Entry<Variable, Integer> minkey = Collections.min(bestVariable.entrySet(),Map.Entry.comparingByValue());
            return minkey.getKey();
        }
    }

    /**
     * La methode renvoie les contraintes de NbConstraintsVariableHeuristic
     * @return Set<Constraint>
     */
    public Set<Constraint> getContraintes() {
        return contraintes;
    }

    /**
     * Cette methode renvoie la preference 
     * @return boolean, vrai si on préfère les variables avec le plus grand domaine, faux dans le cas contraire
     */
    public boolean isPreferable() {
        return isPreferable;
    }
}
