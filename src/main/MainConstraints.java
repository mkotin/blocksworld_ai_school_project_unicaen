package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import blocksworld.BWAscConstraints;
import blocksworld.BWRegularyConstraints;
import blocksworld.BWVariables;
import blocksworld.BlocksWorld;
import modelling.Constraint;
import modelling.Variable;

public class MainConstraints {
    
    public static void main(String[] args) {
      BlocksWorld bw = new BlocksWorld(8, 3);
      BWRegularyConstraints bwRC = new BWRegularyConstraints(bw);
      BWAscConstraints bwAC = new BWAscConstraints(bw);
      BWVariables bwV = bw.getBwVariables();
      
      System.out.println("***************************  Demo Constraints ************************");
      Map<Variable, Object> state0 = new HashMap<>();
      Map<Variable, Object> state = new HashMap<>();
      Map<Variable, Object> state2 = new HashMap<>();
      Map<Variable, Object> state3 = new HashMap<>();
      
      int[][] ls0 = {
          {0, 1},
          {2, 3, 4, 5, 6, 7},
          {}
      };

      int[][] ls = {
          {0, 1, 2},
          {3, 5, 7},
          {4,6}
      };
      int[][] ls2 = {
        { 0, 1, 3  },
        {2, 4, 5, 6},
        {7}
      };

      int[][] ls3 = {
      { 0, 1, 2, 3  },
      {4, 5, 6, 7},
      {}
      };

      state0 = bwV.getState(ls0);
      state = bwV.getState(ls);
      state2 = bwV.getState(ls2);
      state3 = bwV.getState(ls3);

      // Debug instance
      // for (Map.Entry<Variable, Object> entry : state0.entrySet()) {
      //   Variable key = entry.getKey();
      //   Object value = entry.getValue();
      //   System.out.println(key.getName() + " = " + value);
      // }

      boolean allSatisfied = true;

      // Test state (basic constraints) ====================
      for (Constraint constraint : bw.getConstraints()) {
        if (!constraint.isSatisfiedBy(state0)) {
          allSatisfied = false;
          //System.out.println(constraint);
        }
      }
      System.out.println(allSatisfied ? 
      "Basics constraints are satisfied for state " + Arrays.deepToString(ls0) : 
      "Basics constraints are not satisfied for state" + Arrays.deepToString(ls0));
      
      // Test state (regulary constraints) ====================
      for (Constraint constraint : bwRC.getConstraints()) {
        if (!constraint.isSatisfiedBy(state)) {
          allSatisfied = false;
          //System.out.println(constraint);
        }
      }
      System.out.println(allSatisfied ? 
      "Regualary constraints are satisfied for state " + Arrays.deepToString(ls) : 
      "Regulary constraints are not satisfied for state" + Arrays.deepToString(ls));

      // Test state2 (asc constraints) ========================
      allSatisfied = true;
      for (Constraint constraint : bwAC.getConstraints()) {
        if (!constraint.isSatisfiedBy(state2)) {
          allSatisfied = false;
          //System.out.println(constraint);
        }
      }
      System.out.println(allSatisfied ? 
      "Asc constraints are satisfied for state " + Arrays.deepToString(ls2) : 
      "Asc constraints are not satisfied for state" + Arrays.deepToString(ls2));

      // Test state3 (asc and regulary constraints) ========================
      allSatisfied = true;
      Set<Constraint> allConstraints = new HashSet<>(bwRC.getConstraints());
      allConstraints.addAll(bwAC.getConstraints());
      for (Constraint constraint : allConstraints) {
        if (!constraint.isSatisfiedBy(state3)) {
          allSatisfied = false;
          //System.out.println(constraint);
        }
      }
      System.out.println(allSatisfied ? 
      "Regulary and Asc constraints are satisfied for state " + Arrays.deepToString(ls3) : 
      "Regulary and Asc constraints are not satisfied for state" + Arrays.deepToString(ls3));
   
    }
}
