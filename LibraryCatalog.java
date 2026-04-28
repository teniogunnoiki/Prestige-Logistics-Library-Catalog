import java.util.ArrayList;
import java.util.Collections;
public class LibraryCatalog<T extends LibraryItem> {
    private String librarySession;

    private ArrayList<T> items = new ArrayList<>();
    private ArrayList<Shipment> shipments = new ArrayList<>();
    private ArrayList<Warehouse> warehouses = new ArrayList<>();
    private LibraryDeliveryGraph deliveryGraph;

    public LibraryCatalog(){
        this.librarySession = "User Mode";
        this.deliveryGraph = buildDeliveryNetwork();
    
    }
    public LibraryCatalog(String librarySession) {
        this.librarySession = librarySession;
        this.deliveryGraph = buildDeliveryNetwork(); 
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
            System.out.println("Item Removed!");
        } else {
            System.out.println("Item not found.");
        }
    }

    public void sortLibrary() {
        Collections.sort(items);
    }

    public void displayShipments() {
        for (Shipment s : shipments) {
            System.out.println(s.toString());
        }
    }

    public void createShipment(Shipment shipment) {
        if (shipment == null) {
            System.out.println("Cannot add null shipment!");
            return;
        }
        shipments.add(shipment);
        System.out.println("Shipment " + shipment.getShipmentID() + " added to catalog.");
    }

    public Shipment findShipment(int shipmentID) {
        for (Shipment s : shipments) {
            if (s.getShipmentID() == shipmentID) {
                return s;
            }
        }
        return null;
    }

    public void displayItemsInShipment(int shipmentID) {
        Shipment shipment = findShipment(shipmentID);
        if (shipment == null) {
            System.out.println("Shipment not found.");
            return;
        }
        shipment.displayItems();
    }

    public void addItemToShipment(int itemID, int shipmentID) {
        Shipment shipment = findShipment(shipmentID);
        if (shipment == null) {
            System.out.println("Shipment not found.");
            return;
        }
        for (int i=0;i<items.size();i++) {
            if (items.get(i).getItemID() == itemID) {
                shipment.addItem(items.get(i));
                items.remove(i);
                System.out.println("Item moved to shipment.");
                return;
            }
        }
        System.out.println("Item not found in library.");
    }

    public Shipment removeShipment(int shipmentID) {
       Shipment shipment = findShipment(shipmentID);
       if(shipment ==null){
           System.out.println("Shipment not found.");
       }
       shipments.remove(shipment);
       System.out.println("Shipment removed.");
        return shipment;
    }

    private LibraryDeliveryGraph buildDeliveryNetwork() {
        LibraryDeliveryGraph g = new LibraryDeliveryGraph();
        g.addVertex("Main Campus Library");
        g.addVertex("Science & Engineering");
        g.addVertex("Law Library");
        g.addVertex("Medical School Library");
        g.addVertex("Graduate Research Center");
        g.addVertex("East Campus Library");
        g.addVertex("Central Warehouse");

        g.addEdge("Main Campus Library",      "Science & Engineering",     8);
        g.addEdge("Main Campus Library",      "Law Library",              12);
        g.addEdge("Main Campus Library",      "Graduate Research Center",  6);
        g.addEdge("Main Campus Library",      "East Campus Library",      20);
        g.addEdge("Science & Engineering",    "Medical School Library",   10);
        g.addEdge("Science & Engineering",    "Graduate Research Center",  5);
        g.addEdge("Law Library",              "Graduate Research Center", 14);
        g.addEdge("Medical School Library",   "East Campus Library",      15);
        g.addEdge("Medical School Library",   "Central Warehouse",        30);
        g.addEdge("East Campus Library",      "Central Warehouse",        25);
        g.addEdge("Graduate Research Center", "East Campus Library",      18);
        return g;
    }

    public LibraryDeliveryGraph getDeliveryGraph() {
        return deliveryGraph;
    }

}