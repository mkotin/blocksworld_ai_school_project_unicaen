package planning;

import java.util.Map;

import modelling.Variable;

public interface Action {

    public boolean isApplicable(Map<Variable,Object> state);

    public Map<Variable, Object> successor(Map<Variable, Object> state);

    public int getCost();
}