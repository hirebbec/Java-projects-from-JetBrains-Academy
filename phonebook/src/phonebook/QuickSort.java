package phonebook;

import java.util.List;

public class QuickSort implements Sortable {

    @Override
    public boolean sort(List<Contact> people, long maxTime) {
        doQuickSort(people, 0, people.size() - 1);
        return true;
    }

    private void doQuickSort(List<Contact> people, int start, int end) {
        if (start < end) {
            int pivotPoint = partition(people, start, end);
            doQuickSort(people, start, pivotPoint - 1);
            doQuickSort(people, pivotPoint + 1, end);
        }
    }

    private int partition(List<Contact> people, int start, int end) {
        String pivot = people.get(end).getName();
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (people.get(j).getName().compareTo(pivot) <= 0) {
                i++;
                Contact temp = people.get(i);
                people.set(i, people.get(j));
                people.set(j, temp);
            }
        }
        Contact temp = people.get(i + 1);
        people.set(i + 1, people.get(end));
        people.set(end, temp);
        return i + 1;
    }
}