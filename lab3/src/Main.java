import domain.Prietenie;
import domain.Utilizator;
import interfata.Interface;
import repository.InMemoryRepository;
import service.Service;
import validators.UtilizatorValidator;
import validators.ValidarePrietenie;
import validators.ValidationException;
import validators.Validator;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {


        Validator<Utilizator> validatorUtilizator = (Utilizator entity) -> {
            String erori = "";
            if (entity.getId() == null) erori += "id vid ";
            if (entity.getId() < 0) erori += "id negativ";
            if (entity.getLastName().isEmpty()) erori += "missing last name ";
            if (entity.getFirstName().isEmpty()) erori += "missing first name ";
            if (!erori.isEmpty()) throw new ValidationException(erori);
        };
        Validator<Prietenie> validatorPrietenie = (Prietenie prietenie) -> {
            long IdPrieten1 = prietenie.getId().getLeft();
            long IdPrieten2 = prietenie.getId().getRight();
            if (IdPrieten1 == IdPrieten2)
                throw new ValidationException("a friendship cannot be between the same person");
            if (prietenie.getDate().isAfter(LocalDateTime.now()))
                throw new ValidationException("invalide friendship date");
        };

        Service service = new Service(new InMemoryRepository<>( validatorUtilizator), new InMemoryRepository<>( validatorPrietenie));
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
            for (String[] userName : userNames) {
                String firstName = userName[0];
                String lastName = userName[1];


                service.addUtilizator(firstName, lastName);
            }
        } catch (Error e) {
            System.out.println(e.getMessage());
        }
        int[] userIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

        for (int i = 1; i <= 10; i++) {
            int userId1 = i;
            int userId2 = (i % 5) + 1; // Create friendships in a circular manner

            if (userId1 != userId2) {
                //  System.out.println(userId1 +"  "+userId2);
                service.addFriendship(userId1, userId2);
            }
        }
        Interface consola = new Interface(service);

        consola.run();

    }
}
