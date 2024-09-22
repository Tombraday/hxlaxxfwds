package com.equestria.myaiservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AIController {


    @GetMapping("/getLablesFromImg")
    public String AIController() {
        String imgPath="img9.jpg";
        String result=AIUtil.recognize(imgPath);
        return result;
    }

}


