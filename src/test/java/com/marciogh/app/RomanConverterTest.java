package com.marciogh.app;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RomanConverterTest {

    @Autowired
    RomanConverter romanConverter;

    @SpyBean
    RomanConverterEngine romanConverterEngine;

    @ParameterizedTest
    @CsvSource({"1,I", "2,II", "3,III"})
    public void test_Roman_1_to_3(int actual, String excpeted) {
        // set
        // when(romanConverterEngine.fromInt(2)).thenReturn("2"); // fault injection

        // act
        String converted = romanConverter.fromInt(actual);

        // check
        assertThat(converted).isEqualTo(excpeted);
        verify(romanConverterEngine, times(1)).fromInt(actual);
    }

}
