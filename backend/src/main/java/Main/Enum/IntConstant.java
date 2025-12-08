package Main.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum IntConstant {
    DEFAULT_PAGE_SIZE(20);
    private final int value;


}
