package phonebook;

import java.util.List;

public class BinarySearch implements Searchable {

    @Override
    public int search(List<Contact> arr, String name) {
        int left = 0;
        int right = arr.size() - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (arr.get(middle).getName().equals(name)) {
                return middle;
            }
            if (arr.get(middle).getName().compareTo(name) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }
}