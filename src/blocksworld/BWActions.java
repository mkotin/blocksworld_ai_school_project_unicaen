package blocksworld;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import modelling.Variable;
import planning.Action;
import planning.BasicAction;

/* A class generating all actions in block world */
public class BWActions {
    private BlocksWorld blocksWorld;
    Set<Action> actions;


    /**
     * Build a new BWActions instance
     * @param nbBlocks
     * @param nbPiles
     */
    public BWActions(int nbBlocks, int nbPiles) {
        this.blocksWorld = new BlocksWorld(nbBlocks, nbPiles);
        this.actions = new HashSet<>();
        buildActions();
    }

    /**
     * Helper method to build a map of 3 key,value element
     * @param var1
     * @param var2
     * @param var3
     * @param value1
     * @param value2
     * @param value3
     * @return the map
     */
    private Map<Variable, Object> buildMap(Variable var1, Variable var2,
    Variable var3, Object value1, Object value2, Object value3) {
        Map<Variable, Object> res = new HashMap<>();
        res.put(var1, value1);
        res.put(var2, value2);
        res.put(var3, value3);

        return res;
    }

    /**
     * Build all actions;
     */
    private void buildActions(){
        moveB1FromB2ToB3Actions();
        moveB1FromB2ToPActions();
        moveB1FromPToB2Actions();
        moveBFromP1ToP2Actions();
    }

    /**
     * Method to move a block b1 from block b2 to block b3
     */
    private void moveB1FromB2ToB3Actions() {
        for(int b1=0; b1 < this.blocksWorld.getNbBlocks(); b1++) {
            for(int b2=0; b2 < this.blocksWorld.getNbBlocks(); b2++) {
                if(b1 == b2) continue;

                for(int b3=0; b3 < this.blocksWorld.getNbBlocks(); b3++) {
                    if(b3 == b1 || b3 == b2) continue;

                    // Variables needed for actions
                    Variable onb1 = this.blocksWorld.getBwVariables().getOnbVarByIndex(b1);
                    Variable fixedb1 = this.blocksWorld.getBwVariables().getFixedbVarByIndex(b1);
                    Variable fixedb2 = this.blocksWorld.getBwVariables().getFixedbVarByIndex(b2);
                    Variable fixedb3 = this.blocksWorld.getBwVariables().getFixedbVarByIndex(b3);

                    // Create preconditions
                    Map<Variable, Object> precondition =
                    buildMap(onb1, fixedb1, fixedb3, b2, false, false);

                    // Create effect
                    Map<Variable, Object> effect =
                    buildMap(onb1, fixedb2, fixedb3, b3, false, true);

                    // Create Action
                    this.actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }
    }

    /**
     * Method to move a block b1 from block b2 to pile p
     */
    private void moveB1FromB2ToPActions() {
        for(int b1=0; b1 < this.blocksWorld.getNbBlocks(); b1++) {
            for(int b2=0; b2 < this.blocksWorld.getNbBlocks(); b2++) {
                if(b1 == b2){ // skip if three blocks are differents
                    continue;
                }

                for(int p=0; p < this.blocksWorld.getNbPiles(); p++) {

                    // Variables needed for actions
                    Variable onb1 = this.blocksWorld.getBwVariables().getOnbVarByIndex(b1);
                    Variable fixedb1 = this.blocksWorld.getBwVariables().getFixedbVarByIndex(b1);
                    Variable freep = this.blocksWorld.getBwVariables().getFreepVarByIndex(p);
                    Variable fixedb2 = this.blocksWorld.getBwVariables().getFixedbVarByIndex(b2);

                    // Create preconditions
                    Map<Variable, Object> precondition =
                    buildMap(onb1, fixedb1, freep, b2, false, true);

                    // Create effect
                    Map<Variable, Object> effect =
                    buildMap(onb1, fixedb2, freep, (-p-1), false, false);

                    // Create Action
                    this.actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }
    }

    /**
     * Method to move a block b1 from pile p to block b2
     */
    private void moveB1FromPToB2Actions() {
        for(int b1=0; b1 < this.blocksWorld.getNbBlocks(); b1++) {
            for(int b2=0; b2 < this.blocksWorld.getNbBlocks(); b2++) {
                if(b1 == b2){ // skip if three blocks are differents
                    continue;
                }

                for(int p=0; p < this.blocksWorld.getNbPiles(); p++) {

                    // Variables needed for actions
                    Variable onb1 = this.blocksWorld.getBwVariables().getOnbVarByIndex(b1);
                    Variable fixedb1 = this.blocksWorld.getBwVariables().getFixedbVarByIndex(b1);
                    Variable freep = this.blocksWorld.getBwVariables().getFreepVarByIndex(p);
                    Variable fixedb2 = this.blocksWorld.getBwVariables().getFixedbVarByIndex(b2);

                    // Create preconditions
                    Map<Variable, Object> precondition =
                    buildMap(onb1, fixedb1, fixedb2, (-p-1), false, false);

                    // Create effect
                    Map<Variable, Object> effect =
                    buildMap(onb1, freep, fixedb2, b2, true, true);

                    // Create Action
                    this.actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }
    }

    /**
     * Method to move a block b from pile p1 to pile p2
     */
    private void moveBFromP1ToP2Actions() {
        for(int b=0; b < this.blocksWorld.getNbBlocks(); b++) {
            for(int p1=0; p1 < this.blocksWorld.getNbPiles(); p1++) {
                for(int p2=0; p2 < this.blocksWorld.getNbPiles(); p2++) {
                    if(p1 == p2){ // skip if three blocks are differents
                        continue;
                    }

                    // Variables needed for actions
                    Variable onb = this.blocksWorld.getBwVariables().getOnbVarByIndex(b);
                    Variable fixedb = this.blocksWorld.getBwVariables().getFixedbVarByIndex(b);
                    Variable freep2 = this.blocksWorld.getBwVariables().getFreepVarByIndex(p2);
                    Variable freep1 = this.blocksWorld.getBwVariables().getFreepVarByIndex(p1);

                    // Create preconditions
                    Map<Variable, Object> precondition =
                    buildMap(onb, fixedb, freep2, (-p1-1), false, true);

                    // Create effect
                    Map<Variable, Object> effect =
                    buildMap(onb, freep1, freep2, (-p2-1), true, false);

                    // Create Action
                    this.actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }
    }

    // Getters
    public BlocksWorld getBlocksWorld() {
        return blocksWorld;
    }

    public Set<Action> getActions() {
        return actions;
    }


    

    
}
