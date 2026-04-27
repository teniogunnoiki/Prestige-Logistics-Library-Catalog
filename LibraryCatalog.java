Skip to content
teniogunnoiki
Prestige-Logistics-Library-Catalog
Repository navigation
Code
Issues
Pull requests
Actions
Projects
Wiki
Security and quality
Insights
Settings
Prestige-Logistics-Library-Catalog
/
LibraryCatalog.java
in
Teni-Branch---avl-and-hashing

Edit

Preview
Indent mode

Spaces
Indent size

4
Line wrap mode

No wrap
Editing LibraryCatalog.java file contents
  1
  2
  3
  4
  5
  6
  7
  8
  9
 10
 11
 12
 13
 14
 15
 16
 17
 18
 19
 20
 21
 22
 23
 24
 25
 26
 27
 28
 29
 30
 31
 32
 33
 34
 35
 36
import java.util.ArrayList;
import java.util.Collections;
public class LibraryCatalog<T extends LibraryItem> {
    private String librarySession;
    private ArrayList<T> items = new ArrayList<>();
    private ArrayList<Shipment> shipments = new ArrayList<>();
    private ArrayList<Warehouse> warehouses = new ArrayList<>();

    public LibraryCatalog(){this.librarySession = "User Mode";}
    public LibraryCatalog(String librarySession) {
        this.librarySession = librarySession;
    }

    public void addItem(T item) {
        items.add(item);
    }

    public int size() {
        return items.size();
    }

    public void displayLibrary() {
        for (T item : items) {
            System.out.println(item.getDetails());
        }
    }

    public ArrayList<T> getAllItems(){
        return items;
    }



    public void findAndRemoveItem(int item) {
        boolean found = items.removeIf(s1 -> s1.getItemID() == item);
        if (found) {
Use Control + Shift + m to toggle the tab key moving focus. Alternatively, use esc then tab to move to the next interactive element on the page.
