package repository;




import domain.Entity;
import domain.Utilizator;
import validators.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {
    private Validator<E> validator;
    Map<ID,E> entities;

    /**
     * constructer
     * @param validator Validator<E> (generic)
     */
    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities=new HashMap<ID,E>();
    }

    /**
     *
     * @param id -the id of the entity to be returned
     *           id must not be null -> throw Error("id must be not null")
     * @return E
     *
     */
    @Override
    public E findOne(ID id){
        if (id==null)
            throw new Error("id must be not null");
        return entities.get(id);
    }

    /**
     * return an iterable of entities
     * @return Iterable<E>
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     * add an entity in repo
     * @param entity
     *         entity must be not null -> throw Error("id must be not null")
     *         entity is not valid -> throw ValidationException(message)
     * @return null if the entity is saved
     *         the enetity if the entity is already in the repo
     */
    @Override
    public E save(E entity) {
        if (entity==null)
            throw new Error("entity must be not null");
        validator.validate(entity);
        if(entities.get(entity.getId()) != null) {
            return entity;
        }
        else entities.put(entity.getId(),entity);
        return null;
    }

    /**
     * delete an enetity based on the id
     * @param id E
     *      id must be not null
     * @return the entity if the id exists in the map
     *         return null otherwise
     */
    @Override
    public E delete(ID id) {

       return entities.remove(id);

    }

    /**
     * updates an entity
     * @param entity E
     *          entity must not be null -> throw Error("id must be not null")
     * @return entity if there was not in the repo before
     *         null otherwise
     */
    @Override
    public E update(E entity) {

        if(entity == null)
            throw new Error("entity must be not null!");
        validator.validate(entity);

        entities.put(entity.getId(),entity);

        if(entities.get(entity.getId()) != null) {
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;

    }

    /**
     *
     * @return number of elements
     */
    public int size(){
        return entities.size();
    }
}

