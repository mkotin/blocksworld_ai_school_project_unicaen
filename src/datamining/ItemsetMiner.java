package datamining;

import java.util.Set;

public interface ItemsetMiner {
    
    public BooleanDatabase getDatabase();

    public Set<Itemset> extract(float minfr);
}
