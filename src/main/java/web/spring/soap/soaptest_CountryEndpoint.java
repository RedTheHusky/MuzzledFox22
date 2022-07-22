package web.spring.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

//@Endpoint
public class soaptest_CountryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private soaptest_CountryRepository countryRepository;

    //@Autowired
    public soaptest_CountryEndpoint(soaptest_CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    //@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    //@ResponsePayload
    public soaptest_getCountryResponse getCountry(@RequestPayload soaptest_getCountryRequest request) {
        soaptest_getCountryResponse response = new soaptest_getCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));

        return response;
    }
}