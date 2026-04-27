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



    public LibraryItem findAndRemoveItem(int itemID) {

        for (int i = 0; i < items.size(); i++) {

            if (items.get(i).getItemID() == itemID) {

                LibraryItem removed = items.remove(i);

                System.out.println("Item Removed!");
                return removed;
            }
        }

        System.out.println("Item not found.");
        return null;
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

    }
