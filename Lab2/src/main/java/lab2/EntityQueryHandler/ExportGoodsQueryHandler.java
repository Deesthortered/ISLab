package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.ExportGoods;
import lab2.Repository.EntityRepository;
import lab2.Repository.ExportGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ExportGoodsQueryHandler extends EntityQueryHandler {
    private static ExportGoodsQueryHandler instance = new ExportGoodsQueryHandler();
    @Autowired
    private ExportGoodsRepository repository;

    private ExportGoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new ExportGoods();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}