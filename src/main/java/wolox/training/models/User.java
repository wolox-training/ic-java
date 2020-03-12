package wolox.training.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false)
  private String username;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false)

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_user",
        joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id",
            referencedColumnName = "id"))
  private List<Book> books;

  public User() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public List<Book> getBooks() {
    return (List<Book>) Collections.unmodifiableCollection(books);
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void addBook(Book book){
    if(this.books.contains(book))
      throw new BookAlreadyOwnedException("Can't add a book that's already owned");
    this.books.add(book);
  }
  public void removeUser(Book book){
    this.books.remove(book);
  }
}