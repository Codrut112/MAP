package com.example.sem7.java.repository;

import com.example.sem7.java.domain.Utilizator;
import com.example.sem7.java.validators.Validator;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;

public class DbRepository implements Repository<Long, Utilizator> {

    private String url, username, password;
    private Validator validator;

    public DbRepository(String url, String username, String password, Validator validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Utilizator> findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users where id=?")
        ) {
            statement.setInt(1, Math.toIntExact(id));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String firstname = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
                Utilizator user=new Utilizator(firstname,lastname);
                user.setId(id);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Utilizator> findAll() {
        HashSet<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from users");
             ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String firstname = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
                Utilizator utilizator = new Utilizator(firstname, lastname);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> save(Utilizator utilizator) {
        validator.validate(utilizator);
        long id=utilizator.getId();
        String firstname=utilizator.getFirstName();
        String lastname=utilizator.getLastName();
       try(Connection connection=DriverManager.getConnection(url,username,password);
       PreparedStatement statement=connection.prepareStatement("insert into users Values (?,?,?)");
       ) {
           statement.setInt(1, Math.toIntExact(id));
           statement.setString(2,firstname);
           statement.setString(3,lastname);
            statement.execute();
            return Optional.of(utilizator);
       } catch (SQLException e) {

           throw new RuntimeException(e);
       }

    }

    @Override
    public Optional<Utilizator> delete(Long l) {
        try(Connection connection=DriverManager.getConnection(url,username,password);
         PreparedStatement statement=connection.prepareStatement("delete from Users\n" +
                 "where id=?");

        ) {
            statement.setInt(1,Math.toIntExact(l));
            statement.execute();
            return Optional.of(new Utilizator("",""));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Utilizator> update(Utilizator utilizator) {
        long id=utilizator.getId();
        String firstname=utilizator.getFirstName();
        String lastname=utilizator.getLastName();
        try(Connection connection=DriverManager.getConnection(url,username,password);
        PreparedStatement statement=connection.prepareStatement("Update users "+
                "set first_name=?,last_name=? "+
                "where id=?;")
        ) {

            statement.setString(1,firstname);
            statement.setString(2,lastname);
            statement.setInt(3,Math.toIntExact(id));
            statement.execute();
            return Optional.of(utilizator);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
