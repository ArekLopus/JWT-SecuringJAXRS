package jaxrs;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response testGet() {
		System.out.println("Pinged...");
		return Response.ok(this.sysInfo()).build();
	}
	
	
	private String sysInfo() {
		
		Runtime runtime = Runtime.getRuntime();
		String currentDate = String.format("Server time: %tI:%<tM:%<tS%<tp, %<te-%<tm-%<tY %n", new Date());
		
		String info = currentDate +
				"OS: "+System.getProperty("os.name")+"\n"+
				"Java Runtime: "+System.getProperty("java.runtime.version")+"\n"+
				"VM Name: "+System.getProperty("java.vm.name")+"\n"+
				"VM Vendor: "+System.getProperty("java.vm.vendor")+"\n"+
				"Processors/Cores: "+runtime.availableProcessors()+"\n"+
				"Max Memory: "+runtime.maxMemory()/(1024*1024)+" MB\n" +
				"Total Memory: "+runtime.totalMemory()/(1024*1024)+" MB\n" +
				"Free Memory: "+runtime.freeMemory()/(1024*1024)+" MB\n";
		
		return info;
	}

	
}
