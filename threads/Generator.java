package threads;

import functions.Function;
import functions.Functions;
import functions.basic.Log;

public class Generator extends Thread {
    private final Task task;

    public Generator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getNumTasks(); i++) {
            // ждём, пока интегратор освободит место
            while (task.isReady()) {
                Thread.yield(); // уступаем время другому потоку
            }

            double base = 1 + Math.random() * 9;
            Function function = Functions.power(new Log(base), 2);
            double leftX = Math.random() * 100;
            double rightX = Math.random() * 100 + 100;
            double step = Math.random() * 0.9 + 0.1;

            task.setFunction(function);
            task.setLeftX(leftX);
            task.setRightX(rightX);
            task.setStep(step);

            System.out.println("[Complicated] Source leftX = " + leftX +
                    " rightX = " + rightX + " step = " + step);

            task.setReady(true); // данные готовы
        }
    }
}
