package com.tallerwebi.presentacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.DTOs.BrainRotEmojis;
import com.tallerwebi.dominio.ServicioBrainRotEmojis;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorBrainRotEmojis {

    private final ServicioBrainRotEmojis servicioBrainRotEmojis;
    private final ObjectMapper mapper = new ObjectMapper();

    public ControladorBrainRotEmojis(ServicioBrainRotEmojis  servicioBrainRotEmojis) {
        this.servicioBrainRotEmojis = servicioBrainRotEmojis;
    }

    @RequestMapping(value = "/brainRotEmojis", method = RequestMethod.GET)
    public ModelAndView index() throws JsonProcessingException {
        List<BrainRotEmojis> list = servicioBrainRotEmojis.listAll();
        ModelAndView mv = new ModelAndView("index-brainrot-emojis");
        mv.addObject("brainrotsJson", mapper.writeValueAsString(list));
        return mv;
        /*el mapper de java transforma objetos a formato jason, serializa el objeto list*/
    }

    @RequestMapping(value = "/visor", method = RequestMethod.GET)
    public ModelAndView visor() throws JsonProcessingException {
        List<BrainRotEmojis> list = servicioBrainRotEmojis.listAll();
        ModelAndView mv = new ModelAndView("visor-brainrot-emojis");
        mv.addObject("brainrotsJson", mapper.writeValueAsString(list));
        return mv;
        /*el mapper de java transforma objetos a formato ajson, serializa el objeto list*/
    }

    @RequestMapping(value = "/api/brainrots", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<BrainRotEmojis> apiList() {
        return servicioBrainRotEmojis.listAll();
    }

}
