package com.ohgiraffers.handlermethod;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.util.Map;

/* 핸들러 메소드에 파라미터로 특정 몇 가지 타입을 선언하게 되면 핸들러 메소드 호출 시 인자로 값을 전달해준다. */
@Controller
@RequestMapping("/first/*")
@SessionAttributes("id")
public class FirstController {

    /* 컨트롤러 핸들러 메서드의 반환 값을 void로 설정하면 요청 주소가 곧 view의 이름이 된다.
    * => /first/regist 요청이 들어오면 /first/regist 뷰를 응답한다. */
    @GetMapping("/regist")
    public void regist() {}

    /* 1. WebRequest로 요청 파라미터 전달 받기
    * 파라미터 선언부에 WebRequest 타입을 선언하면 해당 메소드 호출 시 인자로 값을 전달해준다.
    * HttpServletRequest/Response, ServletRequest/Respone도 핸들러 메소드 매개변수로 사용 가능하다.
    * 단, WebRequest가 Servlet에 종속적이지 않으므로 Spring 기반의 프로젝트에서 더 자주 사용 된다.*/
    @PostMapping("/regist")
    public String rigistMenu(Model model, WebRequest webRequest) {

        /* webRequest 객체의 getParameter 등의 메서드를 통해 클라이언트로부터 전달 된 파라미터를 가져올 수 있다. */
        String name = webRequest.getParameter("name");
        int price = Integer.parseInt(webRequest.getParameter("price"));
        int categoryCode = Integer.parseInt(webRequest.getParameter("categoryCode"));

        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에" + price + "원으로 등록 하였습니다.";
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @GetMapping("/modify")
    public void modify() {}

    /* 2. @RequestParam으로 요청 파라미터 전달 받기
    * 요청 파라미터를 매핑하여 호출 시 값을 넣어주는 어노테이션으로 매개변수 앞에 작성한다.
    * form의 name 속성 값과 매개변수의 이름이 다른 경우 @RequestParam("name")을 설정하면 된다.
    * 또한 어노테이션은 생략 가능하지만, 명시적으로 작성하는 것이 의미 파악에 좋다.
    *
    * 전달하는 name 속성과 일치하는 것이 없는 경우 400 에러가 발생하게 되는데 이는 required 속성의 기본 값이 true이기 때문이다.
    * required 속성을 false로 변경하면 해당 값이 존재하지 않아도 null로 처리 된다.
    *
    * 값이 입력 되지 않게 되면 "" 빈 문자열이 넘어오게 되는데 이 때 parsing 관련 에러가 발생할 수 있다.
    * defaultValue 속성을 이용하게 되면 기본 값 설정이 가능하다.
    * */

    @PostMapping("modify")
    public String modifyMenuPrice(Model model, @RequestParam(required = false) String modifyName,
                                  @RequestParam(defaultValue = "0") int modifyPrice) {

        String message = modifyName + "메뉴의 가격을 " + modifyPrice + "원으로 변경하였습니다.";
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    /* 파라미터가 여러 개인 경우 Map으로 한 번에 처리할 수 있다.
    * 이 때 맵의 키는 name 속성 값이 된다. */
    @PostMapping("/modifyAll")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameters) {

        String modifyName = parameters.get("modifyName2");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice2"));

        String message = "메뉴의 이름을 " + modifyName + "(으)로, 가격은 " + modifyPrice + "원으로 변경하였습니다.";
        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    @GetMapping("/search")
    public void search() {}

    /* 3. @ModelAttribute를 이용하는 방법
    * DTO 같은 모델을 커맨드 객체로 전달 받는 어노테이션이다.
    * 또한 해당 인스턴스를 model에 담는 작업도 자동으로 일어난다.
    * @ModelAttribute("모델에 담을 key")을 지정할 수 있으며 지정하지 않으면 타입의 앞글자를 소문자로 한 네이밍 규치를 따른다.
    * 해당 어노테이션은 생략이 가능하지만 명시적으로 작성하는 것이 좋다.
    * */
    @PostMapping("/search")
    public String searchMenu(@ModelAttribute("menu") MenuDTO menu) {

        System.out.println("menu : " + menu);

        return "first/searchResult";
    }

    @GetMapping("/login")
    public void login() {}

    /* 4-1. HttpSession 사용하기 */
    @PostMapping("/login1")
    public String sessionTest1(HttpSession session, @RequestParam String id) {

        session.setAttribute("id", id);

        return "first/loginResult";
    }

    @GetMapping("/logout1")
    public String logoutTest1(HttpSession session) {

        session.invalidate(); // 세션 객체 만료

        return "first/loginResult";
    }

    /* 4-2. @SessionAttributes를 이용하기
    * HttpSession은 Servlet에 종속적이므로 Spring에서 제공하는 기능으로 변경해본다.
    * 클래스 레벨에 @SessionAttributes 어노테이션을 이용하여 세션에 값을 담을 key 값을 설정해 두면
    * Model 영역에 해당 key로 값이 추가 되는 경우에 session에 자동으로 등록한다. */

    @PostMapping("/login2")
    public String sessionTest2(Model model, @RequestParam String id) {

        model.addAttribute("id",id);

        return "first/loginResult";
    }

    /* @SessionAttributes로 등록 된 값은 SessionStatus(session의 상태를 관리하는 객체)의 setComplete() 메소드를 호출해야
    * 사용이 만료 된다.*/
    @GetMapping("/logout2")
    public String logoutTest2(SessionStatus sessionStatus) {

        sessionStatus.setComplete();

        return "first/loginResult";
    }

    @GetMapping("/body")
    public void body(){}

    /* 5. @RequestBody를 이용하는 방법
    * 해당 어노테이션은 HTTP 메세지의 본문 자체를 모델로 변환시켜주는 어노테이션이다.
    * String 타입으로 받을 경우 쿼리스트링 형태의 문자열을 확인할 수 있다.
    * JSON으로 전달하는 경우 Jackson converter로 자동 파싱하여 사용할 수 있다.
    * (주로 REST API 작성 시 사용, 일반적인 form 전송에서는 사용하지 않음)
    *
    * 추가적으로 헤더에 대한 정보도 @RequestHeader 어노테이션을 이용해서 가져올 수 있다.
    * @CookieValue를 이용해 쿠키 정보도 쉽게 불러올 수 있다.
    * */

    @PostMapping("/body")
    public void bodyTest(@RequestBody String body,
                         @RequestHeader("content-type") String contentType,
                         @CookieValue(value = "JSESSIONID", required = false) String sessionId){

        System.out.println("body : " + body);
        System.out.println("contentType : " + contentType);
        System.out.println("sessionId : " + sessionId);
    }

}
