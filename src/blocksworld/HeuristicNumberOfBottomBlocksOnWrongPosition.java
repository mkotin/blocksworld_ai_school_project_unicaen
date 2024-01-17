package blocksworld;

import java.util.Map;

import modelling.Variable;
import planning.Heuristic;

/**
 * Class representing an heuristic estimating the number of
 * bottom blocks i.e blocks that directly on top of a pile
 * Implements Heuristic
 */
public class HeuristicNumberOfBottomBlocksOnWrongPosition implements Heuristic {
    private Map<Variable, Object> finalState;
    private final int WEIGHT = 10;

    /**
     * constructor
     *
     * @param finalState : the final state
     */
    public HeuristicNumberOfBottomBlocksOnWrongPosition(Map<Variable, Object> finalState) {
        this.finalState = finalState;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        float estimation = 0;
        for (Variable onb : finalState.keySet()) {
        // if block is a bottom block and is on wrong position we give it a heavier weight
        if (onb.getName().contains("on") && (int) state.get(onb) < 0 && !finalState.get(onb).equals(state.get(onb)))
            estimation += WEIGHT;
        if (onb.getName().contains("on") && (int) state.get(onb) >= 0 && !finalState.get(onb).equals(state.get(onb)))
            estimation += 5;
        }
        return estimation;
    }
}
