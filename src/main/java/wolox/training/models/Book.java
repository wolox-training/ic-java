package wolox.training.models;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToMany;
import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
@ApiModel(description = "Books from the library")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  @ApiModelProperty(notes = "Author of the book")
  private String author;

  @NotNull
  @ApiModelProperty(notes = "Image that will be shown")
  private String image;

  @NotNull
  @ApiModelProperty(notes = "Title of the book")
  private String title;

  @NotNull
  @ApiModelProperty(notes = "Subtitle of the book")
  private String subtitle;

  @NotNull
  @ApiModelProperty(notes = "Publisher of the book")
  private String publisher;

  @NotNull
  @ApiModelProperty(notes = "Year the book was released")
  private String year;

  @NotNull
  @ApiModelProperty(notes = "Number of pages")
  private int pages;

  @NotNull
  @ApiModelProperty(notes = "ISBN code")
  private String isbn;

  @ManyToMany(mappedBy = "books")
  @ApiModelProperty(notes = "Users that have rented this book ")
  private List<User> users = new ArrayList<User>();

  public Book() {
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(image));
    this.image = image;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(author));
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(title));
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(subtitle));
    this.subtitle = subtitle;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(publisher));
    this.publisher = publisher;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(year));
    this.year = year;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    Preconditions.checkNotNull(pages);
    Preconditions.checkArgument(pages > 0);
    this.pages = pages;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(isbn));
    this.isbn = isbn;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    Preconditions.checkNotNull(id);
    this.id = id;
  }

  public List<User> getUsers() {
    return (List<User>) Collections.unmodifiableList(users);
  }

  public void setUsers(List<User> users) {
    Preconditions.checkNotNull(users);
    this.users = users;
  }

  public void addUser(User user) {
    if (this.users.contains(users)) {
      throw new BookAlreadyOwnedException("Can't add a user that already owns that book");
    }
    this.users.add(user);
  }

  public void removeUser(User user) {
    this.users.remove(user);
  }
}
