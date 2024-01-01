package org.example.shoppingapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingapp.dto.common.DataResponse;
import org.example.shoppingapp.dto.order.OrderRequest;
import org.example.shoppingapp.service.OrderService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/all")
    public DataResponse getAllOrders(){
        // TODO: build orders.
        return DataResponse.builder()
                .build();
    }

    @PostMapping("")
    @ResponseBody
    public DataResponse createOrders(@RequestBody List<@Valid OrderRequest> orderRequestList,
                                     BindingResult bindingResult){
        // TODO: handle order requests.
        if(bindingResult.hasErrors()){

        }
        return DataResponse.builder()
                .message("successfully pass the validation!")
                .data(orderRequestList)
                .build();
    }

    private DataResponse buildErrorContent(List<FieldError> errors){
        StringBuilder errorMessage = new StringBuilder();
        errors.forEach(error -> errorMessage.append(error.getObjectName())
                .append(": ")
                .append(error.getDefaultMessage())
                .append("\n"));
        return DataResponse.getGeneralInvalidResponse(errorMessage.toString(),
                "Order request not in correct format, please check your request.");
    }
}
