package backstageManager.controllers.processor;

import backstageManager.controllers.returnObject.ReturnObject;

public interface Processor {

	public ReturnObject processForGet();

	public ReturnObject processForPost();
}
