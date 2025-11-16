import functions.*;
import functions.basic.*;
import threads.*;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException, InterruptedException {

        System.out.println("Задание 1:");

        TabulatedFunction arrayFunction = TabulatedFunctions.tabulate(new Exp(), -1, 1, 5);
        TabulatedFunction linkedListFunction;

        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        linkedListFunction = TabulatedFunctions.tabulate(new Exp(), -1, 1, 5);

        System.out.println("ArrayTabulatedFunction:");
        for (FunctionPoint point : arrayFunction) {
            System.out.println(point);
        }
        System.out.println("\nLinkedListTabulatedFunction:");
        for (FunctionPoint point : linkedListFunction) {
            System.out.println(point);
        }

        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());


        System.out.println("Задание 2");
        Function f2 = new Cos();
        TabulatedFunction tf;
        tf = TabulatedFunctions.tabulate(f2, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f2, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(f2, 0, Math.PI, 11);
        System.out.println(tf.getClass());


        System.out.println("Задание 3");
        TabulatedFunction f3;
        f3 = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, 3);
        System.out.println(f3.getClass());
        System.out.println(f3);

        f3 = TabulatedFunctions.createTabulatedFunction(ArrayTabulatedFunction.class, 0, 10, new double[]{0, 10});
        System.out.println(f3.getClass());
        System.out.println(f3);

        f3 = TabulatedFunctions.createTabulatedFunction(
                LinkedListTabulatedFunction.class,
                new FunctionPoint[]{
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                }
        );
        System.out.println(f3.getClass());
        System.out.println(f3);

        f3 = TabulatedFunctions.tabulate(LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(f3.getClass());
        System.out.println(f3);
    }


}