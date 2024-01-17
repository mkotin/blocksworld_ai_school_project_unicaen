package cp;

import java.util.List;
import java.util.Set;

import modelling.Variable;

public interface ValueHeuristic{

    public List<Object> ordering(Variable var, Set<Object> domaine);
    
}
