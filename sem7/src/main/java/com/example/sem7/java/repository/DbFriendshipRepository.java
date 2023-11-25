package com.example.sem7.java.repository;

import com.example.sem7.java.domain.Prietenie;
import com.example.sem7.java.domain.Tuple;
import com.example.sem7.java.domain.Utilizator;
import com.example.sem7.java.validators.Error;
import com.example.sem7.java.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class DbFriendshipRepository implements FriendshipsRepository<Tuple<Long,Long>, Prietenie> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator validator;

    public DbFriendshipRepository(String url, String username, String password, Validator validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Prietenie> findOne(Tuple<Long, Long> id) {

        long id1=id.getLeft();
        long id2=id.getRight();
        try(Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement= connection.prepareStatement("select * from friendships" + " where id1=? and id2=?")
        ) {
            statement.setInt(1,Math.toIntExact(id1));
            statement.setInt(2,Math.toIntExact(id2));
            ResultSet resultSet=statement.executeQuery();
         if(resultSet.next()) {
             id1 = resultSet.getLong("id1");
             id2 = resultSet.getLong("id2");
             Date sqlDate = resultSet.getDate("start_date");
             Timestamp timestamp = new Timestamp(sqlDate.getTime());
             LocalDateTime localDateTime = timestamp.toLocalDateTime();
             Prietenie friendship = new Prietenie(localDateTime, id1, id2);
           return Optional.of(friendship);
         }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Prietenie> findAll() {
        HashSet<Prietenie>friendships=new HashSet<>();
        try(Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement= connection.prepareStatement("select * from friendships");
            ResultSet resultSet=statement.executeQuery();

        ) {
            while(resultSet.next()){
                long id1=resultSet.getLong("id1");
                long id2=resultSet.getLong("id2");
                Date sqlDate = resultSet.getDate("start_date");
                Timestamp timestamp = new Timestamp(sqlDate.getTime());
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                Prietenie friendship=new Prietenie(localDateTime,id1,id2);
                friendships.add(friendship);
            }
            return friendships;
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Prietenie> save(Prietenie friendship) {

       validator.validate(friendship);
       try(Connection connection=DriverManager.getConnection(url,username,password);
       PreparedStatement statement= connection.prepareStatement("insert into friendships Values(?,?,?)");
       ) {
           System.out.println(friendship);
          statement.setInt(1,Math.toIntExact( friendship.getId().getLeft()));
          statement.setInt(2,Math.toIntExact( friendship.getId().getRight()));

           System.out.println(friendship);
           LocalDateTime localDateTime = friendship.getDate(); // Replace with your LocalDateTime variable
         //  System.out.println("haide");
           Timestamp sqlTimestamp = Timestamp.valueOf(localDateTime);
          // System.out.println("aproapre");
           statement.setTimestamp(3, sqlTimestamp);
          // System.out.println("a reusit");
          statement.execute();
          return Optional.of(friendship);
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public Optional<Prietenie> delete(Tuple<Long, Long> id) {
        try(Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("delete from Friendships\n" +
                    "where (id1=? and id2=?) ");

        ) {
            statement.setInt(1,Math.toIntExact(id.getLeft()));
            statement.setInt(2,Math.toIntExact(id.getRight()));
            statement.execute();
            System.out.println("adsd");

            return Optional.of(new Prietenie(LocalDateTime.now(),1,1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Prietenie> findAllFriends(long idUtilizator){
        List<Prietenie>friendships=new ArrayList<>();
    try(Connection connection=DriverManager.getConnection(url,username,password);
    PreparedStatement statement= connection.prepareStatement("select friends.id1,friends.id2,friends.start_date from users u\n" +
            "inner join friendships friends\n" +
            "on u.id=friends.id1 or u.id=friends.id2\n" +
            "where u.id=?");

    ) {
        statement.setInt(1,Math.toIntExact(idUtilizator));
        ResultSet resultSet= statement.executeQuery();
        while(resultSet.next()){
            long id1=resultSet.getLong("id1");
            long id2=resultSet.getLong("id2");
            Date date=resultSet.getDate("start_date");
            Timestamp timestamp = new Timestamp(date.getTime());
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            Prietenie friendship = new Prietenie(localDateTime, id1, id2);
            friendships.add(friendship);
        }
        return friendships;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }
    @Override
    public Optional<Prietenie> update(Prietenie entity) {
        return Optional.empty();
    }
}
