package com.ohgiraffers.exceptionhandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {

    @GetMapping("/other-controller-null")
    public String nullPointerExceptionTest() {
        String str = null;
        System.out.println(str.charAt(0)); //의도적으로 NullPointerException 발생 시킴
        return "/";
    }

    @GetMapping("/other-controller-user")
    public String userExceptionTest() throws MemberRegistException {
        boolean check = true;
        if(check) throw new MemberRegistException("당신 같은 사람은 회원으로 받을 수 없습니다!");
        return "/";
    }

    // 의도적으로 오류를 발생시키는 코드
    @GetMapping("/other-controller-array")
    public String otherArrayExceptionTest() {
        double[] array = new double[0];
        System.out.println(array[0]);
        return "/";
    }
}
