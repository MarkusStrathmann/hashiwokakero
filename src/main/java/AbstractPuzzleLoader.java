import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public abstract class AbstractPuzzleLoader {

    public static int[][] loadPuzzle(File path, String keyword)
            throws IOException, ParseException, FileNotFoundException {

        int[][] matrix = new int[][] { { 0 } };
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(path));
        JSONArray jsonArray = (JSONArray) jsonObject.get(keyword);

        // get dimensions of weight matrix
        int[][] weightMatrix = new int[jsonArray.size()][jsonArray.get(0).toString().split(",").length];

        // fill weightMatrix with jsonArray
        for (int row = 0; row < jsonArray.size(); row++) {
            String rowString = jsonArray.get(row).toString();
            rowString = rowString.replace("[", "").replace("]", "");
            String[] rowArray = rowString.split(",");

            for (int col = 0; col < rowArray.length; col++) {
                int cellValue = (int) Integer.valueOf(rowArray[col]);
                weightMatrix[row][col] = cellValue;
            }
        }
        matrix = weightMatrix;
        return matrix;
    }
}