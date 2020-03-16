package wolox.training.models;

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
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  private String author;

  @NotNull
  private String image;

  @NotNull
  private String title;

  @NotNull
  private String subtitle;

  @NotNull
  private String publisher;

  @NotNull
  private String year;

  @NotNull
  private int pages;

  @NotNull
  private String isbn;

  @ManyToMany(mappedBy = "books")
  private List<User> users = new ArrayList<User>();

  Book() {
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<User> getUsers() {
    return (List<User>) Collections.unmodifiableCollection(users);
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public void addUser(User user){
    if(this.users.contains(users))
      throw new BookAlreadyOwnedException("Can't add a user that already owns that book");
    this.users.add(user);
  }
  public void removeUser(User user){
    this.users.remove(user);
  }
}
