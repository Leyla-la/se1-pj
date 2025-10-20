package a1_2101040075;

import java.util.ArrayList;

import java.util.List;

public class Doc {
	
    private final List<Word> title;
    private final List<Word> body;

    // Constructor to initialize and process the content into title and body
    public Doc(String content) {
        // check if empty or null
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty");
        }

        // split to 2 part title & body
        String[] doc = content.split("\\r?\\n", 2); 

        // consider title empty
        title = new ArrayList<>();
        if (!doc[0].trim().isEmpty()) {
            for (String word : doc[0].trim().split("\\s+")) { // split word
                if (!word.isEmpty()) {
                    title.add(Word.createWord(word)); // => create list Word
                }
            }
        } else {
            throw new IllegalArgumentException("Please provide a title");
        }

        
        
        // consider if body is empty or null
        body = new ArrayList<>();
        if (doc.length > 1 && !doc[1].trim().isEmpty()) {
            for (String word : doc[1].trim().split("\\s+")) { // split word in body
                if (!word.isEmpty()) {
                    body.add(Word.createWord(word)); // => create list
                }
            }
        } else {
            throw new IllegalArgumentException("Please provide a body");
        }
    }


    public List<Word> getTitle() {
        return title.isEmpty() ? title : new ArrayList<>(title); // empty => empty list
    }

    
    public List<Word> getBody() {
        return body.isEmpty() ? body : new ArrayList<>(body); //same
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doc)) return false;
        Doc other = (Doc) o;
        return title.equals(other.title) && body.equals(other.body);
    }

    
}
