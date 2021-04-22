package skillbox.javapro11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skillbox.javapro11.service.TagService;

/**
 * Created by Artem on 21.04.2021.
 */

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("")
    public void getTag (){}

    @PutMapping("")
    public void createTag (){}

    @DeleteMapping("")
    public void deleteTag (){}
}
