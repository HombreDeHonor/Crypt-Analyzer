import java.io.*;
import java.util.Arrays;

public class CaesarCipher {
    private String ukrainianLanguage = "AБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя";
    private String punctuationMarks = " .,\":-!?";

    private static final String HIDDEN_SIGNATURE_KEY = "Використано шифр Гая Юлія Цезаря\r";
    private static final String RESULT_ENCODED_FILE_PATH = "EncodeResultFile.txt";
    private static final String RESULT_DECODED_FILE_PATH = "DecodeResultFile.txt";
    private String userData;

    public void encode (File userFile, CaesarKey key) {
        ReadUserDataFromFile(userFile);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userData.length(); ++i){
            try {
                Character character = userData.charAt( i );
                if (ukrainianLanguage.contains( character.toString() )) {
                    int positionInAlphabet = ukrainianLanguage.lastIndexOf( character );
                    stringBuilder.append( ukrainianLanguage.charAt( key.calculateCodingUkrainianCharIntCode( positionInAlphabet ) ) );
                } else if (punctuationMarks.contains( character.toString() )) {
                    int position = punctuationMarks.lastIndexOf( character );
                    stringBuilder.append( punctuationMarks.charAt( key.calculateCodingPunctuationCharIntCode( position ) ) );
                } else {
                    stringBuilder.append( character );
                }
            } catch (Exception e) {
                System.out.println(i);
            }
        }

        WriteResultUserDataToFile(stringBuilder.toString(), RESULT_ENCODED_FILE_PATH);
    }

    public void decodeWithKey (File userFile, CaesarKey key) {
        ReadUserDataFromFile(userFile);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < userData.length(); ++i){
            try {
                Character character = userData.charAt(i);
                if (ukrainianLanguage.contains(character.toString())) {
                    int positionInAlphabet = ukrainianLanguage.lastIndexOf(character);
                    stringBuilder.append(ukrainianLanguage.charAt(key.calculateDecodingUkrainianCharIntCode(positionInAlphabet)));
                } else if (punctuationMarks.contains(character.toString())) {
                    int position = punctuationMarks.lastIndexOf(character);
                    stringBuilder.append(punctuationMarks.charAt(key.calculateDecodingPunctuationCharIntCode(position)));
                } else {
                    stringBuilder.append(character);
                }
            } catch (RuntimeException exception) {
                System.out.println(i);
            }
        }

        WriteResultUserDataToFile(stringBuilder.toString(), RESULT_DECODED_FILE_PATH);
    }

    public void decodeBruteForce (File userFile) {
        ReadUserDataFromFile(userFile);

        String[] userDataStringsArray = userData.split( "\n" );

        int userKey = FindKey(userDataStringsArray[userDataStringsArray.length - 1]);
        if (userKey == -1 ){
            WriteResultUserDataToFile("Program did not find correct key", RESULT_DECODED_FILE_PATH);
        } else {
            decodeWithKey( userFile, new CaesarKey(userKey));
        }
    }

    private void ReadUserDataFromFile (File userFile) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(userFile); BufferedReader bufferedReader = new BufferedReader(fileReader)){
            while (bufferedReader.ready()){
                stringBuilder.append(bufferedReader.readLine());
                stringBuilder.append(System.lineSeparator());
            }
        } catch (IOException ioException) {
            System.out.println("Error with reading data from user file");
        }

        userData = stringBuilder.toString();
    }

    private void WriteResultUserDataToFile (String resultUserData, String resultFile) {
        File file = new File(resultFile);
        try(FileWriter fileWriter = new FileWriter(file); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            bufferedWriter.write(resultUserData);
        } catch (IOException ioException) {
            System.out.println("Error with writing data to result file");
        }

    }

    private int FindKey (String stringKey) {
        String currentDecodingTry = stringKey;
        for (int i = 1; i <= 100; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            CaesarKey caesarKey = new CaesarKey(i);
            for (int j = 0; j < stringKey.length(); ++j){
                Character character = stringKey.charAt(j);
                if (ukrainianLanguage.contains(character.toString())) {
                    int positionInAlphabet = ukrainianLanguage.lastIndexOf(character);
                    stringBuilder.append(ukrainianLanguage.charAt(caesarKey.calculateDecodingUkrainianCharIntCode(positionInAlphabet)));
                } else if (punctuationMarks.contains(character.toString())) {
                    int position = punctuationMarks.lastIndexOf(character);
                    stringBuilder.append(punctuationMarks.charAt(caesarKey.calculateDecodingPunctuationCharIntCode(position)));
                } else {
                    stringBuilder.append(character);
                }
            }
            currentDecodingTry = stringBuilder.toString();
            if (currentDecodingTry.equals(HIDDEN_SIGNATURE_KEY)) {
                return i;
            }
        }
        return -1;
    }
}
