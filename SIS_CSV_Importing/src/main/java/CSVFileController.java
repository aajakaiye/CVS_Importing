import java.io.IOException;

/**
 * Created by ayoolaajakaiye on 1/11/16.
 */
public class CSVFileController {

    public static void main(String[] args) throws IOException {
        CSVFileParser parser = new CSVFileParser(args[0], args[1], args[2], args[3]);

        parser.DirectoryParser();
    }
}
