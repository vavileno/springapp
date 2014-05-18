package ru.springtest.app.web.formdata;

public class FilterDataForm {
	
	private String fromDate;
	
	private String toDate;
	
	private String userPattern;
	
	private String page;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getUserPattern() {
		return userPattern;
	}

	public void setUserPattern(String userPattern) {
		this.userPattern = userPattern;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
