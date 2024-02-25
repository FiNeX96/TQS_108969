package integration;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolverService;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressResolverIT {


    private AddressResolverService resolver;
    private ISimpleHttpClient httpClient;


    @BeforeEach
    public void init(){

       httpClient = new TqsBasicHttpClient();
       resolver = new AddressResolverService(httpClient);

    }


    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        String url = "https://www.mapquestapi.com/geocoding/v1/reverse?key=fjpDDX0NAqWObtm30nW1w2njohV84QWs&location=40.63436%2C-8.65616&outFormat=json&thumbMaps=false";
        String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2024 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2024 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.63436,\"lng\":-8.65616}},\"locations\":[{\"street\":\"Avenida da Universidade\",\"adminArea6\":\"Aveiro\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Aveiro\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-489\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.63437,\"lng\":-8.65625},\"displayLatLng\":{\"lat\":40.63437,\"lng\":-8.65625},\"mapUrl\":\"\"}]}]}";


        // setup mock to return the expected result

        assertNotNull(httpClient);

        String api_response = httpClient.doHttpGet(url);

        assertEquals(response, api_response);
        
        
        // will crash for now...need to set the resolver before using it
        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);

        //return
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        ///todo: implement test

        String url = "https://www.mapquestapi.com/geocoding/v1/reverse?key=fjpDDX0NAqWObtm30nW1w2njohV84QWs&location=-361.00000%2C-361.00000&outFormat=json&thumbMaps=false";

        String response = "{\"info\":{\"statuscode\":400,\"copyright\":{\"text\":\"© 2024 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2024 MapQuest, Inc.\"},\"messages\":[\"Illegal argument from request: Invalid LatLng specified.\"]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{},\"locations\":[]}]}";

        
        assertNotNull(httpClient);

        String api_response = httpClient.doHttpGet(url);

        assertEquals(response, api_response);


        Optional<Address> result = resolver.findAddressForLocation( -361,-361);
        // verify no valid result
        assertFalse( result.isPresent());
        
    }

}
