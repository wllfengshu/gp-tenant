package app.wllfengshu.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.wllfengshu.dao.TenantDao;
import app.wllfengshu.entity.Tenant;
import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.util.AuthUtil;
import net.sf.json.JSONObject;

@Service
public class TenantServiceImpl implements TenantService {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private TenantDao tenantDao;
	
	@Override
	public String getTenants(String sessionId,String user_id, int pageNo, int pageSize) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		List<Tenant> tenants = tenantDao.getTenants(pageNo,pageSize);
		responseMap.put("data", tenants);
		responseMap.put("count", tenants.size());
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String addTenant(String tenant,String sessionId,String user_id) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String tenant_id = UUID.randomUUID().toString();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		JSONObject tenantJson = null;
		Tenant tenantTemp=null;
		try{
			tenantJson=JSONObject.fromObject(tenant);
			tenantTemp=(Tenant) JSONObject.toBean(tenantJson,Tenant.class);
			tenantTemp.setId(tenant_id);
		}catch(Exception e){
			throw new NotAcceptableException("数据格式错误");
		}
		tenantDao.addTenant(tenantTemp);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String getTenant(String tenant_id,String sessionId,String user_id) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		Tenant tenant = tenantDao.getTenant(tenant_id);
		responseMap.put("data", tenant);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String updateTenant(String tenant,String sessionId,String user_id) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		JSONObject tenantJson = null;
		Tenant tenantTemp=null;
		try{
			tenantJson=JSONObject.fromObject(tenant);
			tenantTemp=(Tenant) JSONObject.toBean(tenantJson,Tenant.class);
		}catch(Exception e){
			throw new NotAcceptableException("数据格式错误");
		}
		tenantDao.updateTenant(tenantTemp);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String deleteTenant(String tenant_id,String sessionId,String user_id) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		tenantDao.deleteTenant(tenant_id);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}

}
