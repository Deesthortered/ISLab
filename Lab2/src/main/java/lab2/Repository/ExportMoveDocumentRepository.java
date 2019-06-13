package lab2.Repository;

import lab2.Entity.ExportMoveDocument;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExportMoveDocumentRepository extends CrudRepository<ExportMoveDocument, Long>, EntityRepository<ExportMoveDocument, Long> {
    List<ExportMoveDocument> findAll(Example<ExportMoveDocument> employee);
}