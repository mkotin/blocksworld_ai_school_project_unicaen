package blocksworld;

import java.util.HashSet;
import java.util.Set;

import modelling.Constraint;
import modelling.UnaryConstraint;
import modelling.Variable;

public class BWAscConstraints {
    private BlocksWorld blocksWorld;
    private Set<Constraint> constraints;

    public BWAscConstraints(BlocksWorld blocksWorld) {
        this.blocksWorld = blocksWorld;

        this.constraints = new HashSet<>();
        this.constraints.addAll(blocksWorld.getConstraints());
        createAscConstraints();
    }

    /**
     * Method to create ascending constraints
     */
    public void createAscConstraints() {
        Set<Variable> onVariables = this.blocksWorld.getBwVariables().getOnbVaribles();
        for (Variable on_i : onVariables) {
            int i = BWVariables.getIndex(on_i);

            Set<Object> domain_i = new HashSet<>();

            for(int j = -this.blocksWorld.getNbPiles(); j < i; j++) {
                domain_i.add(j);
            }

            constraints.add(new UnaryConstraint(on_i, domain_i));
        }
    }

    // Getters and setters
    public BlocksWorld getBlocksWorld() {
        return blocksWorld;
    }

    public void setBlocksWorld(BlocksWorld blocksWorld) {
        this.blocksWorld = blocksWorld;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }
    

    
}
