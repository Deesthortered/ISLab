package lab2.Repository;

import lab2.Entity.AvailableGoods;
import org.springframework.data.repository.CrudRepository;

public interface AvailableGoodsRepository extends CrudRepository<AvailableGoods, Long> { }