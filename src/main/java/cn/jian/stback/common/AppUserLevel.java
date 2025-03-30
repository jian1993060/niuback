package cn.jian.stback.common;

public enum AppUserLevel {
	NO(-1, "未入单"),PT(0, "普通用户"), QX(1, "区县代理"), CS(2, "城市代理"), 
	SJ(3, "省级代理"), Z(4, "总代理");

	private AppUserLevel(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	int value;

	String desc;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
