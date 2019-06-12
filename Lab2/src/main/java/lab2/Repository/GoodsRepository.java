package lab2.Repository;

import lab2.Entity.Goods;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository<Goods, Long> { }