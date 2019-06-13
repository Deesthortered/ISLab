package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.Goods;
import lab2.Repository.EntityRepository;
import lab2.Repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class GoodsQueryHandler extends EntityQueryHandler {
    private static GoodsQueryHandler instance = new GoodsQueryHandler();
    @Autowired
    private GoodsRepository repository;

    private GoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new Goods();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}