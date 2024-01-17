package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnaryConstraint implements Constraint {
    protected Variable v;
    protected Set<Object> S;
    
    public UnaryConstraint(Variable v, Set<Object> s) {
        this.v = v;
        S = s;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<Variable>();
        scope.add(v);

        return scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> inst) {
        if(inst.get(this.v) == null) {
            throw new IllegalArgumentException("Each Variable of inst in the constraint scopemust have a value");
        }

        return this.S.contains(inst.get(this.v));
    }
}
