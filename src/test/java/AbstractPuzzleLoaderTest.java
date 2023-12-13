import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

public class AbstractPuzzleLoaderTest {
    // loadPuzzle(File path, String keyword)
    @Test
    void weightMatrixPuzzleLoaderShouldBeEqualToWeightMatrixTarget()
            throws IOException, ParseException, FileNotFoundException {
        File puzzleJSON = new File("src/test/resources/examplePuzzleTest.json");
        String keyword = "weightMatrix";
        int[][] weightMatrixPuzzleLoader = AbstractPuzzleLoader.loadPuzzle(puzzleJSON, keyword);
        int[][] weightMatrixTarget = new int[][] {
                { 0, 0, 1, 7, 5, 3, 3, 6, 5, 2 },
                { 0, 2, 4, 4, 0, 0, 0, 5, 0, 0 },
                { 0, 0, 2, 3, 0, 0, 1, 0, 0, 0 },
                { 0, 2, 7, 3, 4, 4, 0, 0, 0, 0 },
                { 0, 0, 7, 7, 0, 1, 0, 0, 0, 0 },
                { 5, 5, 3, 0, 0, 0, 0, 0, 0, 0 },
                { 2, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 }
        };
        assertArrayEquals(weightMatrixTarget, weightMatrixPuzzleLoader);
    }

    @Test
    void loadPuzzleWithWrongPathShouldReturnFileNotFoundException()
            throws IOException, ParseException, FileNotFoundException {
        File puzzleJSON = new File("this/path/is/wrong/examplePuzzleTest.json");
        String keyword = "weightMatrix";
        assertThrows(FileNotFoundException.class, () -> AbstractPuzzleLoader.loadPuzzle(puzzleJSON, keyword));
    }

    @Test
    void loadPuzzleWithWrongFileFormatShouldReturnClassCastException()
            throws IOException, ParseException, FileNotFoundException {
        File puzzleJSON = new File("src/test/resources/wrongFileFormat.txt");
        String keyword = "weightMatrix";
        assertThrows(ClassCastException.class, () -> AbstractPuzzleLoader.loadPuzzle(puzzleJSON, keyword));
    }

    @Test
    void loadPuzzleFromCorruptedFileShouldReturnParseException()
            throws IOException, ParseException, FileNotFoundException {
        File puzzleJSON = new File("src/test/resources/corruptedFile.json");
        String keyword = "weightMatrix";
        assertThrows(ParseException.class, () -> AbstractPuzzleLoader.loadPuzzle(puzzleJSON, keyword));
    }

    @Test
    void loadPuzzleUsingWrongKeywordShouldReturnNullPointerException()
            throws IOException, ParseException, FileNotFoundException {
        File puzzleJSON = new File("src/test/resources/examplePuzzleTest.json");
        String keyword = "wrongKeyword";
        assertThrows(NullPointerException.class, () -> AbstractPuzzleLoader.loadPuzzle(puzzleJSON, keyword));
    }

    @Test
    void getClassOfSubclassInstanceForAbstractPuzzleLoaderShouldThrowNoException() {
        class SubClass extends AbstractPuzzleLoader{};
        SubClass subClass = new SubClass();
        assertDoesNotThrow(() -> subClass.getClass());
    }

}
