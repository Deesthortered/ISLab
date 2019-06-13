package lab2.Repository;

import lab2.Entity.AvailableGoods;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvailableGoodsRepository extends CrudRepository<AvailableGoods, Long>, EntityRepository<AvailableGoods, Long> {
    List<AvailableGoods> findAll(Example<AvailableGoods> employee);
}