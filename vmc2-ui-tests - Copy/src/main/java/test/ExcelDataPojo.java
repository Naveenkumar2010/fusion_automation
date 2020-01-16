package test;

public class ExcelDataPojo {

	/****
	 * Object Repository
	 */
	private String xPathIdType;
	private String xpathIdValue;
	private String action;
	private String actionData;
	private String preRequestType;
	private String preRequestData;
	private String results;
	private String id;
	private String scenario;
	private String description;

	public ExcelDataPojo() {
		super();
	}

	public ExcelDataPojo(String xpathIdValue, String xPathIdType, String action, String actionData,
			String preRequestType, String preRequestData, String id, String scenario, String description,
			String results) {
		super();

		this.xPathIdType = xPathIdType;
		this.xpathIdValue = xpathIdValue;
		this.action = action;
		this.actionData = actionData;
		this.preRequestType = preRequestType;
		this.preRequestData = preRequestData;
		this.id = id;
		this.scenario = scenario;
		this.description = description;
		this.results = results;

	}

	public String getxPathIdType() {
		return xPathIdType;
	}

	public void setxPathIdType(String xPathIdType) {
		this.xPathIdType = xPathIdType;
	}

	public String getXpathIdValue() {
		return xpathIdValue;
	}

	public void setXpathIdValue(String xpathIdValue) {
		this.xpathIdValue = xpathIdValue;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionData() {
		return actionData;
	}

	public void setActionData(String actionData) {
		this.actionData = actionData;
	}

	public String getPreRequestType() {
		return preRequestType;
	}

	public void setPreRequestType(String preRequestType) {
		this.preRequestType = preRequestType;
	}

	public String getPreRequestData() {
		return preRequestData;
	}

	public void setPreRequestData(String preRequestData) {
		this.preRequestData = preRequestData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ExcelData [xPathIdType=" + xPathIdType + ", xpathIdValue=" + xpathIdValue + ", action=" + action
				+ ", actionData=" + actionData + ", preRequestType=" + preRequestType + ", preRequestData="
				+ preRequestData + ", id=" + id + ",scenario=" + scenario + ", description=" + description
				+ ", results=" + results + "]";
	}

}
