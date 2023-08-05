package zhakav.springframework.springrestmvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import zhakav.springframework.springrestmvc.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "beer_id" , length =36 ,
            columnDefinition = "varchar(36)" ,
            nullable = false ,updatable = false)
    private UUID id;
    @Version
    private Integer version;
    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;
    @NotNull
    @Min(0)
    private Integer quantityOnHand;
    @NotNull
    @Min(0)
    private BigDecimal price;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
