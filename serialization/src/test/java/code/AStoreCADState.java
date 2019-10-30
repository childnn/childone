package code;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum Color {RED, BLUE, GREEN}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 16:31
 */
public class AStoreCADState {

    public static void main(String[] args) {
        List<Class<? extends Shape>> shapeTypes =
                Arrays.asList(Circle.class, Square.class, Line.class);
        List<Shape> shapes = IntStream.range(0, 10)
                .mapToObj(i -> Shape.randomFactory())
                .collect(Collectors.toList());
        // Set all the static colors to GREEN:
        shapes.forEach(s -> s.setColor(Color.GREEN));
        // Save the state vector:
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CADState.dat"))
        ) {
            out.writeObject(shapeTypes);
            Line.serializeStaticState(out);
            out.writeObject(shapes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Display the shapes:
        System.out.println(shapes);
    }

}

abstract class Shape implements Serializable {
    private static Random rand = new Random(47);
    private static int counter = 0;
    private int xPos, yPos, dimension;

    Shape(int xVal, int yVal, int dim) {
        xPos = xVal;
        yPos = yVal;
        dimension = dim;
    }

    public static Shape randomFactory() {
        int xVal = rand.nextInt(100);
        int yVal = rand.nextInt(100);
        int dim = rand.nextInt(100);
        switch (counter++ % 3) {
            default:
            case 0:
                return new Circle(xVal, yVal, dim);
            case 1:
                return new Square(xVal, yVal, dim);
            case 2:
                return new Line(xVal, yVal, dim);
        }
    }

    public abstract Color getColor();

    public abstract void setColor(Color newColor);

    public String toString() {
        return getClass() + "color[" + getColor() +
                "] xPos[" + xPos + "] yPos[" + yPos +
                "] dim[" + dimension + "]\n";
    }
}

class Circle extends Shape {
    private static Color color = Color.RED;

    Circle(int xVal, int yVal, int dim) {
        super(xVal, yVal, dim);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }
}

class Square extends Shape {
    private static Color color = Color.RED;

    Square(int xVal, int yVal, int dim) {
        super(xVal, yVal, dim);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }
}

class Line extends Shape {
    private static Color color = Color.RED;

    Line(int xVal, int yVal, int dim) {
        super(xVal, yVal, dim);
    }

    public static void serializeStaticState(ObjectOutputStream os) throws IOException {
        os.writeObject(color);
    }

    public static void deserializeStaticState(ObjectInputStream os) throws IOException, ClassNotFoundException {
        color = (Color) os.readObject();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }

}