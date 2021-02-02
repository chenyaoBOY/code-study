import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author chenyao
 * @date 2021/2/2 13:38
 * @description
 */
public class SpelTest {

    @Test
    public void test(){
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) exp.getValue();
        System.out.println(message);
    }
    @Test
    public void test2(){
        ExpressionParser parser = new SpelExpressionParser();
        // invokes 'getBytes()'
        Expression exp = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp.getValue();
        System.out.println(Arrays.toString(bytes));
    }
    @Test
    public void test3(){
        Inventor tesla = new Inventor("Nikola Tesla", "Serbian" );

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("name");

        EvaluationContext context = new StandardEvaluationContext(tesla);
        String name = (String) exp.getValue(tesla);
//        String name = (String) exp.getValue(context);
        System.out.println(name);

        ExpressionParser parser2 = new SpelExpressionParser();
        Expression exp2 = parser2.parseExpression("name == 'Nikola Tesla'");
        System.out.println(exp2.getValue(tesla));
    }

    @Test
    public void test4(){
        Inventor tesla = new Inventor("Nikola Tesla", "Serbian" );
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("bean",tesla);

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#bean.getName()");
        Object value = expression.getValue(context);
        System.out.println(JSON.toJSONString(value));

    }

    @Data
    private class Inventor {
        private String name;
        private String nationality;
        private Date birthday;

        public Inventor(String name, String nationality) {
            GregorianCalendar c = new GregorianCalendar();
            c.set(1856, 7, 9);
            this.name = name;
            this.nationality = nationality;
            this.birthday = c.getTime();
        }
    }
}
