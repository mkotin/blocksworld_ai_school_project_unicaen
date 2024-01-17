package planning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import modelling.Variable;

/**
 * La classe AStarPlanner implémente l'algorithme de recherche A*.
 * Elle utilise une heuristique pour estimer le coût restant pour atteindre l'objectif
 * et sélectionne l'action à effectuer en fonction de cette estimation.
 */
public class AStarPlanner implements Planner {

  // État initial du problème de planification
  private Map<Variable, Object> initialState;
  // Ensemble d'actions possibles dans le problème
  private Set<Action> actions;
  // Objectif à atteindre
  private Goal goal;
  // Heuristique utilisée pour estimer le coût restant
  private Heuristic heuristic;
  // Planificateur BFS utilisé comme sous-composant
  private BFSPlanner bfs = new BFSPlanner(initialState, actions, goal);
  // Compteur de sondes suivi de performance
  private int sonde;

  /**
   * Constructeur de la classe AStarPlanner.
   * Initialise les attributs avec les paramètres fournis.
   *
   * @param initialState État initial du problème
   * @param actions Ensemble d'actions possibles
   * @param goal Objectif à atteindre
   * @param heuristic Heuristique utilisée
   */
  public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal, Heuristic heuristic) {
    this.initialState = initialState;
    this.actions = actions;
    this.goal = goal;
    this.heuristic = heuristic;
    this.sonde = 0;
  }

  /**
   * Implémentation de l'algorithme A*.
   * Cette méthode explore les états possibles du problème, sélectionne et applique les actions,
   * et utilise une heuristique pour estimer le coût restant pour atteindre l'objectif.
   *
   * @param father Map associant à chaque état l'état père dans l'arbre de recherche
   * @param plan Map associant à chaque état l'action à effectuer pour l'atteindre
   * @param distance Map associant à chaque état la distance depuis l'état initial
   * @param value Map associant à chaque état la somme de la distance et de l'estimation heuristique
   * @return Liste d'actions à effectuer pour atteindre l'objectif
   */
  public List<Action> aStar(Map<Map<Variable, Object>, Map<Variable, Object>> father,
      Map<Map<Variable, Object>, Action> plan, Map<Map<Variable, Object>, Integer> distance,
      Map<Map<Variable, Object>, Float> value) {

    // File de priorité des états à explorer, ordonnés par valeur heuristique
    PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(1, new StateComparator(value));
    // Initialisation des structures de données
    father.put(initialState, null);
    distance.put(initialState, 0);
    value.put(initialState, heuristic.estimate(initialState));
    open.add(initialState);
    // Boucle principale de l'algorithme A*
    while (!open.isEmpty()) {
      // Sélection et traitement de l'état courant
      Map<Variable, Object> currentState = open.poll();
      // Si l'état courant satisfait l'objectif, retour du plan correspondant
      if (goal.isSatisfiedBy(currentState)) {
        return bfs.getBfsPlan(father, plan, currentState);
      }
      // Exploration des actions applicables et mise à jour des structures de données
      for (Action action : actions) {
        if (action.isApplicable(currentState)) {
          Map<Variable, Object> nextState = action.successor(currentState);
          if (!distance.containsKey(nextState)) {
            distance.put(nextState, Integer.MAX_VALUE);
          }
          if (distance.get(nextState) > distance.get(currentState) + action.getCost()) {
            distance.put(nextState, distance.get(currentState) + action.getCost());
            value.put(nextState, distance.get(nextState) + heuristic.estimate(nextState));
            father.put(nextState, currentState);
            plan.put(nextState, action);
            open.add(nextState);
            sonde++;
          }
        }
      }
    }
    return null; // Retourne null si aucun plan n'est trouvé
  }

  /**
   * Méthode plan appelant l'algorithme A* avec des structures de données vides.
   *
   * @return Liste d'actions à effectuer pour atteindre l'objectif
   */
  @Override
  public List<Action> plan() {
    return aStar(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
  }

  // Méthodes d'accès aux attributs de la classe
  @Override
  public Map<Variable, Object> getInitialState() {
    return initialState;
  }

  @Override
  public Set<Action> getActions() {
    return actions;
  }

  @Override
  public Goal getGoal() {
    return goal;
  }

  /**
   * Méthode d'accès au compteur de sondes.
   *
   * @return Valeur du compteur de sondes
   */
  public int getSonde(){
    return sonde;
  }
}
