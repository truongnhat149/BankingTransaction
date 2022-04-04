package lpnt.cg.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String phone;
    private String address;
    private long balance = 0;
    private boolean isSupended = false;
    private LocalDateTime create_at = LocalDateTime.now();

    @OneToMany(targetEntity = Deposit.class, mappedBy = "customer")
    private List<Deposit> deposits;

    @OneToMany(targetEntity = Withdraw.class, mappedBy = "customer")
    private List<Withdraw> withdraws;

    @OneToMany(targetEntity = Transfer.class, mappedBy = "sender")
    private List<Transfer> senders;

    @OneToMany(targetEntity = Transfer.class, mappedBy = "recipient")
    private List<Transfer> recipients;

    public Customer() {}

    public Customer(Long id, String fullName, String email, String phone, String address, long balance, boolean isSupended, LocalDateTime create_at, List<Deposit> deposits, List<Withdraw> withdraws, List<Transfer> senders, List<Transfer> recipients) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.isSupended = isSupended;
        this.create_at = create_at;
        this.deposits = deposits;
        this.withdraws = withdraws;
        this.senders = senders;
        this.recipients = recipients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isSupended() {
        return isSupended;
    }

    public void setSupended(boolean supended) {
        isSupended = supended;
    }

    public LocalDateTime getCreate_at() {
        return create_at;
    }

    public void setCreate_at(LocalDateTime create_at) {
        this.create_at = create_at;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public List<Withdraw> getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(List<Withdraw> withdraws) {
        this.withdraws = withdraws;
    }

    public List<Transfer> getSenders() {
        return senders;
    }

    public void setSenders(List<Transfer> senders) {
        this.senders = senders;
    }

    public List<Transfer> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Transfer> recipients) {
        this.recipients = recipients;
    }
}
