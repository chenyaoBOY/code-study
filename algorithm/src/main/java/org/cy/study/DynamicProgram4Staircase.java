package org.cy.study;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyao
 * @date 2021/2/26 10:27
 * @description 一个楼梯有 10 级台阶，你从下往上走，
 * 每跨一步只能向上迈 1 级或者 2 级台阶，请问一共有多少种走法
 */
public class DynamicProgram4Staircase {


    /**
     * 要走到第10级台阶 只有两种可能
     * 从第9级走1级台阶
     * 从第8级走2级台阶
     * 所以走到第10级台阶的走法 就等于 走到第8级的走法+走到第9级的走法
     * f(10) = f(9)+f(8)
     * 推到出  f(x) = f(x-1) + f(x-2)  x>=3
     * f(1) = 1
     * f(2) = 2
     * <p>
     * 用代码方式 计算
     * 显然这是一个递归
     */
    public static void main(String[] args) {
        DynamicProgram4Staircase bean = new DynamicProgram4Staircase();
        bean.doBetterFx();
        bean.doFx();
        System.out.println(bean.bestFx(10));
    }

    private void doFx() {
        System.out.println(fx(10));
        System.out.println(totalCount_Fx);
    }

    private void doBetterFx() {
        System.out.println(betterFx(10));
        System.out.println(totalCount_betterFx);
    }

    /**
     * 这种方法比较简单 但是有重复计算的情况
     * 比如：10 = 9+8
     * 9 = 8+7
     * 这里8 就会被计算两次
     *
     * @param i
     * @return
     */
    static int totalCount_Fx = 0;

    private static int fx(int n) {
        totalCount_Fx++;
        if (n == 2) {
            return 2;
        }
        if (n == 1) {
            return 1;
        }
        return fx(n - 1) + fx(n - 2);
    }

    static int totalCount_betterFx = 0;
    static Map<Integer, Integer> map = new HashMap<>();

    private static int betterFx(int n) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        totalCount_betterFx++;
        if (n == 2) {
            return 2;
        }
        if (n == 1) {
            return 1;
        }
        int res = betterFx(n - 1) + betterFx(n - 2);
        map.put(n, res);
        return res;
    }

    private int bestFx(int n) {
        if (n == 2) {
            return 2;
        }
        if (n == 1) {
            return 1;
        }
        int a = 1;
        int b = 2;
        int sum = 0;
        for (int i = 3; i <= n; i++) {
            sum = a + b;
            a = b;
            b = sum;
        }
        return sum;
    }
}
