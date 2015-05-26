import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * For purposes of this project, a snippet is a subsequence of a document 
 * that contains all the search terms.
 * 
 * Given a document (a sequence of words) and set of search terms, find the 
 * minimal length subsequence in the document that contains all of the search 
 * terms.
 * 
 * If there are multiple subsequences that have the same minimal length, you 
 * may return any one of them.
 * 
 */
public class MinimumSnippet {
	public int start;
	public int end;
	public boolean checkAll = false;
	public int minimum = 1000000;
	public ArrayList <String> snippet = new ArrayList <String>();
	ArrayList <String> OrgDoc = new ArrayList <String>();
	ArrayList <String> OrgTerm = new ArrayList <String>();

	/**
	 * Compute minimum snippet.
	 * 
	 * Given a document (represented as an List<String>), and a set of terms
	 * (represented as a List<String>), find the shortest subsequence of the
	 * document that contains all of the terms.
	 * 
	 * This constructor should find the minimum snippet, and store information
	 * about the snippet in fields so that the methods can be called to query
	 * information about the snippet. All significant computation should be done
	 * during construction.
	 * 
	 * @param document
	 *            The Document is an Iterable<String>. Do not change the
	 *            document. It is an Iterable, rather than a List, to allow for
	 *            implementations where we scan very large documents that are
	 *            not read entirely into memory. If you have problems figuring
	 *            out how to solve it with the document represented as an
	 *            Iterable, you may cast it to a List<String>; in all but a very
	 *            small number of test cases, in will in fact be a List<String>.
	 * 
	 * @param terms
	 *            The terms you need to look for. The terms will be unique
	 *            (e.g., no term will be repeated), although you do not need to
	 *            check for that. There should always be at least one term and 
	 *            your code should

	 *            throw an IllegalArgumentException if "terms" is
	 *            empty.
	 * 
	 * 
	 */
	public MinimumSnippet(Iterable<String> document, List<String> terms) {
		if (terms.size() == 0) {
			throw new IllegalArgumentException();
		}
		
		ArrayList <String> doc = new ArrayList();
		ArrayList <String> term = new ArrayList();

		for (String i: document) {
			OrgDoc.add(i);
			doc.add(i);
		}
		
		for (String j : terms) {
			OrgTerm.add(j);
			term.add(j);
		}
		
		ArrayList<Integer> startposition = new ArrayList <Integer>();
		int startstep =- 1;
		for (String k : doc) {
			startstep++;
			for (String l : term) {
				if (k.equals(l)) {
					startposition.add(startstep);

				}
			}
		}
		
		ArrayList <Integer> endposition=new ArrayList <Integer>();
		for (Integer hello : startposition) {
			for (int y = hello; y < doc.size(); y++) {
				for (String ok : term) {
					if (doc.get(y).equals(ok)) {
						term.remove(ok);
						break;
					}
				}
				if (term.size() == 0) {
					endposition.add(y);
					for (String u : terms) {
						term.add(u);
						y = doc.size();
					}
				}
			}
		}

		if (endposition.size() != 0) {
			checkAll = true;	
		} 
		
		for (int hhh = 0; hhh < endposition.size(); hhh++) {
			if (endposition.get(hhh) - startposition.get(hhh) < minimum) {
				start = startposition.get(hhh);
				end = endposition.get(hhh);
				minimum = end - start + 1;
			}
		}
	}

	/**
	 * Returns whether or not all terms were found in the document. If all terms
	 * were not found, then none of the other methods should be called.
	 * 
	 * @return whether all terms were found in the document.
	 */
	public boolean foundAllTerms() {
		return checkAll;
	}

	/**
	 * Return the starting position of the snippet
	 * 
	 * @return the index in the document of the first element of the snippet
	 */
	public int getStartingPos() {
		return start;
	}

	/**
	 * Return the ending position of the snippet
	 * 
	 * @return the index in the document of the last element of the snippet
	 */
	public int getEndingPos() {
		return end;
	}

	/**
	 * Return total number of elements contained in the snippet.
	 * 
	 * @return
	 */
	public int getLength() {
		return minimum;
	}

	/**
	 * Returns the position of one of the search terms as it appears in the original document
	 * 
	 * @param index index of the term in the original list of terms.  For example, if index
	 * is 0 then the method will return the position (in the document) of the first search term.
	 * If the index is 1, then the method will return the position (in the document) of the
	 * second search term.  Etc.
	 *  
	 * @return position of the term in the document
	 */
	public int getPos(int index) {
		for (int ku = start; ku <= end; ku++) {
			snippet.add(OrgDoc.get(ku));
		}
		String oh = OrgTerm.get(index);
		return start + snippet.indexOf(oh);
	}
	
	/**
	 * read file and add terms to list
	 * @throws IOException 
	 */
	public static ArrayList<String> readFile() throws IOException {
		ArrayList<String> words = new ArrayList();
		BufferedReader br = new BufferedReader(new FileReader("terms.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			words.add(line);
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				words.add(line);
			}
			String everything = sb.toString();
			} finally {
				br.close();
			}
		words.remove(words.size() - 1);
		return words;
	}

	/**
	 * RUN THE APP!
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<String> myTerms = new ArrayList();
		myTerms = readFile();
		System.out.print(myTerms);
	}
}