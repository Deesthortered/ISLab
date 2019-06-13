package lab2.Repository;

import lab2.Entity.ImportDocument;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImportDocumentRepository extends CrudRepository<ImportDocument, Long>, EntityRepository<ImportDocument, Long> {
    List<ImportDocument> findAll(Example<ImportDocument> employee);
}