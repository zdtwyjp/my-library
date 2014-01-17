package com.sys.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class ControllerSupport {
	private ModelAndView modelAndView = new ModelAndView();

	public ControllerSupport() {}

	@RequestMapping(value = "/go_toAdd")
	public ModelAndView go_toAdd() {
		toAddExcute();
		modelAndView.addObject("mode", "ADD");
		return modelAndView;
	}

	public abstract void toAddExcute();

	@RequestMapping(value = "/go_toEdit")
	public ModelAndView go_toEdit(String[] selectedIds) {
		toEditExcute(selectedIds);
		modelAndView.addObject("mode", "EDIT");
		return modelAndView;
	}

	public abstract void toEditExcute(String[] selectedIds);

	@RequestMapping(value = "/go_view")
	public ModelAndView view(String[] selectedIds) {
		viewExcute(selectedIds);
		modelAndView.addObject("mode", "VIEW");
		return modelAndView;
	}

	public abstract void viewExcute(String[] selectedIds);

	public ModelAndView getModelAndView() {
		return modelAndView;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}
}
