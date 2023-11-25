package com.example.sem7.java.repository;

import com.example.sem7.java.domain.Prietenie;
import com.example.sem7.java.domain.Tuple;
import com.example.sem7.java.validators.Validator;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFriendshipsRepository extends InMemoryRepository<Tuple<Long,Long>, Prietenie> implements FriendshipsRepository<Tuple<Long,Long>, Prietenie>  {
    /**
     * constructor
     *
     * @param validator
     */
    public InMemoryFriendshipsRepository(Validator<Prietenie> validator) {
        super(validator);
    }

    @Override
    public List<Prietenie> findAllFriends(long idUtilizator) {
        Iterable<Prietenie> allFrienhispsAuxiliar= this.findAll();
        List<Prietenie> allFriends =new ArrayList<>();
        allFrienhispsAuxiliar.forEach(allFriends::add);
        return allFriends.stream().filter(prietenie -> prietenie.memberOf(idUtilizator)).toList();
    }
}
