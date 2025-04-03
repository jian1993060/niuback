package cn.jian.stback.common;

public enum BaseStatus {
	enable("1", "正常"),verify("3", "正常"), disable("2", "未完成");

	private BaseStatus(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	String value;

	String desc;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
