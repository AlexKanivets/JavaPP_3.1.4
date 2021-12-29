package web.rest_template;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import web.rest_template.Model.User;
import java.util.*;

public class SpringRestClient {
    private static final String GET_USERS_URL = "http://91.241.64.178:7081/api/users";
    private static final String GET_USER_UPDATE_BY_ID_URL = "http://91.241.64.178:7081/api/users";
    private static final String GET_USER_DELETE_BY_ID_URL = "http://91.241.64.178:7081/api/users/{id}";
    private static final String GET_USERS_CREATE_URL = "http://91.241.64.178:7081/api/users";

    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringRestClient springRestClient = new SpringRestClient();
        springRestClient.getUsers();
    }

    private void getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity <String> ("parameters", headers);

        //GET
        ResponseEntity<String> result = restTemplate.exchange(GET_USERS_URL, HttpMethod.GET, entity,
                String.class);

        List<String> cookie = result.getHeaders().get("Set-Cookie");
        System.out.println(result);

        //POST
        headers.set("Cookie", String.join(";", cookie));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        User newUser = new User(3L, "James", "Brown", (byte) 50);
        HttpEntity<User> requestBody = new HttpEntity<>(newUser, headers);

        ResponseEntity<String> resultAddUser = restTemplate.exchange(GET_USERS_CREATE_URL, HttpMethod.POST,
                requestBody, String.class);
        System.out.println(resultAddUser.getBody());

        //PUT
        headers.set("Cookie", String.join(";", cookie));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 3);
        User updateUser = new User(3L, "Tomas", "Shelby", (byte) 50);
        HttpEntity<User> updateHttp = new HttpEntity<>(updateUser, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(GET_USER_UPDATE_BY_ID_URL, HttpMethod.PUT,
                updateHttp, String.class, params);
        System.out.println(responseEntity.getBody());

        //DELETE
        headers.set("Cookie", String.join(";", cookie));
        Map<String, Integer> param = new HashMap<String, Integer>();
        param.put("id", 3);
        HttpEntity<User> deleteHttp = new HttpEntity<>(updateUser, headers);
        ResponseEntity<String> deleteUserResult = restTemplate.exchange(GET_USER_DELETE_BY_ID_URL, HttpMethod.DELETE,
                deleteHttp, String.class, param);
        System.out.println(deleteUserResult.getBody());
    }
}
