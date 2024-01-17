package datamining;

import java.util.Comparator;
import java.util.Set;

import modelling.BooleanVariable;

public abstract class AbstractItemsetMiner implements ItemsetMiner {

    protected BooleanDatabase database;

    public static final Comparator<BooleanVariable> COMPARATOR =
            (var1, var2) -> var1.getName().compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase database) {
        this.database = database;
    }



    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }

    public abstract Set<Itemset> extract(float minFrequency);

    public float frequency(Set<BooleanVariable> items) {
        float count = 0;

        for (Set<BooleanVariable> transaction : getDatabase().getTransactions()) {
            if (transaction.containsAll(items)) {
                count++;
            }
        }

        return count / getDatabase().getTransactions().size();
    }
    
}
