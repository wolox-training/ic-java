package wolox.training.models;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
@Table(name = "users")
@ApiModel(description = "Users of the site")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false)
  @ApiModelProperty(notes = "Handle of the user")
  private String username;

  @Column(nullable = false)
  @ApiModelProperty(notes = "Real name of the user")
  private String name;

  @Column(nullable = false)
  @ApiModelProperty(notes = "Birth date of the user")
  private LocalDate birthDate;

  @Column(nullable = false)
  @ApiModelProperty(notes = "Books the user rented")
  @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
  private List<Book> books = new ArrayList<Book>();

  public User() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    Preconditions.checkArgument(Strings.isNullOrEmpty(username));
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    Preconditions.checkArgument(Strings.isNullOrEmpty(name));
    this.name = name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    Preconditions.checkArgument(birthDate.isBefore(LocalDate.now()));
    Preconditions.checkNotNull(birthDate);
    this.birthDate = birthDate;
  }

  public List<Book> getBooks() {
    return (List<Book>) Collections.unmodifiableCollection(books);
  }

  public void setBooks(List<Book> books) {
    Preconditions.checkNotNull(books);
    this.books = books;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    Preconditions.checkNotNull(id);
    this.id = id;
  }

  public void addBook(Book book) {
    if (this.books.contains(book)) {
      throw new BookAlreadyOwnedException("Can't add a book that's already owned");
    }
    this.books.add(book);
  }

  public void removeBook(Book book) {
    this.books.remove(book);
  }
}
