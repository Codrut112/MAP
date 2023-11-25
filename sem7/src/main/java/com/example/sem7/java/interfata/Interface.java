package com.example.sem7.java.interfata;

import com.example.sem7.java.service.Service;
import com.example.sem7.java.domain.Utilizator;
import com.example.sem7.java.validators.Error;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
        System.out.println("10.Modifica Utilizator");
        System.out.println("11.Să se afiseze toate relatiile de prietenie ale unui utilizator, create intr-o anumita luna a anului");
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
                        System.out.println("Id Primul prieten: ");
                        long id1 = Long.parseLong(scanner.nextLine());
                        System.out.println("Id Al doilea prieten: ");
                        long id2 = Long.parseLong(scanner.nextLine());
                        System.out.println("doresti data: ");
                        String raspuns=scanner.nextLine();
                        if(raspuns.contentEquals("da")){
                            System.out.println("data: ");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                            String date=scanner.nextLine()+" 00:00:00";
                            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
                            service.addFriendship(id1, id2,localDateTime);}
                        else service.addFriendship(id1,id2,LocalDateTime.now());
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
                    case "10":
                        System.out.println("Id Utilizator: ");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("Nume Utilizator: ");
                        nume = scanner.nextLine();
                        System.out.println("Prenume Utilizator: ");
                        prenume = scanner.nextLine();
                        service.updateUtiliator(id,prenume, nume);
                        break;
                    case "11":
                        System.out.println("id user");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("year:");
                        int year = Integer.parseInt(scanner.nextLine());
                        System.out.println("month:");
                        Month month=Month.valueOf(scanner.nextLine());
                        service.FriendsofUserFromDate(id,year,month).forEach(System.out::println);
                    default:
                        continue;
                }

            } catch (Error e){
                System.out.println(e.getMessage());
            } catch (Exception e) {
               if(!e.getMessage().contains("o results were returned by the query.")) System.out.println(e.getMessage());
            }


        }


    }
}
