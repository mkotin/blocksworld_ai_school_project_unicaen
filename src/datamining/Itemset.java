package datamining;

import java.util.Set;

import modelling.BooleanVariable;

public class Itemset {
    
    protected Set<BooleanVariable> items;
    protected float frequency;

    public Itemset(Set<BooleanVariable> items, float frequency) {
        this.items = items;
        this.frequency = frequency;
    }

    public Set<BooleanVariable> getItems() {
        return items;
    }

    public float getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "[" + items.toString() + ", frequency=" + frequency + "]";
    }

    

    

    
}
