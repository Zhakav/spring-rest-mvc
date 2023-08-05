package zhakav.springframework.springrestmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private UUID id;
    @Size(max = 50)
    @NotNull
    @NotBlank
    private String name;
    private int version;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
