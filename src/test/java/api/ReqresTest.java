package api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in";
    /*
    1. getting json of response
    2. creating pojo class(with help of online page jsonToPojo
    3. using restAssured to get responce
    4. then log it and extract().jsonPath().gteList(path: "",dataClassPojo)
    5. as the result we already validateed json structure
    6. and can manipulate with our data anywhay
     */
    /* Questions:
    1. how to work with nested json elements? nested objects, arrays, etc?

     */

    @Test
    public void checkAvatarAndIdTEst(){

        String url = URL + "/api/users?page=2";
        System.out.println(">>>"+url);
        List<UserData> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get(url)
                .then().log().all()
                .extract().jsonPath().getList("data",UserData.class);
        users.forEach(x-> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
//        users.forEach(x-> Assertions.assertTrue(x.getAvatar().contains(x.getAnanas()), "Ananas is not presented"));
        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in") ));
        List<String> avatars = users.stream().map(UserData::getAvatar).toList();
        List<String> ids = users.stream().map(x->x.getId().toString()).toList();

        for (int i = 0; i < avatars.size(); i++) {
            System.out.println(avatars.get(i));
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }
    @Test
    public void checkAvatarAndIdTestShort(){
        Specifications.installSpecification(Specifications.requestSpecification(URL),Specifications.responseSpecError400());
        String url = "/api/users?page=2";
        System.out.println(">>>"+url);
        List<UserData> users = given()
                .when()
                .get(url)
                .then().log().all()
                .extract().jsonPath().getList("data",UserData.class);
        users.forEach(x-> Assertions.assertTrue(x.getAvatar().contains(x.getId().toString())));
//        users.forEach(x-> Assertions.assertTrue(x.getAvatar().contains(x.getAnanas()), "Ananas is not presented"));
        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in") ));
        List<String> avatars = users.stream().map(UserData::getAvatar).toList();
        List<String> ids = users.stream().map(x->x.getId().toString()).toList();

        for (int i = 0; i < avatars.size(); i++) {
            System.out.println(avatars.get(i));
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successRegTest(){
        Specifications.installSpecification(Specifications.requestSpecification(URL),Specifications.responseSpecOk200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        RegisterData user = new RegisterData("eve.holt@reqres.in","pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);
        Assertions.assertEquals(id,successReg.getId());
        Assertions.assertEquals(token,successReg.getToken());
    }
}
