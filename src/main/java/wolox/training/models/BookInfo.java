package wolox.training.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Book info")
public class BookInfo {

  @ApiModelProperty(notes = "ISBN code")
  private String isbn;

  @ApiModelProperty(notes = "Title of the book")
  private String title;

  @ApiModelProperty(notes = "Subtitle of the book")
  private String subtitle;

  @ApiModelProperty(notes = "Authors")
  private List<String> authors = new ArrayList<String>();

  @ApiModelProperty(notes = "Publishers")
  private List<String> publishers = new ArrayList<String>();

  @ApiModelProperty(notes = "Number of pages")
  private int numberOfPages;

  @ApiModelProperty(notes = "Publishing date")
  private LocalDate publishDate;


  public BookInfo(String isbn, String title, String subtitle, List<String> authors, List<String> publishers, int numberOfPages, LocalDate publishDate) {
    this.setIsbn(isbn);
    this.setTitle(title);
    this.setSubtitle(subtitle);
    this.setAuthors(authors);
    this.setPublishers(publishers);
    this.setNumberOfPages(numberOfPages);
    this.setPublishDate(publishDate);
  }

  public LocalDate getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(LocalDate publishDate) {
    this.publishDate = publishDate;
  }

  public int getNumberOfPages() {
    return numberOfPages;
  }

  public void setNumberOfPages(int numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  public List<String> getPublishers() {
    return publishers;
  }

  public void setPublishers(List<String> publishers) {
    this.publishers = publishers;
  }

  public List<String> getAuthors() {
    return authors;
  }

  public void setAuthors(List<String> authors) {
    this.authors = authors;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }
}
