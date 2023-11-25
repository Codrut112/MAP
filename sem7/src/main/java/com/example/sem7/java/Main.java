package com.example.sem7.java;


import com.example.sem7.java.domain.Utilizator;
import com.example.sem7.java.interfata.Interface;
import com.example.sem7.java.paging.Page;
import com.example.sem7.java.paging.Pageable;
import com.example.sem7.java.paging.PageableImplementation;
import com.example.sem7.java.repository.DbFriendshipRepository;
import com.example.sem7.java.repository.DbRepository;
import com.example.sem7.java.service.Service;
import com.example.sem7.java.validators.UtilizatorValidator;
import com.example.sem7.java.validators.ValidarePrietenie;
import com.example.sem7.java.db.UserDBPagingRepository;
public class Main {

    public static void main(String[] args) {



        UserDBPagingRepository userUserDBPagingRepository  = new UserDBPagingRepository("jdbc:postgresql://localhost:5432/Social_Network",
                "postgres", "coco");

        Pageable pageable = new PageableImplementation(2,5);

        Page<Utilizator> page = userUserDBPagingRepository.findAll(pageable);

        page.getContent().forEach(System.out::println);


        Service service = new Service(new DbRepository("jdbc:postgresql://localhost:5432/Social_Network","postgres","coco",new UtilizatorValidator()), new DbFriendshipRepository("jdbc:postgresql://localhost:5432/Social_Network","postgres","coco",new ValidarePrietenie()));
        Interface consola = new Interface(service);
     consola.run();

    }
}
