package com.cuentas.cuentas.entity;

import com.cuentas.cuentas.dto.ClienteDTO;
import com.cuentas.cuentas.enums.CuentaType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cuentas")
public class Cuenta implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "El campo Numero de cuenta es obligatorio por favor introduzcalo")
    @Pattern(regexp = "^[0-9]{6,11}$", message = "EL numero de cuenta no es valido, ingrese nuevamente")
    @Column(name = "numero_cueta")
    private String numeroCuenta;

    @NotNull(message = "El campo tipo de cuenta es obligatorio, por favor introduzcalo")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private CuentaType tipoCuenta;

    @NotNull(message = "El campo saldo incial es obligatorio, por favor introduzcalo")
    @Column(name = "saldo_inicial")
    private double saldoInicial;

    @NotNull(message = "El campo estado es obligatorio, por favor introduzcalo")
    private Boolean estado;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
    private List<Movimiento> movimientos;
}

