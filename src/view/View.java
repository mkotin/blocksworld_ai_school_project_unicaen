package view;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import blocksworld.BWVariables;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWComponent;
import bwui.BWIntegerGUI;
import modelling.Variable;
import planning.Action;

public class View {
  private BWVariables bwV;
  private Map<Variable, Object> state;
  private String title;

  /**
   * Constructor
   * 
   * @param bwV   : reference to block world variables
   * @param state : the actual state
   * @param title : frame title
   */
  public View(BWVariables bwV, Map<Variable, Object> state, String title) {
    this.bwV = bwV;
    this.state = state;
    this.title = title;
  }

  /**
   * Second constructor
   * 
   * @param bwV   : reference to block world variables
   * @param state : the actual state
   */
  public View(BWVariables bwV, Map<Variable, Object> state) {
    this(bwV, state, "State View");
  }

  public BWState<Integer> makeBwState() {
    int numberOfOnb = bwV.getOnbVaribles().size();
    BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(numberOfOnb);
    for (Variable var : bwV.getOnbVaribles()) {
      int index = BWVariables.getIndex(var);
      int under = (int) state.get(var);
      if (under >= 0)
        builder.setOn(index, under);
    }
    BWState<Integer> bwState = builder.getState();
    return bwState;
  }

  /**
   * Method displaying a given state
   */
  public void display(int x, int y) {
    int numberOfOnb = bwV.getOnbVaribles().size();
    BWState<Integer> bwState = makeBwState();
    BWIntegerGUI gui = new BWIntegerGUI(numberOfOnb);
    JFrame frame = new JFrame(title);
    frame.setLocation(x, y);
    frame.setPreferredSize(new Dimension(500, 500));
    frame.add(gui.getComponent(bwState));
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Method the displaying the execution of a plan
   * 
   * @param actions : the plan
   */
  public void displayPlan(List<Action> actions, int x, int y) {
    int numberOfOnb = bwV.getOnbVaribles().size();
    BWIntegerGUI gui = new BWIntegerGUI(numberOfOnb);
    JFrame frame = new JFrame(this.title);
    frame.setLocation(x, y);
    frame.setPreferredSize(new Dimension(500, 500));
    BWState<Integer> bwState = makeBwState();
    BWComponent<Integer> component = gui.getComponent(bwState);
    frame.add(component);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    for (Action action : actions) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      }
      state = action.successor(state);
      component.setState(makeBwState());
    }
  }
}
