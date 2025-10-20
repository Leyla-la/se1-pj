package a1_2101040075;

import java.util.List;
import java.util.Objects;

public class Result implements Comparable<Result> {
	
    private final Doc d;
    private final List<Match> matches;
    private final int matchCount;
    private final int totalFreq;
    private final double avgFirstIndex;

    // Constructor
    public Result(Doc d, List<Match> matches) {
        this.d = d;
        this.matches = matches;
        this.matchCount = matches.size();
        this.totalFreq = calculateTotalFreq();
        this.avgFirstIndex = calculateAvgFirstIndex();
    }

    public Doc getDoc() {
        return d;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public int getTotalFrequency() {
        return totalFreq;
    }

    public double getAverageFirstIndex() {
        return avgFirstIndex;
    }

    private int calculateTotalFreq() {
        int sum = 0;
        for (Match match : matches) {
            sum += match.getFreq();
        }
        return sum;
    }

    private double calculateAvgFirstIndex() {
        if (matchCount == 0) return 0;
        int sum = 0;
        for (Match match : matches) {
            sum += match.getFirstIndex();
        }
        return (double) sum / matchCount;
    }
    
    
    

    public String htmlHighlight() {
        String title = roundByTag(d.getTitle(), "<u>", "</u>");
        String body = roundByTag(d.getBody(), "<b>", "</b>");
        return "<h3>" + title + "</h3><p>" + body + "</p>";
    }

    private String roundByTag(List<Word> wList, String open, String close) {
        StringBuilder result = new StringBuilder();
        for (Word w : wList) {
            String text = w.getText();
            if (match(text)) {
                result.append(w.getPrefix()).append(open).append(text)
                .append(close).append(w.getSuffix()).append(" ");
            } else {
                result.append(w).append(" ");
            }
        }
        return result.toString().trim();
    }
    
    
    private boolean match(String text) {
        for (Match match : matches) {
            if (match.getWord().getText().equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Result o) {
    	
    	
        // Compare matchCount in descending order
        if (this.matchCount != o.matchCount) {
            return o.matchCount - this.matchCount; // Descending order
        }
        
        

        // Compare totalFreq in descending order
        if (this.totalFreq != o.totalFreq) {
            return o.totalFreq - this.totalFreq; // Descending order
        }

        
        
        // Compare avgFirstIndex in ascending order
        return Double.compare(this.avgFirstIndex, o.avgFirstIndex); // Ascending order
    }

    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result result = (Result) o;
        return d.equals(result.d) && matches.equals(result.matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(d, matches);
    }
}
