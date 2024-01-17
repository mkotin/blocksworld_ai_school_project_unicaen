package cp;

import java.util.Map;

import modelling.Variable;

/**
 * cette interface définie un Solver
 */

public interface Solver {

    /**
     * solve est une méthode de résolution d'un probleme et qui propose une solution
     * @return Map<Variable,object>
     */
    Map<Variable, Object> solve();
    
}
