package cp;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

/**
 * Cette classe définie une heuristic qui permet de choisir une meilleure variable
 * suivant une preference sur la taille des domaines
 */
public class DomainSizeVariableHeuristic  implements VariableHeuristic{
    
    protected boolean isPreferable;

    /**
     * Constructeur de la class DomainSizeVariableHeuristic
     * @param isPreferable est la preference de la taille du domaine (true si on veut des larges domaines false sinon)
     */
    public DomainSizeVariableHeuristic(boolean isPreferable){
        this.isPreferable = isPreferable;
    }

    @Override
    public Variable best (Set<Variable> variables,Map<Variable, Set<Object>> domaines){
        Comparator<Variable> comparator = (var1, var2) -> domaines.get(var1).size() - domaines.get(var2).size();
        return this.isPreferable ? Collections.max(variables, comparator):
        Collections.min(variables,comparator);
    }

    /**
     * cette fonction retourne la preference sur le domaine
     * @return boolean, l'etat de la preference (true si large dommaine false sinon)
     */
    public boolean isPreferable() {
        return isPreferable;
    }
}
