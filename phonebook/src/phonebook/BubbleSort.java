package phonebook;

import java.util.List;

public class BubbleSort implements Sortable {

    @Override
    public boolean sort(List<Contact> people, long maxTime) {
        int length = people.size();
        maxTime += System.currentTimeMillis();
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (System.currentTimeMillis() > maxTime) {
                    return false;
                }
                if (people.get(j).getName().compareTo(people.get(j + 1).getName()) > 0) {
                    Contact temp = people.get(j);
                    people.set(j, people.get(j + 1));
                    people.set(j + 1, temp);
                }
            }
        }
        return true;
    }
}