package com.jmd.staffsync_both;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {
     @GetMapping("/dummy")
     public String dummyEndpoint() {
         return "This is a dummy endpoint and here is reloaded code";
     }
}
