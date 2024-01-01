package org.example.shoppingapp.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @Valid
    @JsonProperty("order")
    List<SingleOrderRequest> orderRequests;
}
