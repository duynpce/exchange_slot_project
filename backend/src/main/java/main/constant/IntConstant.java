package main.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IntConstant {
    DEFAULT_PAGE_SIZE(20),
    CLASS_PAGE_SIZE(15);

    private final int value;


}

