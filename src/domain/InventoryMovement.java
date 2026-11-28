import java.time.LocalDateTime;

public class InventoryMovement {

    private MovementType type;
    private Medication medication;
    private int quantity;
    private LocalDateTime date;
    private String reason;

    public InventoryMovement(MovementType type, Medication medication, int quantity, LocalDateTime date, String reason){
        if(type == null){
            throw new IllegalArgumentException("type have to be ENTRY, EXIT OR ADJUSTMENT");
        }
        this.type = type;
        if(medication==null){
            throw new IllegalArgumentException("medication can't be null");
        }
        this.medication=medication;
        if(quantity<=0){
            throw new IllegalArgumentException("quantity can't be negative");
        }
        this.quantity=quantity;
        if(date==null||date.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("date can't be future date and can't be null");
        }
        this.date=date;
        if(reason==null || reason.trim().length()<3||!reason.matches(".*\\p{L}.*")){ //it allow all letters in every language
            throw new IllegalArgumentException("reason can't be null and it must contain at least 3 characters");
        }
        this.reason = reason;
    }

    

    public MovementType getType(){
        return type;
    }

    public Medication getMedication(){
        return medication;
    }

    public int getQuantity(){
        return quantity;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public String getReason(){
        return reason;
    }
    

    public enum MovementType{
        ENTRY,
        EXIT,
        ADJUSTMENT_IN,
        ADJUSTMENT_OUT

    }
}
