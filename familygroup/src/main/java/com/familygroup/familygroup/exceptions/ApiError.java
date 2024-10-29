package com.familygroup.familygroup.exceptions;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ApiError(

    List<String> error,

    String status,

    Integer code,

    @JsonFormat(pattern="dd:MM:yyyy HH:mm:ss")
    LocalDateTime timeStamp


) {
    
}
