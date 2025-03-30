package cn.jian.stback.common;

public enum RewardType {

	zt("2", "直推"), bb("3", "波比"), bbtj("4", "分红奖励"),hkzt("5", "波比"),;

	private RewardType(String value, String desc) {
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

	public static RewardType getType(String value) {
		for (RewardType level : RewardType.values()) {
			if (level.getValue().equals(value)) {
				return level;
			}
		}
		return null;
	}
}
