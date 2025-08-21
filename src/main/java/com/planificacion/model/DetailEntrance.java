package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "detail_entrance")
public class DetailEntrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false, foreignKey = @ForeignKey(name = "FK_detail_entrance_concert"))
    private Concert concert;

    @ManyToOne
    @JoinColumn(name = "entrance_id", nullable = false, foreignKey = @ForeignKey(name = "FK_detail_entrance_entrance"))
    private Entrance entrance;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "token", nullable = false, unique = true)
    private String token;
}