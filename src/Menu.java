import java.io.File;
import java.util.Scanner;

public class Menu {
    private static final String ENTRY_FILE_PATH = "Caesar Cipher Crypt Analyzer program. Entry path to file : ";
    private static final String INCORRECT_FILE_PATH = "Incorrect file path or file does not exist. Try again.";
    private static final String ENTRY_KEY = "Entry key : ";
    private static final String INCORRECT_USER_KEY = "Incorrect key. Try again.";
    private static final String INCORRECT_MENU_INDEX = "Incorrect menu index. Try again.";
    private static final String MENU_INFO = """
            <Caesar Cipher Menu>
            1. Encode
            2. Decode with key
            3. Decode brute force
            4. Exit
            
            Select operation : """;

    public static final int ENCODE = 1;
    public static final int DECODE_WITH_KEY = 2;
    public static final int DECODE_BRUTE_FORCE = 3;
    public static final int EXIT = 4;

    private File userFile = null;
    private Scanner scanner = new Scanner(System.in);
    private CaesarCipher caesarCipher = new CaesarCipher();


    private static boolean isRunning = true;
    public void Run () {
        ValidateUserFile();

        while (isRunning == true) {
            System.out.println(MENU_INFO);

            switch (scanner.nextInt()){
                case ENCODE -> caesarCipher.encode(userFile, ValidateUserKey());
                case DECODE_WITH_KEY -> caesarCipher.decodeWithKey(userFile, ValidateUserKey());
                case DECODE_BRUTE_FORCE -> caesarCipher.decodeBruteForce(userFile);
                case EXIT -> isRunning = false;
                default -> System.out.println(INCORRECT_MENU_INDEX);
            }
        }
    }

    private void ValidateUserFile () {
        while (true) {
            System.out.print(ENTRY_FILE_PATH);
            String filePath = scanner.nextLine();

            userFile = new File(filePath);

            if (userFile.exists()) {
                break;
            } else {
                System.out.println(INCORRECT_FILE_PATH);
            }
        }
    }

    private CaesarKey ValidateUserKey () {
        while (true) {
            System.out.print(ENTRY_KEY);
            int userKey = scanner.nextInt();

            if(userKey < 1 || userKey > 100) {
                System.out.println(INCORRECT_USER_KEY);
            } else {
                return new CaesarKey(userKey);
            }
        }
    }
}
