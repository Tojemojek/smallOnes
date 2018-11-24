package pl.kostrowski.sudoku.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kostrowski.sudoku.model.Sudoku;
import pl.kostrowski.sudoku.service.MakeWork;
import pl.kostrowski.sudoku.service.Validate;

@Controller
public class OnlyController {

    @Autowired
    MakeWork makeWork;

    @Autowired
    Validate validate;

    @RequestMapping(value = "/")
    public String display(Model model){

        Sudoku sudoku = makeWork.importData(null);

        model.addAttribute("possibleOptions",sudoku.getOutput());
        model.addAttribute("style",sudoku.getStyle());
        model.addAttribute("sudoku",sudoku);model.addAttribute("sudoku",sudoku);
        return "index";
    }

    @RequestMapping(value = "/array", method = RequestMethod.POST)
    public String displaySuggestions(Model model, @RequestParam(value = "rows[]", defaultValue = "1") String[] rows){

        Sudoku sudoku = makeWork.importData(rows);

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String row : rows) {
            i++;
            sb.append(row).append(",");
            if (i % 9 == 0){
                sb.append("\n");
            }
        }

        model.addAttribute("possibleOptions",sudoku.getOutput());
        model.addAttribute("style",sudoku.getStyle());
        model.addAttribute("sudoku",sudoku);
        model.addAttribute("fromForm",sb.toString());

        return "index";
    }

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public String displaySuggestions(Model model, @RequestParam(value = "input", defaultValue = "1") String input){


        input = input.replaceAll("\n","").replaceAll("\r","").replaceAll(" ","");
        String[] rows = input.split(",");
        Sudoku sudoku = makeWork.importData(rows);

        model.addAttribute("possibleOptions",sudoku.getOutput());
        model.addAttribute("style",sudoku.getStyle());
        model.addAttribute("sudoku",sudoku);
        model.addAttribute("fromForm",input);

        return "index";
    }

    @RequestMapping(value = "/verifyString", method = RequestMethod.POST)
    public String checkSolutionCorrect(Model model, @RequestParam(value = "input", defaultValue = "1") String input){

        input = input.replaceAll("\n","").replaceAll("\r","").replaceAll(" ","");
        String[] rows = input.split(",");

        String message = validate.importData(rows);

        Sudoku sudoku = new Sudoku();

        model.addAttribute("sudoku",sudoku);
        model.addAttribute("possibleOptions",sudoku.getOutput());
        model.addAttribute("style",sudoku.getStyle());
        model.addAttribute("fromForm",input);
        model.addAttribute("message",message);

        return "index";
    }

}
