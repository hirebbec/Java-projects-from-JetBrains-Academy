package phonebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HashTable {
    long min;
    long sec;
    long ms;
    long searchMin;
    long searchSec;
    long searchMs;
    HashSet<String> table = new HashSet<>();
    int count = 0;

    HashTable(List<Contact> list, List<String> peopleToFind) {
        long start = System.currentTimeMillis();
        for (Contact contact: list) {
            table.add(contact.getName());
        }
        long timewasted = System.currentTimeMillis() - start;
        min = timewasted / 60000;
        sec = (timewasted - min * 60000) / 1000;
        ms = (timewasted - min * 60000 - sec * 1000);
        start = System.currentTimeMillis();
        for (String contact: peopleToFind) {
            if (table.contains(contact)) {
                count++;
            }
        }
        timewasted = System.currentTimeMillis() - start;
        searchMin = timewasted / 60000;
        searchSec = (timewasted - searchMin * 60000) / 1000;
        searchMs = (timewasted - searchMin * 60000 - searchSec * 1000);
    }
}
