package a1_2101040075;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Engine {
	private List<Doc> docs = new ArrayList<>();

	// Load docs from dir => return count
	public int loadDocs(String dirname) {
		File dir = new File(dirname);

		// Check if the directory exists and is a directory
		// same as loadStopWord => i dont know why when i keep
		// relative path for each file/doc check => it cannot find
		// those files/docs
		// but when i paste absolute file => not any wrong
		if (!dir.exists()) {
			System.out.println("Error: The directory does not exist: "
					+ dirname);
			return 0;
		} else if (!dir.isDirectory()) {
			System.out.println(
					"Error: The path is not a directory: " + dirname);
			return 0;
		}

		// Filter for .txt files
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});

		// Check if files is null
		if (files == null) {
			System.out.println("Error: Unable to read the directory: "
					+ dirname);
			return 0;
		}

		// Clear the existing docs list before loading new files
		docs.clear();
		int count = 0;

		// Read each .txt file and add to docs
		for (File file : files) {
			try {
				// Read the file content
				String content = new String(
						Files.readAllBytes(file.toPath()));
				docs.add(new Doc(content));
				count++;
			} catch (IOException e) {
				System.out.println("Error reading file: "
						+ file.getName() + " - " + e.getMessage());
			}
		}

		// Print message if files were loaded or not
		if (count > 0) {
			System.out.println("Successfully loaded " + count
					+ " .txt file(s) from directory: " + dirname);
		} else {
			System.out
					.println("No .txt files were found in directory: "
							+ dirname);
		}

		// Return the count of successfully loaded documents
		return count;
	}

	// Return documents as array
	public Doc[] getDocs() {
		Doc[] docArray = new Doc[docs.size()];
		return docs.toArray(docArray);
	}

	// Search for query in documents and return sorted results
	public List<Result> search(Query q) {
		List<Result> results = new ArrayList<>();
		for (Doc doc : docs) {
			List<Match> matches = q.matchAgainst(doc);
			if (!matches.isEmpty()) {
				results.add(new Result(doc, matches));
			}
		}
		sortResults(results);
		return results;
	}

	// Sort results manually without using array functions
	private void sortResults(List<Result> results) {
		for (int i = 0; i < results.size() - 1; i++) {
			for (int j = i + 1; j < results.size(); j++) {
				if (results.get(i).compareTo(results.get(j)) > 0) {
					Result temp = results.get(i);
					results.set(i, results.get(j));
					results.set(j, temp);
				}
			}
		}
	}

	// Convert list of results to HTML format
	public String htmlResult(List<Result> results) {
		StringBuilder html = new StringBuilder();
		for (Result result : results) {
			html.append(result.htmlHighlight());
		}
		return html.toString();
	}
}
