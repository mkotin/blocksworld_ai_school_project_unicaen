package datamining;

import java.util.HashSet;
import java.util.Set;

import modelling.BooleanVariable;

public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    
    protected BooleanDatabase database;

    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    public BooleanDatabase getDatabase() {
        return database;
    }

    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {
        for(Itemset itemset: itemsets) {
            if(itemset.getItems().equals(items))
                return itemset.getFrequency();
        }

        throw new IllegalArgumentException("Items does not exists in itemsets");
    }

    public static float confidence(Set<BooleanVariable> premise, 
    Set<BooleanVariable> conclusion, Set<Itemset> itemsets) {
        Set<BooleanVariable> rule = new HashSet<>();
        rule.addAll(premise);
        rule.addAll(conclusion);

        return frequency(rule, itemsets) / frequency(premise, itemsets);
    }

    

    
}
