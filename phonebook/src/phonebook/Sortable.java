package phonebook;

import java.util.List;

public interface Sortable {
    boolean sort(List<Contact> people, long maxTime);
}