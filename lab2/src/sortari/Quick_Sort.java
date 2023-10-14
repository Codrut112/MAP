package sortari;

import container.AbstractSorter;

import java.util.Arrays;
public class Quick_Sort extends AbstractSorter {
    @Override
    public void sort(int[] list) {
        Arrays.sort(list);
    }
}
