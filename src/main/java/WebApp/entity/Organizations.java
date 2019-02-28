package WebApp.entity;


import javax.persistence.*;

@Entity
@Table(name = "organization")
public class Organizations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "description")
    private  String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User organizerId;

    public Organizations() {
    }

    public Organizations(String name, String address, String phoneNumber, String description, User organizerId) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.organizerId = organizerId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public User getOrganizerId() {
        return organizerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrganizerId(User organizerId) {
        this.organizerId = organizerId;
    }
}
