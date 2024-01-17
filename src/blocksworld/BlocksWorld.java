package blocksworld;

import java.util.HashSet;
import java.util.Set;

import modelling.Constraint;
import modelling.DifferenceConstraint;
import modelling.Implication;
import modelling.Variable;

/**
 * A class for blocksworld constraints
 */
public class BlocksWorld {
    
    private int nbBlocks; // number of blocks
    private int nbPiles; // number of piles
    private BWVariables bwVariables; // blocsworld variables
    private Set<Constraint> constraints;

    public BlocksWorld(int nbBlocks, int nbPiles) {
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;
        this.bwVariables = new BWVariables(nbBlocks, nbPiles);
        this.constraints = new HashSet<>();
        createDifferentceConstraints();
        createImplicationConstraints();
    }

    /**
     * Create all differences constraints
    */
    public void createDifferentceConstraints() {
        // For each couple of blocks {b,b'} onb != onb'
        Set<Variable> onbVariables = this.bwVariables.getOnbVaribles();
        
        for(Variable onb1: onbVariables) {
            for(Variable onb2: onbVariables) {
                if(!onb1.equals(onb2))
                    this.constraints.add(new DifferenceConstraint(onb1, onb2));
            }
        }
    }

    /**
     * Create all implications constraints
     */
    public void createImplicationConstraints() {
        
        for (Variable onb1 : this.bwVariables.getOnbVaribles()) {
            int b1Index = BWVariables.getIndex(onb1);

            // 1st constraint: for each block couple (b,b') if onb = b' then fixedb = true
            for(Variable fixedb2: this.bwVariables.getFixedbVariables()) {
                int b2Index = BWVariables.getIndex(fixedb2);

                if(b1Index != b2Index) {
                    Set<Object> onb1Domain = new HashSet<>();
                    onb1Domain.add(b2Index);

                    Set<Object> fiexdb2Domain = new HashSet<>();
                    fiexdb2Domain.add(true);

                    this.constraints.add(
                        new Implication(onb1, onb1Domain, fixedb2, fiexdb2Domain)
                    );
                }
            }

            // 2nd constraint: for each couple (b,p) b a block and p a pile
            // if onb = -(p+1), then freep = true
            for(Variable freep: this.bwVariables.getFreepVariables()) {
                int pIndex = BWVariables.getIndex(freep);

                Set<Object> onb1Domain2 = new HashSet<>();
                onb1Domain2.add(-(pIndex+1));

                Set<Object> freepDomain = new HashSet<>();
                freepDomain.add(false);

                this.constraints.add(
                        new Implication(onb1, onb1Domain2, freep, freepDomain)
                );
            }
        }
    }

    public int getNbBlocks() {
        return nbBlocks;
    }

    public void setNbBlocks(int nbBlocks) {
        this.nbBlocks = nbBlocks;
    }

    public int getNbPiles() {
        return nbPiles;
    }

    public void setNbPiles(int nbPiles) {
        this.nbPiles = nbPiles;
    }

    public BWVariables getBwVariables() {
        return bwVariables;
    }

    public void setBwVariables(BWVariables bwVariables) {
        this.bwVariables = bwVariables;
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
    }

    // Getters and setters
    
}
