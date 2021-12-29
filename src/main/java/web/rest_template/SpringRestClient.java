package web.rest_template;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import web.rest_template.Model.User;
import java.util.*;

public class SpringRestClient {
    private static final String GET_USERS_URL = "http://91.241.64.178:7081/api/users";
    private static final String GET_USER_DELETE_BY_ID_URL = "http://91.241.64.178:7081/api/users/3";

    private static RestTemplate restTemplate = new RestTemplate();
    String resultString = "";

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

            System.out.println(result);

            List<String> cookie = result.getHeaders().get("Set-Cookie");
            headers.set("Content-Type", "application/json");
            headers.set("Cookie", String.join(";", cookie));

            //POST
            User newUser = new User(3L, "James", "Brown", (byte) 50);
            HttpEntity<User> requestBodyPost = new HttpEntity<>(newUser, headers);
            ResponseEntity<String> resultAddUser = restTemplate.exchange(GET_USERS_URL, HttpMethod.POST,
                    requestBodyPost, String.class);
            System.out.println(resultAddUser.getBody());
            resultString += resultAddUser.getBody();


            //PUT
            User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 50);
            HttpEntity<User> requestBodyPut = new HttpEntity<>(updatedUser, headers);
            ResponseEntity<String> userResponseEntityPut = restTemplate.exchange(GET_USERS_URL, HttpMethod.PUT,
                    requestBodyPut, String.class);

            resultString += userResponseEntityPut.getBody();

            //DELETE
            HttpEntity<Long> requestBodyDelete = new HttpEntity<>(headers);
            ResponseEntity<String> userResponseEntityDelete = restTemplate.exchange(GET_USER_DELETE_BY_ID_URL,
                    HttpMethod.DELETE, requestBodyDelete, String.class);
            resultString += userResponseEntityDelete.getBody();

            System.out.println(resultString);
        }
}
