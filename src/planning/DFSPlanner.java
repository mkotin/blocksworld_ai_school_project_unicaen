package planning;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import modelling.Variable;

public class DFSPlanner implements Planner {

    protected Map<Variable, Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;
    // Compteur de sondes utilisé pour le suivi de performance
    private int sonde;

    public DFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.sonde = 0;
    }

    @Override
    public List<Action> plan() {
        List<Action> plan = new ArrayList<Action>();
        Set<Map<Variable, Object>> closed = new HashSet<Map<Variable, Object>>();
        return plan_aux(initialState, plan, closed);
    }

    private List<Action> plan_aux(Map<Variable, Object> instantiation, List<Action> plan,
    Set<Map<Variable, Object>> closed) {
        if(this.goal.isSatisfiedBy(instantiation)) {
            return plan;
        } else {
            for(Action action : this.actions) {
                if(action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);
                    if(!closed.contains(next)) { // todo: check if it works
                        plan.add(action);
                        closed.add(next);
                        sonde++;
                        List<Action> subplan = plan_aux(next, plan, closed);
                        if(subplan != null) {
                            return subplan;
                        } else {
                            // Calculate index of last element
                            int lastIndex = plan.size() - 1;
                    
                            // Delete last element by passing index
                            plan.remove(lastIndex);
                        }
                    }
                }
            }
            return null;
        }
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    public int getSonde(){
      return this.sonde;
    }
    
}
