package WebApp.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "reservation_list")
public class Reservation extends AbstractEntity {

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "organization_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private float rating;

    public Reservation() {
    }

    public Reservation(User user, Organization organization, String comment, float rating) {
        this.user = user;
        this.organization = organization;
        this.comment = comment;
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public Organization getOrganization() {
        return organization;
    }

    public String getComment() {
        return comment;
    }

    public float getRating() {
        return rating;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
