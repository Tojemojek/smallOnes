package pl.kostrowski.sudoku.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kostrowski.sudoku.model.Sudoku;
import pl.kostrowski.sudoku.service.MakeWork;

@Controller
public class OnlyController {

    @Autowired
    MakeWork makeWork;

    @RequestMapping(value = "/")
    public String display(Model model){

        Sudoku sudoku = makeWork.importData(null);

        model.addAttribute("possibleOptions",sudoku.getOutput());
        model.addAttribute("style",sudoku.getStyle());
        model.addAttribute("sudoku",sudoku);model.addAttribute("sudoku",sudoku);
        return "index";
    }

    @RequestMapping(value = "/array", method = RequestMethod.POST)
    public String displaySuggetion(Model model, @RequestParam(value = "rows[]", defaultValue = "1") String[] rows){

        Sudoku sudoku = makeWork.importData(rows);

        model.addAttribute("possibleOptions",sudoku.getOutput());
        model.addAttribute("style",sudoku.getStyle());
        model.addAttribute("sudoku",sudoku);
        return "index";
    }

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public String displaySuggetion(Model model, @RequestParam(value = "input", defaultValue = "1") String input){

        input = input.replaceAll("\n","").replaceAll("\r","");
        String[] rows = input.split(",");

        Sudoku sudoku = makeWork.importData(rows);

        model.addAttribute("possibleOptions",sudoku.getOutput());
        model.addAttribute("style",sudoku.getStyle());
        model.addAttribute("sudoku",sudoku);

        return "index";
    }

}
