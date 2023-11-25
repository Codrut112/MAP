package com.example.sem7.java.db;

import com.example.sem7.java.domain.Utilizator;
import com.example.sem7.java.paging.Page;
import com.example.sem7.java.paging.PageImplementation;
import com.example.sem7.java.paging.Pageable;
import com.example.sem7.java.paging.PagingRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class UserDBPagingRepository extends UserDBRepository  implements PagingRepository<Long, Utilizator>
{


    public UserDBPagingRepository(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public Page<Utilizator> findAll(Pageable pageable) {
      /*  Stream<Utilizator> result = StreamSupport.stream(this.findAll().spliterator(), false)
                .skip(pageable.getPageNumber()  * pageable.getPageSize())
                .limit(pageable.getPageSize());
        return new PageImplementation<>(pageable, result);*/

        HashSet<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users limit ? offset ?");

        ) {
            statement.setInt(1,pageable.getPageSize());
            statement.setInt(2,pageable.getPageNumber()*pageable.getPageNumber()-1);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstname = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
                Utilizator utilizator = new Utilizator(firstname, lastname);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return  new PageImplementation<Utilizator>(pageable,users.stream());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
