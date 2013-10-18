package com.sdl.selenium.conditions;

public abstract class SuccessCondition extends Condition {

    public SuccessCondition(){

    }

    public SuccessCondition(String message){
        super(message);
    }

    @Override
    public boolean isSuccess(){
        return true;
    }
}
