import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Receipt {

    private final int id;
    private final LocalDateTime date;
    private ReceiptStatus status;
    private final Customer customer;
    private final Employee employee;
    private final List<ReceiptLine> lines;

    public Receipt(int id, Customer customer, Employee employee, List<ReceiptLine> lines) {
        this.customer = Objects.requireNonNull(customer, "El cliente no puede ser nulo");
        this.employee = Objects.requireNonNull(employee, "El empleado no puede ser nulo");

        // Multiplicidad 1..* con ReceiptLine: un recibo necesita al menos una línea
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("Un recibo debe tener al menos una línea");
        }

        this.id = id;
        this.date = LocalDateTime.now();
        this.status = ReceiptStatus.ACTIVE;
        this.lines = new ArrayList<>(lines);
    }

    public double calculateTotal() {
        double total = 0;
        for (ReceiptLine line : lines) {
            total += line.getAmount();
        }
        return total;
    }

//hay que poner los mensajes en inglés se me olvidó
    public void cancel() {
        if (status == ReceiptStatus.CANCELLED) {
            throw new IllegalStateException("El recibo " + id + " ya está anulado");
        }
        status = ReceiptStatus.CANCELLED;
    }

    public List<ReceiptLine> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
