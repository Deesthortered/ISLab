package lab2.EntityQueryHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab2.Entity.Entity;
import lab2.Repository.EntityRepository;
import org.springframework.data.domain.Example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class EntityQueryHandler {
    protected EntityQueryHandler() {
    }

    public String getEntityList(String filter, String limited, String listBeginInd, String listSize) throws IOException {
        String json = java.net.URLDecoder.decode(filter, StandardCharsets.UTF_8.name());
        ObjectMapper mapper = new ObjectMapper();
        Entity filteringEntity = mapper.readValue(json, getEntity().getClass());

        EntityRepository repository = getRepository();
        List<Entity> list = repository.findAll(Example.of(filteringEntity));

        return mapper.writeValueAsString(list);
    }
    public String addEntity(String jsonEntity) throws IOException {
        String json = java.net.URLDecoder.decode(jsonEntity, StandardCharsets.UTF_8.name());
        ObjectMapper mapper = new ObjectMapper();
        Entity entity = mapper.readValue(json, getEntity().getClass());

        EntityRepository repository = getRepository();
        repository.save(entity);
        return "ok";
    }
    public String deleteEntity(String id) {
        EntityRepository repository = getRepository();
        repository.delete(Long.parseLong(id));
        return "ok";
    }
    public String editEntity(String jsonEntity) throws IOException {
        String json = java.net.URLDecoder.decode(jsonEntity, StandardCharsets.UTF_8.name());
        ObjectMapper mapper = new ObjectMapper();
        Entity entity = mapper.readValue(json, getEntity().getClass());

        EntityRepository repository = getRepository();
        Entity oldEntity = (Entity) repository.findAll(Example.of(entity)).get(0);
        entity.setId(oldEntity.getId());
        repository.save(entity);
        return "ok";
    }

    public abstract Entity getEntity();
    public abstract EntityRepository getRepository();
}