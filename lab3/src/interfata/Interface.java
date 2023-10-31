package interfata;

import domain.Utilizator;
import service.Service;
import validators.ValidationException;

import java.util.Scanner;

public class Interface {
    Service service;

    public Interface(Service service) {
        this.service = service;
    }

    private void printOptiuni() {
        System.out.println("Optiuni");
        System.out.println("0.Iesire");
        System.out.println("1.Adauga Utilizator");
        System.out.println("2.Sterge Utilizator");
        System.out.println("3.Adauga prietenie");
        System.out.println("4.Sterge prietenie");
        System.out.println("5.Numarul de comunitati");
        System.out.println("6.Cea mai mare comunitate de prieteni");
        System.out.println("7.Afiseaza Utilizatorii");
        System.out.println("8.Afiseaza Prietenii");
        System.out.println("9.Optiuni");
        System.out.println("alege optiunea:");
    }

    public void run() {
        printOptiuni();
        while (true) {


            Scanner scanner = new Scanner(System.in);
            String optiune = scanner.nextLine();

            try {
                switch (optiune) {
                    case "0":
                        return;
                    case "1":

                        System.out.println("Nume Utilizator: ");
                        String nume = scanner.nextLine();
                        System.out.println("Prenume Utilizator: ");
                        String prenume = scanner.nextLine();
                        service.addUtilizator(prenume, nume);
                        break;
                    case "2":
                        System.out.println("Id Utilizator: ");
                        long id = Long.parseLong(scanner.nextLine());
                        service.deleteUtilizator(id);
                        break;
                    case "3":
                        System.out.println("Id Primul prieteen: ");
                        long id1 = Long.parseLong(scanner.nextLine());
                        System.out.println("Id Al doilea prieten: ");
                        long id2 = Long.parseLong(scanner.nextLine());
                        service.addFriendship(id1, id2);
                        break;
                    case "4":
                        System.out.println("Id Primul prieten: ");
                        id1 = Long.parseLong(scanner.nextLine());
                        System.out.println("Id Al doilea prieten: ");
                        id2 = Long.parseLong(scanner.nextLine());
                        service.deleteFriendship(id1, id2);
                        break;
                    case "5":
                        System.out.println("numarul de comunitati este " + service.numberOfCommunities());
                        break;
                    case "6":
                        Iterable<Utilizator> utilizatori = service.theBiggestCommunity();
                        utilizatori.forEach(System.out::println);
                        break;
                    case "7":
                        service.getAllUtilizatori().forEach(System.out::println);
                        break;
                    case "8":
                        service.getAllFriendships().forEach(System.out::println);
                        break;
                    case "9":
                        printOptiuni();
                        break;
                    default:
                        continue;
                }

            } catch (ValidationException e) {
                System.out.println(e.getMessage());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }


    }
}
