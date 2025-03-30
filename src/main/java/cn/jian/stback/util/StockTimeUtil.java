package cn.jian.stback.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * 判断美股A股是否开盘
 */
public class StockTimeUtil {

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	static final Set<LocalDate> CN_HOLIDAYS = new HashSet<>();

	static final Set<LocalDate> US_HOLIDAYS = new HashSet<>();

	static {
		// CN
		CN_HOLIDAYS.add(LocalDate.of(2025, 4, 4));
		CN_HOLIDAYS.add(LocalDate.of(2025, 4, 5));
		CN_HOLIDAYS.add(LocalDate.of(2025, 5, 1));
		CN_HOLIDAYS.add(LocalDate.of(2025, 5, 2));
		CN_HOLIDAYS.add(LocalDate.of(2025, 5, 3));
		CN_HOLIDAYS.add(LocalDate.of(2025, 5, 4));
		CN_HOLIDAYS.add(LocalDate.of(2025, 5, 5));
		CN_HOLIDAYS.add(LocalDate.of(2025, 6, 2));
		// US
		US_HOLIDAYS.add(LocalDate.of(2025, 2, 17));
		US_HOLIDAYS.add(LocalDate.of(2025, 4, 18));
		US_HOLIDAYS.add(LocalDate.of(2025, 5, 26));
		US_HOLIDAYS.add(LocalDate.of(2025, 6, 19));
		US_HOLIDAYS.add(LocalDate.of(2025, 7, 4));
		US_HOLIDAYS.add(LocalDate.of(2025, 9, 1));
	}

	public static void main(String[] args) {
		System.out.println(validateCN());
	}

	// A股还有判断是否开盘中

	public static boolean validateCNing() {
		// 将LocalDateTime转换为中国时区（上海时间）
		ZonedDateTime nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
				.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
		if (nowTime.getDayOfWeek().getValue() > 6) {
			return false;
		}
		if (CN_HOLIDAYS.contains(nowTime.toLocalDate())) {
			return false;
		}
		int hour = nowTime.getHour();
		int minute = nowTime.getMinute();

		// 判断是否在开盘时间内（9:30 - 15:00）
		if ((hour > 9 || (hour == 9 && minute >= 30)) && ((hour < 16 || (hour == 15 && minute == 0)))) {
			return true;
		}
		return false;
	}

	public static boolean validateCN() {
		// 将LocalDateTime转换为中国时区（上海时间）
		ZonedDateTime nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
				.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
		if (nowTime.getDayOfWeek().getValue() > 6) {
			return false;
		}
		if (CN_HOLIDAYS.contains(nowTime.toLocalDate())) {
			return false;
		}
		int hour = nowTime.getHour();
		int minute = nowTime.getMinute();

		// 判断是否在上午开盘时间（9:30 - 11:30）
		if ((hour == 9 && minute >= 30) || (hour == 10) || (hour == 11 && minute <= 30)) {
			return true;
		}

		// 判断是否在下午开盘时间（13:00 - 15:00）
		if ((hour == 13) || (hour == 14) || (hour == 15 && minute == 0)) {
			return true;
		}

		return false;
	}

	public static boolean validateUs() {
		ZonedDateTime nowTime = LocalDateTime.now().atZone(ZoneId.systemDefault())
				.withZoneSameInstant(ZoneId.of("America/New_York"));
		// 获得美国时间
		if (nowTime.getDayOfWeek().getValue() > 6) {
			return false;
		}
		if (US_HOLIDAYS.contains(nowTime.toLocalDate())) {
			return false;
		}
		int hour = nowTime.getHour();
		int minute = nowTime.getMinute();
		// 判断是否在开盘时间内（9:30 - 16:00）
		if ((hour > 9 || (hour == 9 && minute >= 30)) && hour < 16) {
			return true;
		}

		return false;
	}
}
