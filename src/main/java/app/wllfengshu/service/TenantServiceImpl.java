package app.wllfengshu.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private TenantDao tenantDao;
	
	@Override
	public String getTenants(String sessionId,String user_id,String domain,String company_name, int pageNo, int pageSize) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		List<Tenant> tenants = tenantDao.getTenants(domain,company_name,(pageNo-1)*pageSize,pageSize);
		responseMap.put("data", tenants);
		responseMap.put("count", tenantDao.getTenantsCount(domain,company_name));
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String addTenant(String tenant,String sessionId,String user_id) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String tenant_id = UUID.randomUUID().toString();
		String tenantTMuser_id  = UUID.randomUUID().toString();//租户管理员的user_id
		AuthUtil.instance.checkUserInfo(sessionId, user_id);
		JSONObject tenantJson = null;
		Tenant tenantTemp=null;
		JSONObject userJson =new JSONObject();
		String tenantTMUsername=null;
		try{
			tenantJson=JSONObject.fromObject(tenant);
			tenantTMUsername=tenantJson.getString("username");
			tenantJson.remove("username");
			tenantTemp=(Tenant) JSONObject.toBean(tenantJson,Tenant.class);
			tenantTemp.setId(tenant_id);
			tenantTemp.setStatus(1);
			//在创建租户的时候，必须在user表中创建租户管理员
			userJson.put("id", tenantTMuser_id);
			userJson.put("email", "默认值");
			userJson.put("sex", "默认值");
			userJson.put("last_activity_time", sdf.format(new Date(System.currentTimeMillis())));
			userJson.put("login_name", "默认值");
			userJson.put("domain", tenantTemp.getDomain());
			userJson.put("phone", "默认值");
			userJson.put("status", 1);
			userJson.put("tenant_id", tenant_id);
			userJson.put("org_id", "1");
			userJson.put("username", tenantTMUsername);
			userJson.put("password", "Aa123456");
			userJson.put("sys", "tm");
			AuthUtil.instance.addUserInfo(sessionId,userJson);
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
