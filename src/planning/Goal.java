package planning;

import java.util.Map;

import modelling.Variable;

public interface Goal {
    
    public boolean isSatisfiedBy(Map<Variable,Object> state);
}
