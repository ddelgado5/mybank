package com.cuentas.cuentas.entity;

import com.cuentas.cuentas.enums.MovimientoType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimientos")
public class Movimiento
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime fecha;

    @NotNull(message = "El campo tipo de movimiento es obligatorio, seleccione AHORRO o CORRIENTE")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private MovimientoType tipoMovimiento;

    @NotNull(message = "EL campo valor es obligatorio")
    private Double valor;

    private Double saldo;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_cuenta")
    private Cuenta cuenta;
}