package com.ohgiraffers.crud.menu.controller;

import com.ohgiraffers.crud.menu.model.dto.CategoryDTO;
import com.ohgiraffers.crud.menu.model.dto.MenuDTO;
import com.ohgiraffers.crud.menu.model.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/menu")
public class MenuController {

    /* Logger 객체 생성 */
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    private final MenuService menuService;

    private final MessageSource messageSource;

    public MenuController(MenuService menuService, MessageSource messageSource) {
        this.menuService = menuService;
        this.messageSource = messageSource;
    }
    @GetMapping("/list")
    public String findMenuList(Model model){

        List<MenuDTO> menuList = menuService.findAllMenu();

        model.addAttribute("menuList", menuList);

        return "menu/list";
    }

    @GetMapping("/regist")
    public void registPage(){}

    @GetMapping("/category")
    public @ResponseBody List<CategoryDTO> findCategory() {

        return menuService.findAllCategory();
    }

    @PostMapping("/regist")
    public String registMenu(@ModelAttribute MenuDTO newMenu, Locale locale, RedirectAttributes rttr) {

        logger.info("newMenu : {}",newMenu);

        /* 로그 레벨 테스트 */
        logger.trace("locale : {}", locale);
        logger.debug("locale : {}", locale);
        logger.info("locale : {}", locale);
        logger.warn("locale : {}", locale);
        logger.error("locale : {}", locale);

        menuService.registNewMenu(newMenu);

        rttr.addFlashAttribute("successMessage", messageSource.getMessage("registMenu", null, locale));

        return "redirect:/menu/list";
    }
}
