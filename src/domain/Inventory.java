import java.time.LocalDateTime;
import java.util.LinkedList;



public class Inventory {
    private LinkedList<Medication> medications;
    private LinkedList<Supplier> suppliers;
    private LinkedList<InventoryMovement> movements;

    public Inventory() {
        medications = new LinkedList<>();
        suppliers = new LinkedList<>();
        movements = new LinkedList<>();
    }

    // crud medication
    public void registerMedication(int id, String name, double price, int stock, int minStock, String supplierName) {
        Supplier supplier = findSupplierByName(supplierName);
        if (supplier != null) {
            Medication medication = findMedicationById(id);
            if (medication == null) {
                medications.add(new Medication(id, name, price, stock, minStock, supplier));
            } else {
                throw new IllegalArgumentException("this medication already exists");
            }
        } else {
            throw new IllegalArgumentException("add a Supplier first");
        }
    }

    public void updateMedication(int newId, String newName, double newPrice, String newSupplierName) {
        Supplier supplier = findSupplierByName(newSupplierName);
        if (supplier != null) {
            Medication medication = findMedicationById(newId);
            if (medication != null) {
                medication.setId(newId);
                medication.setName(newName);
                medication.setPrice(newPrice);
                medication.setSupplier(supplier);
            } else {
                throw new IllegalArgumentException("this medication doesn't exists");
            }
        } else {
            throw new IllegalArgumentException("this Supplier doens't exists");
        }
    }

    public Medication findMedicationById(int medicationId) {
        for (Medication m : medications) {
            if (m.getId() == medicationId) {
                return m;
            }
        }
        return null;
    }

    // crud suppliers
    public void registerSupplier(int id, String name, String phone) {
        Supplier s = findSupplierByName(name);
        if (s == null) {
           suppliers.add(new Supplier(id, name, phone));
        }else{
            throw new IllegalArgumentException("this Supplier already exists");
        }
    }

    public void updateSupplier(int newId, String newName, String newPhone, String actualName){
        Supplier s = findSupplierByName(actualName);
        if(s!=null){
            s.setName(newName);
            s.setId(newId);
            s.setPhone(newPhone);
        }else{
            throw new IllegalArgumentException("this Supplier doesn't exists");
        }
    }

    public Supplier findSupplierByName(String supplierName) {
        for (Supplier s : suppliers) {
            if (s.getName().equalsIgnoreCase(supplierName)) {
                return s;
            }
        }
        return null;
    }

    public void registerEntry(int medicationId, int quantity, String reason){ // search that medication exists, then increase the stock, finally 
        Medication medication = findMedicationById(medicationId);            //registers the movement registers that it was an entry, the medication
        if(medication==null){                                                //the quantity that was added, the date and the reason
            throw new IllegalArgumentException("this medication does't exists");
        }
        medication.increaseStock(quantity);

        InventoryMovement movement = new InventoryMovement(InventoryMovement.MovementType.ENTRY,medication,quantity,LocalDateTime.now(), reason);
        movements.add(movement);
           
    }

}
