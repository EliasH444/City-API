package com.mycompany.hellorestest;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Root resource (exposed at "myresource" path)
 */
@Singleton
@Path("myresource")
public class CityResource {

    private ArrayList<City> cityList = new ArrayList<>();
    private boolean added = false;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCities() {
        lock.readLock().lock();
        try {
            if (!added) {
                added = true;
                City city;
                for (City b : cityList) {
                    System.out.println(b.getCountry() + "title is " + b.getState());
                }
            }
            return Response.status(Response.Status.OK).entity(cityList).build();
        } finally {
            lock.readLock().unlock();
        }
    }

    @GET
    @Path("/{country}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCityByCountry(@PathParam("country") String country) {
        lock.readLock().lock();
        try {
            for (City city : cityList) {
                if (city.getCountry().equals(country)) {
                    return Response.status(Response.Status.OK).entity(city).build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            lock.readLock().unlock();
        }
    }

    @GET
    @Path("/country/state/{state}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCityByState(@PathParam("state") String state) {
        lock.readLock().lock();
        try {
            for (City city : cityList) {
                if (city.getState().equals(state)) {
                    return Response.status(Response.Status.OK).entity(city).build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            lock.readLock().unlock();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCity(City city) {
        lock.writeLock().lock();
        try {
            if (contains(city)) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("Duplicate Item")
                        .type(MediaType.TEXT_PLAIN).build();
            }
            cityList.add(city);
            return Response.status(Response.Status.OK).build();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private boolean contains(City city) {
        for (City b : cityList) {
            if (b.getCountry().equals(city.getCountry()) && b.getName().equals(city.getName()) && b.getState().equals(city.getState())) {
                return true;
            }
        }
        return false;
    }

    @DELETE
    @Path("/{name}/{country}/{state}/{year}")
    @Consumes(MediaType.APPLICATION_JSON)
    public synchronized Response deleteLocation(@PathParam("name") String name, @PathParam("country") String country, @PathParam("state") String state, @PathParam("year") int year) {
        lock.writeLock().lock();
        try {
            for (int i = 0; i < cityList.size(); i++) {
                City b = cityList.get(i);
                if (b.getName().equals(name) && b.getCountry().equals(country) && b.getState().equals(state) && b.getYear() == year) {
                    cityList.remove(i);
                    return Response.status(Response.Status.OK).build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND).build();

        } finally {
            lock.writeLock().unlock();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCityBeforeDate(@QueryParam("before") int year
    ) {
        List<City> filteredCities = new ArrayList<>();
        for (City b : cityList) {
            if (b.getYear() < year) {
                filteredCities.add(b);
            }
        }
        return Response.status(Response.Status.OK).entity(filteredCities).build();

    }

    @GET
    @Path("/ping/respond")
    public Response ping() {
        String message = "OK";
        return Response.ok().entity(message).build();
    }
}
