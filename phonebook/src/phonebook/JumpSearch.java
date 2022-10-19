package phonebook;

import java.util.List;

public class JumpSearch implements Searchable {

    @Override
    public int search(List<Contact> arr, String name) {
        int curr = 0;
        int prev = 0;
        int last = arr.size() - 1;
        int step = (int) Math.sqrt(arr.size());

        while (arr.get(curr).getName().compareTo(name) < 0) {
            if (curr == last) {
                return -1;
            }
            prev = curr;
            curr = Math.min(curr + step, last);
        }

        while (arr.get(curr).getName().compareTo(name) > 0) {
            curr = curr - 1;
            if (curr <= prev) {
                return -1;
            }
        }

        if (arr.get(curr).getName().equals(name)) {
            return curr;
        } else {
            return -1;
        }
    }
}