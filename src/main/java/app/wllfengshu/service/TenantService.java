package app.wllfengshu.service;

import app.wllfengshu.exception.NotAcceptableException;

public interface TenantService {
	
	public String getTenants(String sessionId,String user_id, int pageNo, int pageSize) throws NotAcceptableException;

	public String addTenant(String tenant,String sessionId,String user_id) throws NotAcceptableException;

	public String getTenant(String tenant_id,String sessionId,String user_id) throws NotAcceptableException;

	public String updateTenant(String tenant,String sessionId,String user_id) throws NotAcceptableException;

	public String deleteTenant(String tenant_id,String sessionId,String user_id) throws NotAcceptableException;
	
}
