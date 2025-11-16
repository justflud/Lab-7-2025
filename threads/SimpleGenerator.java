package threads;

import functions.Function;
import functions.Functions;
import functions.basic.Log;

public class SimpleGenerator implements Runnable {
    private final Task task;

    public SimpleGenerator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getNumTasks(); i++) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("[Simple] Generator interrupted");
                return;
            }

            double base = 1 + Math.random() * 9;
            Function function = Functions.power(new Log(base), 2);
            double leftX = Math.random() * 100;
            double rightX = Math.random() * 100 + 100;
            double step = Math.random() * 0.9 + 0.1;

            synchronized (task) {
                task.setFunction(function);
                task.setLeftX(leftX);
                task.setRightX(rightX);
                task.setStep(step);
                System.out.println("[Simple] Source leftX = " + leftX +
                        " rightX = " + rightX + " step = " + step);
            }

            try {
                Thread.sleep(5); // для наглядной асинхронности
            } catch (InterruptedException e) {
                System.out.println("[Simple] Generator interrupted during sleep");
                return;
            }
        }
    }
}
