package com.sise.zhaodaola.core;

/**
 * 随机邮箱
 * 代码源于网络 由kingYiFan整理  create2019/05/24
 */
public class EmailRandom {

    private static final String[] email_suffix = ("@qq.com,@163.com,@scse.com.cn,@sise.com.cn").split(",");
    public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    /**
     * 返回Email
     *
     * @param lMin 最小长度
     * @param lMax 最大长度
     * @return
     */
    public static String getEmail(int lMin, int lMax) {
        int length = getNum(lMin, lMax);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = (int) (Math.random() * base.length());
            sb.append(base.charAt(number));
        }
        sb.append(email_suffix[(int) (Math.random() * email_suffix.length)]);
        return sb.toString();
    }

    //  代码源于网络 由kingYiFan整理  create2019/05/24
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            String email = getEmail(9, i);
            System.out.println(email);
        }
    }
}
