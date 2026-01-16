package dev.atinroy.counterBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Counter {
    @Id
    private Long id;
    String counterName;
    Integer counterValue;
    public Counter() {
    }
    public Counter(String counterName, Integer counterValue) {}
}
