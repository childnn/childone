package code;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 16:35
 */
public class RecoverCADState {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("CADState.dat"))
        ) {
            // Read in the same order they were written:
            List<Class<? extends Shape>> shapeTypes = (List<Class<? extends Shape>>) in.readObject();
            Line.deserializeStaticState(in);
            List<Shape> shapes = (List<Shape>) in.readObject();
            System.out.println(shapes);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
