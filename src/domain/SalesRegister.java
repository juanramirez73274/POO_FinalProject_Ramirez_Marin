import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class SalesRegister {

    private final List<Receipt> receipts;

    public SalesRegister() {
        this.receipts = new ArrayList<>();
    }

    public void add(Receipt receipt) {
        Objects.requireNonNull(receipt, "El recibo no puede ser nulo");

        if (findById(receipt.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un recibo con id " + receipt.getId());
        }
        receipts.add(receipt);
    }

    public void cancel(int id) {
        Receipt receipt = findById(id);
        if (receipt == null) {
            throw new IllegalArgumentException("No existe un recibo con id " + id);
        }
        receipt.cancel();
    }
  
    public List<Receipt> findBetween(LocalDateTime from, LocalDateTime to) {
        Objects.requireNonNull(from, "La fecha inicial no puede ser nula");
        Objects.requireNonNull(to, "La fecha final no puede ser nula");
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("La fecha inicial no puede ser posterior a la final");
        }

        List<Receipt> result = new ArrayList<>();
        for (Receipt receipt : receipts) {
            LocalDateTime date = receipt.getDate();
            if (!date.isBefore(from) && !date.isAfter(to)) {
                result.add(receipt);
            }
        }
        return result;
    }

    public double getTotalBetween(LocalDateTime from, LocalDateTime to) {
        double total = 0;
        for (Receipt receipt : findBetween(from, to)) {
            if (receipt.getStatus() == ReceiptStatus.ACTIVE) {
                total += receipt.calculateTotal();
            }
        }
        return total;
    }

    public List<Receipt> getReceipts() {
        return Collections.unmodifiableList(receipts);
    }

    private Receipt findById(int id) {
        for (Receipt receipt : receipts) {
            if (receipt.getId() == id) {
                return receipt;
            }
        }
        return null;
    }
}