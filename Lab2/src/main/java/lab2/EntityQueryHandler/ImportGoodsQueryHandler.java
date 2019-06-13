package lab2.EntityQueryHandler;

import lab2.Entity.Entity;
import lab2.Entity.ImportGoods;
import lab2.Repository.EntityRepository;
import lab2.Repository.ImportGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportGoodsQueryHandler extends EntityQueryHandler {
    private static ImportGoodsQueryHandler instance = new ImportGoodsQueryHandler();
    @Autowired
    private ImportGoodsRepository repository;

    private ImportGoodsQueryHandler() {
    }
    public static EntityQueryHandler getInstance() {
        return instance;
    }

    @Override
    public Entity getEntity() {
        return new ImportGoods();
    }

    @Override
    public EntityRepository getRepository() {
        return repository;
    }
}