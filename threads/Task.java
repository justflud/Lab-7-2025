package threads;

import functions.Function;

public class Task {

    private Function function;
    private double leftX, rightX, step;
    private int numTasks;
    private boolean ready = false;


    public Task(int numTasks) {
        if (numTasks <= 0) {
            throw new IllegalArgumentException();
        }
        this.numTasks = numTasks;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public double getLeftX() {
        return leftX;
    }

    public void setLeftX(double leftX) {
        this.leftX = leftX;
    }

    public double getRightX() {
        return rightX;
    }

    public void setRightX(double rightX) {
        if (rightX <= leftX) {
            throw new IllegalArgumentException();
        }
        this.rightX = rightX;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        if (step <= 0) {
            throw new IllegalArgumentException();
        }
        this.step = step;
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void setNumTasks(int numTasks) {
        if (numTasks <= 0) {
            throw new IllegalArgumentException();
        }
        this.numTasks = numTasks;
    }
    public boolean isReady() { return ready; }
    public void setReady(boolean ready) { this.ready = ready; }
}
