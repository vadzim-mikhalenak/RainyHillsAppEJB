package com.rainyhills.controller;

import com.rainyhills.client.model.CalculateWaterAmountRequest;
import com.rainyhills.client.model.CalculateWaterAmountResponse;
import com.rainyhills.domain.service.RainyHillsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * @author Vadzim Mikhalenak.
 */
@Stateless
@Path("/rainyhills")
@Produces(MediaType.APPLICATION_XML)
public class RainyHillsController {

	private static final Logger logger = LoggerFactory.getLogger(RainyHillsService.class);

	@EJB
	private RainyHillsService rainyHillsService;

	@POST
	@Path("/calculatewater")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response calculateWaterAmount(CalculateWaterAmountRequest request) {

		if (request.getHills() == null) {
			logger.info("Illegal input data. Hills couldn't be null.");
			return Response
					.status(BAD_REQUEST)
					.entity(new CalculateWaterAmountResponse("Hills should not be null"))
					.build();
		}

		logger.debug("Validate hills for negative data");
		for (final int hill : request.getHills()) {
			if (hill < 0) {
				logger.debug("Negative value was found");
				return Response
						.status(BAD_REQUEST)
						.entity(new CalculateWaterAmountResponse("Hills should not contain negative value"))
						.build();
			}
		}

		logger.info("Calculate amount of water for: {}", Arrays.toString(request.getHills()));

		Integer waterAmount = rainyHillsService.calculateRemainingWater(request.getHills());

		return Response
				.ok(new CalculateWaterAmountResponse(waterAmount))
				.build();
	}

}
