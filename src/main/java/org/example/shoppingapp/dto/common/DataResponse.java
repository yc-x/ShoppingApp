package org.example.shoppingapp.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataResponse {
    private Boolean success;
    private String message;
    private Object data;

    public static DataResponse getGeneralInvalidResponse(Object errorMessage, String info){
        return DataResponse.builder()
                .data(errorMessage)
                .success(false)
                .message(info)
                .build();
    }
}
