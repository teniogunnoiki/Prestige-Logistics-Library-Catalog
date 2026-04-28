    import java.util.ArrayList;
    import java.util.Comparator;

    public class SortUtils {

        public static <T> ArrayList<T> mergeSort(ArrayList<T> tArrayList, Comparator<T> comp){

            if(tArrayList.size()<=1 ){
                return tArrayList;
            }

            int mid = tArrayList.size()/2;
            ArrayList<T> left = new ArrayList<>(tArrayList.subList(0,mid));
            ArrayList<T> right = new ArrayList<>(tArrayList.subList(mid,tArrayList.size()));
            left = mergeSort(left, comp);
            right = mergeSort(right, comp);
            return merge(left,right,comp);

        }
        private static <T> ArrayList<T> merge(ArrayList<T> left,ArrayList<T> right, Comparator<T> comp ){
            ArrayList<T> result = new ArrayList<>();
            int i =0;
            int j = 0;

            while(i<left.size() && j< right.size()){
                if (comp.compare(left.get(i), right.get(j)) <=0) {
                    result.add(left.get(i));
                    i++;
                }
                else{
                    result.add(right.get(j));
                    j++;
                }
            }
            while(i<left.size()){
                result.add(left.get(i));
                i++;
            }
            while (j<right.size()){
                result.add(right.get(j));
                j++;
            }
            return result;
        }
        public static <T> void quickSort(ArrayList<T> list, int low, int high, Comparator<T> comp) {
            if (low < high) {
                int pivotIndex = partition(list, low, high, comp);

                quickSort(list, low, pivotIndex - 1, comp);
                quickSort(list, pivotIndex + 1, high, comp);
            }
        }
        private static <T> int partition(ArrayList<T> list, int low, int high, Comparator<T> comp) {
            T pivot = list.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (comp.compare(list.get(j), pivot) <= 0) {
                    i++;

                    T temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }

            T temp = list.get(i + 1);
            list.set(i + 1, list.get(high));
            list.set(high, temp);

            return i + 1;
        }
        public static int binarySearch(
                ArrayList<LibraryItem> list,
                int key
        ) {
            int left = 0;
            int right = list.size() - 1;

            while (left <= right) {

                int mid = left + (right - left) / 2;

                int midID = list.get(mid).getItemID();

                if (midID == key) {
                    return mid;
                }

                if (midID < key) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return -1;
        }
        public static Comparator<LibraryItem> compareByTitle = (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle());
        public static Comparator<LibraryItem> compareByID = (a, b) -> Integer.compare(a.getItemID(), b.getItemID());

        public static Comparator<Shipment> compareByDate = (a, b) -> a.getShipmentDate().compareTo(b.getShipmentDate());
        public static Comparator<Shipment> compareBySID = (a, b) -> Integer.compare(a.getShipmentID(), b.getShipmentID());


    }
