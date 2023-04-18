package com.yeolsimee.moneysaving.unit;

import com.yeolsimee.moneysaving.util.TimeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유틸 테스트")
public class UtilTest {

    @Test
    void makeDateList() {
        String startDate = "20230429";
        String endDate = "20230502";
        List<String> dateList = TimeUtils.makeDateList(startDate, endDate);
        assertThat(dateList).containsExactly("20230429","20230430","20230501","20230502");
    }
}
