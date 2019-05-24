package WebApp.entity;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class Report extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status;

    public Report() {
    }

    public Report(User user, Service service, String description, boolean status) {
        this.user = user;
        this.service = service;
        this.description = description;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
