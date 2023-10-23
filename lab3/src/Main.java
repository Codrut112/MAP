import domain.Utilizator;
import interfata.Interface;
import repository.InMemoryRepository;
import service.Service;
import validators.UtilizatorValidator;
import validators.ValidarePrietenie;
import validators.ValidationException;

public class Main {

    public static void main(String[] args) {

        Utilizator u1=new Utilizator("u1FirstName", "u1LastName");
        u1.setId(1l);
        Utilizator u2=new Utilizator("u2FirstName", "u2LastName"); u2.setId(1l);
        Utilizator u3=new Utilizator("u3FirstName", "u3LastName"); u3.setId(2l);
        Utilizator u4=new Utilizator("u4FirstName", "u4LastName"); u4.setId(3l);
        Utilizator u5=new Utilizator("u5FirstName", "u5LastName"); u5.setId(4l);
        Utilizator u6=new Utilizator("u6FirstName", "u6LastName"); u6.setId(5l);
        Utilizator u7=new Utilizator("u7FirstName", "u7LastName"); u7.setId(6l);

        Service service=new Service(new InMemoryRepository<>(new UtilizatorValidator()),new InMemoryRepository<>(new ValidarePrietenie()));
        try {
            String[][] userNames = {
                    {"John", "Doe"},
                    {"Jane", "Smith"},
                    {"Robert", "Johnson"},
                    {"Emily", "Wilson"},
                    {"Michael", "Brown"},
                    {"Olivia", "Davis"},
                    {"William", "Martinez"},
                    {"Sophia", "Anderson"},
                    {"David", "Garcia"},
                    {"Emma", "Lee"},
                    {"Daniel", "Miller"},
                    {"Ava", "Taylor"},
                    {"James", "Harris"},
                    {"Mia", "Clark"},
                    {"Joseph", "White"}
            };

            // Add the new users to the service
            for (int i = 0; i < userNames.length; i++) {
                String firstName = userNames[i][0];
                String lastName = userNames[i][1];
                int userId = i + 1; // User IDs start from 1

                service.addUtilizator(userId, firstName, lastName);
            }
        }
        catch (Error e){
            System.out.println(e.getMessage());
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        } int[] userIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        for (int i = 1; i <= 10; i++) {
            int userId1 = i;
            int userId2 = (i % 5) + 1; // Create friendships in a circular manner

            if (userId1 != userId2) {
             //   System.out.println(userId1 +"  "+userId2);
                service.addFriendship(userId1, userId2);
            }}
        Interface consola=new Interface(service);

        consola.run();

}}
