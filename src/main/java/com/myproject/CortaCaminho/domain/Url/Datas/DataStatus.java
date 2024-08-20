package com.myproject.CortaCaminho.domain.Url.Datas;

import com.myproject.CortaCaminho.domain.Url.Status;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DataStatus(
        @NotNull
        Long idUrl,
        Status status,
        LocalDateTime time,
        @NotNull
        Boolean scheduledStatus
) {
}
