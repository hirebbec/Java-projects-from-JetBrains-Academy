package phonebook;

import java.util.List;

public interface Searchable {

    int search(List<Contact> arr, String name);
}