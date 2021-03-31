package com.jaejoon.demo.item74;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> {@code NullPointerException} 지정한 요소에 null 이 들어오는 경우 발생합니다.</p>
 */
public class MainRunner {

    /**
     * @throws SQLException SQL 이 잘못된 경우
     * @throws ClassNotFoundException 지정한 경로에 클래스파일이 존재하지 않는경우.
     */
    public void examMethod() throws SQLException,ClassNotFoundException{
        Class.forName("asd");
        // TODO 무언가 했음.
    }

    public void examMethod2() throws IOException{
       //TODO 일하는 중
    }

}
