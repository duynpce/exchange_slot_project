package main.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StringConstant {
    MORNING_SLOT("1,2"),
    AFTERNOON_SLOT("3,4");

    private final String value;
}
