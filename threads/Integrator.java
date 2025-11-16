package threads;

import functions.Functions;
import functions.InappropriateFunctionPointException;

public class Integrator extends Thread {
    private final Task task;

    public Integrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getNumTasks(); i++) {
            // ждём, пока генератор подаст данные
            while (!task.isReady()) {
                Thread.yield(); // отдаём квант времени
            }

            double result = 0;
            try {
                result = Functions.integrate(task.getFunction(),
                        task.getLeftX(), task.getRightX(), task.getStep());
            } catch (InappropriateFunctionPointException e) {
                throw new RuntimeException(e);
            }

            System.out.println("[Complicated] Result leftX = " + task.getLeftX() +
                    " rightX = " + task.getRightX() + " step = " + task.getStep() +
                    " Result: " + result);

            task.setReady(false); // освобождаем место для следующего задания
        }
    }
}
