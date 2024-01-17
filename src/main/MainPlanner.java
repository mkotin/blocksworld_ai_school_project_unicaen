package main;

import java.util.List;
import java.util.Map;
import java.util.Set;

import blocksworld.BWActions;
import blocksworld.BWVariables;
import blocksworld.DisplayPlanThread;
import blocksworld.HeuristicNumberOfBlocksOnWrongPosition;
import blocksworld.HeuristicNumberOfBottomBlocksOnWrongPosition;
import modelling.Variable;
import planning.AStarPlanner;
import planning.Action;
import planning.BFSPlanner;
import planning.BasicGoal;
import planning.DFSPlanner;
import planning.DijkstraPlanner;
import planning.Goal;
import view.View;

public class MainPlanner {
    public static void main(String[] args) {

    long T = 1_000_000_000;
    BWActions bwA = new BWActions(5, 3);
    BWVariables bwV = bwA.getBlocksWorld().getBwVariables();
    Set<Action> actions = bwA.getActions();

    // Initial State
    int[][] ls = {
        { 0, 2, 4 },
        {},
        { 1, 3 }
    };
    Map<Variable, Object> state = bwV.getState(ls);

    // Goal State
    int[][] ls2 = {
        { 4, 2, 0 },
        { 3, 1 },
        {}
    };
    Map<Variable, Object> goalState = bwV.getState(ls2);

    // Instantiation of different planners
    Goal goal = new BasicGoal(goalState);
    DFSPlanner dfs = new DFSPlanner(state, actions, goal);
    BFSPlanner bfs = new BFSPlanner(state, actions, goal);
    DijkstraPlanner djk = new DijkstraPlanner(state, actions, goal);
    
    HeuristicNumberOfBlocksOnWrongPosition hr = new HeuristicNumberOfBlocksOnWrongPosition(goalState);
    HeuristicNumberOfBottomBlocksOnWrongPosition hr2 = new HeuristicNumberOfBottomBlocksOnWrongPosition(goalState);
    AStarPlanner astar = new AStarPlanner(state, actions, goal, hr);
    AStarPlanner astar2 = new AStarPlanner(state, actions, goal, hr2);

      // Setting timers and launching planners
      long t1 = System.nanoTime();
      List<Action> dfsplan = dfs.plan();
      long t2 = System.nanoTime();
      List<Action> bfsplan = bfs.plan();
      long t3 = System.nanoTime();
      List<Action> djkplan = djk.plan();
      long t4 = System.nanoTime();
      List<Action> astarplan = astar.plan();
      long t5 = System.nanoTime();
      List<Action> astarplan2 = astar2.plan();
      long t6 = System.nanoTime();
      System.out.println("**********************  Planner DEMO  ****************************");
      System.out.println("Number of Visited nodes for DFS: " + dfs.getSonde() + ", Lenght of plan: "
          + dfsplan.size() + ", Computing Time: " + (float) (t2 - t1) / T + " Seconds");
      System.out.println();
      System.out.println("Number of Visited nodes for BFS: " + bfs.getSonde() + ", Lenght of plan: "
          + bfsplan.size() + ", Computing Time: " + (float) (t3 - t2) / T + " Seconds");
      System.out.println();
      System.out.println("Number of Visited nodes for Djikstra: " + djk.getSonde() + ", Lenght of plan: "
          + djkplan.size() + ", Computing Time: " + (float) (t4 - t3) / T + " Seconds");
      System.out.println();
      System.out.println("*****************************************************************");
      System.out.println("ASTAR with heuristic computing the number of blocks on wrong positions");
      System.out
          .println("Number of Visited nodes: "
              + astar.getSonde() + ", Lenght of plan: "
              + astarplan.size() + ", Computing Time: " + (float) (t5 - t4) / T + " Seconds");
      System.out.println();
      System.out.println("*****************************************************************");
      System.out.println("ASTAR2 with heuristic computing the number of bottom blocks on wrong positions");
      System.out
          .println(
              "Number of Visited nodes: "
                  + astar2.getSonde() + ", Lenght of plan: "
                  + astarplan2.size() + ", Computing Time: " + (float) (t6 - t5) / T + " Seconds");
      System.out.println();
    
    // Display of the plans
    // Bfs
    View viewBfs = new View(bwV, state, "Bfs Plan");

    // DFS
    View viewDfs = new View(bwV, state, "Dfs Plan");

    // Dijkstra
    View viewDijkstra = new View(bwV, state, "Dijkstra Plan");

    // Astar with heuristic computing the number of blocks on wrong positions
    View viewAstar = new View(bwV, state, "Astar wrong block number heuristic plan");

    // Astar with heuristic computing the number of bottom blocks on wrong positions
    View viewAstar2 = new View(bwV, state, "Astar wrong bottom block number heuristic plan");

    
    new DisplayPlanThread(viewBfs, bfsplan, 0, 0).start();
    new DisplayPlanThread(viewDfs, dfsplan, 600, 0).start();
    new DisplayPlanThread(viewDijkstra, djkplan, 1200, 0).start();
    new DisplayPlanThread(viewAstar, astarplan, 0, 600).start();
    new DisplayPlanThread(viewAstar2, astarplan2, 600, 1200).start();

  }
}
