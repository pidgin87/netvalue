package nz.netvalue.controller.utils;

import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
public class IfMatchHeaderService {

    private static final String WEAK_VALIDATION_FLAG = "W/";
    private static final String QUOTE = "\"";

    public Long getTrimmedLongValue(String ifMatchHeader) {
        if (isNull(ifMatchHeader)) {
            return null;
        }
        String value = ifMatchHeader.replace(WEAK_VALIDATION_FLAG, EMPTY).replace(QUOTE, EMPTY);
        return Long.valueOf(value);
    }
}
