package com.basic.core.birt;

import java.io.Serializable;

public class BirtDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1554009516988798920L;

	private String templateName;

	private String reportName;

	private String format;

	private String data;

	private long id;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
