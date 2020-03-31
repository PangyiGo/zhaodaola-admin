package com.sise.zhaodaola.core.setting;

import com.sise.zhaodaola.business.entity.Contact;
import com.sise.zhaodaola.business.service.ContactService;
import com.sise.zhaodaola.tool.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User: PangYi
 * Date: 2020-03-23
 * Time: 10:32
 * Description:
 */
@RestController
@RequestMapping("/api/contact")
@Slf4j
public class ContactController {

    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/{type}")
    public ResponseEntity<Object> getOne(@PathVariable("type") String type) {
        Contact contact = new Contact();
        if ("contact".equals(type)) {
            contact = contactService.getContact();
        }
        if ("about".equals(type)) {
            contact = contactService.getAbout();
        }
        return ResponseEntity.ok(contact);
    }

    @Log("新增或修改联系我们")
    @PostMapping("/update")
    public ResponseEntity<Object> updateContact(@RequestBody Contact contact) {
        contactService.updateContact(contact);
        return new ResponseEntity<>("新增或修改成功", HttpStatus.OK);
    }
}
