package backend.service;

import backend.db.Broker;
import backend.db.ConnectionManager;
import backend.main.Reason;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("service")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class PermissionService {


    /*@POST
    @Path("/addSamisClientApp/{LimitOfRequests}/{FirstDigitIndicator}/{InitialValue}")
    public String AddSamisClientApp(@PathParam("LimitOfRequests") int LimitOfRequests, @PathParam("FirstDigitIndicator") int FirstDigitIndicator,
                                    @PathParam("InitialValue") long initVal) throws Exception {

        return "";
    }*/


    @GET
    @Path("/getUserData")
    public Response getUsers(
            @QueryParam("id") long id ) {

        try {

            return Response
                    .status(200)
                    .entity(id + "-"+Broker.getPermission(ConnectionManager.getFullStopConnection(),id)).build();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;



    }

    @GET
    @Path("/setUserData")
    public Response getUsers(
            @QueryParam("id") long id,
            @QueryParam("isPermanent") boolean isPermanent,
            @QueryParam("reason") String textedReason,
            @QueryParam("place") String place,
            @QueryParam("attached") String attached) {

        Reason reason= Reason.toReason(textedReason, isPermanent,attached);
        boolean needRevision = false;

        //some code about needRevision


        try {
            Broker.setPossiblePemission(ConnectionManager.getFullStopConnection(),id,isPermanent,reason.getTextedReason(),place,reason.getDate1(),reason.getDate2(),attached ,needRevision);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }


        return Response
                .status(200)
                .entity(true).build();
    }

    @GET
    @Path("/print")
    public Response print(
            @QueryParam("id") long id ) {

            System.out.println(" print works "+ id);
            return Response
                    .status(200)
                    .entity("print "+id).build();



    }
}
