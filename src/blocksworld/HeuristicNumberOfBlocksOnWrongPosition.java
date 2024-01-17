package blocksworld;

import java.util.Map;

import modelling.Variable;
import planning.Heuristic;


/**
 * Class estimating the number of blocks that
 * are not in the correct position i.e the blocks that
 * are not in top of the correct block
 * Implements Heuristic
 */

public class HeuristicNumberOfBlocksOnWrongPosition implements Heuristic {

  private Map<Variable, Object> finalState;
  private final int WEIGHT = 2;

  /**
   * Constructor
   *
   * @param finalState : the final state
   */
  public HeuristicNumberOfBlocksOnWrongPosition(Map<Variable, Object> finalState) {
    this.finalState = finalState;
  }

  @Override
  public float estimate(Map<Variable, Object> state) {
    float estimation = 0;
    for (Variable var : state.keySet()) {
      if (var.getName().contains("on") && state.get(var) != finalState.get(var)) {
        estimation += WEIGHT;
      }
    }
    return estimation;
  }

}