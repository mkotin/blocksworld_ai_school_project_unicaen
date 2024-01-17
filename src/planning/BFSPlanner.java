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

public class BFSPlanner implements Planner {

    protected Map<Variable, Object> initialState;
    protected Set<Action> actions;
    protected Goal goal;
    private int sonde;

    

    public BFSPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal goal) {
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

        // Creating a map to associate each state to the action which permit to obtain it
        Map<Map<Variable, Object>, Action> plan = new HashMap<Map<Variable, Object>, Action>();

        // Closed states : states to not check
        Set<Map<Variable, Object>> closed = new HashSet<Map<Variable, Object>>();

        // Open states: states to check
        Queue<Map<Variable, Object>> open = new LinkedList<Map<Variable, Object>>();
        open.add(this.initialState);

        // Set initial state father to null
        father.put(this.initialState, null);

        // Return empty plan if initial state stafies the goal
        if(this.goal.isSatisfiedBy(this.initialState)) {
            return new ArrayList<Action>();
        }

        while(!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.poll();
            closed.add(instantiation);

            for(Action action: this.actions) {
                if(action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);

                    if(!closed.contains(next) && !open.contains(next)) {
                        father.put(next, instantiation);
                        plan.put(next, action);
                        sonde++;

                        if(this.goal.isSatisfiedBy(next)) {
                            return getBfsPlan(father, plan, next);
                        } else {
                            open.add(next);
                        }
                    }
                }
            }
        }

        return null;
    }

    public List<Action>  getBfsPlan(Map<Map<Variable, Object>, Map<Variable, Object>>  father
    , Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        Queue<Action> bfsPlan = new LinkedList<Action>(); // todo: ask prof if its the correct datastructure or should i use queue instead


        while(father.get(goal) != null) {
            bfsPlan.add(plan.get(goal));
            goal = father.get(goal);
        }


        reversequeue(bfsPlan);

        return new ArrayList<Action>(bfsPlan);
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

    public int getSonde() {
        return sonde;
    }
    
}
