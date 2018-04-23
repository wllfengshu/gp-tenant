package app.wllfengshu.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.service.TenantService;
import app.wllfengshu.util.LogUtils;

@Controller
@Path("/tenant")
public class TenantRest {
	
	@Autowired
	private TenantService tenantService ;
    
	/**
	 * @title 查询所有租户信息
	 * @param sessionId
	 * @param request
	 * @param response
	 * @return
	 */
    @GET
    public Response getTenants(@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@QueryParam("pageNo") int pageNo,@QueryParam("pageSize") int pageSize,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=tenantService.getTenants(sessionId,user_id,pageNo,pageSize);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# TenantRest-getTenants,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 添加租户信息
     * @param Tenant 租户信息数据
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @POST
    public Response addTenant(String tenant,
    		@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=tenantService.addTenant(tenant,sessionId,user_id);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# TenantRest-addTenant,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 查询租户详情
     * @param Tenant_id 租户ID
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/{tenant_id}/")
    public Response getTenant(@PathParam("tenant_id")String tenant_id,
    		@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=tenantService.getTenant(tenant_id,sessionId,user_id);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# TenantRest-getTenant,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 修改租户信息
     * @param Tenant 租户信息数据
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @PUT
    @Path("/{tenant_id}/")
    public Response updateTenant(String tenant,
    		@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=tenantService.updateTenant(tenant,sessionId,user_id);//前端的Tenant中必须包含Tenant_id
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# TenantRest-updateTenant,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 删除租户信息
     * @param Tenant_id 租户ID
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @DELETE
    @Path("/{tenant_id}/")
    public Response deleteTenant(@PathParam("tenant_id")String tenant_id,
    		@HeaderParam(value="sessionId") String sessionId,
    		@HeaderParam(value="user_id") String user_id,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=tenantService.deleteTenant(tenant_id,sessionId,user_id);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# TenantRest-deleteTenant,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
}
