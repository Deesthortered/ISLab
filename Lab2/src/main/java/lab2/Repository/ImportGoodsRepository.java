package lab2.Repository;

import lab2.Entity.ImportGoods;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImportGoodsRepository extends CrudRepository<ImportGoods, Long>, EntityRepository<ImportGoods, Long> {
    List<ImportGoods> findAll(Example<ImportGoods> employee);
}