package com.example.librarymanagementsystem1.controller;

import com.example.librarymanagementsystem1.model.UserInfo;
import com.example.librarymanagementsystem1.service.interfaces.EmailService;
import com.example.librarymanagementsystem1.service.interfaces.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")

public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/userInfoPage")
    public  String homePage(@ModelAttribute("userInfo") UserInfo userInfo, Model model){
        return findPageited(1,"name", "asc", model);
//        String type = "text";
//        model.addAttribute("type",type);
//        List<UserInfo> userInfoList = userInfoService.getAllUserInfo();
//        model.addAttribute("userInfoList", userInfoList);
//
//        return "userInfo";

    }
    @PostMapping("/userInfoSave")
    public String saveUserInfo(@ModelAttribute("userInfo") UserInfo userInfo, Model model){
        userInfoService.saveUserInfo(userInfo);
        return "redirect:/userInfoPage";

    }
    @GetMapping("/userInfoUpdate/{id}")

    public String updateUserInfo(@PathVariable(value = "id") UUID id, Model model) {
        String type = "hidden";
        model.addAttribute("type",type);
        List<UserInfo> userInfoList = userInfoService.getAllUserInfo();
        model.addAttribute("userInfoList", userInfoList);
        UserInfo userInfo = userInfoService.getUserInfoById(id);
        model.addAttribute("userInfo", userInfo);

        return "userInfo";
    }
    @GetMapping("/userInfoDelete/{id}")
    public String deleteUserInfo(@PathVariable(value = "id")UUID id, Model model){
        this.userInfoService.deleteUserInfoById(id);
        return "redirect:/admin/userInfoPage";
    }
    @GetMapping("/pageUserInfo/{pageNo}")
    public String findPageited(@PathVariable(value = "pageNo")int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        int pageSize = 5;
        Page<UserInfo> page = userInfoService.findPaginated(pageNo, pageSize,sortField, sortDir);
        List<UserInfo> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);
        UserInfo userInfo =new UserInfo();
        model.addAttribute("userInfo", userInfo);
        return"userInfo";

    }
    @PostMapping("/pageSearchUserInfo/{pageNo}")
    public String search(@PathVariable(value = "pageNo")int pageNo,
                         @RequestParam("name") String name,
                         @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        int pageSize = 5;
        Page<UserInfo> page = userInfoService.search(name,pageNo, pageSize,sortField, sortDir);
        List<UserInfo> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);
        UserInfo userInfo =new UserInfo();
        model.addAttribute("userInfo", userInfo);
        return"userInfo";

    }
//    @PostMapping("/forgot")
//    public String recoverPassword(@RequestParam(value = "email") String email, Model model){
//        Random random = new Random();
//        //Optional<UserInfo> userInfo = userInfoService.getEmail(email);
//        int recoverSecret = random.nextInt(1000,9999);
//        String subject = "Account created";
//        String body = "Please enter the given " +recoverSecret+
//                " to Recovery the password";
//        emailService.sendEmail(email,subject,body);
//        return "recover";
//    }
}
