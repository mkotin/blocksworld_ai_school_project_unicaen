package cp;

import modelling.Variable;
import modelling.Constraint;

import java.util.Map;
import java.util.Set;

/**
 * cette classe abstraite permettant d'implementer un solveur de contraintes
 */

public abstract class AbstractSolver implements Solver{

    protected Set<Variable> variables;
    protected Set<Constraint> contraintes;

    /**
     * constructeur pemettant de creer une instance de AbstractSOlver
     * @param variable
     * @param contrainte
     */
    public AbstractSolver(Set<Variable> variable,Set<Constraint> contrainte){
        this.variables = variable;
        this.contraintes = contrainte;
    }

    /**
     * Une methode qui prend en argument une affectation partielle des variables et qui return
     * un booleen  indiquant si cette affectation satisfait toutes les contraintes qui portent sur des
     * variables instanciées dans l'affectation 
     * @param instantiationPartielle
     * @return boolean
     */


    public boolean  isConsistent(Map<Variable,Object> instantiationPartielle){
        for(Constraint constraint: contraintes){
            if(instantiationPartielle.keySet().containsAll(constraint.getScope())){
                 if(!constraint.isSatisfiedBy(instantiationPartielle)) {
                    return false;
                 }
            }
        }
        return true;

    }
    


    
}
