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
        File file = new File(fileName);
        try {
            file.createNewFile();
            fileReaderWriter.addStringToFile("1", fileName);
            JSONArray lines = fileReaderWriter.readAllLines(fileName);
            assertEquals("1", lines.getString(0));
            file.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canReaderReadInputFilesSyntax() {
        String fileName = "/Users/macbook/Desktop/automaat/src/main/java/textFiles/inputTest.txt";
        File file = new File(fileName);
        try {
            file.createNewFile();
            fileReaderWriter.addStringToFile("1, 1, 1", fileName);
            List list = fileReaderWriter.readLinesSpecific(fileName);
            assertEquals(list.size(), 3);
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
