package sortari;

import container.AbstractSorter;

public class Bubble_sort extends AbstractSorter {



    @Override
    public void sort(int[] list) {
        boolean ok=false;
        while(!ok) {
            ok=true;
            for(int i=0;i<list.length-1;i++)
            {
                if(list[i]>list[i+1]) {

                    int aux=list[i];
                    list[i]=list[i+1];
                    list[i+1]=aux;
                    ok=false;
                }

            }
        }
    }


}
