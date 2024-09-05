package com.gwais.sk_users.response;

import java.time.LocalDateTime;

public class ApiMetaData {
	
    private LocalDateTime requestDate;
    private long responseTimeInMilliSeconds;
    private long recordCount;
    private String clientIp;
    private String serverIp;

    public ApiMetaData() {
    }

    public ApiMetaData(LocalDateTime requestDate, long responseTime, long recordCount) {
        this.requestDate = requestDate;
        this.setResponseTimeInMilliSeconds(responseTime);
        this.recordCount = recordCount;
    }

    public ApiMetaData(LocalDateTime requestDate, long responseTime, long recordCount,
    		String clientIp, String serverIp) {
        this.requestDate = requestDate;
        this.setResponseTimeInMilliSeconds(responseTime);
        this.recordCount = recordCount;
        this.setClientIp(clientIp);
        this.setServerIp(serverIp);
    }

	public LocalDateTime getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public long getResponseTimeInMilliSeconds() {
		return responseTimeInMilliSeconds;
	}

	public void setResponseTimeInMilliSeconds(long responseTimeInMilliSeconds) {
		this.responseTimeInMilliSeconds = responseTimeInMilliSeconds;
	}
}