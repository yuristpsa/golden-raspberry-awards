package com.yurisa.gra.domain.producer;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RangeOfAward {
    private int previousWin;
    private int followingWin;

    public int getInterval() {
        return followingWin - previousWin;
    }
}
