package com.yurisa.gra.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RangeOfAwardsDto {
    private List<ProducerDto> min;
    private List<ProducerDto> max;
}
