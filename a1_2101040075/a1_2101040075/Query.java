package a1_2101040075;

import java.util.ArrayList;
import java.util.List;

public class Query {
    private final List<Word> keywords;

    // Constructor
    public Query(String searchPhrase) {
        keywords = new ArrayList<>();
        String[] rawSP = searchPhrase.split("\\s+");
        for (String eachSW : rawSP) {
            Word word = Word.createWord(eachSW);
            if (word.isKeyword()) {
                keywords.add(word);
            }
        }
    }

    //if empty => list rong
    public List<Word> getKeywords() {
        return keywords.isEmpty() ? new ArrayList<>() : new ArrayList<>(keywords);
    }
    
    // 
    public List<Match> matchAgainst(Doc d) {
    	
    	
        List<Word> words = new ArrayList<>();
        words.addAll(d.getTitle());
        words.addAll(d.getBody());

        
        List<Match> matches = new ArrayList<>();
        for (Word keyword : keywords) {
            int freq = 0;
            int firstIndex = -1;


            // Loop through each word in the document to find matches
            for (int i = 0; i < words.size(); i++) {
            	// Check if current word = the keyword
                if (words.get(i).equals(keyword)) {
                    freq++;
                 // If first occurrence => record index
                    if (firstIndex == -1) {
                        firstIndex = i; 
                        //Set firstIndex to the current index
                    }
                }
            }

            if (freq > 0) {
                matches.add(new Match(d, keyword, freq, firstIndex));
            }
        }

        for (int i = 0; i < matches.size() - 1; i++) {
        	
            for (int j = i + 1; j < matches.size(); j++) {
            	
            	// Swap matches if first index of first match > second
                if (matches.get(i).getFirstIndex() > matches.get(j).getFirstIndex()) {
                    Match temp = matches.get(i);
                    matches.set(i, matches.get(j));
                    matches.set(j, temp);
                }
            }
        }

        return matches;
    }
}
