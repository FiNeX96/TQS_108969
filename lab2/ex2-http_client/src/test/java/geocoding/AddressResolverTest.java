package geocoding;

import connection.ISimpleHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import  org.mockito.Mock;
import  org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @InjectMocks
    AddressResolverService resolver; // subject under test

    @Mock 
    ISimpleHttpClient httpClient; // service to mock

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        //todo: implement test; remove Disabled annotation
        String url = "https://www.mapquestapi.com/geocoding/v1/reverse?key=fjpDDX0NAqWObtm30nW1w2njohV84QWs&location=40.63436%2C-8.65616&outFormat=json&thumbMaps=false";
        String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.633116,\"lng\":-8.658784}},\"locations\":[{\"street\":\"Avenida João Jacinto de Magalhães\",\"adminArea6\":\"Aveiro\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Aveiro\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-149\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.63312,\"lng\":-8.65873},\"displayLatLng\":{\"lat\":40.63312,\"lng\":-8.65873},\"mapUrl\":\"\"}]}]}";

        // setup mock to return the expected result
        Mockito.when(httpClient.doHttpGet(url)).thenReturn(
                response
        );

        // will crash for now...need to set the resolver before using it
        Optional<Address> result = resolver.findAddressForLocation(40.63436, -8.65616);

        //return
        Address expected = new Address( "Avenida João Jacinto de Magalhães", "Aveiro","3810-149", "");

        verify(httpClient,times(1)).doHttpGet(url);

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        ///todo: implement test

        String url = "https://www.mapquestapi.com/geocoding/v1/reverse?key=fjpDDX0NAqWObtm30nW1w2njohV84QWs&location=-361.00000%2C-361.00000&outFormat=json&thumbMaps=false";

        String response = "{\"info\":{\"statuscode\":400,\"copyright\":{\"text\":\"© 2024 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2024 MapQuest, Inc.\"},\"messages\":[\"Illegal argument from request: Invalid LatLng specified.\"]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{},\"locations\":[]}]}";

        
        Mockito.when(httpClient.doHttpGet(url)).thenReturn(
                response
        );

        Optional<Address> result = resolver.findAddressForLocation( -361,-361);

        verify(httpClient,times(1)).doHttpGet(url);
        // verify no valid result
        assertFalse( result.isPresent());

    }
}