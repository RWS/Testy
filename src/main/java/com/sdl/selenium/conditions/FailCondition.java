package com.sdl.selenium.conditions;

import com.sdl.selenium.web.WebLocator;

public abstract class FailCondition extends Condition {

    public FailCondition(){

    }

    public FailCondition(String message){
        super(message);
    }

    @Override
    public boolean isSuccess(){
        return false;
    }

    public static FailCondition fail(){
        return new FailCondition() {
            @Override
            public boolean execute() {
                return true;
            }
        };
    }

    public static FailCondition fail(String message){
        return new FailCondition(message) {
            @Override
            public boolean execute() {
                return true;
            }
        };
    }

    public static FailCondition clickFail(WebLocator element){
        return new FailCondition("Could not click on " + element) {
            @Override
            public boolean execute() {
                return true;
            }
        };
    }
}
