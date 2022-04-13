package lpnt.cg.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "withdraws")
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idOwner;

    @Min(value = 1000, message = "Withdraw not min 1000$")
    private long amount;

    private boolean isDelete = false;

    private LocalDateTime dateTime = LocalDateTime.now();

    public Withdraw() {}

    public Withdraw(Long idOwner) {
        this.idOwner = idOwner;
    }

    public Withdraw(Long id, Long idOwner,
                    @Min(value = 1000, message = "Withdraw not min 1000$") long amount, boolean isDelete, LocalDateTime dateTime) {
        this.id = id;
        this.idOwner = idOwner;
        this.amount = amount;
        this.isDelete = isDelete;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
