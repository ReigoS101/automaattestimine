import fileReaderWriter.fileReaderWriter;
import org.json.JSONArray;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class fileReaderWriterTests {


    @Test
    public void doesWriterWriteToFile() {
        String fileName = "/Users/macbook/Desktop/automaat/src/main/java/textFiles/fileWriterReaderOutput.txt";
        fileReaderWriter writer = new fileReaderWriter();
        File file = new File(fileName);
        try {
            file.createNewFile();
            writer.addStringToFile("1", fileName);
            JSONArray lines = writer.readAllLines(fileName);
            file.delete();
            assertEquals("1", lines.getString(0));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canReaderReadInputFilesSyntax() {
        String fileName = "/Users/macbook/Desktop/automaat/src/main/java/textFiles/inputTest.txt";
        fileReaderWriter writer = new fileReaderWriter();
        File file = new File(fileName);
        try {
            file.createNewFile();
            writer.addStringToFile("1, 1, 1", fileName);
            List list = fileReaderWriter.readLinesSpecific(fileName);
            file.delete();
            assertEquals(list.size(), 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
