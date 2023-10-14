package taskuri;

import container.AbstractSorter;
import sortari.Bubble_sort;
import sortari.Quick_Sort;
import sortari.Sortari;

public class SortingTask extends Task {
    private int[] list;

    private AbstractSorter sorter;
    public SortingTask(String taskId, String descriere,int[] list, Sortari sortare) {
        super(taskId, descriere);
        this.list=list;
        if(sortare== Sortari.QUICK_SORT)
        {this.sorter =new Quick_Sort();}
        if(sortare==Sortari.BUBBLE_SORT)
        {this.sorter=new Bubble_sort();}
    }

    @Override
    public void execute() {
        sorter.sort(list);
        for(int elm:list) System.out.println(elm);
    }








    /*private void AbstractSorter(Sortari sortare){
        if(sortare==Sortari.BUBBLE_SORT) {
        boolean ok=false;
            while(!ok) {
                ok=true;
            for(int i=0;i<list.length-1;i++)
            {
            if(list[i]<list[i+1]) {

                int aux=list[i];
                list[i]=list[i+1];
                list[i+1]=aux;
                ok=false;
            }

            }
            }

        }
        else{
            Arrays.sort(list);

        }

    }*/
}
