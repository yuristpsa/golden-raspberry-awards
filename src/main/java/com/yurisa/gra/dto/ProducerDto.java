package com.yurisa.gra.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProducerDto {
    private String producer;
    private int previousWin;
    private int followingWin;
    private int interval;
}
