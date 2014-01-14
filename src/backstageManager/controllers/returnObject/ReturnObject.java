package backstageManager.controllers.returnObject;

public interface ReturnObject {

	public boolean isSuccess();

	public String getReason();

	public String toJsonStr();

}
