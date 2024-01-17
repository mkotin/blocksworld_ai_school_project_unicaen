package modelling;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Implication implements Constraint {
    protected Variable v1;
    protected Set<Object> S1;
    
    protected Variable v2;
    protected Set<Object> S2;

    

    public Implication(Variable v1, Set<Object> S1, Variable v2, Set<Object> S2) {
        this.v1 = v1;
        this.S1 = S1;
        this.v2 = v2;
        this.S2 = S2;
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
        
        return (!this.S1.contains(inst.get(this.v1)) || this.S2.contains(inst.get(this.v2)));
    }
    
}
