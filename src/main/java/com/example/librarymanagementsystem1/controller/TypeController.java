package com.example.librarymanagementsystem1.controller;

import com.example.librarymanagementsystem1.model.Type;
import com.example.librarymanagementsystem1.service.interfaces.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller

public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/typePage")
    public  String homePage(@ModelAttribute("type") Type type, Model model){
        return findPageited(1,"typeName", "asc", model);

    }
    @PostMapping("/typeSave")
    public String saveType(@ModelAttribute("type") Type type, Model model){
        typeService.saveType(type);
        return "redirect:/typePage";

    }
    @GetMapping("/typeUpdate/{id}")

    public String updateType(@PathVariable(value = "id") UUID id, Model model) {
        List<Type> typeList = typeService.getAllType();
        model.addAttribute("typeList", typeList);
        Type type = typeService.getTypeById(id);
        model.addAttribute("type", type);

        return "type";
    }
    @GetMapping("/typeDelete/{id}")
    public String deleteType(@PathVariable(value = "id")UUID id, Model model){
        this.typeService.deleteTypeById(id);
        return "redirect:/typePage";
    }
    @GetMapping("/pageType/{pageNo}")
    public String findPageited(@PathVariable(value = "pageNo")int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        int pageSize = 5;
        Page<Type> page = typeService.findPaginated(pageNo, pageSize,sortField, sortDir);
        List<Type> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);

        List<Type> typeList = typeService.getAllType();
        model.addAttribute("typeList", typeList);
        Type type= new Type();
        model.addAttribute("type", type);


        return"type";

    }
    @PostMapping("/pageSearchType/{pageNo}")
    public String search(@PathVariable(value = "pageNo")int pageNo,
                               @RequestParam("name") String name,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        int pageSize = 5;
        Page<Type> page = typeService.search(name,pageNo, pageSize,sortField, sortDir);
        List<Type> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);

        List<Type> typeList = typeService.getAllType();
        model.addAttribute("typeList", typeList);
        Type type= new Type();
        model.addAttribute("type", type);


        return"type";

    }
}
