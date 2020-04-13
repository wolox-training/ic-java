package wolox.training.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import wolox.training.models.BookInfo;

public class OpenLibraryService {
  private final RestTemplate restTemplate;
  ;

  public OpenLibraryService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public BookInfo bookInfo(String isbn){
    String jsonResponse = this.getBookInfoAsJsonString();


    JsonParser springParser = JsonParserFactory.getJsonParser();
    Map<String, Object> map = springParser.parseMap(jsonResponse);

    String title = (String) map.get("title");
    String subtitle = (String) map.get("subtitle");
    int numberOfPages = (int) map.get("number_of_pages");
    List<String> authors = (List<String>) map.get("authors");
    List<String> publishers = new ArrayList<String>;
    LocalDate publishDate = new LocalDate(2020, 12, 12, 20, 20, 20);
    return new BookInfo(isbn, title, subtitle, authors, publishers, numberOfPages, publishDate);
  }

 private String getBookInfoAsJsonString() {
    String url = "https://jsonplaceholder.typicode.com/posts";
    return this.restTemplate.getForObject(url, String.class);
  }

}

/*
{"ISBN:0385472579":
{"publishers": [
  {"name": "Anchor Books"}
 ],
  "cover": {"small": "https://covers.openlibrary.org/b/id/240726-S.jpg", "large": "https://covers.openlibrary.org/b/id/240726-L.jpg", "medium": "https://covers.openlibrary.org/b/id/240726-M.jpg"},
  "subjects": [
    {"url": "https://openlibrary.org/subjects/caricatures_and_cartoons",
    "name": "Caricatures and cartoons"},
    {"url": "https://openlibrary.org/subjects/zen_buddhism",
    "name": "Zen Buddhism"}
  ],
  "publish_date": "1994",
  "key": "/books/OL1397864M",
  "authors": [
    {"url": "https://openlibrary.org/authors/OL223368A/Zhizhong_Cai", "name": "Zhizhong Cai"}
  ],
  "classifications": {"dewey_decimal_class": ["294.3/927"],
  "lc_classifications": ["BQ9265.6 .T7313 1994"]},
  "publish_places": [
    {"name": "New York"}]}
  }
 */