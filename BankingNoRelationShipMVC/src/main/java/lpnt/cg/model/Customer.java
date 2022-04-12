package lpnt.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "fullName not empty")
    @Size(min = 20, max = 100)
    private String fullName;

    @Pattern(regexp = "(^$|[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$)",
            message = "vd: admin@gmail.com")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "(^$|[0][0-9]{9,10}$)",
            message = "SDT gom 10 chu so, 0987654321")
    private String phone;

    @Size(min = 1, max = 255, message = "Address description within 255 character")
    private String address;

    private long balance = 0;

    private boolean isDelete = false;

    private LocalDateTime datetime = LocalDateTime.now();

    public Customer() {}

//    public Customer(Long id, String fullName, String email, String phone, String address, long balance, boolean isDelete, LocalDateTime datetime) {
//        this.id = id;
//        this.fullName = fullName;
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.balance = balance;
//        this.isDelete = isDelete;
//        this.datetime = datetime;
//    }

    public Customer(Long id,
                    @NotEmpty(message = "fullName not null")
                    @Size(min = 20, max = 100) String fullName,
                    @Pattern(regexp = "(^$|[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$)"
                    , message = "email vd: admin@codegym.com") String email,
                    @Pattern(regexp ="(^$|[0][0-9]{9,10}$)",
                    message = "vd: 0868.868.686 ") String phone,
                    @Size(min =1 , max = 255, message = "Address character 255") String address,
                    long balance,
                    boolean isDelete,
                    LocalDateTime datetime) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.isDelete = isDelete;
        this.datetime = datetime;
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

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
