package org.study.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author chenyao
 * @date 2021/2/2 13:34
 * @description
 */
@Slf4j
public class SpelParser {


    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        System.out.println(message);
    }

    public static String getSpelValue(String[] keys,Object[] vals,String spel){
        StandardEvaluationContext context = new StandardEvaluationContext();
        try {
            for (int i = 0; i < keys.length; i++) {
                context.setVariable(keys[i],vals[i]);
            }
            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(spel);
            return (String) expression.getValue(context);
        } catch (Exception e) {
            log.error("spel解析失败",e);
            return spel;
        }
    }
}
