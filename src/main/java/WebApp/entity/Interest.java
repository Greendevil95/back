package WebApp.entity;

import WebApp.entity.enums.Category;
import WebApp.entity.enums.Event;

import javax.persistence.*;

@Entity
@Table(name = "interests")
public class Interest extends AbstractEntity {

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "count")
    private Integer count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "event")
    @Enumerated(EnumType.STRING)
    private Event event;


    public Interest() {
    }

    public Interest(Category category, Integer count, User user, Event event) {
        this.category = category;
        this.count = count;
        this.user = user;
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}
