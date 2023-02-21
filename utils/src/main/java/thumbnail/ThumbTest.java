package thumbnail;

import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2023/1/10
 */
public class ThumbTest {

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String originalFile = "/Users/onechild/Pictures/十元_2.png";
        Thumbnails.of(Files.newInputStream(Paths.get(originalFile))).size(200, 200).toOutputStream(baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        // IOUtils.copy(bais, baos);
        Files.copy(bais, Paths.get(
                "/Users/onechild/Documents/creator/newchild/childone/utils/src/main/resources/static/foo.png"));
    }

}
