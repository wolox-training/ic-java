
package wolox.training.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import wolox.training.models.BookInfo;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class OpenLibraryServiceTest {

  @Autowired
  private OpenLibraryService openLibraryService;

  private MockRestServiceServer mockServer;

  @MockBean
  private RestTemplate restTemplate;

  @Before
  public void setUp() {
  }

  private String isbn = "asdf";

  @Test
  public void whenFindingByIsbn_itReturnsBookInfoWithAValidTitle() {
    mockBookResponse(isbn);
    assertThat(openLibraryService.bookInfo(isbn).getTitle()).isEqualTo("Zen speaks");
  }

  @Test
  public void whenFindingByIsbn_itReturnsBookInfoWithAValidSubtitle() {
    mockBookResponse(isbn);
    assertThat(openLibraryService.bookInfo(isbn).getSubtitle()).isEqualTo("shouts of nothingness");
  }

  @Test
  public void whenFindingByIsbn_itReturnsBookInfoWithAValidNumberOfPages() {
    mockBookResponse(isbn);
    assertThat(openLibraryService.bookInfo(isbn).getNumberOfPages()).isEqualTo(159);
  }

  @Test
  public void whenFindingByIsbn_itReturnsBookInfoWithAValidPublishDate() {
    mockBookResponse(isbn);
    BookInfo response = openLibraryService.bookInfo(isbn);
    assertThat(response.getPublishDate())
        .isEqualTo(LocalDate.of(1994, 1, 1));
  }

  @Test
  public void whenFindingByIsbn_itReturnsBookInfoWithAValidNumberOfPublishers() {
    mockBookResponse(isbn);
    assertThat(openLibraryService.bookInfo(isbn).getPublishers().size()).isEqualTo(1);
  }

  @Test
  public void whenFindingByIsbn_itReturnsBookInfoWithAValidNumberOfAuthors() {
    mockBookResponse(isbn);
    assertThat(openLibraryService.bookInfo(isbn).getAuthors().size()).isEqualTo(1);
  }

  @Test
  public void whenFindingByNonExistentIsbn_itReturnsNothing() {
    mockNonExistentBookResponse(isbn);
    assertThat(openLibraryService.bookInfo(isbn).getAuthors().size()).isEqualTo(1);
  }

  private UriComponents buildBookUrl(String isbn) {
    return UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("openlibrary.org")
        .path("api/books")
        .queryParam("bibkeys  ", "ISBN:" + isbn)
        .queryParam("format", "json")
        .queryParam("jscmd", "data").build();
  }

  private void mockBookResponse(String isbn) {
    Mockito
        .when(restTemplate.getForObject(buildBookUrl(isbn).toString(), String.class))
          .thenReturn(
                "{\"ISBN:0385472579\": "
                    + "{\"publishers\": [{\"name\": \"Anchor Books\"}], "
                    + "\"pagination\": \"159 p. :\", "
                    + "\"identifiers\": {\"lccn\": [\"93005405\"], "
                    + "\"openlibrary\": [\"OL1397864M\"], "
                    + "\"isbn_10\": [\"0385472579\"], "
                    + "\"librarything\": [\"192819\"], "
                    + "\"goodreads\": [\"979250\"]}, "
                    + "\"subtitle\": \"shouts of nothingness\", "
                    + "\"title\": \"Zen speaks\", "
                    + "\"url\": \"https://openlibrary.org/books/OL1397864M/Zen_speaks\", "
                    + "\"number_of_pages\": 159, "
                    + "\"cover\": {\"small\": \"https://covers.openlibrary.org/b/id/240726-S.jpg\", \"large\": \"https://covers.openlibrary.org/b/id/240726-L.jpg\", \"medium\": \"https://covers.openlibrary.org/b/id/240726-M.jpg\"}, "
                    + "\"subjects\": [], "
                    + "\"publish_date\": \"1994\", "
                    + "\"key\": \"/books/OL1397864M\", "
                    + "\"authors\": [{\"url\": \"https://openlibrary.org/authors/OL223368A/Zhizhong_Cai\", \"name\": \"Zhizhong Cai\"}], "
                    + "\"classifications\": {}, "
                    + "\"publish_places\": [{\"name\": \"New York\"}]}}");
  }

  private void mockNonExistentBookResponse(String isbn) {
    Mockito
        .when(restTemplate.getForObject(buildBookUrl(isbn).toString(), String.class))
        .thenReturn("{}");
  }
}