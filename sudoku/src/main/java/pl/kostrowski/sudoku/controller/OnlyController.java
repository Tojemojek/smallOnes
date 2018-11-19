package pl.kostrowski.sudoku.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kostrowski.sudoku.service.FindPossibilities;

@Controller
public class OnlyController {

    @Autowired
    FindPossibilities findPossibilities;

    @RequestMapping(value = "/")
    public String display(Model model,@RequestParam(value = "rows[]", defaultValue = "1") String[] rows){

        String[][] possibleOptions;
        String[][] style;

        if (rows.length != 81){
            possibleOptions = findPossibilities.createEmptyInput();
        } else {
            possibleOptions = findPossibilities.findPossibleOptions(rows);
        }

        style = findPossibilities.createStyleSheet();

        model.addAttribute("possibleOptions",possibleOptions);
        model.addAttribute("style",style);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String displaySuggetion(Model model, @RequestParam(value = "rows[]", defaultValue = "1") String[] rows){

        String[][] style;
        if (rows.length != 81){
            rows = new String[81];
        }

        String[][] possibleOptions = findPossibilities.findPossibleOptions(rows);

        style = findPossibilities.createStyleSheet();

        model.addAttribute("possibleOptions",possibleOptions);
        model.addAttribute("style",style);

        return "index";
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String reset(){

        findPossibilities.reset();
        return "redirect:index";
    }

}
