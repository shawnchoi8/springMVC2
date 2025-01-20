package hello.typeconverter.formatter;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.*;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        //Register Converter
        conversionService.addConverter(new StringToIpPortConverter()); //String -> IP PORT
        conversionService.addConverter(new IpPortToStringConverter()); //IP PORT -> String

        //Register Formatter
        conversionService.addFormatter(new MyNumberFormatter()); //Formatter (String -> Number, Object -> String)

        //Use Converter
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        //Use Formatter
        assertThat(conversionService.convert(1000, String.class)).isEqualTo("1,000");
        assertThat(conversionService.convert("1,000", Integer.class)).isEqualTo(1000L);
    }
}
