package planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import modelling.Variable;

public class DijkstraPlanner implements Planner {

    protected Map<Variable, Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;
    private int sonde;

    

    public DijkstraPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
        this.initialState = initialState;
        this.actions = actions;
        this.goal = goal;
        this.sonde = 0;
    }

    @Override
    public List<Action> plan() {
        // Creating a map to associate a state to his father
        Map<Map<Variable, Object>, Map<Variable, Object>> father = 
        new HashMap<Map<Variable, Object>, Map<Variable, Object>>();
        father.put(this.initialState, null); // Set initial state father to null

        // Creating a map to associate each state to the action which permit to obtain it
        Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();

        // Creating a distance map to store the distance of each state from initial state
        Map<Map<Variable, Object>, Double> distance = new HashMap<Map<Variable, Object>, Double>();
        distance.put(initialState, 0.0); // set distance of initial state to 0
        
        // Goals
        Set<Map<Variable, Object>> goals = new HashSet<Map<Variable, Object>>();

        // Open states: states to check
        Set<Map<Variable, Object>> open = new HashSet<Map<Variable, Object>>();
        open.add(initialState);

        while(!open.isEmpty()) {
            Map<Variable, Object> instantiation = getMinDistanceNode(open, distance);

            open.remove(instantiation);

            if(this.getGoal().isSatisfiedBy(instantiation)) {
                goals.add(instantiation);
            }

            for(Action action: this.actions) {
                if(action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);
                    if(!distance.containsKey(next)) {
                        distance.put(next, Double.POSITIVE_INFINITY);
                    }

                    if(distance.get(next) > (distance.get(instantiation) + action.getCost())){
                        distance.put(next, distance.get(instantiation) + action.getCost());
                        father.put(next, instantiation);
                        plan.put(next, action);
                        open.add(next);
                        sonde++;
                    }
                }
            }
        }

        if(goals.isEmpty()) {
            return null;
        } else {
            return getDijkstraPlan(father, plan, goals, distance);
        }

        
    }

    private List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,
    Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals,
    Map<Map<Variable, Object>, Double> distance) {
        Queue<Action> dijPlan = new LinkedList<Action>();
        Map<Variable, Object> goal = getMinDistanceNode(goals, distance);

        while(father.get(goal) != null) {
            dijPlan.add(plan.get(goal));
            goal = father.get(goal);
        }

        reversequeue(dijPlan);

        return new ArrayList<Action>(dijPlan);
    }

    // Function to reverse the queue
    private void reversequeue(Queue<Action> queue) // todo: check if it works and needed lol
    {
        Stack<Action> stack = new Stack<>();
        while (!queue.isEmpty()) {
            stack.add(queue.peek());
            queue.remove();
        }
        while (!stack.isEmpty()) {
            queue.add(stack.peek());
            stack.pop();
        }
    }

   /**
    * Return the node in nodes which have the min distance value
    * @param nodes nodes to search in
    * @param distance a map of nodes distances
    * @return the node in nodes which have the min distance value
    */
    // todo: handle null values for distance.get
    private Map<Variable, Object> getMinDistanceNode(Set<Map<Variable, Object>> nodes, Map<Map<Variable, Object>,
    Double> distance) {
        Map<Variable, Object> min = null;

        for(Map<Variable, Object> element: nodes) {
            if(min == null || distance.get(min) > distance.get(element)) {
                min = element;
            }
        }

        return min;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.goal;
    }

    public int getSonde(){
        return sonde;
    }
    
}
