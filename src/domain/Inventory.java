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

    public void updateMedication(int findId, String newName, double newPrice, String newSupplierName) {
        Supplier supplier = findSupplierByName(newSupplierName);
        if (supplier != null) {
            Medication medication = findMedicationById(findId);
            if (medication != null) {
                String oldName = medication.getName();
                double oldPrice = medication.getPrice();
                Supplier oldSupplier = medication.getSupplier();
                try {
                    medication.setName(newName);
                    medication.setPrice(newPrice);
                    medication.setSupplier(supplier);
                } catch (IllegalArgumentException e) {
                    medication.setName(oldName);
                    medication.setPrice(oldPrice);
                    medication.setSupplier(oldSupplier);
                    throw e;
                }
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
// it asks for a text, if it's null or empty throw Exception, then create a variable that becomes the txt in lowercase and without spaces
//then create a list of medication if some medication contain the coincidence, it's gonna adding to the allcoincidences list and finnaly return it
    public LinkedList<Medication> searchMedicationByName(String txt){
        if(txt==null||txt.trim().isEmpty()){
            throw new IllegalArgumentException("text can't be empty");
        }
        String coincidence= txt.trim().toLowerCase();
        LinkedList<Medication> allConcidences= new LinkedList<>();
        for(Medication m:medications){
            if(m.getName().toLowerCase().contains(coincidence)){
                allConcidences.add(m);
            }
        }
        return allConcidences;
    }
    public LinkedList<Medication> findMedicationBySupplier(String supplierName){
        Supplier s = findSupplierByName(supplierName);
        if(s==null){
            throw new IllegalArgumentException("Supplier doesn't exists");
        }
        LinkedList<Medication> asociateSupplier = new LinkedList<>();
        for(Medication m : medications){
            if(m.getSupplier()== s){
                asociateSupplier.add(m);
            }
        }
        return asociateSupplier;
    }

    // crud suppliers
    public void registerSupplier(int id, String name, String phone) {
        Supplier s = findSupplierByName(name);
        if (s == null) {
            suppliers.add(new Supplier(id, name, phone));
        } else {
            throw new IllegalArgumentException("this Supplier already exists");
        }
    }

    public void updateSupplier(int newId, String newName, String newPhone, String actualName) {
        Supplier s = findSupplierByName(actualName);
        if (s != null) {
            int oldId = s.getId();
            String oldPhone = s.getPhone();
            String oldName= s.getName();
            try{
                s.setName(newName);
                s.setId(newId);
                s.setPhone(newPhone);
            }catch(IllegalArgumentException e){
                s.setName(oldName);
                s.setId(oldId);
                s.setPhone(oldPhone);
                throw e;
            }
        } else {
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
//movements
    public void registerEntry(int medicationId, int quantity, String reason) { // search that medication exists, then
        Medication medication = findMedicationById(medicationId); // create a movement, increase the
        if (medication == null) {                                 // stock and finally add the movement to the list
            throw new IllegalArgumentException("this medication does't exists");
        }
        InventoryMovement movement = new InventoryMovement(InventoryMovement.MovementType.ENTRY, medication, quantity,
                LocalDateTime.now(), reason);
        medication.increaseStock(quantity);
        movements.add(movement);
    }

    public void registerExit(int medicationId, int quantity, String reason) {
        Medication medication = findMedicationById(medicationId);
        if (medication == null) {
            throw new IllegalArgumentException("this medication doesn't exists");
        }
        InventoryMovement movement = new InventoryMovement(InventoryMovement.MovementType.EXIT, medication, quantity,
                LocalDateTime.now(), reason);
        medication.decreaseStock(quantity);
        movements.add(movement);
    }

    public void registerAdjustmentOut(int medicationId, int quantity, String reason) {
        Medication medication = findMedicationById(medicationId);
        if (medication == null) {
            throw new IllegalArgumentException("this medication doesn't exists");
        }
        InventoryMovement movement = new InventoryMovement(InventoryMovement.MovementType.ADJUSTMENT_OUT, medication,
                quantity, LocalDateTime.now(), reason);
        medication.decreaseStock(quantity);
        movements.add(movement);
    }

    public void registerAdjustmentIn(int medicationId, int quantity, String reason) {
        Medication medication = findMedicationById(medicationId);
        if (medication == null) {
            throw new IllegalArgumentException("this medication does't exists");
        }
        InventoryMovement movement = new InventoryMovement(InventoryMovement.MovementType.ADJUSTMENT_IN, medication,
                quantity, LocalDateTime.now(), reason);
        medication.increaseStock(quantity);
        movements.add(movement);
    }

    public LinkedList<InventoryMovement> getMovements(int medicationId){
        Medication m = findMedicationById(medicationId);
        if(m==null){
            throw new IllegalArgumentException("this medication doesn't exists");
        }
        LinkedList <InventoryMovement> medicationMovements = new LinkedList<>();
        for(InventoryMovement im:movements){
            if(im.getMedication()==m){
                medicationMovements.add(im);
            }
        }
        return medicationMovements;

    }
//stock

    public int getStock(int medicationId){
        Medication m =findMedicationById(medicationId);
        if(m==null){
            throw new IllegalArgumentException("this medication does't exists");
        }
        return m.getStock();
    }

    public boolean hasEnoughtStock(int medicationId,int quantity){
        Medication m = findMedicationById(medicationId);
        if(m==null){
            throw new IllegalArgumentException("medication does't exists");
        }
        return m.getStock()>=quantity;
    }
    public LinkedList<Medication> getLowStock(){
        LinkedList<Medication> lowStock = new LinkedList<>();
        for(Medication m : medications){
           if(m.isActive() && m.isLowStock()){
                lowStock.add(m);
           }
        }
        return lowStock;
    }
    //deletes

    public void deleteMedication(int medicationId){
        Medication m = findMedicationById(medicationId);
        if(m==null){
            throw new IllegalArgumentException("this medication doesn't exists");
        }
        if(hasMovements(m)){
            m.deactivate();
        }else{
            medications.remove(m);
        }
    }
    
    public void deleteSupplier(String supplierName){
        Supplier s = findSupplierByName(supplierName);
        if(s==null){
            throw new IllegalArgumentException("this Supplier doesn't exists");
        }
        boolean hasMedications = false;
        for(Medication m: medications){
            if(m.getSupplier()==s){
                hasMedications = true;
                break;
            }
        }
        if(hasMedications){
            s.deactivate();
        }else{
            suppliers.remove(s);
        }
    }

    public boolean hasMovements(Medication medication){
        for(InventoryMovement im : movements){
            if(im.getMedication()==medication){
                return true;
            }
        }
        return false;
    }
}
