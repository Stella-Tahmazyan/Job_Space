package am.jobspace.api.endPoint;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class ToDoEndpoint {

//  @GetMapping("/todos")
//  @ApiOperation(value = "get All users", response = ToDo.class, responseContainer = "list")
//  public ResponseEntity getAll() {
//    String url = "https://jsonplaceholder.typicode.com/todos";
//    RestTemplate restTemplate = new RestTemplate();
//
//    ResponseEntity<List<ToDo>> response = restTemplate.exchange(
//      url,
//      HttpMethod.GET,
//      null,
//      new ParameterizedTypeReference<List<ToDo>>() {
//      });
//
//    List<ToDo> toDos = response.getBody();
//    return ResponseEntity.ok(toDos);
//  }


}
