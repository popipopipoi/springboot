package com.ohgiraffers.exceptionhandler;

public class MemberRegistException extends Exception { // Exception 를 상속받을때면 반드시 메세지를 정의해야한다.
    public MemberRegistException(String message) {
        super(message);
    }
}
