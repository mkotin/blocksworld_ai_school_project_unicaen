package datamining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import modelling.BooleanVariable;

public class Apriori extends AbstractItemsetMiner {

    public Apriori(BooleanDatabase database) {
        super(database);
    }

    /**
     * Return all singletons itemset with given frequency
     * @param frequency
     * @return all singletons itemset with given frequency
     */
    public Set<Itemset> frequentSingletons(float frequency) {
        Set<BooleanVariable> items = this.database.getItems(); // all database items

        Set<Itemset> frequentSingletonsList = new HashSet<Itemset>();

        // Search frequent singletons in transaction
        for(BooleanVariable item: items) {
            Set<BooleanVariable> singleton = Collections.singleton(item);
            float singletonFrequency = this.frequency(singleton);

            if(singletonFrequency >= frequency) { // singleton is frequent
                frequentSingletonsList.
                add(new Itemset(singleton, singletonFrequency)); // add to return set
            }
        }

        return frequentSingletonsList;
    }

    /**
     * Combine two sorterdset with some conditions. Return null if conditions
     * are not matched
     * @param sset1
     * @param sset2
     * @return the combined sortedset
     */
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> sset1,
    SortedSet<BooleanVariable> sset2) {
        int k = sset1.size();

        if(k==0) {
            return null;
        }

        if(k == sset2.size()){ // condition 1 (same size) is satisfied
            SortedSet<BooleanVariable> subset1 = sset1.subSet(sset1.first(), sset1.last());
            SortedSet<BooleanVariable> subset2 = sset2.subSet(sset2.first(), sset2.last());

            // Check if the subsets are equal
            if(subset1.equals(subset2)) { // condition 2 is matched
                // Condition 3: check if the kth element are differents
                if(!sset1.last().equals(sset2.last())) { // condtion 3 is matched
                    SortedSet<BooleanVariable> combinedSet = new TreeSet<>(COMPARATOR);
                    combinedSet.addAll(sset1);
                    combinedSet.add(sset2.last());
                    return combinedSet;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> frequentSets) {
        // Check if all subsets are frequent
        for (BooleanVariable item : items) {
            SortedSet<BooleanVariable> subset = new TreeSet<>(COMPARATOR);
            subset.addAll(items);
            subset.remove(item);

            if (!frequentSets.contains(subset)) {
                return false;
            }
        }
        return true;
    }

     @Override
    public Set<Itemset> extract(float minFrequency) {
        // Frequents itemset (the result)
        Set<Itemset> frequentItemsets = new HashSet<>();

       // Add frequent singletons (k = 1, k is the size of itemset)
       frequentItemsets.addAll(this.frequentSingletons(minFrequency));

       // Frequents set of size k = 1
       ArrayList<SortedSet<BooleanVariable>> frequentSets = new ArrayList<>();
       for (Itemset itemset : frequentItemsets) {
        frequentSets.add(new TreeSet<>(COMPARATOR){{addAll(itemset.getItems());}});
       }
       
       // Generate frequents sets of size k with frequents set of size k-1
       // if frequent add to frequentItemsets
       while(!frequentSets.isEmpty()) {
            ArrayList<SortedSet<BooleanVariable>> nextGenFrequentSets = new ArrayList<>();
            
            for (int i=0; i < frequentSets.size(); i++) {
                for (int j=i+1; j < frequentSets.size(); j++) {
                    // generate candidates
                    SortedSet<BooleanVariable> sortedSet1 = frequentSets.get(i);
                    SortedSet<BooleanVariable> sortedSet2 = frequentSets.get(j);
                    SortedSet<BooleanVariable> candidate = combine(sortedSet1, sortedSet2);

                    if(candidate != null) {
                        // No need to calculate frequency if not all subsets frequents
                        if(allSubsetsFrequent(candidate, frequentSets)) { 
                            if(this.frequency(candidate) >= minFrequency) { 
                                frequentItemsets.add(new Itemset(candidate, frequency(candidate)));
                                nextGenFrequentSets.add(candidate);
                            }
                        }
                    }
                }
            }

            frequentSets = nextGenFrequentSets; // go to k+1
       }
       return frequentItemsets;
    }

    
}

