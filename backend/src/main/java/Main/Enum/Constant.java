package Main.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Constant {
    DefaultPageSize(20);

    private final int PageSize;


}
