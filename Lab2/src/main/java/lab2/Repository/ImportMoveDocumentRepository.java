package lab2.Repository;

import lab2.Entity.ImportMoveDocument;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImportMoveDocumentRepository extends CrudRepository<ImportMoveDocument, Long>, EntityRepository<ImportMoveDocument, Long> {
    List<ImportMoveDocument> findAll(Example<ImportMoveDocument> employee);
}