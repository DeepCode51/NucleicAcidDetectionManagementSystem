package com.yuanlrc.base.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    // 各种时间格式
    public static final SimpleDateFormat date_sdf = new SimpleDateFormat(
            "yyyy-MM-dd");
    // 各种时间格式
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
            "yyyyMMdd");
    // 各种时间格式
    public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat(
            "yyyy年MM月dd日");
    public static final SimpleDateFormat time_sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat(
            "HH:mm");
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    // 以毫秒表示的时间
    private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
    private static final long HOUR_IN_MILLIS = 3600 * 1000;
    private static final long MINUTE_IN_MILLIS = 60 * 1000;
    private static final long SECOND_IN_MILLIS = 1000;

    // 指定模式的时间格式
    private static SimpleDateFormat getSDFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 当前日历，这里用中国时间表示
     *
     * @return 以当地时区表示的系统当前日历
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 505      * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     * 506      *
     * 507      * @param src
     * 508      *            将要转换的原始字符窜
     * 509      * @param pattern
     * 510      *            转换的匹配格式
     * 511      * @return 如果转换成功则返回转换后的日期
     * 512      * @throws ParseException
     * 513      * @throws AIDateFormatException
     * 514
     */
    public static Date parseDate(String src, String pattern)
            throws ParseException {
        return getSDFormat(pattern).parse(src);
    }

    /**
     * 默认方式表示的系统当前日期，具体格式：时：分
     *
     * @return 默认日期按“时：分“格式显示
     */
    public static String formatShortTime() {
        Date time = getCalendar().getTime();
        return short_time_sdf.format(time);
    }


    /**
     * 开始时间和结束时间 HH:MM
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String hhmm(long startTime, long endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        Long result = endTime - startTime;    //获取两时间相差的毫秒数
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long hour = result % nd / nh;     //获取相差的小时数
        long min = result % nd % nh / nm;  //获取相差的分钟数
        long day = result / nd;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");//初始化Formatter的转换格式。
        long hMiles = hour * 3600000;  //小时数转换成毫秒
        long mMiles = min * 60000;    //分钟数转换成毫秒
        long resulMiles = (hMiles + mMiles);
        //下面这段很重要 ,计算之后设置时区,不然会差几小时
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String resultFormat = formatter.format(resulMiles);
        return resultFormat;
    }

    public static boolean checkDateTime(Date startTime, Date endTime) {
        if (startTime.after(endTime)) {
            return false;
        }
        return true;
    }


    public static final String Format_Date = "yyyy-MM-dd";
    public static final String Format_Time = "HH:mm:ss";
    public static final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";

    public DateUtil() {
    }

    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 时间相减得到天数
     * @param date
     * @param date2
     * @return
     */
    public static int getDaySub(Date date,Date date2){
        int day = 0;

        day=(int) ((date2.getTime()-date.getTime())/(24*60*60*1000));

        return day+1;
    }

    /**
     * 根据日期获得所在周的日期
     * @param mdate
     * @return
     */
    @SuppressWarnings("deprecation")
    public static List<Date> dateToWeek(Date mdate) {
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a-1, fdate);
        }
        return list;
    }

    /**
     * 获取两个日期之间的日期(包含起始和结束日期)
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期集合
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        result.add(start);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.add(end);
        return result;
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException{

        //时分秒清零
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));

        //获得设置之后时间的毫秒值
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();

        //计算天数
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期相差几周
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int weeksBetween(Date smdate,Date bdate) throws ParseException {

        //设置星期一为第一天
        Calendar cal=Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(smdate);

        //设置较小时间为该周星期一
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        //查询两个日期相差天数
        int day=daysBetween(cal.getTime(),bdate);

        //返回周数差
        return day/7;

    }

    //String转Date
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    //String转Date
    public static Date strToDateMin(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取星期几
     * @param dt
     * @return
     */
    public static int getWeekNoOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return w+1;
    }

    public static Date  tommorrow(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        getCleanDate(date);
        return  date;
    }

    /**
     * 传入时间 往后推分钟
     * @param minut
     * @return
     */
    public static Date  addDateMinut(Date date,int minut){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MINUTE,minut);//把日期往后增加几分钟.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推几分钟的结果
        getCleanDate(date);
        return  date;
    }

    public static Date  tommorrow(Date time,int days){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        calendar.add(calendar.DATE,days);//把日期往后增加一天.整数往后推,负数往前移动
        time=calendar.getTime(); //这个时间就是日期往后推一天的结果
        getCleanDate(time);
        return  time;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekNumOfDate(Date dt) {
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekJiDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 取得某天所在周的最后一天
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    public static Date getLastDayOfMonth(Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(5,c.getActualMaximum(5));
        return c.getTime();
    }

    /**
     * 得到本月第一天的日期
     * @Methods Name getFirstDayOfMonth
     * @return Date
     */
    public static Date getBeginDayOfMonth(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(cDay.getTime());
        return cDay.getTime();
    }

    /**
     * 得到本月最后一天的日期
     * @Methods Name getLastDayOfMonth
     * @return Date
     */
    public static Date getEndDayOfMonth(Date date)   {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println(cDay.getTime());
        return cDay.getTime();
    }

    /**
     * 获取上一周的周一
     */
    public static Date getLastWeekMondy(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    /**
     * 获取当前周的周一
     */
    public static Date getThisWeekMonday(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取下一周的周一
     */
    public static Date getNextWeekMonday(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekMonday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    public static String addDays(String date,String format,int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parse(date,format));
        calendar.add(Calendar.DATE,days);
        return DateUtil.toString(calendar.getTime(),format);
    }



    /**
     * 获得两个时间段之内的所有日期小时：例如传参数："2018-12-06 01"和"2018-12-06 23", 返回结果：[2018-12-06
     * 01, 2018-12-06 02, 2018-12-06 03, 2018-12-06 04, ......, 2018-12-06 23]
     * @param beginDate
     * @param endDate
     * @return
     * @throws ParseException
     * @throws ParseException
     */
    public static List<String> getHoursBetweenTwoDate(String beginDate,
                                                      String endDate,Integer slot) throws ParseException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        List<String> lDate = new ArrayList<String>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(sdf.parse(beginDate));
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.MINUTE, slot);
            // 测试此日期是否在指定日期之后
            if (sdf.parse(endDate).after(cal.getTime())) {
                lDate.add(sdf.format(cal.getTime()));
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }



    public static String getCurrentDate() {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String getCurrentTime() {
        return (new SimpleDateFormat("HH:mm:ss")).format(new Date());
    }

    public static String getCurrentTime(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String getCurrentDateTime() {
        String format = "yyyy-MM-dd HH:mm:ss";
        return getCurrentDateTime(format);
    }

    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(7);
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(7);
    }

    public static int getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(5);
    }

    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    public static int getMaxDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(5);
    }

    public static String getFirstDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        cal.set(5, 1);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
    }

    public static int getDayOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(6);
    }

    public static int getDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(6);
    }

    public static int getDayOfWeek(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(7);
    }

    public static int getDayOfMonth(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(5);
    }

    public static int getDayOfYear(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(date));
        return cal.get(6);
    }

    public static String getCurrentDateTime(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public static String toString(Date date) {
        if (date == null)
            return "";
        else
            return (new SimpleDateFormat("yyyy-MM-dd")).format(date);
    }

    public static String toDateTimeString(Date date) {
        if (date == null)
            return "";
        else
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    public static String toString(Date date, String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(date);
    }

    public static String toString(Date date, String format,Locale language) {
        SimpleDateFormat t = new SimpleDateFormat(format,language);
        return t.format(date);
    }

    public static String toTimeString(Date date) {
        if (date == null)
            return "";
        else
            return (new SimpleDateFormat("HH:mm:ss")).format(date);
    }

    public static int compare(String date1, String date2) {
        return compare(date1, date2, "yyyy-MM-dd");
    }

    public static int compareTime(String time1, String time2) {
        return compareTime(time1, time2, "HH:mm:ss");
    }

    public static int compare(String date1, String date2, String format) {
        Date d1 = parse(date1, format);
        Date d2 = parse(date2, format);
        return d1.compareTo(d2);
    }

    public static int compareTime(String time1, String time2, String format) {
        String arr1[] = time1.split(":");
        String arr2[] = time2.split(":");
        if (arr1.length < 2)
            throw new RuntimeException("\u9519\u8BEF\u7684\u65F6\u95F4\u503C:" + time1);
        if (arr2.length < 2)
            throw new RuntimeException("\u9519\u8BEF\u7684\u65F6\u95F4\u503C:" + time2);
        int h1 = Integer.parseInt(arr1[0]);
        int m1 = Integer.parseInt(arr1[1]);
        int h2 = Integer.parseInt(arr2[0]);
        int m2 = Integer.parseInt(arr2[1]);
        int s1 = 0;
        int s2 = 0;
        if (arr1.length == 3)
            s1 = Integer.parseInt(arr1[2]);
        if (arr2.length == 3)
            s2 = Integer.parseInt(arr2[2]);
        if (h1 < 0 || h1 > 23 || m1 < 0 || m1 > 59 || s1 < 0 || s1 > 59)
            throw new RuntimeException("\u9519\u8BEF\u7684\u65F6\u95F4\u503C:" + time1);
        if (h2 < 0 || h2 > 23 || m2 < 0 || m2 > 59 || s2 < 0 || s2 > 59)
            throw new RuntimeException("\u9519\u8BEF\u7684\u65F6\u95F4\u503C:" + time2);
        if (h1 != h2)
            return h1 <= h2 ? -1 : 1;
        if (m1 == m2) {
            if (s1 == s2)
                return 0;
            else
                return s1 <= s2 ? -1 : 1;
        } else {
            return m1 <= m2 ? -1 : 1;
        }
    }

    public static boolean isTime(String time) {
        try {
            String arr[];
            arr = time.split(":");
            if (arr.length < 2)
                return false;
            int h;
            int m;
            int s;
            h = Integer.parseInt(arr[0]);
            m = Integer.parseInt(arr[1]);
            s = 0;
            if (arr.length == 3)
                s = Integer.parseInt(arr[2]);
            return h >= 0 && h <= 23 && m >= 0 && m <= 59 && s >= 0 && s <= 59;
        } catch (Exception e) {
            return false;
        }

    }

    public static boolean isDate(String date) {
        try {
            String arr[];
            arr = date.split("-");
            if (arr.length < 3)
                return false;
            int y;
            int m;
            int d;
            y = Integer.parseInt(arr[0]);
            m = Integer.parseInt(arr[1]);
            d = Integer.parseInt(arr[2]);
            return y >= 0 && m <= 12 && m >= 0 && d >= 0 && d <= 31;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 是否同一个月
     *
     * @param date1
     * @param date2
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
    }

    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int t = cal.get(7);
        return t == 7 || t == 1;
    }

    public static boolean isWeekend(String str) {
        return isWeekend(parse(str));
    }

    public static Date parse(String str) {
        if (StringUtils.isEmpty(str))
            return null;
        try {
            return (new SimpleDateFormat("yyyy-MM-dd")).parse(str);
        } catch (ParseException e) {
            return null;
        }
    }
    public static  Date parseFromExcel(String str) {
        if(EmptyUtil.isEmpty(str)){
            return null;
        }
        try {
            if(str.indexOf("-")!=-1){
                return (new SimpleDateFormat("yyyy-MM-dd")).parse(str);
            }
            if(str.indexOf("/")!=-1){
                return (new SimpleDateFormat("yyyy/MM/dd")).parse(str);
            }
            if(StringUtils.isNumeric(str)){
                Calendar c = new GregorianCalendar(1900,0,-1);
                return addDay(c.getTime(),Integer.parseInt(str));
            }
            return (new SimpleDateFormat("yyyyMMdd")).parse(str);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Date parse(String str, String format) {
        if (StringUtils.isEmpty(str))
            return null;
        try {
            SimpleDateFormat t = new SimpleDateFormat(format);
            return t.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseDateTime(String str) {
        if (StringUtils.isEmpty(str))
            return null;
        if (str.length() <= 10)
            return parse(str);
        try {
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDateTime(String str, String format) {
        if (StringUtils.isEmpty(str))
            return null;
        try {
            SimpleDateFormat t = new SimpleDateFormat(format);
            return t.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date addMinute(Date date, int count) {
        return new Date(date.getTime() + 60000L * count);
    }

    public static Date addHour(Date date, int count) {
        return new Date(date.getTime() + 0x36ee80L * count);
    }

    public static Date addDay(Date date, int count) {
        return new Date(date.getTime() + 0x5265c00L * count);
    }

    public static Date addWeek(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(3, count);
        return c.getTime();
    }

    public static Date addMonth(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, count);
        return c.getTime();
    }

    public static Date addYear(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(1, count);
        return c.getTime();
    }

    public static String toDisplayDateTime(String date) {
        if (StringUtils.isEmpty(date))
            return null;
        if (isDate(date))
            return toDisplayDateTime(parse(date));
        try {
            Date d;
            SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            d = t.parse(date);
            return toDisplayDateTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return "不是标准格式时间!";
        }
    }

    public static String toDisplayDateTime(Date date) {
        long minite = (System.currentTimeMillis() - date.getTime()) / 60000L;
        if (minite < 60L)
            return toString(date, "MM-dd") + " " + minite + "分钟前";
        if (minite < 1440L)
            return toString(date, "MM-dd") + " " + minite / 60L + "小时前";
        else
            return toString(date, "MM-dd") + " " + minite / 1440L + "天前";
    }

    public static String getSpacingTimeStr(Date date) {
        long second = (System.currentTimeMillis() - date.getTime()) / 1000L;
        if (second < 60L) {
            return second + "秒前";
        }
        if (second < 3600L) {
            return second / 60L + "分钟前";
        }
        if (second < 86400L) {
            return second / 3600L + "小时前";
        }
        if (second < 2592000L) {
            return second / 86400L + "天前";
        }
        if (second < 31536000L) {
            return second / 2592000L + "月前";
        } else {
            return second / 31536000L + "年前";
        }
    }

    /**
     * 根据给予的时间计算，显示一定格式的时间
     * @param date
     * @return
     */
    public static String getLastDate(Date date) {
        long second = (System.currentTimeMillis() - date.getTime()) / 1000L;
        if (second < 60L) {
            return second + "秒前";
        }
        if (second < 3600L) {
            return second / 60L + "分钟前";
        }
        if (second < 86400L) {
            return second / 3600L + "小时前";
        }
        if (second < 2592000L) {
            return second / 86400L + "天前";
        }
        if (second < 31536000L) {
            return format(date,"MM月dd日");
        } else {
            return format(date,"yyyy年MM月dd日");
        }
    }

    /**
     * 按年月日创建文件夹
     */
    public static String getDatePath() {
        Calendar date = Calendar.getInstance();
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH) + 1;
        int year = date.get(Calendar.YEAR);
        return "/" + year + "/" + month + "/" + day;
    }

    /***
     * 根据日期获得一周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {

        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());

        return c.getTime();
    }

    /***
     * 根据日期获得一月的第一天
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getFirstDayOfMonth(Date date) throws ParseException {

        Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH,1);

        return ClearDate(c.getTime());
    }

    /***
     * 根据日期获得当前月的第一天
     *
     * @param
     * @return
     * @throws ParseException
     */
    public static Date getFirstDayOfMonth() throws ParseException {
        return getFirstDayOfMonth(new Date());
    }

    /***
     * 获取一天的凌晨0点时间
     *
     * @param date 要获取的时间
     * @return Date
     */
    public static Date getInitTime(Date date) {

        Calendar ca = Calendar.getInstance();

        ca.setTime(date);

        Calendar initDate = new GregorianCalendar(ca
                .get(Calendar.YEAR), ca.get(Calendar.MONTH),
                ca.get(Calendar.DATE), 0, 0, 0);

        return initDate.getTime();

    }

    /**
     * 获取时间差(计算到分钟)
     *
     * @param startTime
     * @param endTime
     * @return
     * @author chenjiajun
     */
    public static String getMinByDate(Date startTime, Date endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//
        long diff = endTime.getTime() - startTime.getTime();//结束时间减去开始时间
        long min = diff / nm;//计算差多少分钟
        String mins = min + "";//得到分钟
        return mins;
    }

    /**
     * 获取指定日期的0点0分0秒的date
     *
     * @param date
     * @return
     * @author 陈燊俊
     */
    public static Date getCleanDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得当日某小时0分0秒的date
     *
     * @param hour
     * @return
     * @author 陈燊俊
     */
    public static Date getCleanHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();

    }

    public static boolean betWeen(Date source, Date startDate, Date endDate) {
        if ((startDate.before(source)||startDate.equals(source)) && endDate.after(source)) {
            return true;
        }
        return false;
    }

    /**
     * 根据给予的pattern，格式化日期到字符串格式
     * pattern主要元素 yyyy MM dd HH mm ss
     * 例：yyyy年MM月dd日
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        if(EmptyUtil.isEmpty(date)){
            return "";
        }
        return format.format(date);
    }
    /**
     * 根据给予的pattern，格式化日期到字符串格式
     * pattern主要元素 yyyy MM dd HH mm ss
     * 例：yyyy年MM月dd日
     *
     * @param dateStr
     * @param fromPattern
     * @param toPattern
     * @return
     */
    public static String formatStringDate(String dateStr, String fromPattern,String toPattern) {
        Date date = parse(dateStr,fromPattern);
        return format(date,toPattern);
    }

    /**
     * 清除时分秒
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date ClearDate(Date date) throws ParseException {
        //时分秒清零
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        date=sdf.parse(sdf.format(date));
        return date;
    }

    public static String getTimeBetweenDates(Date startTime,Date endTime){
        String str="";
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long diff = endTime.getTime() - startTime.getTime();//结束时间减去开始时间
        long day = diff / nd;//计算差多少天
        long hour = (diff%nd) / nh;//去除天后计算差多少小时
        str= String.valueOf(day)+"天"+hour+"小时";
        return str;
    }

    /**
     * 通过开始时间和星期几查询当前时间对应当前周中某一天的时间
     * 按中国星期周算法，星期一默认第一天
     * @param startTime
     * @param weekday 星期一传1，对应后面依次2(二)，3(三)，4(四)，5(五)，6(六)，7(日)
     * @return
     */
    public static Date getDateByWeekday(Date startTime,Integer weekday){
        int[] weekdays={Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
        weekday=weekdays[weekday-1];
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);//设星期一为第一天（系统默认星期天为第一天）
        c.setTime(startTime);
        c.set(Calendar.DAY_OF_WEEK, weekday);
        return c.getTime();//获取当前学年第一周的时间
    }

    /**
     * 获得某个日期周一到周日的日期列表
     * @param date  待查询的日期字符串
     * @param isChina 是否按国内的星期格式
     * @return 周一到周日的日期字符串列表
     * @throws ParseException
     */
    public static List<String> getWeekDays(Date date,boolean isChina) throws Exception {
        List<String> list  = new ArrayList<String>();
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(date);
        int currentYear=c.get(Calendar.YEAR);
        int weekIndex = c.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek==1&&isChina){
            c.add(Calendar.DAY_OF_MONTH,-1);
            list=getWeekDays(c.getTime(),isChina);
        }
        else{
            c.setWeekDate(currentYear, weekIndex, 1);
            for(int i=1;i<=7;i++){
                c.add(Calendar.DATE, 1);
                String date_str = DateUtil.toString(c.getTime());
                list.add(date_str);
            }
        }
        return list;
    }

    /**
     * 自定义时间
     * @param date 基础时间
     * @param year 年份（2000）
     * @param month 月份(0-11)
     * @param day 日期(1-31)
     * @param hour 小时()
     * @param minute  分钟()
     * @param second  秒()
     * @return
     */
    public static Date setDate(Date date,Integer year,Integer month,Integer day,Integer hour,Integer minute,Integer second ){
        Calendar calendar= Calendar.getInstance();
        if(!EmptyUtil.isEmpty(date)){
            calendar.setTime(date);
        }
        if(year!=null){
            calendar.set(Calendar.YEAR,year);
        }
        if(month!=null){
            calendar.set(Calendar.MONTH,month);
        }
        if(day!=null){
            calendar.set(Calendar.DATE,day);
        }
        if(hour!=null){
            calendar.set(Calendar.HOUR_OF_DAY,hour);
        }
        if(minute!=null){
            calendar.set(Calendar.MINUTE,minute);
        }
        if(second!=null){
            calendar.set(Calendar.SECOND,second);
        }
        return calendar.getTime();
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }


    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 根据开始时间、结束时间、间隔时长 计算
     * @param startTime
     * @param endTime
     * @param slot
     * @return
     * @throws ParseException
     */
    public static List<String> getDateSlot(String startTime, String endTime, Integer slot) throws ParseException {
        List<String> currentList = new ArrayList<>();
        List<String> hoursBetweenTwoDate = DateUtil.getHoursBetweenTwoDate(startTime, endTime, slot);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        for (int i = 0; i < hoursBetweenTwoDate.size() - 1; i++) {
            currentList.add(hoursBetweenTwoDate.get(i) + "-" + hoursBetweenTwoDate.get(i + 1));
        }
        String time = currentList.get(currentList.size() - 1);
        String[] split = time.split("-");
        String s = split[0];
        String s2 = split[1];
        long time1 = simpleDateFormat.parse(s).getTime();
        long time2 = simpleDateFormat.parse(s2).getTime();
        long time3 = time1 - time2;
        long current = time3 / 1000 / 60;
        if (slot + current > 0) {
            currentList.remove(currentList.size() - 1);
        }
        return currentList;
    }

    /**
     * 转为Date
     * @param time
     * @return
     */
    public static Date toDate(String time){
        Date date = null;
        try {
            date = date_sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
