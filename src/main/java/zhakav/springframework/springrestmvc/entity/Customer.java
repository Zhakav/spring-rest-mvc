package zhakav.springframework.springrestmvc.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id" , length =36 ,
            columnDefinition = "varchar" ,
            nullable = false ,updatable = false)
    private UUID id;
    private String name;
    @Version
    private int version;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
