package planning;

import java.util.Map;

import modelling.Variable;

public class BasicGoal implements Goal {

    Map<Variable, Object> goal;

    public BasicGoal(Map<Variable, Object> goal) {
        this.goal = goal;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> state) {
        return state.entrySet().containsAll(goal.entrySet());
    }
    
}
