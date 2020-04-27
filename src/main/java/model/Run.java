package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Michael König
 */
@Entity
@Table(name = "run")
public class Run implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "run_id")
    private Integer id;

    @Column(name = "run_date")
    private LocalDate date;

    @Column(name = "run_minutes")
    private Integer minutes;

    @Column(name = "run_distance")
    private Integer distance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="runner_id")
    private Runner runner;

    public Run() {}

    public Run(LocalDate date, Integer minutes, Integer distance) {
        this.date = date;
        this.minutes = minutes;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if(this.id !=null)
            throw new IllegalArgumentException("ID already set");
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Run run = (Run) o;

        return getId() != null ? getId().equals(run.getId()) : run.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Run{" +
                "id=" + id +
                ", date=" + date +
                ", minutes=" + minutes +
                ", distance=" + distance +
                ", runner id=" + runner.getId() +
                '}';
    }
}
