package service;

import domain.Prietenie;
import domain.Tuple;
import domain.Utilizator;
import repository.InMemoryRepository;
import validators.Error;
import validators.ValidarePrietenie;

import java.time.LocalDateTime;
import java.util.*;

public class Service {
    private InMemoryRepository<Long, Utilizator> repoUtilizatori;
    private InMemoryRepository<Tuple<Long,Long>, Prietenie> repoPrietenii=new InMemoryRepository<>(new ValidarePrietenie());

    /**
     * constructor
     * @param repoUtilizatori
     * @param repoPrietenii
     */
    public Service(InMemoryRepository<Long, Utilizator> repoUtilizatori, InMemoryRepository<Tuple<Long, Long>, Prietenie> repoPrietenii) {
        this.repoUtilizatori = repoUtilizatori;
        this.repoPrietenii = repoPrietenii;
    }

    /**
     * addd an user
     * @param idUtilizator long
     * @param firstName String
     * @param lastName String
     * @throws Error if id invalid
     */
    public void addUtilizator(long idUtilizator,String firstName,String lastName) throws Error{
       Utilizator utilizator=new Utilizator(firstName,lastName);
       utilizator.setId(idUtilizator);

           Utilizator ok = repoUtilizatori.save(utilizator);
           if (ok != null) throw new Error("id invalid");

    }

    /**
     * delete  user and its Friendships
     * @param idUtilizator long
     * @throws Error if repo does not have the user
     */
    public void deleteUtilizator(long idUtilizator) throws Error {

        Utilizator utilizator=repoUtilizatori.delete(idUtilizator);
        if(utilizator==null)throw new Error("utilizator inexistent");
        Iterable<Prietenie> prietenii=repoPrietenii.findAll();
        List<Prietenie> toDelete=new ArrayList<>();
        for(Prietenie prietenie:prietenii){
          //  System.out.println(prietenie);
            if(prietenie.memberOf(idUtilizator))toDelete.add(prietenie);
        //    System.out.println(prietenie);
        }
        for(Prietenie prietenie:toDelete)repoPrietenii.delete(prietenie.getId());
    }

    /**
     * create a friendship betweeen user1 and user2
     * @param idUtilizator1 long
     * @param idUtilizator2 long
     * @throws Error -> priteenie existenta
     *               -> utilizator inexistent
     */
    public void addFriendship(long idUtilizator1, long idUtilizator2)throws Error{

        if(repoUtilizatori.findOne(idUtilizator1)!=null && repoUtilizatori.findOne(idUtilizator2)!=null){
        Prietenie prietenie=new Prietenie(LocalDateTime.now(),idUtilizator1,idUtilizator2);
        if(repoPrietenii.findOne(new Tuple(idUtilizator1,idUtilizator2))==null)
        {prietenie.setId(new Tuple(idUtilizator1,idUtilizator2));}
        else throw new Error("prietenie existenta");
        repoPrietenii.save(prietenie);}
        else throw new Error("utilizator inexistent");}

    /**
     * delete an user
     * @param idUtilizator1 long
     * @param idUtilizator2 long
     * @throws Error ->(prietenie inexistent)
     */
    public void deleteFriendship(long idUtilizator1, long idUtilizator2) throws Error{

       if(repoPrietenii.delete(new Tuple(idUtilizator1,idUtilizator2))==null && repoPrietenii.delete(new Tuple(idUtilizator2,idUtilizator1))==null)
           throw new Error("prietenie inexistenta");

    }

    /**
     * return all the users
     * @return Iterable<Utilizator>
     */
    public Iterable<Utilizator> getAllUtilizatori(){return repoUtilizatori.findAll();}

    /**
     * return the community which the user take part
     * @param idUtilizator ;ong
     * @return List<Utilizator>
     */
    private List<Utilizator> groupOfFriends(long idUtilizator) {

        Iterable<Prietenie> prieteni = repoPrietenii.findAll();
        List<Utilizator> prieteniUtilizator=new ArrayList<>();
        Utilizator utilizator=repoUtilizatori.findOne(idUtilizator);
        prieteniUtilizator.add(utilizator);

        for(Prietenie prietenie:prieteni){
            if(prietenie.prietenComun(idUtilizator)!=-1 )prieteniUtilizator.add(repoUtilizatori.findOne(prietenie.prietenComun(idUtilizator)));
        }
        return prieteniUtilizator;

    }

    /**
     * DFS on the list of users based on friendships
     * @param utilizator
     * @param set
     * @return
     */
    private List<Utilizator> DFS(Utilizator utilizator, Set<Utilizator> set){
            List<Utilizator>lista=new ArrayList<>();
            set.add(utilizator);
            lista.add(utilizator);

            for(Utilizator u:groupOfFriends(utilizator.getId())){

                if(!set.contains(u)){
                    List<Utilizator> l=DFS(u,set);
                    lista.addAll(l);
                }

            }
            return lista;

    }

    /**
     *
     * @return the number of communities (number of connectected components)
     */
    public int numberOfCommunities(){
     Iterable<Utilizator>utilizatori=repoUtilizatori.findAll();
     Set<Utilizator>set=new HashSet<>();
     int contor =0;
     for(Utilizator utilizator:utilizatori){

         if(!set.contains(utilizator)){
             set.add(utilizator);
             contor++;
             DFS(utilizator,set);
         }

     }
     return contor;
    }

    /**
     * return a list with all the Communities and their users
     * @return List<List<Utilizator>>
     */
    public List<List<Utilizator>> getAllCommunities(){
        List<List<Utilizator>> communities=new ArrayList<>();
        Iterable<Utilizator> utilizatori=repoUtilizatori.findAll();
        HashSet<Utilizator>set=new HashSet<>();
        for(Utilizator utilizator:utilizatori){
            if(!set.contains(utilizator)){
                communities.add(DFS(utilizator,set));
            }
        }
     return communities;
    }

    /**
     *
     * @return theBiggestCommunity
     */
    public List<Utilizator> theBiggestCommunity(){
        List<List<Utilizator>> communities=getAllCommunities();
        List<Utilizator> theBiggestCommunity=new ArrayList<>();
        int size=0;
        for(List<Utilizator> comunity:communities){
            int lungime=longestPath(comunity);
            if(lungime>size){
                size=lungime;
                theBiggestCommunity=comunity;
            }
        }
        System.out.println(size);
        return theBiggestCommunity;
    }

    private int longestPath(List<Utilizator> comunity) {
        int max=0;
        for(Utilizator utilizator:comunity){
            int lungime=longestPathFromSource(utilizator);
            if(lungime>max)max=lungime;
        }
        return max;
    }

    private int longestPathFromSource(Utilizator utilizator) {
        Set<Utilizator>set=new HashSet<>();
        return lee(utilizator,set);
    }

    private int lee(Utilizator source, Set<Utilizator> set) {

        int max = -1;
        for(Utilizator f: groupOfFriends(source.getId()))
            if(!set.contains(f) && !f.equals(source))
            {
                set.add(f);
                int l = lee(f, set);
                if(l > max)
                    max = l;
                set.remove(f);
            }

        return max + 1;
    }

    /**
     * return all Friendships
     * @return Iterable<Prietenie>
     */
    public Iterable<Prietenie> getAllFriendships(){
        return repoPrietenii.findAll();
    }
}
