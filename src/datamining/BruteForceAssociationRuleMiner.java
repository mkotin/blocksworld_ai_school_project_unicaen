package datamining;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import modelling.BooleanVariable;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }

    @Override
    public Set<AssociationRule> extract(float minimalFrequency, float minimalConfidence) {
        // initialisation des structures de donnees et recuperation de tous les motifs frequents
        Set<AssociationRule> res = new HashSet<>();
        Set<Itemset> frequent = (new Apriori(this.database)).extract(minimalFrequency);
        for (Itemset item : frequent) {
            Set<Set<BooleanVariable>> premises = allCandidatePremises(item.getItems()); // recupere tous les sous-ensembles possibles
            for (Set<BooleanVariable> premise : premises) {
                // on recupére la conclusion association  cette premisse
                Set<BooleanVariable> conclusion = new HashSet<>(item.getItems());
                conclusion.removeAll(premise);
                float confidence = confidence(premise, conclusion,frequent);
                if (confidence >= minimalConfidence) {
                    res.add(new AssociationRule(premise, conclusion,
                    frequency(item.getItems(), frequent), confidence));
                }
            }
        }
        return res;
    }


    public static  Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        
        Set<Set<BooleanVariable>> subset1 = new HashSet<>();
        int maxi = 1 << items.size(); 
        for (int i = 0; i < maxi; i++) { // generation tous les sous-ensembles
            Set<BooleanVariable> subset2 = new HashSet<>();
            Iterator<BooleanVariable> iterator = items.iterator();
            for (int j = 0; j < items.size(); j++) {
                BooleanVariable item = iterator.next();
                if (((i >> j) & 1) == 1) {
                    subset2.add(item);
                }
            }
            subset1.add(subset2);
        }
        subset1.remove(new HashSet<>()); // retire l'ensemble vide
        subset1.remove(items); // retire l'ensemble lui meme
        return subset1;
    }
    
}
