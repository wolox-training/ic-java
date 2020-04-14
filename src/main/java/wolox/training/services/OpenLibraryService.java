package wolox.training.services;

import static org.springframework.boot.json.JsonParserFactory.getJsonParser;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.BookInfo;
import wolox.training.repositories.BookRepository;

@Service
public class OpenLibraryService {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private BookRepository bookRepository;

  public OpenLibraryService() {
  }

  public BookInfo bookInfo(String isbn) {
    Book existentBook = bookRepository.findByIsbn(isbn);
    if (existentBook != null) {
      return BookInfo.fromBook(existentBook);
    }
    BookInfo bookInfo = getBookFromOpenLibrary(isbn);
    bookRepository.save(Book.fromBookInfo(bookInfo));
    return bookInfo;
  }

  private BookInfo getBookFromOpenLibrary(String isbn) {
    String response = this.getBookInfoAsJsonString(isbn);
    if (response == null || response == "{}") {
      throw new BookNotFoundException("Book not found by ISBN");
    }
    Map<String, Object> map = (Map<String, Object>) getJsonParser().parseMap(response).get("ISBN:0385472579");
    return convertResponseMapToBookInfo(map, isbn);
  }

  private ArrayList<String> getObjectNames(Map<String, Object> map, String key){
    ArrayList<Object> objects = (ArrayList<Object>) map.get(key);
    ArrayList<String> objectNames = new ArrayList<>();
    for (Object object:objects) {
      String authorName = ((LinkedHashMap<String, String>) object).get("name");
      objectNames.add(authorName);
    }
    return objectNames;

  }
  private BookInfo convertResponseMapToBookInfo(Map<String, Object> map, String isbn) {
    LocalDate publishDate = LocalDate.of(Integer.parseInt((String)map.get("publish_date")), 1, 1);
    return new BookInfo(isbn, (String) map.get("title"),
        (String) map.get("subtitle"),
        getObjectNames(map, "authors"),
        getObjectNames(map, "publishers"),
        (int) map.get("number_of_pages"),
        publishDate);
  }

  private String getBookInfoAsJsonString(String isbn) {
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("openlibrary.org")
        .path("api/books")
        .queryParam("bibkeys", "ISBN:" + isbn)
        .queryParam("format", "json")
        .queryParam("jscmd", "data").build();

    return this.restTemplate.getForObject(uriComponents.toString(), String.class);
  }
}