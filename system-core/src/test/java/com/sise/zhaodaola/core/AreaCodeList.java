package com.sise.zhaodaola.core;

import java.util.*;

/**
 * 地区编码集合
 * 代码源于网络 由kingYiFan整理  create2019/05/24
 */
class AreaCodeList {

    //创建一个map存放编码
    static final Map<String, Integer> areaCode = new HashMap<String, Integer>();

    static {
        AreaCodeList.areaCode.put("北京市", 110000);
        AreaCodeList.areaCode.put("江都市", 321088);
        AreaCodeList.areaCode.put("镇江市", 321100);
        AreaCodeList.areaCode.put("市辖区", 321101);
        AreaCodeList.areaCode.put("京口区", 321102);
        AreaCodeList.areaCode.put("润州区", 321111);
        AreaCodeList.areaCode.put("丹徒区", 321112);
        AreaCodeList.areaCode.put("丹阳市", 321181);
        AreaCodeList.areaCode.put("扬中市", 321182);
        AreaCodeList.areaCode.put("句容市", 321183);
        AreaCodeList.areaCode.put("泰州市", 321200);
        AreaCodeList.areaCode.put("市辖区", 321201);
        AreaCodeList.areaCode.put("海陵区", 321202);
        AreaCodeList.areaCode.put("高港区", 321203);
        AreaCodeList.areaCode.put("兴化市", 321281);
        AreaCodeList.areaCode.put("靖江市", 321282);
        AreaCodeList.areaCode.put("泰兴市", 321283);
        AreaCodeList.areaCode.put("姜堰市", 321284);
        AreaCodeList.areaCode.put("宿迁市", 321300);
        AreaCodeList.areaCode.put("市辖区", 321301);
        AreaCodeList.areaCode.put("万宁市", 469006);
        AreaCodeList.areaCode.put("东方市", 469007);
        AreaCodeList.areaCode.put("定安县", 469021);
        AreaCodeList.areaCode.put("屯昌县", 469022);
        AreaCodeList.areaCode.put("澄迈县", 469023);
        AreaCodeList.areaCode.put("临高县", 469024);
        AreaCodeList.areaCode.put("白沙黎族自治县", 469025);
        AreaCodeList.areaCode.put("昌江黎族自治县", 469026);
        AreaCodeList.areaCode.put("乐东黎族自治县", 469027);
        AreaCodeList.areaCode.put("陵水黎族自治县", 469028);
        AreaCodeList.areaCode.put("保亭黎族苗族自治县", 469029);
        AreaCodeList.areaCode.put("琼中黎族苗族自治县", 469030);
        AreaCodeList.areaCode.put("西沙群岛", 469031);
        AreaCodeList.areaCode.put("南沙群岛", 469032);
        AreaCodeList.areaCode.put("中沙群岛的岛礁及其海域", 469033);
        AreaCodeList.areaCode.put("重庆市", 500000);
        AreaCodeList.areaCode.put("市辖区", 500100);
        AreaCodeList.areaCode.put("万州区", 500101);
        AreaCodeList.areaCode.put("涪陵区", 500102);
        AreaCodeList.areaCode.put("渝中区", 500103);
        AreaCodeList.areaCode.put("大渡口区", 500104);
        AreaCodeList.areaCode.put("江北区", 500105);
        AreaCodeList.areaCode.put("沙坪坝区", 500106);
        AreaCodeList.areaCode.put("九龙坡区", 500107);
        AreaCodeList.areaCode.put("南岸区", 500108);
        AreaCodeList.areaCode.put("北碚区", 500109);
        AreaCodeList.areaCode.put("万盛区", 500110);
        AreaCodeList.areaCode.put("双桥区", 500111);
        AreaCodeList.areaCode.put("渝北区", 500112);
        AreaCodeList.areaCode.put("巴南区", 500113);
        AreaCodeList.areaCode.put("澳门特别行政区", 820000);
    }

}

/**
 * 随机生成身份证18位号码工具类
 * <p>
 * 居民身份证号码是根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，
 * 由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。居民身份证是国家法定的证明公民个人身份的有效证件。
 * 　身份证号码构成：
 * 　　1 地址码
 * 　　（身份证前六位）表示编码对象常住户口所在县(市、镇、区)的行政区划代码。
 * 　　2 生日期码
 * 　　（身份证第七位到第十四位）表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。例如：1981年05月11日就用19810511表示。
 * 　　3 顺序码
 * 　　（身份证第十五位到十七位）为同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。其中第十七位奇数分给男性，偶数分给女性。（随机生成）
 * 　　4 校验码
 * 　　（身份证最后一位）是根据前面十七位数字码由号码编制单位按统一的公式计算出来的，
 * 如果某人的尾号是0-9，都不会出现X，但如果尾号是10，那么就得用X来代替，因为如果用10做尾号，那么此人的身份证就变成了19位，而19位的号码违反了国家标准，
 * 并且我国的计算机应用系统也不承认19位的身份证号码。Ⅹ是罗马数字的10，用X来代替10，可以保证公民的身份证符合国家标准
 * 代码源于网络 由kingYiFan整理  create2019/05/24
 */
class IdCardRandom extends AreaCodeList {

    /**
     * 生成随机地区编码
     *
     * @return
     */
    private int randomAreaCode() {
        int index = (int) (Math.random() * super.areaCode.size());
        Collection<Integer> values = super.areaCode.values();
        Iterator<Integer> it = values.iterator();
        int i = 0;
        int code = 0;
        while (i < index && it.hasNext()) {
            i++;
            code = it.next();
        }
        return code;
    }

    /**
     * 随机出生日期
     */
    private String randomBirthday() {
        Calendar birthday = Calendar.getInstance();
        birthday.set(Calendar.YEAR, (int) (Math.random() * 60) + 1950);
        birthday.set(Calendar.MONTH, (int) (Math.random() * 12));
        birthday.set(Calendar.DATE, (int) (Math.random() * 31));

        StringBuilder builder = new StringBuilder();
        builder.append(birthday.get(Calendar.YEAR));
        long month = birthday.get(Calendar.MONTH) + 1;
        if (month < 10) {
            builder.append("0");
        }
        builder.append(month);
        long date = birthday.get(Calendar.DATE);
        if (date < 10) {
            builder.append("0");
        }
        builder.append(date);
        return builder.toString();
    }

    /**
     * 随机产生3位
     *
     * @return
     */
    private String randomCode() {
        int code = (int) (Math.random() * 1000);
        if (code < 10) {
            return "00" + code;
        } else if (code < 100) {
            return "0" + code;
        } else {
            return "" + code;
        }
    }

    /**
     * 生成最后一位数字
     *
     * @param chars
     * @return
     */
    private char calcTrailingNumber(char[] chars) {
        if (chars.length < 17) {
            return ' ';
        }
        int[] c = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] r = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int[] n = new int[17];
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(chars[i] + "");
        }
        for (int i = 0; i < n.length; i++) {
            result += c[i] * n[i];
        }
        return r[result % 11];
    }

    /**
     * 生成身份证
     *
     * @return
     */
    public String generate() {
        StringBuilder generater = new StringBuilder();
        generater.append(this.randomAreaCode());
        generater.append(this.randomBirthday());
        generater.append(this.randomCode());
        generater.append(this.calcTrailingNumber(generater.toString().toCharArray()));
        return generater.toString();
    }

    public static void main(String[] args) {
        IdCardRandom g = new IdCardRandom();
        String generate = g.generate();
        System.out.println(generate);
    }

}
