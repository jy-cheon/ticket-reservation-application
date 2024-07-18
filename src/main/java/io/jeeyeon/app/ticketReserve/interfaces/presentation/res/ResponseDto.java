package io.jeeyeon.app.ticketReserve.interfaces.presentation.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto <T> {

    private T result;
    private String responseMsg;

    public static <T> ResponseDto success(T result){
        return ResponseDto.builder()
                .result(result)
                .responseMsg("요청성공")
                .build();
    }

    public static ResponseDto error(String errMessage){
        return ResponseDto.builder()
                .result(null)
                .responseMsg(errMessage)
                .build();
    }

}
