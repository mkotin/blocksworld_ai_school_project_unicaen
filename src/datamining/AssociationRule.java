package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class AssociationRule {
    
    protected Set<BooleanVariable> premise;
    protected Set<BooleanVariable> conclusion;
    protected float frequency;
    protected float confidence;

    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequency,
            float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    public float getFrequency() {
        return frequency;
    }

    public float getConfidence() {
        return confidence;
    }

    @Override
    public String toString() {
        return "AssociationRule [premise=" + premise.toString() + ", conclusion=" + conclusion.toString() + ", frequency=" + frequency
                + ", confidence=" + confidence + "]";
    }

    

    


    

    

    
}
