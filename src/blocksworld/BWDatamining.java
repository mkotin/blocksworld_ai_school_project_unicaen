package blocksworld;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import bwgeneratordemo.Demo;
import datamining.BooleanDatabase;
import modelling.BooleanVariable;
import modelling.Variable;

/**
 * Class instantiating all the items(BooleanVariable)
 * of the blocks world
 * The world is represented by a certain number
 * of blocks and a number of piles
 * Extends BlockWorld
 */
public class BWDatamining {

  private Set<BooleanVariable> items = new HashSet<>();
  private Map<String, BooleanVariable> mapName2Var = new HashMap<>();
  private BWVariables bwV;

  /**
   * Constructor
   *
   * @param nbBlocks : the number of blocks
   * @param nbPiles  : the number of piles
   */
  public BWDatamining(int nbBlocks, int nbPiles) {
    bwV = new BWVariables(nbBlocks, nbPiles);
    generateOnVariables();
    generateOnTableVariables();
  }

  /**
   * Function generating the on_i_j variables
   * Meaning if block i is on top of block j
   */
  private void generateOnVariables() {
    for (Variable on_i : bwV.getOnbVaribles()) {
      for (Variable fixed_j : bwV.getFixedbVariables()) {
        int i = BWVariables.getIndex(on_i);
        int j = BWVariables.getIndex(fixed_j);
        if (i == j)
          continue;
        String str = "on_" + Integer.toString(i) + "_" + Integer.toString(j);
        BooleanVariable on_ij = new BooleanVariable(str);
        items.add(on_ij);
        items.add((BooleanVariable) fixed_j);
        mapName2Var.put(str, on_ij);
        mapName2Var.put("fixed_" + Integer.toString(j), (BooleanVariable) fixed_j);
      }
    }
  }

  /**
   * Function generating all onTable_i_p variables
   * Meaning if block i is directly on top of pile p
   */
  private void generateOnTableVariables() {
    for (Variable on_i : bwV.getOnbVaribles()) {
      for (Variable free_j : bwV.getFreepVariables()) {
        int i = BWVariables.getIndex(on_i);
        int j = BWVariables.getIndex(free_j);
        String str = "onTable_" + Integer.toString(i) + "_" + Integer.toString(j);
        BooleanVariable onTable_ij = new BooleanVariable(str);
        items.add(onTable_ij);
        items.add((BooleanVariable) free_j);
        mapName2Var.put(str, onTable_ij);
        mapName2Var.put("free_" + Integer.toString(j), (BooleanVariable) free_j);
      }
    }
  }

  /**
   * Functions returning the variables with a true value in a transaction
   * a variable have a true value if a block is on top of another block
   * if block is on top of pile, if a block is fixed
   *
   * @param transaction : list<list<integer>> representing a state of
   *                    blockword(transaction)
   * @return set containing all the variables with a true value
   */
  public Set<BooleanVariable> getTransactionVariables(List<List<Integer>> transaction) {
    int nbPile = transaction.size();
    Set<BooleanVariable> variables = new HashSet<>();
    // Loop through all piles
    for (int p = 0; p < nbPile; p++) {
      // if pile is empty then the variable free_pile = true
      if (transaction.get(p).isEmpty()) {
        String str = "free_" + Integer.toString(p);
        BooleanVariable freep = check(str) ? mapName2Var.get(str) : new BooleanVariable(str);
        // add to set and to cache
        variables.add(freep);
        mapName2Var.put(str, freep);
      }
      int nbBlock = transaction.get(p).size();
      // Loop through each value of each pile i.e each block
      for (int b = 0; b < nbBlock; b++) {
        int j = transaction.get(p).get(b);
        // if block first in pile i.e a bottom of stack then
        // instantiate variable onTable_i_p or retrieve from cache
        if (b == 0) {
          String str = "onTable_" + Integer.toString(j) + "_" + Integer.toString(p);
          BooleanVariable onTable = check(str) ? mapName2Var.get(str) : new BooleanVariable(str);
          // add to set and to cache
          variables.add(onTable);
          mapName2Var.put(str, onTable);
        }
        // instantiate each variable on_i_j and each fixed_i or retrieve from cache
        if (b < nbBlock - 1) {
          int i = transaction.get(p).get(b + 1);
          String str = "on_" +  Integer.toString(i) + "_" + Integer.toString(j);
          String strf = "fixed_" + Integer.toString(j);
          // check if variable have already been cached else instantiate
          BooleanVariable on_ij = check(str) ? mapName2Var.get(str) : new BooleanVariable(str);
          BooleanVariable fixed = check(strf) ? mapName2Var.get(strf) : new BooleanVariable(strf);
          // add to set and to cache
          variables.add(on_ij);
          variables.add(fixed);
          mapName2Var.put(str, on_ij);
          mapName2Var.put(strf, fixed);
        }
      }
    }
    return variables;
  }

  /**
   * Function generating a database
   * Making use of the library BWGenerator
   *
   * @param nbTransactions : the number of transactions
   * @return database
   */
  public BooleanDatabase generateDatabase(int nbTransactions) {
    BooleanDatabase database = new BooleanDatabase(items);
    // System.out.println("[DEBUG] ========Database variables=========");
    for (int i = 0; i < nbTransactions; i++) {
      List<List<Integer>> transaction = Demo.getState(new Random());
      // Debugging
      // System.out.println("transaction: "+ transaction.toString() + "\n");
      Set<BooleanVariable> instance = getTransactionVariables(transaction);
      
      // Debugging
      // System.out.print("\n[");
      // for(BooleanVariable bv: instance) {
      //   System.out.print(bv + " ");
      // }
      // System.out.println("]\n");

      database.add(instance);
    }
    return database;
  }

  /**
   * Function that return if key is map<String, Variable>
   *
   * @param str : the key
   * @return true or false
   */
  public boolean check(String str) {
    return mapName2Var.containsKey(str);
  }

  /**
   * Getter
   *
   * @return a set containing all the boolean variables
   */
  public Set<BooleanVariable> getItems() {
    return items;
  }

  /**
   * Getter
   *
   * @return a map<String, Variable>
   */
  public Map<String, BooleanVariable> getMapName2Var() {
    return mapName2Var;
  }

  @Override
  public String toString() {
    return "{" +
        " items='" + getItems() + "'" +
        "}";
  }

}