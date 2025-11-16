package functions;

import java.io.*;

public class TabulatedFunctions {
    private static final double EPSILON = 1e-10;

    // фабрика по умолчанию
    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    // Приватный конструктор
    private TabulatedFunctions() {}

    // метод изменения фабрики
    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory) {
        TabulatedFunctions.factory = factory;
    }

    // 3 метода createTabulatedFunction

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return factory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return factory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, FunctionPoint[] points) {
        try {
            return functionClass
                    .getConstructor(FunctionPoint[].class)
                    .newInstance((Object) points);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, double leftX, double rightX, int pointsCount) {
        try {
            return functionClass
                    .getConstructor(double.class, double.class, int.class)
                    .newInstance(leftX, rightX, pointsCount);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> functionClass, double leftX, double rightX, double[] values) {
        try {
            return functionClass
                    .getConstructor(double.class, double.class, double[].class)
                    .newInstance(leftX, rightX, values);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> functionClass, Function function, double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        if (leftX < function.getLeftDomainBorder() - EPSILON ||
                rightX > function.getRightDomainBorder() + EPSILON)
        {
            throw new IllegalArgumentException();
        }

        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double interval = Math.abs(rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; ++i) {
            double currentX = leftX + i * interval;

            if (currentX < function.getLeftDomainBorder() - EPSILON ||
                    currentX > function.getRightDomainBorder() + EPSILON)
            {
                throw new IllegalArgumentException();
            }

            points[i] = new FunctionPoint(currentX, function.getFunctionValue(currentX));
        }

        return createTabulatedFunction(functionClass, points);
    }


    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {

        if (leftX < function.getLeftDomainBorder() - EPSILON ||
                rightX > function.getRightDomainBorder() + EPSILON)
        {
            throw new IllegalArgumentException();
        }

        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double interval = Math.abs(rightX - leftX) / (pointsCount - 1);

        for (int i = 0; i < pointsCount; ++i) {
            double currentX = leftX + i * interval;

            if (currentX < function.getLeftDomainBorder() - EPSILON ||
                    currentX > function.getRightDomainBorder() + EPSILON)
            {
                throw new IllegalArgumentException();
            }

            points[i] = new FunctionPoint(currentX, function.getFunctionValue(currentX));
        }
        return createTabulatedFunction(points);
    }



    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(out);
        dataOut.writeInt(function.getPointsCount());

        for (int i = 0; i < function.getPointsCount(); ++i) {
            dataOut.writeDouble(function.getPointX(i));
            dataOut.writeDouble(function.getPointY(i));
        }

        dataOut.close();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);

        int pointsCount = dataIn.readInt();
        FunctionPoint[] points = new FunctionPoint[pointsCount];

        double prevX = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < pointsCount; ++i) {
            double x = dataIn.readDouble();
            double y = dataIn.readDouble();

            if (i > 0 && x <= prevX + EPSILON) {
                throw new IOException();
            }

            points[i] = new FunctionPoint(x, y);
            prevX = x;
        }

        dataIn.close();

        return createTabulatedFunction(points);
    }

    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> functionClass, InputStream in) throws IOException {

        DataInputStream dataIn = new DataInputStream(in);

        int pointsCount = dataIn.readInt();
        FunctionPoint[] points = new FunctionPoint[pointsCount];

        double prevX = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < pointsCount; ++i) {
            double x = dataIn.readDouble();
            double y = dataIn.readDouble();

            if (i > 0 && x <= prevX + EPSILON) {
                throw new IOException();
            }

            points[i] = new FunctionPoint(x, y);
            prevX = x;
        }

        dataIn.close();

        return createTabulatedFunction(functionClass, points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        BufferedWriter writer = new BufferedWriter(out);

        writer.write(Integer.toString(function.getPointsCount()));

        for (int i = 0; i < function.getPointsCount(); ++i) {
            writer.write(" " + function.getPointX(i) + " " + function.getPointY(i));
        }

        writer.close();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);

        tokenizer.nextToken();
        int pointsCount = (int) tokenizer.nval;

        FunctionPoint[] points = new FunctionPoint[pointsCount];

        double prevX = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < pointsCount; ++i) {
            tokenizer.nextToken();
            double x = tokenizer.nval;

            tokenizer.nextToken();
            double y = tokenizer.nval;

            if (i > 0 && x <= prevX + EPSILON) {
                throw new IOException();
            }

            points[i] = new FunctionPoint(x, y);
            prevX = x;
        }

        return createTabulatedFunction(points);
    }

    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> functionClass, Reader in) throws IOException {

        StreamTokenizer tokenizer = new StreamTokenizer(in);

        tokenizer.nextToken();
        int pointsCount = (int) tokenizer.nval;

        FunctionPoint[] points = new FunctionPoint[pointsCount];

        double prevX = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < pointsCount; ++i) {

            tokenizer.nextToken();
            double x = tokenizer.nval;

            tokenizer.nextToken();
            double y = tokenizer.nval;

            if (i > 0 && x <= prevX + EPSILON) {
                throw new IOException();
            }

            points[i] = new FunctionPoint(x, y);
            prevX = x;
        }

        return createTabulatedFunction(functionClass, points);
    }
}
