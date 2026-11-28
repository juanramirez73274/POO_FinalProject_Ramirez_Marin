import java.util.Objects;

public class ReceiptLine {

    private final Medication medication;
    private final int quantity;
    private final double unitPrice;

    public ReceiptLine(Medication medication, int quantity, double unitPrice) {
        this.medication = Objects.requireNonNull(medication, "El medicamento no puede ser nulo");

        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("El precio unitario no puede ser negativo");
        }

        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getAmount() {
        return quantity * unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public Medication getMedication() {
        return medication;
    }
}