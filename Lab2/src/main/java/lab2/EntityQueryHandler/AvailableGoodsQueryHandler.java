package lab2.EntityQueryHandler;

import lab2.Entity.AvailableGoods;
import lab2.Entity.Entity;
import lab2.Repository.AvailableGoodsRepository;
import lab2.Repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AvailableGoodsQueryHandler extends EntityQueryHandler {
    private static AvailableGoodsQueryHandler instance = new AvailableGoodsQueryHandler();
    @Autowired
    private AvailableGoodsRepository repository;

    private AvailableGoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new AvailableGoods();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}