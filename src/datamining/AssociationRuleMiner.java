package datamining;

import java.util.Set;

public interface AssociationRuleMiner {
    

    public BooleanDatabase getDatabase();

    public Set<AssociationRule> extract(float minfrequency, float minconfidence);
}
