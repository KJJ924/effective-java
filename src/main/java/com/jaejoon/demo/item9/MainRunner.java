package com.jaejoon.demo.item9;

public class MainRunner {
    public static void main(String[] args) {
       /* // 기존 문제점 한눈에봐도 코드가 너무 이해하기 힘듬
        MyResource myResource = null;
        try{
            myResource= new MyResource();
            myResource.run(); // 해당 부분에서 첫번째 오류가 발생하지만..?
            MyResource resource = null;
            try {
                resource = new MyResource();
            }finally {
                if(resource !=null){
                    resource.close();
                }
            }
        }finally {
            if(myResource !=null) {
                myResource.close(); //해당 부분에서 오류가 발생함으로 첫번째 오류를 스텍트레이스에 찍어주지 않음.
            }
        }
*/
        // 위에 있는 코드랑 비교하면 명확하다. 또한 오류를 범할수 있는 확률이 현저하게 내려간다.
        try(MyResource  myResource1 = new MyResource();
            MyResource myResource2 =new MyResource()){
            // try-with-resource 를 사용 하면 앞서 살펴본 문제를 해결할 수 있다.
            myResource1.run();
            myResource2.run();
        }
    }
}
