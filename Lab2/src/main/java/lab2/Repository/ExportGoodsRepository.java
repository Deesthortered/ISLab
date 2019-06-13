package lab2.Repository;

import lab2.Entity.ExportGoods;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExportGoodsRepository extends CrudRepository<ExportGoods, Long>, EntityRepository<ExportGoods, Long> {
    List<ExportGoods> findAll(Example<ExportGoods> employee);
}