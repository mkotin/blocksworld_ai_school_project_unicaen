package blocksworld;

import java.util.HashSet;
import java.util.Set;

import modelling.Constraint;
import modelling.Implication;
import modelling.Variable;

public class BWRegularyConstraints {
    private BlocksWorld blocksWorld;
    private Set<Constraint> constraints;


    public BWRegularyConstraints(BlocksWorld blocksWorld) {
        this.blocksWorld = blocksWorld;

        this.constraints = new HashSet<>();
        this.constraints.addAll(blocksWorld.getConstraints());
        createRegularyConstraints();
    }

    /**
     * Method to creat regiulary constraints
     */
    public void createRegularyConstraints() {
        Set<Variable> onVariables = this.blocksWorld.getBwVariables().getOnbVaribles();
        for (Variable on_i : onVariables) {
            for (Variable on_j : onVariables) {
                int i = BWVariables.getIndex(on_i);
                int j = BWVariables.getIndex(on_j);
                
                int k = j - (i - j); // on_j block value
                
                if (i != j) {
                    Set<Object> domain_i = new HashSet<>();
                    domain_i.add(j);
                    
                    Set<Object> domain_j = new HashSet<>();
                    for(int l=1; l<=this.blocksWorld.getNbPiles(); l++) {
                        domain_j.add(-l);
                    }

                    if (k >= 0 && k < this.blocksWorld.getNbBlocks())
                        domain_j.add(k);
                    Constraint constraint = new Implication(on_i, domain_i, on_j, domain_j);
                    constraints.add(constraint);
                }
            }
        }
    }

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

    // Getters and setters
    

    
}
