package a1_2101040075;

public class Match implements Comparable<Match> {
    private final Doc d;         
    private final Word w;       
    private final int freq;        
    private final int firstIndex;  

   
    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.d = d;
        this.w = w;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }
    
    public Word getWord() {
        return w;
    }

    public int getFreq() {
        return freq;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    @Override
    public int compareTo(Match other) {
        // Check other object is null or not
        if (other == null) {
            // other null, => this greater
            return 1; 
        }

        // Get first index of both objs
        int thisIndex = this.firstIndex;
        int otherIndex = other.firstIndex;

        // Compare 2 indices
        if (thisIndex > otherIndex) {
            return 1; // This greater
        } else if (thisIndex < otherIndex) {
            return -1; // This less
        } else {
            return 0; // equal
        }
    }

    @Override
    public String toString() {
        return String.format(
            "==== Match Details ====\n" +
            "Document: %s\n" +
            "Matched Word: '%s'\n" +
            "Frequency: %d\n" +
            "First Occurrence at Index: %d\n" +
            "========================", 
            d, w, freq, firstIndex
        );
    }
}