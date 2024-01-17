package main;

import java.util.Set;

import blocksworld.BWDatamining;
import datamining.Apriori;
import datamining.AssociationRule;
import datamining.BooleanDatabase;
import datamining.BruteForceAssociationRuleMiner;
import datamining.Itemset;

public class MainDatamining {

	public static void main(String[] args) {

		BWDatamining bwD = new BWDatamining(5, 3);
		System.out.println("=================  Datamining for 5 blocks and 3 piles with min frequency 2/3 and confidence 95/10  =================*");
		System.out.println("********************  Frequent items  *********************");
		// Generating database
		BooleanDatabase database = bwD.generateDatabase(10000);
		// Instantiate apriori and launching extract to get frequent itemsets
		Apriori apriori = new Apriori(database);
		Set<Itemset> items = apriori.extract((float) 2 / 3);
		// Print result
		for (Itemset item : items) {
			System.out.println(item);
		}
		System.out.println();
		System.out.println("********************  Rules  *********************");
		// Instantiate associationRuleMiner and launching extract to get rules
		BruteForceAssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(database);
		Set<AssociationRule> rules = ruleMiner.extract((float) 2 / 3, (float) 95 / 100);
		// Print result
		for (AssociationRule rule : rules) {
			System.out.println(rule);
		}
	}

}