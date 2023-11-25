package com.example.sem7.java.service;

import com.example.sem7.java.domain.Prietenie;
import com.example.sem7.java.domain.Tuple;
import com.example.sem7.java.domain.Utilizator;
import com.example.sem7.java.repository.FriendshipsRepository;
import com.example.sem7.java.repository.Repository;
import com.example.sem7.java.validators.Error;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {
    private Repository<Long, Utilizator> repoUtilizatori;
    private FriendshipsRepository<Tuple<Long, Long>, Prietenie> repoPrietenii ;

    /**
     * constructor
     *
     * @param repoUtilizatori
     * @param repoPrietenii
     */
    public Service(Repository<Long, Utilizator> repoUtilizatori, FriendshipsRepository<Tuple<Long, Long>, Prietenie> repoPrietenii) {
        this.repoUtilizatori = repoUtilizatori;
        this.repoPrietenii = repoPrietenii;
    }

    /**
     * addd an user
     *
     * @param firstName String
     * @param lastName  String
     * @throws Error if id invalid
     */
    public void addUtilizator(String firstName, String lastName) throws Error {
        Utilizator utilizator = new Utilizator(firstName, lastName);
        Optional<Utilizator> ok = repoUtilizatori.save(utilizator);
        if (ok.isEmpty()) throw new Error("id invalid");
        //   System.out.println(utilizator.getId());
    }
    public void updateUtiliator(long id,String firstName, String lastName){
        Utilizator utilizator = new Utilizator(firstName, lastName);
        utilizator.setId(id);
        Optional<Utilizator> ok = repoUtilizatori.update(utilizator);
        if (ok.isEmpty()) throw new Error("utilizator inexistent");
    }

    /**
     * delete  user and its Friendships
     *
     * @param idUtilizator long
     * @throws Error if repo does not have the user
     */
    public void deleteUtilizator(long idUtilizator) throws Error {
        Optional<Utilizator> exista = repoUtilizatori.delete(idUtilizator);
        if (exista.isPresent()) {

        //    Iterable<Prietenie> prietenii = repoPrietenii.findAll();
          //  List<Prietenie> toDelete = new ArrayList<>((Collection<? extends Prietenie>) prietenii);
            //toDelete.removeIf(prietenie -> !prietenie.memberOf(idUtilizator));
           // toDelete.forEach(prietenie -> repoPrietenii.delete(prietenie.getId()));
        } else throw new Error("utilizator inexistent");


        //First implementation
          /*  for (Prietenie prietenie : prietenii) {
                //  System.out.println(prietenie);
                if (prietenie.memberOf(idUtilizator)) toDelete.add(prietenie);
                //    System.out.println(prietenie);
            }*/
        /* for (Prietenie prietenie : toDelete) repoPrietenii.delete(prietenie.getId());*/


        //Second refactor
      /*      prietenii.forEach(prietenie->{
                if(prietenie.memberOf(idUtilizator))toDelete.add(prietenie);
            });
            toDelete.forEach(prietenie -> {
                repoPrietenii.delete(prietenie.getId());
            });*/

        //Third refactor

    }

    /**
     * create a friendship betweeen user1 and user2
     *
     * @param idUtilizator1 long
     * @param idUtilizator2 long
     * @throws Error -> pritenie existenta
     *               -> utilizator inexistent
     */
    public void addFriendship(long idUtilizator1, long idUtilizator2,LocalDateTime date) throws Error {
         if(idUtilizator2<idUtilizator1) {
             long aux=idUtilizator1;
             idUtilizator1=idUtilizator2;
             idUtilizator2=aux;
         }

        if (repoUtilizatori.findOne(idUtilizator1).isPresent() && repoUtilizatori.findOne(idUtilizator2).isPresent()) {
            Prietenie prietenie = new Prietenie(LocalDateTime.now(), idUtilizator1, idUtilizator2);
            System.out.println("asfasf");
            if (repoPrietenii.findOne(new Tuple(idUtilizator1, idUtilizator2)).isEmpty() && repoPrietenii.findOne(new Tuple(idUtilizator2, idUtilizator1)).isEmpty()) {
                {prietenie.setId(new Tuple(idUtilizator1, idUtilizator2));
                    prietenie.setDate(date);
                }
            } else throw new Error("prietenie existenta");

            repoPrietenii.save(prietenie);
        } else throw new Error("utilizator inexistent");
    }

    /**
     * delete an user
     *
     * @param idUtilizator1 long
     * @param idUtilizator2 long
     * @throws Error ->(prietenie inexistent)
     */
    public void deleteFriendship(long idUtilizator1, long idUtilizator2) throws Error {
        if(idUtilizator2<idUtilizator1){
            long aux=idUtilizator1;
            idUtilizator1=idUtilizator2;
            idUtilizator2=aux;
        }
        if (repoPrietenii.delete(new Tuple(idUtilizator1, idUtilizator2)).isEmpty() )
        { System.out.println("eroare");
            throw new Error("prietenie inexistenta");
        }
        System.out.println("a facut");
    }

    /**
     * return all the users
     *
     * @return toti utilizatorii
     */
    public Iterable<Utilizator> getAllUtilizatori() {
        return repoUtilizatori.findAll();
    }

    /**
     * return the friends of the user
     *
     * @param idUtilizator Id-ul Utilizatorului
     * @return List<Utilizator>
     */
    private List<Utilizator> UserfFriends(long idUtilizator) {
        if (repoUtilizatori.findOne(idUtilizator).isEmpty()) throw new Error("utilizator inexistent");
        Iterable<Prietenie> prieteniAuxiliar = repoPrietenii.findAllFriends(idUtilizator);
        List<Prietenie> prieteni = new ArrayList<>();
        prieteniAuxiliar.forEach(prieteni::add);
        //System.out.println(prieteni.size());
        Utilizator utilizator = repoUtilizatori.findOne(idUtilizator).get();
        List<Utilizator> UserFriends = prieteni.stream()
                .filter(prietenie -> prietenie.prietenComun(idUtilizator) != -1)
                .map(prietenie -> repoUtilizatori.findOne(prietenie.prietenComun(idUtilizator)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        UserFriends.add(utilizator);
        return UserFriends;

       /* List<Utilizator> prieteniUtilizator = new ArrayList<>();
        for (Prietenie prietenie : prieteni) {
            if (prietenie.prietenComun(idUtilizator) != -1)
                prieteniUtilizator.add(repoUtilizatori.findOne(prietenie.prietenComun(idUtilizator)).get());
        }
        return prieteniUtilizator;*/

    }
    public Stream<String> FriendsofUserFromDate(long idUtilizator, int year, Month month){

        return repoPrietenii.findAllFriends( idUtilizator).stream().filter(prietenie->prietenie.getMonth()==month && prietenie.getYear()==year)
                .map(prietenie->{
            long id=prietenie.getId().getLeft();
            if(id==idUtilizator)id=prietenie.getId().getRight();
            Utilizator user=repoUtilizatori.findOne(id).get();
            return String.valueOf(user.getFirstName())+"|"+String.valueOf(user.getLastName())+"|"+String.valueOf(prietenie.getDate());
        });
    }
    /**
     * DFS on the list of users based on friendships
     *
     * @param utilizator
     * @param set
     * @return
     */
    private List<Utilizator> DFS(Utilizator utilizator, Set<Utilizator> set) {
        List<Utilizator> lista = new ArrayList<>();
        set.add(utilizator);
        lista.add(utilizator);

        List<Utilizator> userFriends = UserfFriends(utilizator.getId());
        userFriends.forEach(friend -> {
            if (!set.contains(friend)) {
                List<Utilizator> l = DFS(friend, set);
                lista.addAll(l);
            }
        });
        return lista;
     /*   for (Utilizator u : UserfFriends(utilizator.getId())) {

            if (!set.contains(u)) {
                List<Utilizator> l = DFS(u, set);
                lista.addAll(l);
            }

        }*/


    }

    /**
     * @return the number of communities (number of connectected components)
     */
    public int numberOfCommunities() {
        Iterable<Utilizator> utilizatori = repoUtilizatori.findAll();
        Set<Utilizator> set = new HashSet<>();
        AtomicInteger contor = new AtomicInteger(0);

        utilizatori.forEach(utilizator -> {
            if (!set.contains(utilizator)) {
                set.add(utilizator);
                contor.getAndIncrement();
                DFS(utilizator, set);
            }
        });
        return contor.get();
  /*      for (Utilizator utilizator : utilizatori) {

            if (!set.contains(utilizator)) {
                set.add(utilizator);
                contor++;
                DFS(utilizator, set);
            }

        }*/

    }

    /**
     * return a list with all the Communities and their users
     *
     * @return toti utilizatorii
     */
    public List<List<Utilizator>> getAllCommunities() {
        List<List<Utilizator>> communities = new ArrayList<>();
        Iterable<Utilizator> utilizatori = repoUtilizatori.findAll();
        HashSet<Utilizator> set = new HashSet<>();
        utilizatori.forEach(utilizator -> {

            if (!set.contains(utilizator)) {
                communities.add(DFS(utilizator, set));
            }
        });
        return communities;
       /* for (Utilizator utilizator : utilizatori) {
            if (!set.contains(utilizator)) {
                communities.add(DFS(utilizator, set));
            }
        }
        return communities;*/
    }

    /**
     * @return theBiggestCommunity
     */
    public List<Utilizator> theBiggestCommunity() {
        List<List<Utilizator>> communities = getAllCommunities();
        System.out.println("a gasit toate communitatile");
        int size = 0;
        return communities.stream()
                .max((community1, community2) -> {
                    System.out.println("compara doua comunitati");
                    int lungime1 = longestPath(community1);
                    System.out.println("a calculat lungimea primei comunitati");
                    int lungime2 = longestPath(community2);
                    return Integer.compare(lungime1, lungime2);
                })
                .orElse(null);


      /*  for (List<Utilizator> comunity : communities) {
            int lungime = longestPath(comunity);
            if (lungime > size) {
                size = lungime;
                theBiggestCommunity = comunity;
            }
        }
        //System.out.println(size);
        return theBiggestCommunity;*/
    }

    /**
     * return the longest path in a community of friends
     *
     * @param comunity List<Utilizator>
     * @return int
     */
    private int longestPath(List<Utilizator> comunity) {

        return comunity.stream().max((comuniatea1, comunitatea2) -> Integer.compare(longestPathFromSource(comuniatea1), longestPathFromSource(comunitatea2)))
                .map(this::longestPathFromSource).get();
    }

    /**
     * return the longest path from one user
     *
     * @param utilizator Utlizator
     * @return int
     */
    private int longestPathFromSource(Utilizator utilizator) {

        Set<Utilizator> set = new HashSet<>();
        Map<Long,List<Utilizator>> friendgroups=new HashMap<>();
        return lee(utilizator, set,friendgroups);
    }

    /**
     * @param source Utilizator
     * @param set    Set<Utilizator>
     * @return int
     */
    private int lee(Utilizator source, Set<Utilizator> set,Map<Long,List<Utilizator>> friendgroups) {
        
        if(!friendgroups.containsKey(source.getId()))
        {List<Utilizator> userFriends = UserfFriends(source.getId());
         friendgroups.put(source.getId(),userFriends);
        }
        List<Utilizator> userFriends=friendgroups.get(source.getId());
        AtomicInteger max = new AtomicInteger();

        userFriends.forEach(user -> {
            if (!set.contains(user) && !user.equals(source)) {
                set.add(user);
                int l = lee(user, set,friendgroups);

                if (l > max.get())
                    max.set(l);
                set.remove(user);

            }
        });
        return max.get() + 1;
       /* int max = -1;
        for (Utilizator friend : UserfFriends(source.getId()))
            if (!set.contains(friend) && !friend.equals(source)) {
                set.add(friend);
                int l = lee(friend, set);
                if (l > max)
                    max = l;
                set.remove(friend);
            }

        return max + 1;*/
    }

    /**
     * return all Friendships
     *
     * @return prietenii
     */
    public Iterable<Prietenie> getAllFriendships() {
        return repoPrietenii.findAll();
    }
}
