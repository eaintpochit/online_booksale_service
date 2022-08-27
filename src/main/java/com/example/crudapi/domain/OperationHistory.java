package com.example.crudapi.domain;


import javax.persistence.*;


@MappedSuperclass
public abstract class OperationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "status")
    private String status;
    @Column(name = "create_date_time")
//    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String createdDateTime;
    @Column(name = "data")
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
