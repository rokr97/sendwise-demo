package org.example.sendwise.demo.ws;

import com.nhl.link.rest.DataResponse;
import com.nhl.link.rest.LinkRest;
import com.nhl.link.rest.SimpleResponse;
import io.bootique.linkrest.demo.api.ArticleSubResource;
import io.bootique.linkrest.demo.cayenne.Domain;
import org.example.sendwise.demo.persistent.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	@Context
	private Configuration config;

	@GET
	public DataResponse<Account> getAll(@Context UriInfo uriInfo) {
		// List<Account> accounts =  ObjectSelect.query(Account.class)
        // .where(Painting.NAME.likeIgnoreCase("gi%")).select(context); ;
        // context.deleteObject(picasso);
		return LinkRest.select(Account.class, config).uri(uriInfo).get();
	}

/*
	@GET
	@Path("{domainId}")
	public DataResponse<Domain> getOne(@PathParam("domainId") int id, @Context UriInfo uriInfo) {
		return LinkRest.select(Domain.class, config).byId(id).uri(uriInfo).getOne();
	}

	@POST
	public SimpleResponse create(String data) {

		// 'data' is a single object or an array of objects..

		return LinkRest.create(Domain.class, config).sync(data);
	}

	@PUT
	public SimpleResponse createOrUpdate(String data) {

		// 'data' is a single object or an array of objects... Objects without
		// IDs will be treated as "new". LinkRest will try to locate objects
		// with IDs, and update them if found, or create if not

		return LinkRest.createOrUpdate(Domain.class, config).sync(data);
	}

	*/
/**
	 * A relationship resource for a single domain, that routes all article
	 * requests to a sub-resource. Articles can only be viewed or manipulated in
	 * the context of a single web domain.
	 *//*

	@Path("{domainId}/articles")
	public ArticleSubResource articles(@PathParam("domainId") int domainId) {
		return new ArticleSubResource(config, domainId);
	}
*/
}
