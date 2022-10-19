package phonebook;

import java.util.List;

public class LinearSearch implements Searchable {

    @Override
    public int search(List<Contact> arr, String name) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}