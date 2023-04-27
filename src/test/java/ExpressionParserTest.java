import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ExpressionParserTest{

    private final String whiteSpace = " ";
    private final ExpressionParser parser = new ExpressionParser(whiteSpace);

    @Nested
    class 공백_delimiter_로_주입받은_파서는 {

        @Test
        void 공백_을_2개_가진_문자열을_3개_로_쪼갠다() {
            String strWithTwoSpaces = "1 2 3";

            List<String> parts = parser.parse(strWithTwoSpaces);

            Assertions.assertThat(parts).hasSize(3);
        }

        @Test
        void 공백만이_존재하는_문자열_은_비어있는_리스트를_리턴한다() {
            String emptyStr = "   ";

            List<String> parts = parser.parse(emptyStr);

            Assertions.assertThat(parts).isEmpty();
        }

    }

    @Test
    void 정수와_사칙연산자와_공백_외의_글자가_포함된_경우_예외를_던진다() {
        String invalidExpression = "1 + 2 + a";

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> parser.parse(invalidExpression));
    }
}
