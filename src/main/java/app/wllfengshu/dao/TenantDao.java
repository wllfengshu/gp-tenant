package app.wllfengshu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import app.wllfengshu.entity.Tenant;

@Repository
public interface TenantDao {
	public List<Tenant> getTenants(@Param("domain")String domain, @Param("company_name")String company_name, int pageNo, int pageSize);

	public void addTenant(@Param("tenant")Tenant tenant);

	public Tenant getTenant(@Param("id")String id);

	public void updateTenant(@Param("tenant")Tenant tenant);

	public void deleteTenant( @Param("id")String id);
	
}
