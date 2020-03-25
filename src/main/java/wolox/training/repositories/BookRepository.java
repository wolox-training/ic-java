package wolox.training.repositories;

    import org.springframework.data.repository.CrudRepository;
    import org.springframework.stereotype.Repository;
    import wolox.training.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

  Book findByAuthor(String author);

  Book findById(int id);
}
