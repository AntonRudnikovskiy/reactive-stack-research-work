package com.losevskiyfz.reactivestackresearchwork.domain;

import jakarta.validation.constraints.NotEmpty;

public record PostBookDto(
        String type,
        Integer quantity,
        String[] authors,
        @NotEmpty
        String name,
        Integer pages,
        String publisher,
        Integer year,
        String city,
        String department_id,
        String summary,
        String room
) {}
