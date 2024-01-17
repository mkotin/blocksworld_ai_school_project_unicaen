package main;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;


import blocksworld.BWAscConstraints;
import blocksworld.BWRegularyConstraints;
import blocksworld.BlocksWorld;
import cp.*;
import modelling.*;
import view.View;

public class MainCP {

    public static void main(String[] args){

        long T = 1_000_000_000;
        System.out.println("############ Demo Problèmes de satisfaction de contraintes  ############");
        BlocksWorld bw = new BlocksWorld(5, 2);
        Set<Variable> variables = bw.getBwVariables().getVariables();

        System.out.println("*****************************************************************");
        System.out.println("&&&&&&&&&&&&&&&&&&  Regulary constraints  &&&&&&&&&&&&&&&&&&&&&&&&");

        // contrainte reguliere
        BWRegularyConstraints bwRC = new BWRegularyConstraints(bw);
        Set<Constraint> constraintsRC = bwRC.getConstraints();

        Solver solverRC_Backtract = new BacktrackSolver(variables, constraintsRC);
        Solver solverRC_MAC = new MACSolver(variables, constraintsRC);
        VariableHeuristic var_DomaineRC = new DomainSizeVariableHeuristic(true);
        ValueHeuristic     vh_RandomRC = new RandomValueHeuristic( new Random());
        Solver solver3 = new HeuristicMACSolver(variables,constraintsRC,var_DomaineRC,vh_RandomRC);

        long ta = System.nanoTime();
        Map<Variable, Object> mapRC = solverRC_Backtract.solve();
        long tb = System.nanoTime();
        Map<Variable, Object> mapRC_MAC = solverRC_MAC.solve();
        long tc = System.nanoTime();

        Map<Variable, Object> map3 = solver3.solve();
        long td = System.nanoTime();

        // System.out.println("Solution RC: " + mapRC + " " + mapRC_MAC + " " + map3 + " ");

        System.out.println("Time of Backtrack solver with RegularyConstraints: " + (float) (tb - ta) / T + " seconds");
        System.out.println("Time MACSolver with RegularyConstraints: " + (float)(tc - tb) / T + " seconds");
        System.out.println(" Time HeuristicMACSolver RC: " + (float)(td-tc)/T + " seconds");

        // Display Views RC
        View VieRCBacktrack = new View(bw.getBwVariables(), mapRC, "RC Backtrack");
        View ViewRCMAC = new View(bw.getBwVariables(), mapRC_MAC, "RC MAC");
        View ViewRCHeuristicMAC = new View(bw.getBwVariables(), map3, "RC HeuristicMAC");
        VieRCBacktrack.display(0, 0);
        ViewRCMAC.display(600, 0);
        ViewRCHeuristicMAC.display(1200, 0);

        System.out.println("*****************************************************************");
        System.out.println("&&&&&&&&&&&&&&&&&&&  Asc constraints  &&&&&&&&&&&&"); // contrainte  croissante
        // contraite asc
        BWAscConstraints bwAC = new BWAscConstraints(bw);
        Set<Constraint> constraintsAC = bwAC.getConstraints();
        Solver solverAC_Backtract = new BacktrackSolver(variables, constraintsAC);
        Solver solverAC_MAC = new MACSolver(variables, constraintsAC);

        VariableHeuristic var_DomaineAC = new DomainSizeVariableHeuristic(true);
        ValueHeuristic     vh_RandomAC = new RandomValueHeuristic( new Random());
        Solver solver3AC = new HeuristicMACSolver(variables,constraintsAC,var_DomaineAC,vh_RandomAC);

        long t1 = System.nanoTime();
        Map<Variable, Object> mapAC = solverAC_Backtract.solve();
        long t2 = System.nanoTime();
        Map<Variable, Object> mapAC_MAC = solverAC_MAC.solve();
        long t3 = System.nanoTime();

        Map<Variable, Object> map3AC = solver3AC.solve();
        long t4 = System.nanoTime();

        // System.out.println("Solution AC: " + mapAC + " " + mapAC_MAC + " " + map3AC + " ");

        System.out.println("Time of Backtrack solver with ACS: " + (float) (t2 - t1) / T + " seconds");
        System.out.println("Time MACSolver with ACS: " + (float)(t3 - t2) / T + " seconds");
        System.out.println("Time HeuristicMACSolver ACS: " + (float)(t4-t3)/T + " seconds");

        // Display Views AC
        View VieACBacktrack = new View(bw.getBwVariables(), mapAC, "AC Backtrack");
        View ViewACMAC = new View(bw.getBwVariables(), mapAC_MAC, "AC MAC");
        View ViewACHeuristicMAC = new View(bw.getBwVariables(), map3AC, "AC HeuristicMAC");
        VieACBacktrack.display(0, 800);
        ViewACMAC.display(600, 800);
        ViewACHeuristicMAC.display(1200, 800);


        // System.out.println("*****************************************************************");
        System.out.println("&&&&&&&&&&&&&&&&&&  Regulrary and asc constraint  &&&&&&&&&&&&&&&&&&&&&&&&");

        Set<Constraint> regAscConstraints = new HashSet<>();
        regAscConstraints.addAll(bwRC.getConstraints());
        regAscConstraints.addAll(bwAC.getConstraints());

        Solver solverRAc_Backtract = new BacktrackSolver(variables, regAscConstraints);
        Solver solverRAc_MAC = new MACSolver(variables, regAscConstraints);
        VariableHeuristic var_DomaineRAc = new DomainSizeVariableHeuristic(true);
        ValueHeuristic     vh_RandomRAc = new RandomValueHeuristic( new Random());
        Solver solver3Sc = new HeuristicMACSolver(variables,regAscConstraints,var_DomaineRAc,vh_RandomRAc);

        long tA = System.nanoTime();
        Map<Variable, Object> mapRAc = solverRAc_Backtract.solve();
        long tB = System.nanoTime();
        Map<Variable, Object> mapRAc_MAC = solverRAc_MAC.solve();
        long tC = System.nanoTime();

        Map<Variable, Object> map3RAc = solver3Sc.solve();
        long tD = System.nanoTime();

        System.out.println("Time of Backtrack solver with Regulary and Asc Contrainte: " + (float) (tB - tA) / T + " seconds");
        System.out.println("Time MACSolver with Regulary and Asc Contrainte: " + (float)(tC - tB) / T + " seconds");
        System.out.println(" Time HeuristicMACSolver Regulary and Asc Contrainte: " + (float)(tD-tC)/T + " seconds");

        // Display Views AC
        View ViewRACBacktrack = new View(bw.getBwVariables(), mapRAc, "RAC Backtrack");
        View ViewRACMAC = new View(bw.getBwVariables(), mapRAc_MAC, "RAC MAC");
        View ViewRACHeuristicMAC = new View(bw.getBwVariables(), map3RAc, "RAC HeuristicMAC");
        ViewRACBacktrack.display(200, 800);
        ViewRACMAC.display(800, 800);
        ViewRACHeuristicMAC.display(1400, 800);

        
    }
}
