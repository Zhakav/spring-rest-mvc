package zhakav.springframework.springrestmvc.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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
    @Column(name = "beer_id" , length =36 ,
            columnDefinition = "varchar" ,
            nullable = false ,updatable = false)
    private UUID id;
    @Version
    private Integer version;
    @NotNull
    @NotBlank
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;
    @NotNull
    @NotBlank
    private String upc;
    @NotNull
    @Min(0)
    private Integer quantityOnHand;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
