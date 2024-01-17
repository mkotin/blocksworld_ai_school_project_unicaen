package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DifferenceConstraint implements Constraint {
    protected Variable v1;
    protected Variable v2;

    
    public DifferenceConstraint(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<Variable>();
        scope.add(v1);
        scope.add(v2);

        return scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> inst) {
        if(inst.get(this.v1) == null || inst.get(this.v2) == null) {
            throw new IllegalArgumentException("Each Variable of inst in the constraint scopemust have a value");
        }

        return !(inst.get(this.v1).equals(inst.get(this.v2)));
    }
    
}
