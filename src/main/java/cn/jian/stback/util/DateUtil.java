package cn.jian.stback.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by zq on 2016/11/30.
 */
public class DateUtil {

    /**
     * 获取当日日期字符串
     * @return
     */
    public static String getToday(){
        return LocalDate.now().toString();
    }


    /**
     * 获取年月string
     * @return
     */
    public static String getMonthStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        String time = df.format(new Date());
        return (time);
    }

    /**
     * 时间戳转Date (毫秒)
     * @return
     */
    public static Date toDate(long timestamp){
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String source = sdf.format(timestamp);
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * <p class="detail">
     * 功能：获取日期yyyyMMdd
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static String getDateStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(new Date());
        return (time);
    }


    /**
     *
     * <p class="detail">
     * 功能：获取天数（1970年）
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static int  getDays() {
        Calendar cal = Calendar.getInstance();
        cal.set(1970, 0, 1, 0, 0, 0);
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        long intervalMilli = now.getTimeInMillis() - cal.getTimeInMillis();
        return  (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     *
     * <p class="detail">
     * 功能：获取日期yyyyMMdd
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static String getDateStrYmd(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time ;
        if (date==null){
            time = df.format(new Date());
        }else{
            time = df.format(date);
        }

        return (time);
    }

    /**
     *
     * @return
     */
    public static String getDateStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(date);
        return (time);
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间日期yyyyMMddHHmmss
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static String getDateTimeStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(new Date());
        return (time);
    }

    /**
     * 返回精确到毫秒的格式化时间 20160828090501555
     * @return
     */
    public static String getDateTimeStrMilliSecond(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间日期yyyy-MM-dd HH:mm:ss
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String getTime(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public static String getMinusMinutesTime(Integer minutes){
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(minutes);
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }

    /**
     * 获取时间日期yyyy-MM-dd
     * @return
     */
    public static String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     *
     * <p class="detail">
     * 功能：获取date对象
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param time  yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date getTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(time==null || time.equals("")){
            return null;
        }
        try {
            return df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * <p class="detail">
     * 功能：获取date对象
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param date yyyy-MM-dd
     * @return
     */
    public static Date getDate(String date) {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if(date==null || date.equals("")){
            return null;
        }
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间戳，毫秒
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param date
     * @return
     */
    public static Long getTimeLong(String date){
        Date d = getTime(date);
        return d.getTime();
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间戳，毫秒
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @param date
     * @return
     */
    public static Long getTimeLong(Date date){
        return date.getTime();
    }

    /**
     *
     * <p class="detail">
     * 功能：获取时间戳，秒
     * </p>
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    public static Integer getTimeLong(){
        return (int) (System.currentTimeMillis()/1000);
    }

    /**
     * 获取本周第一天
     * @return
     */
    public static String getWeekStart(){
        Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        String s = df.format(cal.getTime());
        return s+" 00:00:01";
    }

    /**
     * 获取本周最后一天
     * @return
     */
    public static String getWeekEnd(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);		//增加一个星期，才是我们中国人理解的本周日的日期
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        String s = df.format(cal.getTime());
        return s+" 23:59:59";
    }

    // 获得当前日期与本周日相差的天数
    private static int getMondayPlus(Date gmtCreate) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(gmtCreate);
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            return -6;
        } else {
            return 1 - dayOfWeek;
        }
    }

    // 获得下周星期一的日期
    public static Date getNextMonday(Date gmtCreate) {
        int mondayPlus = getMondayPlus(gmtCreate);
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        return monday;
    }

    /**
     * 获得n天的时间  n为负数就是前n天时间
     * @param days
     * @return
     * @throws ParseException
     */
    public static Date getStatetime(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, days );
        return c.getTime();

    }



    /**
     * 判断当前时间是否为周一
     *
     * @param date
     * @return
     */
    public static boolean isMonday(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        return dayOfWeek == 1;
    }

    /**
     * 判断两个字符串时间是否同一天
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(String date1,String date2){
        return date1.substring(0,10).equals(date2.substring(0,10));
    }


    public static String getTimeChina(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
        return df.format(new Date());
    }

    public static boolean equals(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }

    /**
     * Date 转 LocalDate
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * Date 转 LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime 转 Date
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 计算时间差
     * @param start
     * @param end
     * @return
     */
    public static Duration timeDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end);
    }

    /**
     * 获取本日是第几周
     * @return
     */
    public static int getWeeks()  {
            Date  date=new Date();
            return getWeeks(date);
    }


    /**
     * 获取本日是第几周
     * @return
     */
    public static int getWeeks(Date date)  {
        if(date==null){
            date=new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }



    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param smallDate
     * @param bigDate
     * @return
     */
    public static int differentDaysByMillisecond(Date smallDate,Date bigDate)
    {
        int days = (int) ((bigDate.getTime() - smallDate.getTime()) / (1000*3600*24));
        return days;
    }


    /**
     * 判断当前时间是否在时间段内
     * @param start
     * @param end
     * @return
     */
    public static boolean isbetweenTime(String start,String end){
        SimpleDateFormat df = new SimpleDateFormat("HHmmss");
        String curr = df.format(new Date());
        return Integer.parseInt(curr) > Integer.parseInt(start) && Integer.parseInt(curr) < Integer.parseInt(end);
    }



    /**
     *
     * @param date 当前时间
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     * @return
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
        }
        return cal.getTime();
    }


    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
//            System.out.println("判断day1 : " + date1);
//            System.out.println("判断day2 : " + date2);
//            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }



    /**
     *
     * <p class="detail">
     * 功能：获取当前日期的天数，从1970年起
     * </p>

     */
    public static Long getCurrentDay() {

        return (System.currentTimeMillis()+8*60*60*1000) / (24 * 60 * 60 * 1000) ;
    }

    /**
     * 获取日期的小时
     * @param date 日期
     * @return 小时
     */
    public static int getHours(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
       return c.get(Calendar.HOUR_OF_DAY);
    }



    /**
     *得到当天好还剩多少秒
     * @date 2016年7月15日
     * @author <a href="mailto:1435290472@qq.com">zq</a>
     * @return
     */
    /**
     * 获取到当天结束还有多少毫秒
     * @return
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        Calendar midnight=Calendar.getInstance();
        midnight.setTime(currentDate);
        midnight.add(Calendar.DAY_OF_MONTH,1);
        midnight.set(Calendar.HOUR_OF_DAY,0);
        midnight.set(Calendar.MINUTE,0);
        midnight.set(Calendar.SECOND,0);
        midnight.set(Calendar.MILLISECOND,0);
        Integer seconds=(int)((midnight.getTime().getTime()-currentDate.getTime())/1000);
        return seconds;
    }


    /**
     * 获得前几个月
     * month 上1个月就是1  下一个月是-1
     * @return
     */
    public static String getLastMonth(int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -month);
        Date m = c.getTime();
        return format.format(m);
    }


    /**
     *
     * <p class="detail">
     * 功能：获取当前日期周数，从1970年起
     * </p>
     */
    public static Long getCurrentWeek() {
        return (getCurrentDay()+4)/7;
    }



    /**
     *
     * <p class="detail">
     * 功能：获取当前日期周数，从1970年起
     * </p>
     */
    public static Date getAfterDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return  cal.getTime();
    }

    /**
     *
     * <p class="detail">
     * 功能：获取当前日期周数，从1970年起
     * </p>
     */
    public static Date getAfterDays(Date date,int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return  cal.getTime();
    }



    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }




    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, String begin1, String end2) {

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(begin1);
            endTime = df.parse(end2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar date = Calendar.getInstance();
        date.setTime(now);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否在周一到周五
     * @return
     */
    public static boolean isInWeekDays() {

     SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
     String format = sd.format(new Date());
     SimpleDateFormat sdw = new SimpleDateFormat("E");
     Date d = null;
     try {
         d = sd.parse(format);
     } catch (ParseException e) {
         e.printStackTrace();
     }
     String format1 = sdw.format(d);
     if (format1.equalsIgnoreCase("星期一")||format1.equalsIgnoreCase("星期二")||format1.equalsIgnoreCase("星期三")||format1.equalsIgnoreCase("星期四")||format1.equalsIgnoreCase("星期五")){
         return  true;
     }
     return false;
    }

    /**
     * 判断时间是周几
     *
     * @param date
     * @return
     * @author xupx
     */
    public static int getDamaiDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            case Calendar.SUNDAY:
                return 7;
            default:
                return 0;
        }
    }


    /**
     * 获取年月string yyyy-MM
     * @return
     */
    public static String getMonthStrYM(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String time = df.format(new Date());
        return (time);
    }


    /**
     * 获取年月string yyyy-MM
     * @return
     */
    public static String getlastMonthStrYM(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        String time = df.format( cal.getTime());
        return (time);
    }



    public static void main(String[] args) {
        System.out.println(DateUtil.getDateStrYmd(DateUtil.getAfterDays(-1)));
    }
}
