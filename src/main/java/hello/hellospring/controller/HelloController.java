package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // html 태그에 있는 body 가 아니다. - http 에서 header 와 body 가 있는데 여기서 body 부에 데이터를 직접 널어주겠다는 뜻
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
        // 템플릿엔진 과의 차이점? - 뷰 같은게 없다 -> 데이터가 그대로 웹 브라우저로 내려간다.
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 문자열이 아닌 객체 데이터를 넘겨준다.
    }

    // api 를 통해 데이터를 전달해주는 경우를 학습하기 위해 내부 클래스(객체) 작성
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
