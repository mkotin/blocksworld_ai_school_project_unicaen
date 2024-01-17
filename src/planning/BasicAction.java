package planning;

import java.util.HashMap;
import java.util.Map;

import modelling.Variable;

public class BasicAction implements Action {

    Map<Variable, Object> precondition;
    Map<Variable, Object> effect;
    int cost;

    

    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect, int cost) {
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> state) {
        return state.entrySet().containsAll(precondition.entrySet());
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state) {
        // Copying state for successor 
        Map<Variable, Object> successor = new HashMap<Variable, Object>();
        for(Map.Entry<Variable, Object> stateEntry: state.entrySet() ) {
            successor.put(stateEntry.getKey(), stateEntry.getValue());
        }
        

        // Applying effect 
        for(Map.Entry<Variable, Object> entry: effect.entrySet()) {
            Variable ekey = entry.getKey();
            Object evalue = entry.getValue();

            successor.put(ekey, evalue); // todo: check if we shoul put for non existing key also
        }

        return successor;
    }

    @Override
    public int getCost() {
        return cost;
    }
    
}
