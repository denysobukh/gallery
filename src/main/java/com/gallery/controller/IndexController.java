package com.gallery.controller;

import com.gallery.GalleryApplicationException;
import com.gallery.model.MenuItem;
import com.gallery.model.directory.DirectoryWalkerI;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Controller
public class IndexController {

    @Autowired
    private Logger logger;

    @Autowired
    private DirectoryWalkerI directoryWalker;

    @Autowired
    @Qualifier("currentDir")
    private Path currentDir;


    //!FIXME session is not working


    @GetMapping(value = {"/browse", ""})
    public String dir(Model model, HttpServletRequest request, @RequestParam Optional<String> d) throws GalleryApplicationException {
        Path requestedDir;
        if (d.isPresent()) {
            requestedDir = Paths.get(d.get());
            if (!directoryWalker.isWithinRootDir(requestedDir)) throw new GalleryApplicationException("Wrong path");
            currentDir = directoryWalker.getRoot().resolve(requestedDir).normalize();
        } else
            requestedDir = currentDir;

        logger.debug("requestedDir=" + requestedDir);
        logger.trace("currentDir=" + currentDir);
        logger.trace("root = " + directoryWalker);

        List<MenuItem> paths =
                Stream.concat(
                        Stream.of(directoryWalker.getParent(currentDir))
                                .filter(p -> p != null)
                                .map(p -> new MenuItem("..", p.toString())),
                        directoryWalker.listDirs(currentDir).stream()
                                .sorted()
                                .map(p -> new MenuItem(p.getFileName().toString(), p.toString()))
                ).collect(Collectors.toCollection(ArrayList::new));

        model.addAttribute("rootDir", directoryWalker.getRoot());
        model.addAttribute("paths", paths);
        model.addAttribute("name", request.getSession().getId());
        model.addAttribute("images", directoryWalker.listFiles(currentDir));
        return "index";
    }

    @GetMapping(value = "/error")
    public String error(ServletRequest request, Model model) {
        Object error = request.getAttribute(ERROR_STATUS_CODE);
        model.addAttribute("error", error);
        return "error_generic";
    }

    @RequestMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/test")
    public String test(Model model) {
        Path root = directoryWalker.getRoot();
        Path c = Paths.get("/Users/denysobukhov/Nextcloud/Electronics");
        System.out.println(root);
        System.out.println(c);
        System.out.println(root.relativize(c));


        return "index";
    }

    /*
    @ExceptionHandler(GalleryApplicationException.class)
    public ModelAndView exceptionHandler(GalleryApplicationException e) {
        ModelAndView mv = new ModelAndView("errors/generic");
        mv.addObject("exception", e);
        return mv;
    }
*/

}
