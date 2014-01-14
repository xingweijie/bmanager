package backstageManager.controllers.returnObject;

import com.alibaba.fastjson.JSON;

public class ManagerLoginIdentifyReturn implements ReturnObject {
	private boolean success;
	private String reason;

	public ManagerLoginIdentifyReturn(boolean success, String reason) {
		this.success = success;
		this.reason = reason;
	}

	@Override
	public String toJsonStr() {
		String jsonStr = JSON.toJSONString(this);
		return jsonStr;
	}

	@Override
	public boolean isSuccess() {
		return success;
	}

	@Override
	public String getReason() {
		return reason;
	}
}
