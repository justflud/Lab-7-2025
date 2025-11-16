package threads;

import functions.Function;
import functions.Functions;
import functions.InappropriateFunctionPointException;

public class SimpleIntegrator implements Runnable {
    private final Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getNumTasks(); i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("[Simple] Integrator interrupted");
                return;
            }

            double leftX, rightX, step;
            Function function;

            synchronized (task) {
                if (task.getFunction() == null) {
                    continue;
                }
                leftX = task.getLeftX();
                rightX = task.getRightX();
                step = task.getStep();
                function = task.getFunction();
            }

            try {
                double result = Functions.integrate(function, leftX, rightX, step);
                System.out.println("[Simple] Result leftX = " + leftX +
                        " rightX = " + rightX + " step = " + step +
                        " Result: " + result);
            } catch (InappropriateFunctionPointException e) {
                System.out.println("[Simple] Integration error: " + e.getMessage());
            }

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("[Simple] Integrator interrupted during sleep");
                return;
            }
        }
    }
}
