package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael König
 */
@Entity
@Table(name = "runner")
public class Runner {
    @Id
    @GeneratedValue
    @Column(name="runner_id")
    private Integer id;

    @Column(name="runner_last_name")
    private String lastName;

    @Column(name="runner_first_name")
    private String firstName;

    @Column(name="runner_birth_day")
    private LocalDate birthDay;

    @Column(name="runner_gender")
    private Character gender;

    @Column(name="runner_weight")
    private Integer weight;

    @OneToMany(mappedBy = "runner", cascade = CascadeType.ALL)
    private List<Run> runs = new ArrayList<>();

    public Runner() {}

    public Runner(String lastName, String firstName, LocalDate birthDay, Character gender, Integer weight) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if(this.id !=null)
            throw new IllegalArgumentException("ID already set");
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<Run> getRuns() {
        return runs;
    }

    public void setRuns(List<Run> runs) {
        this.runs = runs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Runner runner = (Runner) o;

        return getId() != null ? getId().equals(runner.getId()) : runner.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Runner{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthDay=" + birthDay +
                ", gender=" + gender +
                ", weight=" + weight +
                ", runs size=" + runs.size() +
                '}';
    }
}
