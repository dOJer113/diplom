import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    private final Map<String, List<PageEntry>> map = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File pdf : pdfsDir.listFiles()) {
            var doc = new PdfDocument(new PdfReader(pdf));
            int pageCount = doc.getNumberOfPages();
            int i = 0;
            while (i < pageCount) {
                i++;
                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> freqs = new HashMap<>();
                for (var word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);
                }
                for (String word : freqs.keySet()) {
                    PageEntry pageEntry = new PageEntry(pdf.getName(), i, freqs.get(word));
                    if (map.containsKey(word)) {
                        map.get(word).add(pageEntry);
                        map.put(word,map.get(word).stream().sorted().collect(Collectors.toList()));
                    } else {
                        map.put(word, new ArrayList<>());
                        map.get(word).add(pageEntry);
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> result = map.get(word.toLowerCase());

        if (result != null) {
            return result;
        }

        return Collections.emptyList();
    }
}