package blocksworld;

import view.View;

import java.util.List;

import planning.Action;

public class DisplayPlanThread extends Thread {
    private View view;
    private List<Action> plan;
    private int x;
    private int y;

    public DisplayPlanThread(View view, List<Action> plan, int x, int y) {
        this.view = view;
        this.plan = plan;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        view.displayPlan(plan, x, y);
    }

}
