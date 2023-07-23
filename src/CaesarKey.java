public class CaesarKey {
    private int userKey;

    private static final int UKRAINIAN_CHARACTERS_COUNT = 66;

    private static final int PUNCTUATION_MARKS_CHARACTERS_COUNT = 8;
    CaesarKey (int userKey) {
        this.userKey = userKey;
    }

    public int calculateCodingUkrainianCharIntCode (int positionInDictionary){
        if (positionInDictionary + userKey > UKRAINIAN_CHARACTERS_COUNT - 1) {
            return Math.abs(positionInDictionary + userKey - UKRAINIAN_CHARACTERS_COUNT);
        } else {
            return positionInDictionary + userKey;
        }
    }

    public int calculateCodingPunctuationCharIntCode (int positionInDictionary){
        if (positionInDictionary + userKey > PUNCTUATION_MARKS_CHARACTERS_COUNT - 1) {
            return Math.abs(positionInDictionary + userKey - PUNCTUATION_MARKS_CHARACTERS_COUNT);
        } else {
            return positionInDictionary + userKey;
        }
    }

    public int calculateDecodingUkrainianCharIntCode (int positionInDictionary){
        if (positionInDictionary - userKey < 0) {
            return Math.abs(UKRAINIAN_CHARACTERS_COUNT - userKey + positionInDictionary);
        } else {
            return positionInDictionary - userKey;
        }
    }

    public int calculateDecodingPunctuationCharIntCode (int positionInDictionary){
        if (positionInDictionary - userKey < 0) {
            return Math.abs(PUNCTUATION_MARKS_CHARACTERS_COUNT - userKey + positionInDictionary);
        } else {
            return positionInDictionary - userKey;
        }
    }
}
