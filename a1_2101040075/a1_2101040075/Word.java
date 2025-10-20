package a1_2101040075;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Word {
	public static Set<String> stopWords = new HashSet<>();
	private final String rawText;
	private String text, prefix, suffix = "";
	private boolean ok = false;

	// constructor
	public Word(String rawText) {
		this.rawText = rawText;
		converse();
	}

	public static Word createWord(String rawText) {
		return new Word(rawText);
	}

	// Function to load stopwords from a file
	// i have to check because I dont know why i paste absolute path
	// for each file or docs it works, but relative path it said that
	// not found???
	public static boolean loadStopWords(String fileName) {
		try (Scanner scanner = new Scanner(new File(fileName))) {
			stopWords.clear(); // Clear existing stop words
			while (scanner.hasNextLine()) {
				stopWords
						.add(scanner.nextLine().trim().toLowerCase());
				// Add each line as a stop word
			}
			return true; // Return true if loading is successful
		} catch (FileNotFoundException e) {
			// Print error message if the file is not found
			System.out.println("Error: The file '" + fileName
					+ "' was not found.");
			return false; // Return false if an error occurs
		} catch (Exception e) {
			// Optional: catch any other unexpected exceptions
			System.out.println("An unexpected error occurred: "
					+ e.getMessage());
			return false; // Return false if any other error occurs
		}
	}

	private void converse() {
	    // Cut all whitespace before and after to keep text only
	    String trimmed = rawText.trim();
	    System.out.println("Trimmed rawText: '" + trimmed + "'");

	    // Cut suffix from rawText
	    suffix = clipSuff(trimmed);
	    System.out.println("Detected suffix: '" + suffix + "'");

	    if (suffix != null) {
	        trimmed = trimmed.substring(0, trimmed.length() - suffix.length());
	        System.out.println("Trimmed text after removing suffix: '" + trimmed + "'");
	    }

	    // Extract prefix from rawText
	    prefix = clipPref(trimmed);
	    System.out.println("Detected prefix: '" + prefix + "'");

	    if (prefix != null) {
	        trimmed = trimmed.substring(prefix.length());
	        System.out.println("Trimmed text after removing prefix: '" + trimmed + "'");
	    }

	    // Validate text => valid: trim all space; not valid => keep rawText as text
//	    ok = !trimmed.isEmpty() && trimmed.matches("(['-]?)[a-zA-Z]+([-'][a-zA-Z]+)*");
//	    ok = !trimmed.isEmpty() && trimmed.matches("['-]?[a-zA-Z]+([-'][a-zA-Z]+)*['-]?");
	    ok = !trimmed.isEmpty() && trimmed.matches("['-]*[a-zA-Z]+([-'][a-zA-Z]+)*['-]*");


	    System.out.println("Is the trimmed text valid? " + ok);

	    text = ok ? trimmed : rawText;
	    System.out.println("Final text: '" + text + "'");

	    // If invalid => not have prefix and suffix
	    if (!ok) {
	        prefix = suffix = "";
	        System.out.println("Text is invalid, resetting prefix and suffix to empty.");
	    }
	}

	private String clipSuff(String text) {
        if (text.endsWith("'s") || text.endsWith("'d")) {
            return text.endsWith("'s") ? "'s" : "'d";
        }

        if (text.endsWith("'") || text.endsWith("-") || text.endsWith("-'") || text.endsWith("'-")) {
            // Do not extract anything as a suffix
            return "";
        }
        // Find the end index for a valid suffix
        int end = text.length();
        while (end > 0 && !Character.isLetterOrDigit(text.charAt(end - 1))) {
            end--;
        }

        return (end < text.length()) ? text.substring(end) : "";
    }
    
	  
	private String clipPref(String text) {
	    int start = 0;
	    while (start < text.length()) {
	        char firstChar = text.charAt(start);
	        if (Character.isLetterOrDigit(firstChar) ||
	            (start == 0 && (firstChar == '-' || firstChar == '\''))) {
	            break;
	        }
	        start++;
	    }
	    return (start > 0) ? text.substring(0, start) : "";
	}

	public boolean isKeyword() {
		return ok && !stopWords.contains(text.toLowerCase());
	}

	public String getPrefix() {
		return prefix != null ? prefix : "";
		// Return empty string instead of null if null
	}

	public String getSuffix() {
		return suffix != null ? suffix : "";
		// Return empty string instead of null if null
	}

	public String getText() {
		return text;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Word))
			return false;
		Word word = (Word) o;
		return text.equalsIgnoreCase(word.text);
	}

	@Override
	public String toString() {
		return prefix + text + suffix;
	}

	@Override
	public int hashCode() {
		return text.toLowerCase().hashCode();
	}
}
