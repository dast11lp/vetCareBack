package io.demo.veterinary.models;

import java.time.LocalTime;

public class HourAvailable {
    private Long id;
    private String veterinarian;

    private LocalTime hour;

    public HourAvailable (Long id, String veterinarian, LocalTime hour) {
        this.id = id;
        this.veterinarian = veterinarian;
        this.hour = hour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(String veterinarian) {
        this.veterinarian = veterinarian;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }
}
