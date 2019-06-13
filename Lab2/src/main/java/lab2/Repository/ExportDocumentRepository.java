package lab2.Repository;

import lab2.Entity.ExportDocument;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExportDocumentRepository extends CrudRepository<ExportDocument, Long>, EntityRepository<ExportDocument, Long> {
    List<ExportDocument> findAll(Example<ExportDocument> employee);
}