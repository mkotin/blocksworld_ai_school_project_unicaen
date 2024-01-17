package cp;

import modelling.Variable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import modelling.Constraint;

public class MACSolver extends AbstractSolver {
    private ArcConsistency arcConsistency;

    // Constructeur de la classe MACSolver
    public MACSolver(Set<Variable> variables, Set<Constraint> contraintes) {
        super(variables, contraintes);
        this.arcConsistency = new ArcConsistency(contraintes);
    }

    // Méthode pour résoudre le CSP en utilisant l'algorithme MAC
    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> domaines = new HashMap<>();
        for (Variable var : variables) {
            domaines.put(var, var.getDomain());
        }
        return mac(new HashMap<>(), new LinkedList<>(variables), domaines);
    }

    /**
     * La méthode MAC
     *
     * @param partialInstanciation    : l'affectation partielle
     * @param nonInstanciatedVariables : liste des variables non instanciées
     * @param domaines                : l'ensemble de domaines des variables
     * @return l'état de la solution si toutes les contraintes sont satisfaites
     */

    public Map<Variable, Object> mac(Map<Variable, Object> partialInstanciation, LinkedList<Variable> nonInstanciatedVariables,
            Map<Variable, Set<Object>> domaines) {

        if (nonInstanciatedVariables.isEmpty()) {
            return partialInstanciation;
        }

        if (!arcConsistency.ac1(domaines)) {
            return null; //Aucune solution n'est possible
        }

        Variable var = nonInstanciatedVariables.poll();
        for (Object value : domaines.get(var)) {
            Map<Variable, Object> state = new HashMap<>(partialInstanciation);
            state.put(var, value);
            if (isConsistent(state)) {
                Map<Variable, Object> newState = mac(state, nonInstanciatedVariables, domaines);
                if (newState != null) {
                    return newState;
                }
            }
        }
        nonInstanciatedVariables.add(var);
        return null;
    }
}
