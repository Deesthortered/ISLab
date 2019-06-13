package lab2.Repository;

import lab2.Entity.Goods;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, Long>, EntityRepository<Goods, Long> {
    List<Goods> findAll(Example<Goods> employee);
}