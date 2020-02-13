package springboot.Resource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springboot.utils.Region;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/region")
public class HelloController{

    private final String url = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";

    @RequestMapping(
            //здесь указываю тип запроса region и что запрос вернет результат в виде Json
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public Region region(@RequestParam(value = "city", defaultValue = "казань") String city){

        RestTemplate restTemplate=new RestTemplate();

        //хэдеры
        HttpHeaders headers=new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Token fbf8ceff8291c288ff37fa1b67f099b89cdcb7ea");

        //тело
        Map<String, Object> map = new HashMap<>();
        map.put("query", city);
        map.put("count", 10);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String>response =restTemplate.postForEntity(url, entity, String.class);
        String body = response.getBody();
        JsonObject bodyJson =  new JsonParser().parse(body).getAsJsonObject();
        JsonObject firstResult =  bodyJson.getAsJsonArray("suggestions").get(0).getAsJsonObject();


        return  new Region(firstResult.getAsJsonObject("data").get("city_kladr_id").getAsString());
    }
}